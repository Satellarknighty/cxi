import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * The Deck that contains the Cards to be drawn. The Cards in the Deck are unique and follows the LIFO principle.
 * Default number of Cards in this Deck is 13. Deck with custom number of Cards is not in the plan.
 */
public class Deck {
    /** The Cards remaining in the Deck. */
    private final Deque<Card> cards;
    /** Randomize the number of shuffles or the amount of cards in a cut. */
    private final Random random;
    /** Creates a new Deck. Cards will be added from Ace to King, as such King will stay on top of the Deck
     *  unless the method shuffle() or cut() is called. */
    public Deck(){
        cards = new ArrayDeque<>();
        for (String value : Card.CARD_VALUES){
            cards.offerFirst(new Card(value));
        }
        random = new Random();
    }

    /**
     * Draws the first Card of the Deck.
     * @return The first Card.
     */
    public Card draw(){
        return cards.pollFirst();
    }
    public void returnToTop(Card card){
        if (card.getDisplay().equals("A"))
            card.setValue(Card.ACE_VALUE_11);
        cards.offerFirst(card);
    }

    /**
     * Cut the Deck. The top half will be chosen randomly
     * and then moved under the bottom half (now top!).
     */
    public void cut(){
        int cuttingPoint = random.nextInt(Card.CARD_VALUES.length);
        cut(cuttingPoint);
    }

    /**
     * Cut the Deck. The top half will be chosen by the input
     * and then moved under the bottom half (now top!).
     * @param topHalf The amount of Cards in the to-be-moved half.
     */
    void cut(int topHalf){
        for (int card = 0; card < topHalf; card++) {
            cards.offerLast(cards.pollFirst());
        }
    }

    /**
     * Shuffle the Deck randomly for a random amount of time.
     * Limit is 99 times to avoid hindering the programm's speed.
     */
    public void shuffle(){
        int numberOfTime = random.nextInt(100);
        for (int time = 0; time < numberOfTime; time++) {
            cut();
        }
    }

    public Deque<Card> getCards() {
        return cards;
    }
}
