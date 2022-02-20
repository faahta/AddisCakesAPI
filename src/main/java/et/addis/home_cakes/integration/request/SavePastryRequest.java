package et.addis.home_cakes.integration.request;

import et.addis.home_cakes.orders.dto.BranchDto;
import et.addis.home_cakes.orders.dto.BusinessHoursDto;
import et.addis.home_cakes.orders.dto.PastryDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fassil on 11/02/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SavePastryRequest implements Serializable {
    private PastryDto pastry;
    private List<BranchDto> branches;
    private Double latitude;
    private Double longitude;
    private List<BusinessHoursDto> businessHoursDto;
}
