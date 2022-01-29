package et.addis.home_cakes.orders.controller;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.services.PastryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Fassil on 26/01/22.
 */
@RestController
@RequestMapping("/v1/api")
public class PastryController {

    @Autowired
    PastryService pastryService;

    private static final Logger LOG = LogManager.getLogger(PastryController.class);

    @GetMapping(value = "/pastry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPastryByName(@Param("name") String name) throws Exception {
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

    @PostMapping(value = "/pastry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePastry(@RequestBody PastryDto pastry) throws Exception {
        String pfn = "[PastryController::savePastry]";
        LOG.info(pfn + " Received request - POST /api/pastry : "+pastry.toString());
        try {
            ExecResult result = pastryService.savePastry(pastry);
            LOG.info(pfn + " END");
            return ResponseEntity.ok().body(result);
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }

}
