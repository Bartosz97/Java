package skijumpingMechanicPack;

/**
 *
 * @author Bartosz
 */
public class FourHillsTournamentClassification {
    
     private Jumper jumper;
    private int placeIn4HT; // place -> miejsce w turnieju 4 skoczni
    private double pointsIn4HT;

    public FourHillsTournamentClassification(int placeIn4HT, Jumper jumper, double pointsIn4HT) {
        this.jumper = jumper;
        this.placeIn4HT = placeIn4HT;
        this.pointsIn4HT = pointsIn4HT;
    }

    public Jumper getJumper() {
        return jumper;
    }

    public int getPlaceIn4HT() {
        return placeIn4HT;
    }

    public double getPointsIn4HT() {
        return pointsIn4HT;
    }

    public void setJumper(Jumper jumper) {
        this.jumper = jumper;
    }

    public void setPlaceIn4HT(int placeIn4HT) {
        this.placeIn4HT = placeIn4HT;
    }

    public void setPointsIn4HT(double pointsIn4HT) {
        this.pointsIn4HT = pointsIn4HT;
    }

    @Override
    public String toString() {
        return "FourHillsTournamentClassification{" + "jumper=" + jumper + ", placeIn4HT=" + placeIn4HT + ", pointsIn4HT=" + pointsIn4HT + '}';
    }
}
