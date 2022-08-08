import java.util.Iterator;
public class OpponentsHand extends Hand {
    /**
     * Displays the current Cards in the Hand, and the current value.
     * As this is the opponent's Hand, the second Card onwards will be
     * hidden and marked with a "X".
     *
     * @return The String to be displayed on console.
     */
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        final Iterator<Card> handIterator = getCards().descendingIterator();
        final Card firstCardInHand = handIterator.next();
        result.append(firstCardInHand);
        while (handIterator.hasNext()){
            result.append("[X]");
            handIterator.next();
        }
        result.append("\t Value: ").append(firstCardInHand.getValue()).append(" + ?");
        return result.toString();
    }
}
