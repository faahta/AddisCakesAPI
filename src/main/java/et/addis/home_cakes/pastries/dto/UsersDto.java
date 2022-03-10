package et.addis.home_cakes.pastries.dto;

import et.addis.home_cakes.pastries.model.UserRoles;
import et.addis.home_cakes.security.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Fassil on 27/01/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private UUID userId;
    private String email;
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String phoneNumber;
    private boolean isEnabled;
    private AuthProvider provider;
    private String providerId;
    private String imageUrl;
    private Set<UserRoles> roles= new HashSet<>();
    private LocalDateTime dateCreated;
}
