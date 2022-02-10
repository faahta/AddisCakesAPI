package et.addis.home_cakes.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pastry_id")
    private Long pastryId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="website")
    private String website;

    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="description")
    private String description;

    @Column(name="min_price", nullable = false)
    private Double minPrice;

    @Column(name="max_price", nullable = false)
    private Double maxPrice;

    @Column(name="tin_number", nullable = false)
    private Long tinNumber;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
    private Bank bank;

    @Column(name="bank_acct_no", nullable = false)
    private String bankAcctNo;
    @Column(name="bank_acct_owner", nullable = false)
    private String bankAcctOwner;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name= "logo_image")
    private byte[] logoImage;


  /*  @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @JsonIgnore
    private Point geom;*/

    @ManyToOne
   // @JoinColumn(name = "sub_city_id", referencedColumnName = "id")
    private SubCity subCity;

    @Column(name="woreda", nullable = false)
    private String woreda;

    @Column(name="kebele")
    private String kebele;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name="image1")
    private byte[] image1;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name="image2")
    private byte[] image2;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name="image3")
    private byte[] image3;



    @OneToMany(mappedBy="pastry")
    //@JsonIgnore
    private List<PastryBranches> branches = new ArrayList<>();
}
