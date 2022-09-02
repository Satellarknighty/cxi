package Parser;

public enum AvailableCommand {
    /**
     * Enum for hit (drawing a Equipments.Card).
     */
    HIT,
    /**
     * Enum for stay.
     */
    STAY,
    /**
     * Enum to return a Equipments.Card on your Equipments.Hand to the Equipments.Deck (for now).
     */
    CHEAT,
    /**
     * Enum to shuffle the Equipments.Deck.
     */
    SHUFFLE,
    /**
     * Enum to look at the top Equipments.Card of the Equipments.Deck.
     */
    PEEK,
    /**
     * Enum to quit the game.
     */
    QUIT,
    /**
     * Enum to continue playing the game.
     */
    YES,
    /**
     * Enum for invalid inputs.
     */
    UNKNOWN,
    /**
     * Enum for listing available Commands.
     */
    HELP
}
