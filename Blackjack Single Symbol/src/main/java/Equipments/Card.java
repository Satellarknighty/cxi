package Equipments;

import java.util.Objects;

/**
 * The playing Equipments.Card. It doesn't have a suit. The value of a card is determined
 * according to the rules of Blackjack. As in, cards from 2 to 10 have face value,
 * J, Q, K have value of 10, and Ace are either 11 or 1.
 */
public class Card {
    /** The number or letter that appears on the Equipments.Card. */
    public static final String[] CARD_VALUES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    /** The current value of the Equipments.Card. */
    private int value;
    /** The number or letter that appears on this Equipments.Card. */
    private final String display;
    /** Constant for the 11 value of Ace. */
    public static final int ACE_VALUE_11 = 11;
    /** Constant for the 1 value of Ace. */
    public static final int ACE_VALUE_1 = 1;
    /** Constant for the 10 value of Jack, Queen and King. */
    public static final int JQK_VALUE = 10;

    /**
     * Creates a new Equipments.Card with the given display. The value of the Equipments.Card will be determined
     * automatically. Aces are given the initial value of 11.
     * @param display The face of the Equipments.Card.
     */
    public Card (String display){
        this.display = display;
        this.value = switch (display){
            case "A" -> ACE_VALUE_11;
            case "J", "Q", "K" -> JQK_VALUE;
            default -> Integer.parseInt(display);
        };
    }

    /**
     * Purely for testing purposes of the Ace Equipments.Card.
     * @param display   The face of the Equipments.Card.
     * @param value     The value of the Equipments.Card.
     */
    public Card(String display, int value){
        this.display = display;
        this.value = value;
    }
    @Override
    public String toString() {
        return "[" + display + "]";
    }

    public int getValue() {
        return value;
    }

    /**
     * Set the value of a Equipments.Card. Used only for the Ace where the value could be 11 or 1 so that it best
     * complements the player's Equipments.Hand.
     * @param value The new value of the Equipments.Card.
     */
    public void setValue(int value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && Objects.equals(display, card.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, display);
    }

}
