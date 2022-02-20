package et.addis.home_cakes.orders.services.impl;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dao.PastryDAO;
import et.addis.home_cakes.orders.dto.BranchDto;
import et.addis.home_cakes.orders.dto.BusinessHoursDto;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.dto.SubCityDto;
import et.addis.home_cakes.orders.mapper.BranchMapper;
import et.addis.home_cakes.orders.mapper.BusinessHoursMapper;
import et.addis.home_cakes.orders.mapper.PastryMapper;
import et.addis.home_cakes.orders.mapper.SubCityMapper;
import et.addis.home_cakes.orders.model.Branch;
import et.addis.home_cakes.orders.model.BusinessHours;
import et.addis.home_cakes.orders.model.Pastry;
import et.addis.home_cakes.orders.model.SubCity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import et.addis.home_cakes.orders.services.PastryService;

import java.nio.charset.StandardCharsets;
import java.util.List;

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
    public ExecResult savePastry(PastryDto pastryDto, List<BranchDto> branchesDtos, Double latitude, Double longitude, List<BusinessHoursDto> businessHoursDtos) {
        String pfn = "[PastryServiceImpl::savePastry]";
        LOG.info(pfn + " START");
        try {
            Pastry pastry = PastryMapper.INSTANCE.dtoToModel(pastryDto);
            List<Branch> branches = BranchMapper.INSTANCE.dtosToModels(branchesDtos);
            List<BusinessHours> businessHours = BusinessHoursMapper.INSTANCE.dtosToModels(businessHoursDtos);
            ExecResult result = pastryDAO.savePastry(pastry, branches, latitude, longitude, businessHours);
            LOG.info(pfn + " END");
            return result;
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SubCityDto> getAllSubCities() {
        String pfn = "[PastryServiceImpl::getAllSubCities]";
        LOG.info(pfn + " START");
        try {
            List<SubCity> subCities = pastryDAO.findAllSubCities();
            LOG.info(pfn + " END");
            return SubCityMapper.INSTANCE.modelsToDtos(subCities);
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }
}
