package et.addis.home_cakes.orders.dao.impl;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dao.PastryDAO;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.model.*;
import et.addis.home_cakes.util.ErrorConstants;
import et.addis.home_cakes.util.MessageConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.List;

/**
 * Created by Fassil on 27/01/22.
 */
@Repository
public class PastryDAOImpl implements PastryDAO {

    private static final Logger LOG = LogManager.getLogger(PastryDAOImpl.class);

    @PersistenceContext(name = "addis.homecakes.persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public Pastry findPastryByName(String name) {
        String pfn = "[PastryDAOImpl::findPastryByName]";
        LOG.info(pfn + " BEGIN");
        try {
            Query query = entityManager.createQuery("select p from Pastry p where lower(p.name) like lower(:name)").setParameter("name", name);
            Pastry pastry = (Pastry) query.getSingleResult();
            LOG.info(pfn + " END");
            return pastry;
        } catch (Exception e){
            LOG.error(pfn + " "+ e.getMessage());
            LOG.info(pfn + " END");
            throw e;
        }
    }

    @Override
    @Transactional
    public ExecResult savePastry(Pastry pastry,List<Branch> branches, Double latitude, Double longitude, List<BusinessHours> businessHours) {
        String pfn = "[PastryDAOImpl::savePastry]";
        LOG.info(pfn + " BEGIN");
        try {
            //STEP 1. Persist Pastry
            /* Check if pastry has branches */
            if(!pastry.getHasBranch()) {
                // No branches => spatial quries => insert with native query
                Double Lon = BigDecimal.valueOf(latitude).setScale(3, RoundingMode.HALF_UP).doubleValue();
                Double Lat = BigDecimal.valueOf(longitude).setScale(3, RoundingMode.HALF_UP).doubleValue();
                StringBuilder sql = new StringBuilder();
                String queryP = "INSERT INTO pastry (pastry_id, name, tin_number, bank_id, bank_acct_no, logo_image, description, phone_no," +
                        "email, website, min_price, max_price, has_branch, bank_acct_owner, sub_city_id, woreda, kebele," +
                        "image1, image2, image3, location) VALUES (";
                sql.append(queryP).append("(SELECT nextval('pastry_pastry_id_seq'))").append(",'")
                                .append(pastry.getName()).append("',")
                                .append(pastry.getTinNumber()).append(",").append(pastry.getBank().getBankId()).append(",")
                                .append(pastry.getBankAcctNo()).append(",'").append(pastry.getLogoImage()).append("','")
                                .append(pastry.getDescription()).append("','").append(pastry.getPhoneNo()).append("','")
                                .append(pastry.getEmail()).append("','").append(pastry.getWebsite()).append("',")
                                .append(pastry.getMinPrice()).append(",").append(pastry.getMaxPrice()).append(",").append(pastry.getHasBranch()).append(",'")
                                .append(pastry.getBankAcctOwner()).append("',").append(pastry.getSubCity().getId()).append(",")
                                .append(pastry.getWoreda()).append(",").append(pastry.getKebele()).append(",'")
                                .append(pastry.getImage1()).append("','").append(pastry.getImage2()).append("','")
                                .append(pastry.getImage3()).append("',")
                                .append("ST_GeomFromText('POINT(").append(Lat).append(" ").append(Lon).append(")'").append(", 4326)")
                                .append(")");
                LOG.info(pfn + " Query pastry: " + sql);
                entityManager.createNativeQuery(sql.toString()).executeUpdate();
                // GET inserted pastry_id
                Query q = entityManager.createNativeQuery("SELECT MAX(pastry_id) from pastry");
                Integer pastryId = (Integer) q.getSingleResult();
                pastry.setPastryId(pastryId);
                LOG.info(pfn + " NO BRANCHES: Inserted Pastry ID : " + pastryId);
            } else {
                //persist with Hibernate
                entityManager.persist(pastry);
                LOG.info(pfn + " HAS BRANCHES: Inserted Pastry ID : " + pastry.getPastryId());
            }

            //STEP 2. Persist Branch with native query (require spatial query)
            for(Branch b: branches) {
                Double Lon = BigDecimal.valueOf(b.getLongitude()).setScale(3, RoundingMode.HALF_UP).doubleValue();
                Double Lat = BigDecimal.valueOf(b.getLatitude()).setScale(3, RoundingMode.HALF_UP).doubleValue();
                StringBuilder sql1 = new StringBuilder();
                String queryB = "INSERT INTO branch (branch_id, branch_name, location, phone_no) VALUES ( ";
                sql1.append(queryB).append("(SELECT nextval('branch_branch_id_seq'))").append(", '").append(b.getBranchName()).append("',")
                       .append("ST_GeomFromText('POINT(").append(Lat).append(" ").append(Lon).append(")'").append(", 4326)")
                               .append(",'").append(b.getPhoneNo()).append("')");

                LOG.info(pfn + " Query branch : " + sql1);
                entityManager.createNativeQuery(sql1.toString()).executeUpdate();
                // GET inserted branch_id
                Query q = entityManager.createNativeQuery("SELECT MAX(branch_id) from branch");
                Integer branchId = (Integer) q.getSingleResult();
                LOG.info( pfn + " Inserted branch ID : " +branchId );

                //STEP 3. Persist PastryBranches
                String queryPB = "INSERT INTO pastry_branches(pastry_id, branch_id) VALUES (?, ?)";
                entityManager.createNativeQuery(queryPB).setParameter(1, pastry.getPastryId()).setParameter(2, branchId).executeUpdate();
            }
            // STEP 4. Persist Pastry business hours
            for (BusinessHours businessHour : businessHours) {
                businessHour.setPastryId(pastry.getPastryId());
                entityManager.persist(businessHour);
                LOG.info(pfn + " inserted Business hour ID: " + businessHour.getId());
            }
            ExecResult res = new ExecResult();
            res.setEsito(true);
            res.setMsg(MessageConstants.MSG_SAVE_SUCCESS);
            LOG.info(pfn + " END");
            return res;
        } catch (Exception e) {
            LOG.error(pfn + " "+ e.getMessage());
           /* ExecResult res = new ExecResult();
            res.setEsito(false);
            res.setMsg(ErrorConstants.ERR_SAVE + " "+ e.getMessage());*/
            LOG.info(pfn + " END");
            throw e;
        }
    }

    @Override
    @Transactional
    public List<SubCity> findAllSubCities() {
        String pfn = "[PastryDAOImpl::findPastryByName]";
        LOG.info(pfn + " BEGIN");
        try {
            Query query = entityManager.createQuery("select sc from SubCity sc");
            List<SubCity> subCities = query.getResultList();
            LOG.info(pfn + " END");
            return subCities;
        } catch (Exception e){
            LOG.error(pfn + " "+ e.getMessage());
            LOG.info(pfn + " END");
            throw e;
        }
    }
}
