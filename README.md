# Hypernym_Hyponym
Creating Database of categories and their detalis
This project in a fairly simple demonstation of the ability to sort and order a large database
by titles or categories ("hypernyms") and word that relate to each of these categories (hyponyms).

using regular expressions to recognize patterns in a given corpus of text - I sorted the corpus to hypernyms,
and - in each hypernym - ordered the hyponyms first by frequency in the corpus, then alphabetically.

using the right data structures and some basic optimizations - I managed to narrow down the runtime significantlly.

to run the code - clone it to your computer,
open the terminal to the files path.
press "ant compile"
then choose an option:
press "ant run1 pathToYouChoose"  will run a full check on the corpus and print the full output to file you gave as an argument.
press "ant run2 pathToCorpus HypernymYouChoose"  will run a check only for this hypernym and it hyponyms and will print the results to terminal


enjoy!
