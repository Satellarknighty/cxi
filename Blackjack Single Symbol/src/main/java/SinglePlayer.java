import Equipments.Deck;
import Equipments.Hand;
import Equipments.OpponentsHand;
import Parser.AvailableCommand;
import Parser.Parser;

/**
 * Call the class game() to start a Single Player game. I hate myself for what I wrote here.
 * And no you are not getting a Test unit for this class.
 */
public class SinglePlayer {
    /**
     * A Single Player game will start after this method is called.
     */
    public static void game() {
        boolean gameOver = false;
        final Parser userInput = new Parser();
        while (!gameOver){
            final Deck deck = new Deck();
            final Hand playersHand = new Hand();
            final OpponentsHand opponentsHand = new OpponentsHand();
            setUpBoard(deck, playersHand, opponentsHand);
            boolean roundOver = false;
            while (!roundOver){
                roundOver = playRound(userInput, deck, playersHand, opponentsHand);
            }
            gameOver = askGameOver(userInput);
        }
        System.out.println("Okay bye!\nType anything to quit.");
        userInput.getScanner().nextLine();
    }

    /**
     * Manages the stuff that happens in a round of Blackjack. First an Equipments.Deck is created,
     * then 2 Hands, and each Equipments.Hand is dealt two Cards. The Player will be prompted to
     * either Hit or Stay. Afterwards if the Opponent hasn't stayed, it can decide to either hit or stay.
     * Both Hands are checked if they are busted after each hit.
     *
     * @param userInput     Valid inputs are "hit", "stay", "cheat", "reveal" and "shuffle", which are
     *                      case-insensitive. For "cheat", "peek" and "shuffle" read the source code
     *                      to see what they do.
     * @param deck          The Equipments.Deck which contains the Cards for this Round.
     * @param playersHand   The Player's Equipments.Hand.
     * @param opponentsHand The Opponent's Equipments.Hand.
     * @return  When a winner is found, returns true.
     */
    private static boolean playRound(Parser userInput, Deck deck, Hand playersHand, OpponentsHand opponentsHand) {
        switch (userInput.getPlayCommand()){
            case HIT -> {
                if (whenPlayerHits(deck, playersHand, opponentsHand))
                    return true;
            }
            case STAY -> {
                whenPlayerStays(deck, playersHand, opponentsHand);
                return true;
            }
            case SHUFFLE -> deck.shuffle();
            case CHEAT -> playersHand.returnLastDrawnCardToDeck(deck);
            case PEEK -> {
                reveal(playersHand, opponentsHand);
                return false;
            }
            case QUIT -> {
                return true;
            }
            case UNKNOWN -> {
                System.out.println("Just say Hit or Stay man it's not that hard. \n");
                return false;
            }
            case HELP -> {
                userInput.helpDuringPlay();
                return false;
            }
        }
        gameState(playersHand, opponentsHand);
        return false;
    }

    /**
     * Used when the Player decides to hit. They will draw a Card, and their Hand will be checked for
     * Bust. Then it is the opponent's turn to act.
     *
     * @param deck          The Deck to draw the Card from.
     * @param playersHand   The player's Hand.
     * @param opponentsHand The opponent's Hand.
     * @return  True if either Hand is busted after drawing a Card.
     */
    private static boolean whenPlayerHits(Deck deck, Hand playersHand, OpponentsHand opponentsHand){
        playersHand.draw(deck);
        if (playersHand.isBusted()){
            showDown(playersHand, opponentsHand, true);
            return true;
        }
        if (!opponentsHand.checkStayed()){
            opponentsHand.action(playersHand, deck);
            if (opponentsHand.isBusted()){
                showDown(playersHand, opponentsHand, true);
                return true;
            }
        }
        return false;
    }

    /**
     * Used when the player stays. The opponent will continue to act until it stays
     * or is busted.
     *
     * @param deck          The Deck to draw the Card from.
     * @param playersHand   The player's Hand.
     * @param opponentsHand The opponent's Hand.
     */
    private static void whenPlayerStays(Deck deck, Hand playersHand, OpponentsHand opponentsHand){
        while (!opponentsHand.checkStayed()){
            opponentsHand.action(playersHand, deck);
            if (opponentsHand.isBusted()){
                showDown(playersHand, opponentsHand, true);
                return;
            }
        }
        showDown(playersHand, opponentsHand, false);
    }

    /**
     * Asks the Player if they would like to play another Round.
     *
     * @param userInput Valid inputs to stop the game are "n" which is case-
     *                  insensitive, "no" and "No". Any other inputs will start
     *                  a new Round.
     * @return  true if the Player wants to quit.
     */
    private static boolean askGameOver(Parser userInput) {
        return userInput.getQuitCommand().equals(AvailableCommand.QUIT);
    }

    /**
     * Set the game up by shuffling the Equipments.Deck and dealing each Equipments.Hand two Cards.
     *
     * @param deck          The Equipments.Deck of Cards, to be shuffled.
     * @param firstHand     The Player's Equipments.Hand.
     * @param secondHand    The Opponent's Equipments.Hand.
     */
    private static void setUpBoard(Deck deck, Hand firstHand, Hand secondHand){
        deck.shuffle();
        firstHand.draw(deck);
        secondHand.draw(deck);
        firstHand.draw(deck);
        secondHand.draw(deck);
        gameState(firstHand, secondHand);
    }

    /**
     * Shows the state of two Hands on console, with the secondHand (opponent's)
     * being hidden.
     *
     * @param firstHand     The Player's Equipments.Hand.
     * @param secondHand    The Opponent's Equipments.Hand.
     */
    private static void gameState(Hand firstHand, Hand secondHand){
        System.out.println("You: \t" + firstHand);
        System.out.println("Opp: \t" + secondHand);
    }

    /**
     * Shows the state of two Hands on console when the Round concludes. The secondHand
     * is now fully shown.
     *
     * @param firstHand     The Player's Equipments.Hand.
     * @param secondHand    The Opponent's Equipments.Hand.
     * @param busted        If any of the two Players busted.
     */
    private static void showDown(Hand firstHand, OpponentsHand secondHand, boolean busted){
        if (!busted){
            if (firstHand.getTotalValue() == secondHand.getTotalValue()){
                System.out.println("It's a draw.");
            }
            else {
                System.out.println(firstHand.getTotalValue() < secondHand.getTotalValue() ?
                        "You lose!" : "You win!");
            }
        }
        else {
            System.out.println(firstHand.isBusted() ? "You busted! You lose!" :
                    "Opponent busted! You win!");
        }
        reveal(firstHand, secondHand);
    }

    /**
     * Same as gameState() but the secondHand is fully shown.
     *
     * @param firstHand     The Player's Equipments.Hand.
     * @param secondHand    The Opponent's Equipments.Hand.
     */
    private static void reveal(Hand firstHand, OpponentsHand secondHand){
        System.out.println("You: \t" + firstHand);
        System.out.println("Opp: \t" + secondHand.reveal());
    }
}
