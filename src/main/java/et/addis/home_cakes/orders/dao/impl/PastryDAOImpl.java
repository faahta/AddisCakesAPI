package et.addis.home_cakes.orders.dao.impl;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dao.PastryDAO;
import et.addis.home_cakes.orders.model.Branch;
import et.addis.home_cakes.orders.model.Pastry;
import et.addis.home_cakes.orders.model.PastryBranches;
import et.addis.home_cakes.orders.model.SubCity;
import et.addis.home_cakes.util.ErrorConstants;
import et.addis.home_cakes.util.MessageConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public ExecResult savePastry(Pastry pastry, List<Branch> branches) {
        String pfn = "[PastryDAOImpl::savePastry]";
        LOG.info(pfn + " BEGIN");
        try {
            //STEP 1. Persist Pastry
            entityManager.persist(pastry);
            for(Branch b: branches) {
                //STEP 2. Persist Branch (require spatial query)
                Double Lon = BigDecimal.valueOf(b.getLongitude()).setScale(3, RoundingMode.HALF_UP).doubleValue();
                Double Lat = BigDecimal.valueOf(b.getLatitude()).setScale(3, RoundingMode.HALF_UP).doubleValue();
                //String queryId = "(select max(branch_id) + 1 from branch)";
                String query = "INSERT INTO branch (branch_id, branch_name, location, phone_no) " +
                        "VALUES((select max(branch_id) + 1 from branch), '" + b.getBranchName() + "', ST_GeomFromText('POINT("+Lat +" " + Lon + ")', 4326) ,'"  + b.getPhoneNo() + "' ) ";
                LOG.info(pfn + " Query : " + query.toString());
                entityManager.createNativeQuery(query).executeUpdate();
               /* entityManager.createNativeQuery("INSERT INTO branch ( branch_name, location, phone_no) VALUES( ?, ST_GeomFromText('POINT('"+b.getLatitude()+"' '"+b.getLongitude()+"')', 4326), ? ) ")
                        .setParameter(1, b.getBranchName()).setParameter(2, b.getPhoneNo()).executeUpdate();*/

                //STEP 3. Persist PastryBranches (requires spatial query)
                PastryBranches pastryBranches = new PastryBranches(pastry, b);
                entityManager.persist(pastryBranches);
            }

            ExecResult res = new ExecResult();
            res.setEsito(true);
            res.setMsg(MessageConstants.MSG_SAVE_SUCCESS);
            LOG.info(pfn + " END");
            return res;
        } catch (Exception e){
            LOG.error(pfn + " "+ e.getMessage());
            ExecResult res = new ExecResult();
            res.setEsito(false);
            res.setMsg(ErrorConstants.ERR_SAVE + " "+ e.getMessage());
            LOG.info(pfn + " END");
            return res;
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
