package et.addis.home_cakes.orders.services.impl;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dao.PastryDAO;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.mapper.PastryMapper;
import et.addis.home_cakes.orders.model.Pastry;
import et.addis.home_cakes.orders.services.PastryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Fassil on 26/01/22.
 */
@Service
public class PastryServiceImpl implements PastryService {

    private static final Logger LOG = LogManager.getLogger(PastryServiceImpl.class);

    @Autowired
    PastryDAO pastryDAO;

    @Override
    public PastryDto getPastryByName(String name) {
        String pfn = "[PastryServiceImpl::getPastryByName]";
        LOG.info(pfn + " START");
        try {
            Pastry pastry = pastryDAO.findPastryByName(name);
            LOG.info(pfn + " END");
            return PastryMapper.INSTANCE.modelToDto(pastry);
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }

    @Override
    public ExecResult savePastry(PastryDto pastryDto) {
        String pfn = "[PastryServiceImpl::savePastry]";
        LOG.info(pfn + " START");
        try {
            Pastry pastry = PastryMapper.INSTANCE.dtoToModel(pastryDto);
            ExecResult result = pastryDAO.savePastry(pastry);
            LOG.info(pfn + " END");
            return result;
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }
}
