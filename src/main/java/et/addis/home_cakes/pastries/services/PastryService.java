package et.addis.home_cakes.pastries.services;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.pastries.dto.BranchDto;
import et.addis.home_cakes.pastries.dto.BusinessHoursDto;
import et.addis.home_cakes.pastries.dto.PastryDto;
import et.addis.home_cakes.pastries.dto.SubCityDto;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by Fassil on 26/01/22.
 */
public interface PastryService {
    PastryDto getPastryByName(String name);

    ExecResult savePastry(PastryDto pastryDto, List<BranchDto> branches, Double latitude, Double longitude, List<BusinessHoursDto> businessHoursDto) throws MessagingException;

    List<SubCityDto> getAllSubCities();
}
