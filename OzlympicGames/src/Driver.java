import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Driver {
    public static final String PATH = "info.in";
    
    private Map<String, Athlete> athleteMap;
    private Map<String, Official> officialMap;
    private List<Game> gameList;
    private Game currentGame;
    private String predictId;
    
    /**
     * constructor method
     */
    public Driver() {
        athleteMap = new HashMap<String, Athlete>();
        officialMap = new HashMap<String, Official>();
        gameList = new ArrayList<Game>();
        predictId = "";
        
        this.readData();
    }
    
    /**
     * read the data from config file
     */
    private void readData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(PATH)));
            String line;
            while((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                String id = arr[0];
                String name = arr[1];
                int age = Integer.valueOf(arr[2]);
                String state = arr[3];
                String type = arr[4];
                switch(type) {
                case "swimmer": athleteMap.put(id, new Swimmer(id, name, age, state));break;
                case "cyclist": athleteMap.put(id, new Cyclist(id, name, age, state));break;
                case "sprinter": athleteMap.put(id, new Sprinter(id, name, age, state));break;
                case "official": officialMap.put(id, new Official(id, name, age, state));break;
                case "superAthlete":athleteMap.put(id, new SuperAthlete(id, name, age, state));break;
                default: continue;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * show menu
     */
    public void showMenu() {
        System.out.println("\nOzlympic Game");
        System.out.println("===================================");
        System.out.println("1. Select a game to run");
        System.out.println("2. Predict the winner of the game");
        System.out.println("3. Start the game");
        System.out.println("4. Display the final results of all games");
        System.out.println("5. Display the points of all athletes");
        System.out.println("6. Exit");
        System.out.println();
        System.out.print("Enter an option: ");
    }
    
    /**
     * create a new game
     * @param scanner
     */
    public void createGame(Scanner scanner) {
        System.out.println("1.Swimming\n2.Cycling\n3.Running");
        System.out.print("which game(1-3): ");
        int select = scanner.nextInt();
        while(select > 3 || select < 1) {
            System.out.print("please enter 1-3: ");
            select = scanner.nextInt();
        }
        System.out.print("enter game's name: ");
        String name = scanner.next();
        if(select == 1) {
            currentGame = new Swimming(name);
        } else if (select == 2) {
            currentGame = new Cycling(name);
        } else {
            currentGame = new Running(name);
        }
        if(!selectPlayers(scanner)) {
            System.out.println("creating game failed");
            return;
        }
        System.out.println("game created");
    }
    
    /**
     * select players to add to the game
     * @param scanner
     * @return
     */
    private boolean selectPlayers(Scanner scanner) {
        System.out.println("----add players----");
        System.out.println("id  name  age  state");
        for(Athlete ath : athleteMap.values()) {
            Participant p = (Participant) ath;
            String type = "";
            if(ath instanceof Swimmer) {
                type = "swimmer";
            } else if (ath instanceof Cyclist) {
                type = "cyclist";
            } else if (ath instanceof Sprinter) {
                type = "sprinter";
            } else if (ath instanceof SuperAthlete){
                type = "superAthlete";
            }
            System.out.println(p.getId() + "  " + p.getName() 
                        + "  " + p.getAge() + "  " + p.getState() + "  " + type);
        }
        System.out.print("select at least 5 players' id to add to the game:(split by space) ");
        scanner.nextLine();
        String[] arr = scanner.nextLine().split(" ");
        while(arr.length < 5) {
            System.out.print("select at least 5 players' id to add to the game:(split by space) ");
            arr = scanner.nextLine().split(" ");
        }
        for(int i=0; i<arr.length; i++) {
            Athlete ath = athleteMap.get(arr[i]);
            if(ath == null) {
                System.out.println("id error");
                return false;
            }
            ath.setType(currentGame.getType());
            currentGame.addAthlete(athleteMap.get(arr[i]));
        }
        
        System.out.println("----set one official----");
        System.out.println("id  name  age  state");
        for(Official off : officialMap.values()) {
            Participant p = (Participant) off;
            System.out.println(p.getId() + "  " + p.getName() 
                        + "  " + p.getAge() + "  " + p.getState());
        }
        System.out.print("select one official: ");
        String id = scanner.next();
        currentGame.setOfficial(officialMap.get(id));
        
        return true;
    }
    
    /**
     * predict the winner of the game
     * @param scanner
     */
    public void predictWinner(Scanner scanner) {
        System.out.println("----all players in current game----");
        for(Athlete ath : currentGame.getAthletes()) {
            Participant p = (Participant) ath;
            System.out.println(p.getId() + "  " + p.getName() 
            + "  " + p.getAge() + "  " + p.getState());
        }
        System.out.print("select one player's id: ");
        predictId = scanner.next();
        while(!athleteMap.containsKey(predictId)) {
            System.out.println("the id does not exist");
            System.out.print("select one player's id: ");
            predictId = scanner.next();
        }
    }
    
    /**
     * run the game
     */
    public void runGame() {
        Random random = new Random(System.currentTimeMillis());
        for(Athlete ath : currentGame.getAthletes()) {
            ath.compete(random);
        }
        String winnerId = currentGame.getOfficial().summerize(currentGame);
        gameList.add(currentGame);
        
        System.out.println("game finished");
        
        if(winnerId.equals(predictId)) {
            System.out.println("Congratulations! you predict the winner correctly!");
        } else {
            System.out.println("Sorry, you predict the winner uncorrectly");
        }
    }
    
    /**
     * show results of all the games
     */
    public void showAllResults() {
        System.out.println("gameid  gametype  name1   time1  name2  time2  name3  time3");
        for(Game game : gameList) {
            System.out.println(game);
        }
    }
    
    /**
     * show the points of all players
     */
    public void showAllPoints() {
        System.out.println("id  name  age  state  point");
        for(Athlete ath : currentGame.getAthletes()) {
            Participant p = (Participant) ath;
            System.out.println(p.getId() + "  " + p.getName() 
            + "  " + p.getAge() + "  " + p.getState() + "  " + p.getPoint());
        }
    }
}
