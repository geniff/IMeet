package Model;

public class Profile {
    private Long id;
    private String email;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserInformation() {
        return UserInformation;
    }

    public void setUserInformation(String userInformation) {
        UserInformation = userInformation;
    }

    private String surname;
    private String UserInformation;
}
