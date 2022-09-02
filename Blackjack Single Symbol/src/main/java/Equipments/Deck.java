package Equipments;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * The Equipments.Deck that contains the Cards to be drawn. The Cards in the Equipments.Deck are unique and follows the LIFO principle.
 * Default number of Cards in this Equipments.Deck is 13. Equipments.Deck with custom number of Cards is not in the plan.
 */
public class Deck {
    /** The Cards remaining in the Equipments.Deck. */
    private final Deque<Card> cards;
    /** Randomize the number of shuffles or the amount of cards in a cut. Also used in randomizing the actions of
     * the opponent. */
    static final Random random = new Random();
    /** Creates a new Equipments.Deck. Cards will be added from Ace to King, as such King will stay on top of the Equipments.Deck
     *  unless the method shuffle() or cut() is called. */
    public Deck(){
        cards = new ArrayDeque<>();
        for (String value : Card.CARD_VALUES){
            cards.offerFirst(new Card(value));
        }
    }

    /**
     * Draws the first Equipments.Card of the Equipments.Deck.
     * @return The first Equipments.Card.
     */
    public Card draw(){
        return cards.pollFirst();
    }

    /**
     * Returns the passed in Equipments.Card back to the top of the Equipments.Deck. If it is an Ace,
     * makes sure that Equipments.Card has its original value of 11.
     *
     * @param card  The returned Equipments.Card.
     */
    public void returnToTop(Card card){
        if (card.getDisplay().equals("A"))
            card.setValue(Card.ACE_VALUE_11);
        cards.offerFirst(card);
    }

    /**
     * Cut the Equipments.Deck. The top half will be chosen randomly
     * and then moved under the bottom half (now top!).
     */
    public void cut(){
        int cuttingPoint = random.nextInt(cards.size());
        cut(cuttingPoint);
    }

    /**
     * Cut the Equipments.Deck. The top half will be chosen by the input
     * and then moved under the bottom half (now top!).
     * @param topHalf The amount of Cards in the to-be-moved half.
     */
    public void cut(int topHalf){
        Deque<Card> secondStack = new ArrayDeque<>();
        for (int card = 0; card < topHalf; card++) {
            secondStack.offerFirst(cards.pollFirst());
        }
        while (!secondStack.isEmpty()){
            cards.offerLast(secondStack.pollFirst());
        }
    }

    /**
     * Shuffle the Equipments.Deck randomly for a random amount of time.
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
