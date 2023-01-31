// Yair Cohen 313355786
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * Class creates a new hypernym relations database, based on a given text files.
 * Connects hypernyms with hyponyms according to certain rules.
 */
public class CreateHypernymDatabase {
    private ManageDataBase dataBase = new ManageDataBase();

    /**
     * Main method of class.
     * @param args - first argument is the path to the files the text will be read from.
     *               second argument - the path to the file to write the new hierarchy to.
     */
    public static void main(String[] args) {
       /*if (args.length == 0) {
           System.out.println("no file location inserted");
           return;
       }*/
       //int counter = 0;
       try {
            ManageDataBase manager = new ManageDataBase();
            File fileSource = new File(args[0]); //corpus
            File[] files = fileSource.getAbsoluteFile().listFiles();
         //  counter = 1;
           for (File file: files) {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String currentRead;
                while ((currentRead = bufferedReader.readLine()) != null) {
                        manager.lookForMatch(currentRead, null);
                }
           //    System.out.println("end file number: " + counter);
               //   counter++;
            }
           File outFile = new File(args[1]);
           PrintStream stream = new PrintStream(outFile);
           System.setOut(stream);
           manager.printHypernyms(null);
           //System.out.println("\n\n" + manager.getHyprListSize());
        } catch (Exception e) {
            System.out.println("file does not exist");
        }
    }
}
