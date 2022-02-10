package et.addis.home_cakes.orders.controller;

import et.addis.home_cakes.orders.dto.BankDto;
import et.addis.home_cakes.orders.services.PaymentService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Fassil on 02/02/22.
 */
@RestController
@RequestMapping(value = "/v1/api")
public class PaymentController {
    private static final Logger LOG = LogManager.getLogger(PaymentController.class);

    @Autowired
    PaymentService paymentService;

    @GetMapping(value = "/banks/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets all banks.", response = BankDto.class, responseContainer = "List")
    public ResponseEntity< List<BankDto>> getAllBanks() throws Exception {
        String pfn = "[PaymentController::getAllBanks]";
        LOG.info(pfn + " Received request - GET /banks/all");
        try {
            List<BankDto> banks = paymentService.getAllBanks();
            LOG.info(pfn + " END");
            return ResponseEntity.ok().body(banks);
        } catch (Exception e){
            LOG.info(pfn + " END");
            LOG.error(pfn + " "+ e.getMessage());
            throw e;
        }
    }

}
