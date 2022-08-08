import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;


/**
 * The Hand of the player. Contains the drawn Cards, the current value of the Hand,
 * and if the Hand is Busted. Cards can be put back to the top of the Deck from here.
 */
public class Hand {
    /** The current value of the Hand. Automatically optimized in the case of Ace. */
    private int totalValue;
    /** The current Cards in this Hand. */
    private final Deque<Card> cards;
    /** Tells if this Hand is Busted. */
    private boolean isBusted;
    /** If the current value of the Hand exceeds this amount, the Hand is considered Busted. */
    private static final int LIMIT_BEFORE_BUST = 21;

    /**
     * Generates a new Hand. Current value is 0, and as such the Hand is not yet Busted.
     */
    public Hand(){
        totalValue = 0;
        cards = new ArrayDeque<>();
        isBusted = false;
    }

    /**
     * Adds a Card to the Hand from a Deck. Then check the value of the
     * @param fromDeck The Deck to add the Card from.
     */
    public void draw(Deck fromDeck){
        final Card drawnCard = fromDeck.draw();
        cards.offerFirst(drawnCard);
        calculateTotalValue();
    }

    /**
     * Calculate the current Value of the Hand, optimizing the Ace's value.
     * If the value exceeds 21, the Hand is Busted.
     */
    private void calculateTotalValue() {
        totalValue = 0;
        Card ace = null;
        for (Card card : cards) {
            if (card.getDisplay().equals("A")){
                ace = card;
                continue;
            }
            totalValue += card.getValue();
        }
        if (ace != null) {
            if (totalValue + Card.ACE_VALUE_11 > LIMIT_BEFORE_BUST)
                ace.setValue(Card.ACE_VALUE_1);
            else
                ace.setValue(Card.ACE_VALUE_11);
            totalValue += ace.getValue();
        }
        isBusted = totalValue > LIMIT_BEFORE_BUST;
    }

    /**
     * Return the latest drawn card back to the Deck.
     * @param toDeck    The Deck to return the card to.
     */
    public void returnLastDrawnCardToDeck(Deck toDeck) {
        if (!cards.isEmpty()) {
            Card removedCard = cards.pollFirst();
            toDeck.returnToTop(removedCard);
            calculateTotalValue();
        }
    }

    public int getTotalValue() {
        return totalValue;
    }

    public Deque<Card> getCards() {
        return cards;
    }

    public boolean isBusted() {
        return isBusted;
    }

    /**
     * Displays the current Cards in the Hand, and the current value.
     *
     * @return The String to be displayed on console.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator<Card> handIterator = cards.descendingIterator();
        while (handIterator.hasNext()){
            result.append(handIterator.next());
        }
        result.append("\t Value: ").append(totalValue);
        return result.toString();
    }
}
