package et.addis.home_cakes.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Fassil on 10/02/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PastryBranchesDto implements Serializable  {
    private Integer id;
    private PastryDto pastry;
    private BranchDto branch;

}
