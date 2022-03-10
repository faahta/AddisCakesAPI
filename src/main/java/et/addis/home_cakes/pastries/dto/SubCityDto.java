package et.addis.home_cakes.pastries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Fassil on 04/02/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCityDto implements Serializable {
    private Long id;
    private String name;
    private Double area;
}
