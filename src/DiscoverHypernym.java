// Yair Cohen 313355786
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Class provides all hypernym relations to a hyponym given as an argument by the user.
 * Generates a list of hypernyms sorted according to amount of appearances of the given hyponym in those
 * categories.
 */
public class DiscoverHypernym {
    /**
     * Main method of class.
     * @param args - first argument is the path to the files the text will be read from.
     *               second argument - the hyponym to be checked.
     */
    public static void main(String[] args) {
       // int counter;
        try {
            ManageDataBase manager = new ManageDataBase();
            File fileSource = new File(args[0]); //corpus
            File[] files = fileSource.getAbsoluteFile().listFiles();
         //   counter = 1;
            for (File file: files) {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String currentRead;
                while ((currentRead = bufferedReader.readLine()) != null) {
                    manager.lookForMatch(currentRead, args[1]);
                }
           //     System.out.println("end file number: " + counter);
             //   counter++;
            }
            manager.printHypernyms(args[1]);
            //System.out.println("\n\n" + manager.getHyprListSize());
        } catch (Exception e) {
            System.out.println("problem occured in your files");
        }
    }
}