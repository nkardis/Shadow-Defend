package lists;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadLevel {

    private static final String MAP_ONE = "res/levels/1.tmx";
    private static final String MAP_TWO = "res/levels/2.tmx";
    private static final String WAVES = "res/levels/waves.txt";
    private static ArrayList<String> waves = new ArrayList<String>();

    /**
     *
     * @param mapComp boolean to see if map was completed
     * @return returns directory for next map
     */
    public static String loadMap(boolean mapComp){
        if(!mapComp){
            return MAP_ONE;
        } else {
            return MAP_TWO;
        }

    }

    /**
     *
     * @return ArrayList of waves
     */
     public static ArrayList<String> makeFormat(){
         try (Scanner scanner = new Scanner(new FileReader(WAVES))) {
             while (scanner.hasNextLine()) {
                 waves.add(scanner.nextLine());
             }
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
         return waves;
     }

}
