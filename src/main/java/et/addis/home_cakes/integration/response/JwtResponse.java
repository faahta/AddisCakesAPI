package et.addis.home_cakes.integration.response;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fassil on 11/01/22.
 */
public class JwtResponse implements Serializable {
    private  final long serialVersionUID = -8091879091924046844L;
    private  String jwtToken;
    private  UUID userId;
    private  String fullName;
    private  String email;
    private  String dateOfBirth;
    private  String phoneNumber;
    private List<String> roles;

    public JwtResponse(String jwtToken, String email, String fullName){
            this.jwtToken = jwtToken;
            this.email = email;
            this.fullName = fullName;
        }
        // private final String password;
    public JwtResponse(String jwtToken, UUID userId, String fullName, String email,
                String dateOfBirth, String phoneNumber, List<String> roles) {
            this.jwtToken = jwtToken;
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.dateOfBirth = dateOfBirth;
            this.phoneNumber = phoneNumber;
            this.roles = roles;
            // this.password = password;
        }

    public JwtResponse(String jwt, UUID id, String username, String email, String dob, List<String> roles) {
            this.jwtToken = jwt;
            this.userId = id;
            this.fullName = username;
            this.email = email;
            this.dateOfBirth = dob;
            // this.password = password;
        }

        public String getJwtToken() {
            return this.jwtToken;
        }

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public UUID getUserId() {
            return userId;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
}
