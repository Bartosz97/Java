package skijumpingMechanicPack;

/**
 *
 * @author Bartosz
 */
public class User
{
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private int accessLevel;
    
    public User(String firstname, String lastname, String login, String password, int accessLevel)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
    }
    
    public User(String firstname, String lastname, int accessLevel)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return "User{" + "firstname=" + firstname + ", lastname=" + lastname + ", login=" + login + ", password=" + password + ", accessLevel=" + accessLevel + '}';
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getAccessLevel() {
        return accessLevel;
    }
}
