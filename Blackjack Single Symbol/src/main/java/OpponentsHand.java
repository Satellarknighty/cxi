import java.util.Iterator;

/**
 * A class for the Hand of an opponent. Only the first Card is visible to the player,
 * the rest are hidden and marked with "X".
 */
public class OpponentsHand extends Hand {
    /**
     * Indicate if the person Stayed.
     */
    private boolean hasStayed;
    public OpponentsHand() {
        super();
        hasStayed = false;
    }
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

    /**
     * At the end of the round, call this function to reveal the current
     * Cards in the Hand and the current value.
     *
     * @return The String to be displayed on console.
     */
    public String reveal(){
        return super.toString();
    }
    /**
     * The opponent performs an action when this method is called. He can either hit,
     * stay, or even use power-ups, depending on which cards he currently has and which
     * cards his opponent has. The action is defined through a probability using an
     * instance of the Random class found in {@link Deck}. The reference to the opposing
     * Hand is still Work in progress.
     *
     * @param opposingHand  The opposing (or player's Hand in a Single Player game) to
     *                      refer to.
     * @param fromDeck      The Deck to draw the Card from.
     */
    /*
    12 -> 9 -> -1
    13 -> 8 -> -2
    14 -> 7 -> -3
    15 -> 6 -> -4
    16 -> 5 -> -5
    17 -> 4 -> -6
    18 -> 3 -> -7
    19 -> 2 -> -8
    20 -> 1 -> -9
    21 -> 0 -> -10
     */
    public void action(Hand opposingHand, Deck fromDeck){
        if (!hasStayed) {
            int probability = 10;
            probability -= (getTotalValue() - (LIMIT_BEFORE_BUST - probability)); //Now it will lie somewhere between 0 and >=10
            if (Deck.random.nextInt(1, 11) <= probability) {
                draw(fromDeck);
                System.out.println("Opponent hit!");
            }
            else {
                hasStayed = true;
                System.out.println("Opponent stayed!");
            }
        }
    }

    /**
     * Check if they have stayed or not.
     *
     * @return true if they stayed
     */
    public boolean checkStayed(){
        return hasStayed;
    }
}
