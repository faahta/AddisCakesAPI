package et.addis.home_cakes.pastries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.*;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by Fassil on 10/02/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto implements Serializable  {
    private Integer branchId;
    private String branchName;
    private Point location;
    private String phoneNo;
    private String branchManager;
    private String email;
    private Double latitude;
    private Double longitude;
}
