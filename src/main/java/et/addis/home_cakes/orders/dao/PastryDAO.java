package et.addis.home_cakes.orders.dao;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.dto.BusinessHoursDto;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.model.Branch;
import et.addis.home_cakes.orders.model.BusinessHours;
import et.addis.home_cakes.orders.model.Pastry;
import et.addis.home_cakes.orders.model.SubCity;

import java.util.List;

/**
 * Created by Fassil on 27/01/22.
 */
public interface PastryDAO {
    Pastry findPastryByName(String name);

    ExecResult savePastry(Pastry pastry, List<Branch> branches, Double latitude, Double longitude, List<BusinessHours> businessHours);

    List<SubCity> findAllSubCities();
}
