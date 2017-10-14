package skijumpingMechanicPack;

/**
 *
 * @author Bartosz
 */
public class Hill {
    private String name;
    private String country;
    private int pointK;
    private int pointHS;
    private double pointDistance;
    private double pointWind;
    
    public Hill (String name, String country, int pointK, int pointHS, double pointDistance, double pointWind) 
    {

        this.name = name;
        this.country = country;
        this.pointK = pointK;
        this.pointHS = pointHS;
        this.pointDistance = pointDistance;
        this.pointWind = pointWind;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getCountry()
    {
        return country;
    }
    public int getPointK()
    {
        return pointK;
    }
    public int getPointHS()
    {
        return pointHS;
    }
    public double getPointDistance()
    {
        return pointDistance;
    }
    public double getPointWind()
    {
        return pointWind;
    }    
    
}

