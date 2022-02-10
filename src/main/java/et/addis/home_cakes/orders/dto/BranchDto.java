package et.addis.home_cakes.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.postgis.Point;

import java.io.Serializable;

/**
 * Created by Fassil on 10/02/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto implements Serializable  {
    private Integer id;
    private String branchName;
    private Point location;
    private String phoneNo;
}
