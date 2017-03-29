import java.util.Scanner;

public class Ozlympic {

    /**
     * the main method
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Driver driver = new Driver();
        while(true) {
            driver.showMenu();
            int select = scanner.nextInt();
            while(select < 1 || select > 6) {
                System.out.print("please enter (1-6): ");
            }
            switch(select) {
            case 1: driver.createGame(scanner); break;
            case 2: driver.predictWinner(scanner); break;
            case 3: driver.runGame();break;
            case 4: driver.showAllResults();break;
            case 5: driver.showAllPoints();break;
            case 6: System.out.println("program end");System.exit(0);
            default: break;
            }
        }
    }
}
