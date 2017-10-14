package skijumpingMechanicPack;

import java.util.ArrayList;

/**
 *
 * @author Bartosz
 */
public class Competition {
    
    private Jumper jumper;
    private int place; // place -> miejsce w konkursie
    private double firstRound, secondRound, points;
    private int pointsToWorldCup;

    public Competition(int place, Jumper jumper, double firstRound, double secondRound, double points, int pointsToWorldCup) {
        this.jumper = jumper;
        this.place = place;
        this.firstRound = firstRound;
        this.secondRound = secondRound;
        this.points = points;
        this.pointsToWorldCup = pointsToWorldCup;
    }

    public Jumper getJumper() {
        return jumper;
    }

    public int getPlace() {
        return place;
    }

    public double getFirstRound() {
        return firstRound;
    }

    public double getSecondRound() {
        return secondRound;
    }

    public double getPoints() {
        return points;
    }

    public int getPointsToWorldCup() {
        return pointsToWorldCup;
    }

    public void setJumper(Jumper jumper) {
        this.jumper = jumper;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public void setFirstRound(double firstRound) {
        this.firstRound = firstRound;
    }

    public void setSecondRound(double secondRound) {
        this.secondRound = secondRound;
    }

    public void setPoints(double points) {
        this.points = points;
    }    

    public void setPointsToWorldCup(int pointsToWorldCup) {
        this.pointsToWorldCup = pointsToWorldCup;
    }
    
    @Override
    public String toString() {
        return "Competition{" + "jumper=" + jumper.getFirstName() + " " + jumper.getLastName() + " " + jumper.getCountry() + ", place=" + place + ", firstRound=" + firstRound + ", secondRound=" + secondRound + ", points=" + points + ", pointsToWorldCup=" + pointsToWorldCup + '}';
    }
    
    
    
    public static ArrayList<Competition> sort(ArrayList<Competition> sortList)      // sortowanie pełnej tablicy wynikow
    {
        // w sortowaniu nie potrzebuję kopiować pkt do PŚ, bo te zosatną przydzielone pozniej
        Competition tmp = new Competition(0, new Jumper("", "", ""), 0, 0, 0, 0);
        int changePlace = 1;
        
        while (changePlace > 0)
        {
            changePlace = 0;
            for (int i = 0; i < sortList.size() - 1; i++)
            {
                if (sortList.get(i).getPoints() < sortList.get(i + 1).getPoints())
                {
                    tmp.setPlace(sortList.get(i + 1).getPlace());         // przypisuje obiektowi tmp obiekt i+1 w liscie  
                    tmp.setJumper(new Jumper(sortList.get(i + 1).getJumper().getFirstName(), sortList.get(i + 1).getJumper().getLastName(), sortList.get(i + 1).getJumper().getCountry()));
                    tmp.setFirstRound(sortList.get(i + 1).getFirstRound());
                    tmp.setSecondRound(sortList.get(i + 1).getSecondRound());
                    tmp.setPoints(sortList.get(i + 1).getPoints());
                    
                    
                    sortList.get(i + 1).setPlace(sortList.get(i).getPlace());         // przypisuje obiektowi i+1 w liscie obiekt i w liscie
                    sortList.get(i + 1).setJumper(new Jumper(sortList.get(i).getJumper().getFirstName(), sortList.get(i).getJumper().getLastName(), sortList.get(i).getJumper().getCountry()));
                    sortList.get(i + 1).setFirstRound(sortList.get(i).getFirstRound());
                    sortList.get(i + 1).setSecondRound(sortList.get(i).getSecondRound());
                    sortList.get(i + 1).setPoints(sortList.get(i).getPoints());
                    
                    sortList.get(i).setPlace(tmp.getPlace());         // przypisuje obiektowi i w liscie obiekt tmp
                    sortList.get(i).setJumper(new Jumper(tmp.getJumper().getFirstName(), tmp.getJumper().getLastName(), tmp.getJumper().getCountry()));
                    sortList.get(i).setFirstRound(tmp.getFirstRound());
                    sortList.get(i).setSecondRound(tmp.getSecondRound());
                    sortList.get(i).setPoints(tmp.getPoints());
                    
                    changePlace++;
                }
            }
        }
        return sortList;
    }
    
    public static int pointsToWorldCup(int placeInCompetition)
     {
         int pointsToWC = 0;
         int tmp = 0;
         
         if (placeInCompetition <= 10) tmp = 1;
         else if (placeInCompetition > 10 && placeInCompetition <= 20) tmp = 2;
         else if (placeInCompetition > 20 && placeInCompetition <= 30) tmp = 3;
         
         switch(tmp)
         {
             case 1:
                 switch(placeInCompetition)
                 {
                    case 1:  pointsToWC = 100;
                             break;
                    case 2:  pointsToWC = 80;
                             break;
                    case 3:  pointsToWC = 60;
                             break;
                    case 4:  pointsToWC = 50;
                             break;
                    case 5:  pointsToWC = 45;
                             break;
                    case 6:  pointsToWC = 40;
                             break;
                    case 7:  pointsToWC = 36;
                             break;
                    case 8:  pointsToWC = 32;
                             break;
                    case 9:  pointsToWC = 29;
                             break;
                    case 10: pointsToWC = 26;
                             break;
                    default: pointsToWC = 0;
                             break;
                 }
                 break;
             case 2:
                 switch(placeInCompetition)
                 {
                    case 11: pointsToWC = 24;
                             break;
                    case 12: pointsToWC = 22;
                             break;
                    case 13:  pointsToWC = 20;
                             break;
                    case 14:  pointsToWC = 18;
                             break;
                    case 15:  pointsToWC = 16;
                             break;
                    case 16:  pointsToWC = 15;
                             break;
                    case 17:  pointsToWC = 14;
                             break;
                    case 18:  pointsToWC = 13;
                             break;
                    case 19:  pointsToWC = 12;
                             break;
                    case 20:  pointsToWC = 11;
                             break;
                    default: pointsToWC = 0;
                    break;
                 }
                 break;
             case 3:
                 switch(placeInCompetition)
                 {
                    case 21: pointsToWC = 10;
                             break;
                    case 22: pointsToWC = 9;
                             break;
                    case 23: pointsToWC = 8;
                             break;
                    case 24: pointsToWC = 7;
                             break;
                    case 25: pointsToWC = 6;
                             break;
                    case 26: pointsToWC = 5;
                             break;
                    case 27: pointsToWC = 4;
                             break;
                    case 28: pointsToWC = 3;
                             break;
                    case 29: pointsToWC = 2;
                             break;
                    case 30: pointsToWC = 1;
                             break;
                    default: pointsToWC = 0;
                             break;
                 }
                 break;
             default: pointsToWC = 0;
                 break;
         }
         return pointsToWC;
     }
}
