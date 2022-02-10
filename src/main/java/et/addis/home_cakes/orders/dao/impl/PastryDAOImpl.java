package et.addis.home_cakes.orders.dao.impl;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dao.PastryDAO;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.model.Pastry;
import et.addis.home_cakes.orders.model.SubCity;
import et.addis.home_cakes.util.ErrorConstants;
import et.addis.home_cakes.util.MessageConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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
    public ExecResult savePastry(Pastry pastry) {
        String pfn = "[PastryDAOImpl::savePastry]";
        LOG.info(pfn + " BEGIN");
        try {
            entityManager.persist(pastry);
            ExecResult res = new ExecResult();
            res.setEsito(true);
            res.setMsg(MessageConstants.MSG_SAVE_SUCCESS);
            LOG.info(pfn + " END");
            return res;
        } catch (Exception e){
            LOG.error(pfn + " "+ e.getMessage());
            ExecResult res = new ExecResult();
            res.setEsito(false);
            res.setMsg(ErrorConstants.ERR_SAVE);
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
