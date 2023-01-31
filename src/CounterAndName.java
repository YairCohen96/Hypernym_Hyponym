// Yair Cohen 313355786

/**
 * Class represents hyponym or hypernym according to use.
 * In fact - a noun phrase and a counter - count amount of times it appears in text, or other use
 * of it appears - according to use.
 */
public class CounterAndName implements Comparable<CounterAndName> {
    private String name;
    private int counter;

    /**
     * Constructor for class.
     * @param name - the noun phrase
     * @param counter - counter of times.
     */
    public CounterAndName(String name, int counter) {
        this.name = name;
        this.counter = counter;
    }

    /**
     * @return the noun phrase.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the counter.
     */
    public int getCounter() {
        return counter;
    }
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(CounterAndName other) {
        if (this.counter < other.getCounter()) {
            return 1;
        } else if (this.counter > other.getCounter()) {
            return -1;
        } else { //counter are equal - return lexicographical order
            return this.name.compareTo(other.getName());
        }
    }
}
