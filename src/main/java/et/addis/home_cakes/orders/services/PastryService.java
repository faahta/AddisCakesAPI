package et.addis.home_cakes.orders.services;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.dto.SubCityDto;

import java.util.List;

/**
 * Created by Fassil on 26/01/22.
 */
public interface PastryService {
    PastryDto getPastryByName(String name);

    ExecResult savePastry(PastryDto pastry);

    List<SubCityDto> getAllSubCities();
}
