// Yair Cohen 313355786

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class manages a database of hypernym-hyponym relations on a given text.
 * creates the pattern to scan the text for relations according to them, and checks the text.
 */
public class ManageDataBase {
    private TreeMap<String, Hypernym> hypernyms;
    private Pattern[] patterns;
    private static final int HYPERFIRST = 4;
    private static final int HYPERLAST = 5;

    /**
     * Constructor for class.
     * creates a nes manager, and scan patterns.
     */
    public ManageDataBase() {
        hypernyms = new TreeMap<String, Hypernym>();
        createPatterns();
    }

    /**
     * Creates new regex patterns to check for hyponym-hypernym relations according to.
     */
    public void createPatterns() {
        patterns = new Pattern[6];
        String regZero = "<np>([^<])+</np>";
        String regOne = "<np>([^<>/])+</np>( ,)? such as <np>([^<>/])+</np>(( ,)?"
                + " <np>([^<>/])+</np>)*(( ,)? (or|and) <np>([^<>/])+</np>)?";
        String regTwo = "such <np>([^<>/])+</np> as <np>([^<>/])+</np>(( ,)? <np>([^<>/])+</np>)*(( ,)?"
                + " (or|and) <np>([^<>/])+</np>)?";
        String regThree = "<np>([^<>/])+</np>( ,)? including <np>([^<>/])+</np>(( ,)?"
                + " <np>([^<>/])+</np>)*(( ,)? (or|and) <np>([^<>/])+</np>)?";
        String regFour = "<np>([^<>/])+</np>( ,ֿֿ)? especially <np>([^<>/])+</np>(( ,)? "
                + "<np>([^<>/])+</np>)*(( ,)? (or|and) <np>([^<>/])+</np>)?";
        String regFive = "<np>([^<>/])+</np>( ,)? which is ((an example|a kind|a class) of )?<np>([^<>/])+</np>";
        patterns[0] = Pattern.compile(regZero);
        patterns[1] = Pattern.compile(regOne);
        patterns[2] = Pattern.compile(regTwo);
        patterns[3] = Pattern.compile(regThree);
        patterns[4] = Pattern.compile(regFour);
        patterns[5] = Pattern.compile(regFive);
    }

