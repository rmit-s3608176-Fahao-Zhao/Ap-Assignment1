import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;

public abstract class Game {
    
    private String id;
    private List<Athlete> athleteList;
    private Official official;
    protected GameType type;
    
    private List<Participant> recordList;
    
    /**
     * constructor method
     * @param id
     */
    public Game(String id) {
        this.id = id;
        athleteList = new ArrayList<>();
        official = null;
    }
    
    /**
     * add one athlete to the game
     * @param ath
     */
    public void addAthlete(Athlete ath) {
        athleteList.add(ath);
    }
    
    /**
     * get the athletes
     * @return
     */
    public List<Athlete> getAthletes() {
        return athleteList;
    }
    
    /**
     * set official of the game
     * @param off
     */
    public void setOfficial(Official off) {
        this.official = off;
    }
    
    /**
     * get official of the game
     * @return
     */
    public Official getOfficial() {
        return official;
    }
    
    /**
     * record the player's state
     * @param pants
     */
    public void record(List<Participant> pants) {
        recordList = new ArrayList<Participant>();
        for(Participant p : pants) {
            recordList.add(p.copy());
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += id + "  " + type.toString().toLowerCase() + "  ";
        for(int i=0; i<3; i++) {
            Participant p = (Participant) recordList.get(i);
            str += p.getName() + "  " +  p.getTime() + "  ";
        }
        
        return str;
    }
    
    /**
     * set the game's type
     * @param type
     */
    public void setType(GameType type) {
        this.type = type;
    }
    
    /**
     * get the game's type
     * @return
     */
    public GameType getType() {
        return type;
    }
    
}
