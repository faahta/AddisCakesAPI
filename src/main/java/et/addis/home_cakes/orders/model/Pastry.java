package et.addis.home_cakes.orders.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Fassil on 26/01/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pastry")
public class Pastry  implements Serializable {
    @Id
    @Column(name = "pastry_id", nullable = false)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="website")
    private String website;

    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="description")
    private String description;

    @Column(name="min_price")
    private Double minPrice;

    @Column(name="max_price")
    private String maxPrice;

    @Column(name="tin_number")
    private Long tinNumber;

    @Column(name="bank_acct_no")
    private String bankAcctNo;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name = "geom")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Point geom;

}
