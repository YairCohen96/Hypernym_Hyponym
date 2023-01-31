// Yair Cohen 313355786
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class represents a noun phrase of a category in a hypernym semantic relations.
 * Each objects holds the list of phrases in its category and amount of times they appear,
 * related to it in a given text files.
 */
public class Hypernym {
    private HashMap<String, CounterAndName> hyponyms;
    private String name;

    /**
     * Constructor for class.
     * @param hypos list of nouns in this hypernym category.
     * @param hyper - the category.
     */
    public Hypernym(ArrayList<String> hypos, String hyper) {
        name = hyper;
        hyponyms = new HashMap<String, CounterAndName>();
        for (String hypo: hypos) {
            addHypo(hypo);
        }
    }

    /**
     * @return the collection of hyponyms in the category - as a map.
     */
    public HashMap<String, CounterAndName> getHyponyms() {
        return hyponyms;
    }

    /**
     * Adds a new hyponym to the category.
     * add a brand nwe phrase if appears for first time
     * else - increase counter of appearances.
     * @param hypo new phrase to be added.
     */
    public void addHypo(String hypo) {
        if (hyponyms.containsKey(hypo)) {
            Integer counter = hyponyms.get(hypo).getCounter() + 1;
            hyponyms.replace(hypo, new CounterAndName(hypo, counter));
        } else {
            hyponyms.put(hypo, new CounterAndName(hypo, 1));
        }
    }

    /**
     * Print the phrase along with phrases related to it as hyponyms
     * in a certain order.
     */
    public void printHypernym() {
        //System.out.println();
        System.out.print(name + ": ");
        printHyponymsLists();
    }
    private void printHyponymsLists() {
        Set<Map.Entry<String, CounterAndName>> entrySet = hyponyms.entrySet();
        ArrayList<CounterAndName> hypoList = new ArrayList<CounterAndName>();
        for (Map.Entry<String, CounterAndName> entry : entrySet) {
            hypoList.add(new CounterAndName(entry.getKey(), entry.getValue().getCounter()));
        }
        Collections.sort(hypoList); //sort according to Hyponym compareTo
        int listSize = hypoList.size();
        for (int i = 0; i < listSize - 1; i++) {
            System.out.print(hypoList.get(i).getName() + " (" + hypoList.get(i).getCounter() + "), ");
        }
        System.out.println(hypoList.get(listSize - 1).getName()
                + " (" + hypoList.get(listSize - 1).getCounter() + ") ");
    }
}
