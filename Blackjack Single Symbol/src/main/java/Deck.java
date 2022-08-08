import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * The Deck that contains the Cards to be drawn. The Cards in the Deck are unique and follows the LIFO principle.
 * Default number of Cards in this Deck is 13. Deck with custom number of Cards is not in the plan.
 */
public class Deck {
    /** The Cards remaining in the Deck. */
    private Deque<Card> cards;
    /** Creates a new Deck. Cards will be added from Ace to King, as such King will stay on top of the Deck
     *  unless the method shuffle() or cut() is called. */
    public Deck(){
        cards = new ArrayDeque<>();
        for (String value : Card.CARD_VALUES){
            cards.offerFirst(new Card(value));
        }
    }

    /**
     * Draws the first Card of the Deck.
     * @return The first Card.
     */
    public Card draw(){
        return cards.pollFirst();
    }
    public Deque<Card> getCards() {
        return cards;
    }

    /**
     * Cut the Deck. The top half will be chosen randomly
     * and then moved under the bottom half (now top!).
     */
    public void cut(){
        Random random = new Random();
        final int cuttingPoint = random.nextInt(Card.CARD_VALUES.length);
        cut(cuttingPoint);
    }

    /**
     * Cut the Deck. The top half will be chosen by the input
     * and then moved under the bottom half (now top!).
     * @param topHalf The amount of Cards in the to-be-moved half.
     */
    void cut(int topHalf){
        for (int time = 0; time < topHalf; time++) {
            cards.offerLast(cards.pollFirst());
        }
    }
}
