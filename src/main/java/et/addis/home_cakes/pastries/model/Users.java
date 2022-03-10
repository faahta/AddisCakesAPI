package et.addis.home_cakes.pastries.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import et.addis.home_cakes.security.AuthProvider;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")}, schema = "public"
)
public class Users implements Serializable, UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_enabled")
    @JsonIgnore
    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    private String imageUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserRoles> roles= new HashSet<>();

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    public Users(String email, String password, String fullName, Date dateOfBirth,
                 String phoneNumber) {
        this.dateCreated = LocalDateTime.now();
        this.email = email;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Users(String email, String encode) {
        this.dateCreated = LocalDateTime.now();
        this.email = email;
        this.password = encode;
    }
    public Users(String email, String encode, String fullName) {
        this.dateCreated = LocalDateTime.now();
        this.fullName = fullName;
        this.email = email;
        this.password = encode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }



}
