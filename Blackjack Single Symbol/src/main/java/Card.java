import java.util.Objects;

/**
 * The playing Card. It doesn't have a suit. The value of a card is determined
 * according to the rules of Blackjack.
 */
public class Card {
    /** The number or letter that appears on the Card. */
    static final String[] CARD_VALUES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    /** The current value of the Card. */
    private int value;
    /** The number or letter that appears on this Card. */
    private final String display;
    /** Creates a new Card with the given display. The value of the Card will be determined
     * automatically. Aces are given the value of 11. */
    public Card (String display){
        this.display = display;
        this.value = switch (display){
            case "A" -> 11;
            case "J", "Q", "K" -> 10;
            default -> Integer.parseInt(display);
        };
    }

    public int getValue() {
        return value;
    }

    /**
     * Set the value of a Card. Used only for the Ace where the value could be 11, 10 or 1 so that it best
     * complements the player's Hand.
     * @param value The new value of the Card.
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
