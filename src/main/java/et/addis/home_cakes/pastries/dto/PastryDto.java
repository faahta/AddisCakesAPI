package et.addis.home_cakes.pastries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * Created by Fassil on 26/01/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PastryDto implements Serializable  {
    private Long pastryId;

    @NonNull
    private String name;
    @NonNull
    private String email;

    private String website;
    private String phoneNo;
    private String description;

    @NonNull
    private Double minPrice;
    @NonNull
    private Double maxPrice;

    @NonNull
    private BankDto bank;
    @NonNull
    private String bankAcctNo;
    @NonNull
    private String bankAcctOwner;
    @NonNull
    private Long tinNumber;

    private String logoImage;
   /* @JsonIgnore
    private Point geom;*/

    @NonNull
    private SubCityDto subCity;
    @NonNull
    private String woreda;

    private String kebele;
    private String image1;
    private String image2;
    private String image3;

    private Boolean hasBranch;
   // private Point location;
 /*   private List<BranchDto> branches;*/
}
