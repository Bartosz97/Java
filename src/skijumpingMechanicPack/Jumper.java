package skijumpingMechanicPack;

/**
 *
 * @author Bartosz
 */
public class Jumper {
    
    private String firstName;
    private String lastName;
    private String country;
    
    public Jumper (String firstName, String lastName, String country) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public String getCountry()
    {
        return country;
    }

    @Override
    public String toString() {
        return "Jumper{" + "firstName=" + firstName + ", lastName=" + lastName + ", country=" + country + '}';
    }

}
