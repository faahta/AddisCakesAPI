package et.addis.home_cakes.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;

/**
 * Created by Fassil on 26/01/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PastryDto {
    private Long id;
    private String name;
    private String email;
    private String website;
    private String phoneNo;
    private String description;
    private Double minPrice;
    private String maxPrice;
    private Long tinNumber;
    private String bankAcctNo;
    private String imageUrl;
    private Point geom;
}
