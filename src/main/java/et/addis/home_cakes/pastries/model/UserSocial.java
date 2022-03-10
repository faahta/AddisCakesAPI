package et.addis.home_cakes.pastries.model;

import javax.persistence.*;

/**
 * Created by Fassil on 04/09/20.
 */
@Entity
@Table(name = "user_social")
public class UserSocial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "user_birthday")
    private String userBirthday;
    public UserSocial(){}

    public UserSocial(String email, String name, String userBirthday) {
        this.email = email;
        this.name = name;
        this.userBirthday = userBirthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }
}
