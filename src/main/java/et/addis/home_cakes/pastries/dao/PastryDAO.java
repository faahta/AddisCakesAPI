package et.addis.home_cakes.pastries.dao;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.pastries.model.Branch;
import et.addis.home_cakes.pastries.model.BusinessHours;
import et.addis.home_cakes.pastries.model.Pastry;
import et.addis.home_cakes.pastries.model.SubCity;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by Fassil on 27/01/22.
 */
public interface PastryDAO {
    Pastry findPastryByName(String name);

    ExecResult savePastry(Pastry pastry, List<Branch> branches, Double latitude, Double longitude, List<BusinessHours> businessHours) throws MessagingException;

    List<SubCity> findAllSubCities();
}
