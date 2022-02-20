package et.addis.home_cakes.orders.controller;

import et.addis.home_cakes.integration.request.SavePastryRequest;
import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.dto.SubCityDto;
import et.addis.home_cakes.orders.services.PastryService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Fassil on 26/01/22.
 */
@RestController
@RequestMapping(value = "/v1/api")
public class PastryController {

    @Autowired
    PastryService pastryService;

    private static final Logger LOG = LogManager.getLogger(PastryController.class);

    @GetMapping(value = "/pastry", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets pastry by name.", response = PastryDto.class)
    public ResponseEntity<PastryDto> getPastryByName(@Param("name") String name) throws Exception {
        String pfn = "[PastryController::getPastryByName]";
        LOG.info(pfn + " Received request - GET /api/pastry?name="+name);
        try {
            PastryDto pastryDto = pastryService.getPastryByName(name);
            LOG.info(pfn + " END");
            return ResponseEntity.ok().body(pastryDto);
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/pastry", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Saves a pastry.", response = ExecResult.class)
    public ResponseEntity<ExecResult> savePastry(@RequestBody SavePastryRequest savePastryRequest) throws Exception {
        String pfn = "[PastryController::savePastry]";
        LOG.info(pfn + " Received request - POST /api/pastry : "+savePastryRequest.getPastry().toString());
        try {
            ExecResult result = pastryService.savePastry(savePastryRequest.getPastry(),
                    savePastryRequest.getBranches(), savePastryRequest.getLatitude(), savePastryRequest.getLongitude(),
                    savePastryRequest.getBusinessHoursDto());
            LOG.info(pfn + " END");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/sub-cities/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets all sub-cities in addis ababa.", response = SubCityDto.class, responseContainer = "List")
    public ResponseEntity<List<SubCityDto>> getAllSubCities() throws Exception {
        String pfn = "[PastryController::getAllSubCities]";
        LOG.info(pfn + " Received request - GET /api/sub-cities/all");
        try {
            List<SubCityDto> subCityDto = pastryService.getAllSubCities();
            LOG.info(pfn + " END");
            return ResponseEntity.ok().body(subCityDto);
        } catch (Exception e){
            LOG.info(pfn + " END"); LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }


}
