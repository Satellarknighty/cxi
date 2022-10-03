package Equipments;

import java.util.Iterator;
import java.util.Objects;

/**
 * A class for the Equipments.Hand of an opponent. Only the first Equipments.Card is visible to the player,
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
     * Displays the current Cards in the Equipments.Hand, and the current value.
     * As this is the opponent's Equipments.Hand, the second Equipments.Card onwards will be
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
     * Cards in the Equipments.Hand and the current value.
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
     * Equipments.Hand is still Work in progress.
     *
     * @param opposingHand  The opposing (or player's Equipments.Hand in a Single Player game) to
     *                      refer to.
     * @param fromDeck      The Equipments.Deck to draw the Equipments.Card from.
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
            final float probability = calculateProbability(opposingHand, fromDeck); //Now it will lie somewhere between 0 and >=10
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

    private float calculateProbability(Hand opposingHand, Deck deck){
//        return getTotalValue() - (LIMIT_BEFORE_BUST - currentProbability);
        final int valueOfBestCard = LIMIT_BEFORE_BUST - getTotalValue();
        if (valueOfBestCard >= Card.JQK_VALUE)  return valueOfBestCard;
        int numberOfGoodCards = valueOfBestCard;
        for (Card card : getCards()) {
            int cardValue = card.getValue();
            if ((cardValue == Card.ACE_VALUE_11 ? Card.ACE_VALUE_1 : cardValue) <= valueOfBestCard)
                numberOfGoodCards--;
        }
        if (Objects.requireNonNull(opposingHand.getCards().peekLast()).getValue() <= valueOfBestCard)
            numberOfGoodCards--;
        return (float) numberOfGoodCards / deck.getCards().size();
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
