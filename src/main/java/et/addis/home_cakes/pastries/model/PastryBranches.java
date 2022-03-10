package et.addis.home_cakes.pastries.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
///////JPA/////////
// OneToMany: LAZY
// ManyToOne: EAGER
// ManyToMany: LAZY
// OneToOne: EAGER

///////HIBERNATE/////////
//ALL LAZY
/**
 * Created by Fassil on 10/02/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pastry_branches")
public class PastryBranches {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pastry_id")
    private Pastry pastry;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;


    public PastryBranches(Pastry pastry, Branch b) {
        this.pastry = pastry;
        this.branch = b;
    }
}
