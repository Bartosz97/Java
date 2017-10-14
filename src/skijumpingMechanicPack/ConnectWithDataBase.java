package skijumpingMechanicPack;

/**
 *
 * @author Bartosz
 */
public class ConnectWithDataBase
{
    private String driver, url, login, password;

    public ConnectWithDataBase() 
    {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/skijumpingproject?useUnicode=yes&characterEncoding=UTF-8";
        login = "root";
        password = "";
    }

    public String getDriver() 
    {
        return driver;
    }

    public String getUrl()
    {
        return url;
    }

    public String getLogin() 
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }
    
    
}
