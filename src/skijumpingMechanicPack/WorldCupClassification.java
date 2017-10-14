package skijumpingMechanicPack;

/**
 *
 * @author Bartosz
 */
public class WorldCupClassification {
    private Jumper jumper;
    private int placeInWC; // place -> miejsce w PŚ
    private int pointsInWorldCup;

    public WorldCupClassification(int placeInWC, Jumper jumper, int pointsInWorldCup) {
        this.jumper = jumper;
        this.placeInWC = placeInWC;
        this.pointsInWorldCup = pointsInWorldCup;
    }

    public Jumper getJumper() {
        return jumper;
    }

    public int getPlaceInWC() {
        return placeInWC;
    }

    public int getPointsInWorldCup() {
        return pointsInWorldCup;
    }

    public void setJumper(Jumper jumper) {
        this.jumper = jumper;
    }

    public void setPlaceInWC(int placeInWC) {
        this.placeInWC = placeInWC;
    }

    public void setPointsInWorldCup(int pointsInWorldCup) {
        this.pointsInWorldCup = pointsInWorldCup;
    }

    @Override
    public String toString() {
        return "WorldCupClassification{" + "jumper=" + jumper + ", placeInWC=" + placeInWC + ", pointsInWorldCup=" + pointsInWorldCup + '}';
    }
    
    
}