    /**
     * Method will look for a match to the patterns in a given string.
     *
     * @param str   the given string to look in
     * @param lemma a specific word to look for, if given. if null - will be checked for every possible pattern.
     */
    public void lookForMatch(String str, String lemma) {
        if (lemma == null) {
            for (int i = 1; i <= HYPERLAST; i++) {
                boolean foundHyper;
                Matcher matcher = patterns[i].matcher(str);
                String hyper = null;
                ArrayList<String> hyponyms = new ArrayList<>(0);
                // all options with hypernyms first in the sequence
                if ((str.contains("such") && i <= 2)
                        || (str.contains("including") && i == 3)
                        || (str.contains("especially") && i == HYPERFIRST)) {
                    while (matcher.find()) {
                        String txtLn = matcher.group();
                        Matcher matcherZero = patterns[0].matcher(txtLn);
                        foundHyper = true;
                        while (matcherZero.find()) {
                            if (foundHyper) {
                                //hyper will contain only the text without marks
                                hyper = txtLn.substring(matcherZero.start() + 4, matcherZero.end() - 5).toLowerCase();
                                foundHyper = false;
                            } else {
                                hyponyms.add(txtLn.substring(matcherZero.start()
                                        + 4, matcherZero.end() - 5).toLowerCase());
                            }
                        }
                        addHyper(hyper, hyponyms);
                    }
                    // all options with hypernyms last in the sequence
                } else if (i == HYPERLAST && str.contains("which is")) {
                    while (matcher.find()) {
                        String txtLn = matcher.group();
                        Matcher matcherZero = patterns[0].matcher(txtLn);
                        while (matcherZero.find()) {
                            hyponyms.add(txtLn.substring(matcherZero.start() + 4, matcherZero.end() - 5).toLowerCase());
                        }
                        hyper = hyponyms.get(hyponyms.size() - 1);
                        hyponyms.remove(hyponyms.size() - 1);
                        addHyper(hyper, hyponyms);
                    }
                }
            }
        } else { //lemma !=null
            for (int i = 1; i <= HYPERLAST; i++) {
                boolean foundHyper;
                Matcher matcher = patterns[i].matcher(str);
                String hyper = null;
                ArrayList<String> hyponyms = new ArrayList<>(0);
                // all options with hypernyms first in the sequence

                if (str.contains(lemma)) {
                    if ((str.contains("such") && i < 3)
                            || (str.contains("including") && i == 3)
                            || (str.contains("especially") && i == 4)) {
                        while (matcher.find()) {
                            String txtLn = matcher.group();
                            Matcher matcherZero = patterns[0].matcher(txtLn);
                            foundHyper = true;
                            while (matcherZero.find()) {
                                if (foundHyper) {
                                    //hyper will contain only the text without marks
                                    hyper = txtLn.substring(matcherZero.start()
                                            + 4, matcherZero.end() - 5).toLowerCase();
                                    foundHyper = false;
                                } else {
                                    hyponyms.add(txtLn.substring(matcherZero.start()
                                            + 4, matcherZero.end() - 5).toLowerCase());
                                }
                            }
                            addHyper(hyper, hyponyms);
                        }
                    } else if (i == 5 && str.contains("which is")) {
                        while (matcher.find()) {
                            String txtLn = matcher.group();
                            Matcher matcherZero = patterns[0].matcher(txtLn);
                            while (matcherZero.find()) {
                                hyponyms.add(txtLn.substring(matcherZero.start()
                                        + 4, matcherZero.end() - 5).toLowerCase());
                            }
                            hyper = hyponyms.get(hyponyms.size() - 1);
                            hyponyms.remove(hyponyms.size() - 1);
                            addHyper(hyper, hyponyms);
                        }
                    }
                }
            }
        }
    }

    private void addHyper(String hypernym, ArrayList<String> hypos) {
        if (hypernyms.containsKey(hypernym)) {
            for (int i = 0; i < hypos.size(); i++) {
                hypernyms.get(hypernym).addHypo(hypos.get(i));
            }
        } else {
            Hypernym hyper = new Hypernym(hypos, hypernym);
            hypernyms.put(hypernym, hyper);
        }
    }

    /**
     * Print the map of hypernyms as a sorted list according to number of appearances in text.
     * if given a lemma to look for - will print only hypernyms that will contain the lemma, according to same order.
     *
     * @param lemma - a noun to look for.
     */
    public void printHypernyms(String lemma) {
        if (hypernyms.isEmpty()) {
            System.out.println("The lemma doesn't appear in the corpus.");
            return;
        }
        System.out.println();
        Set<Map.Entry<String, Hypernym>> entrySet = hypernyms.entrySet();
        ArrayList<CounterAndName> hypersWithLemma = new ArrayList<CounterAndName>(0);
        for (Map.Entry<String, Hypernym> entry : entrySet) {
            if (lemma == null) {
                if (entry.getValue().getHyponyms().size() >= 3) {
                    entry.getValue().printHypernym();
                }
            } else {
                if (entry.getValue().getHyponyms().containsKey(lemma)) {
                    hypersWithLemma.add(new CounterAndName(entry.getKey(),
                            entry.getValue().getHyponyms().get(lemma).getCounter()));
                }
            }
        }
        if (lemma != null) {
            Collections.sort(hypersWithLemma);
            for (int i = 0; i < hypersWithLemma.size(); i++) {
                System.out.println(hypersWithLemma.get(i).getName()
                        + ": (" + hypersWithLemma.get(i).getCounter() + ")");
            }
        }
    }

    /**
     * @return the amount of found hypernyms.
     */
    public int getHyprListSize() {
        return hypernyms.size();
    }
}