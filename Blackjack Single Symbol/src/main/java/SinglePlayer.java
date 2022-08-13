import java.util.Scanner;

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
        while (!gameOver){
            final Deck deck = new Deck();
            final Hand playersHand = new Hand();
            final Hand opponentsHand = new OpponentsHand();
            final Scanner userInput = new Scanner(System.in);
            setUpBoard(deck, playersHand, opponentsHand);
            boolean roundOver = false;
            while (!roundOver){
                roundOver = playRound(userInput, deck, playersHand, opponentsHand);
            }
            gameOver = askGameOver(userInput);
        }
        System.out.println("Okay bye!");
    }

    /**
     * Manages the stuff that happens in a round of Blackjack. First a Deck is created,
     * then 2 Hands, and each Hand is dealt two Cards. The Player will be prompted to
     * either Hit or Stay. If the Player hits then a Card is added to their Hand, and afterwards
     * a Card is added to the Opponent's Hand. Both Hands are checked if they bust. If the Player
     * stays then the Opponent would draw as many Cards as it can until it busts, then it will
     * return the last drawn Card back to the Deck and two Hands are compared for value.
     *
     * @param userInput     Valid inputs are "hit", "stay", "cheat", "reveal" and "shuffle", which are
     *                      case-insensitive. For "cheat", "peek" and "shuffle" read the source code
     *                      to see what they do.
     * @param deck          The Deck which contains the Cards for this Round.
     * @param playersHand   The Player's Hand.
     * @param opponentsHand The Opponent's Hand.
     * @return  When a winner is found, returns true.
     */
    private static boolean playRound(Scanner userInput, Deck deck, Hand playersHand, Hand opponentsHand) {
        System.out.println("Hit or Stay? \n> ");
        final String input = userInput.nextLine();
        if (input.equalsIgnoreCase("Hit")){
            playersHand.draw(deck);
            if (playersHand.isBusted()){
                showDown(playersHand,(OpponentsHand) opponentsHand, true);
                return true;
            }
            opponentsHand.draw(deck);
            if (opponentsHand.isBusted()){
                showDown(playersHand,(OpponentsHand) opponentsHand, true);
                return true;
            }
        }
        else if (input.equalsIgnoreCase("Stay")){
            while (!opponentsHand.isBusted()){
                opponentsHand.draw(deck);
            }
            opponentsHand.returnLastDrawnCardToDeck(deck);
            showDown(playersHand, (OpponentsHand) opponentsHand, false);
            return true;
        }
        else if (input.equalsIgnoreCase("Shuffle"))
            deck.shuffle();
        else if (input.equalsIgnoreCase("Cheat"))
            playersHand.returnLastDrawnCardToDeck(deck);
        else if (input.equalsIgnoreCase("Peek")) {
            reveal(playersHand, (OpponentsHand) opponentsHand);
        } else
            System.out.println("Just say Hit or Stay man it's not that hard.");
        gameState(playersHand, opponentsHand);
        return false;
    }

    /**
     * Asks the Player if they would like to play another Round.
     *
     * @param userInput Valid inputs to stop the game are "n" which is case-
     *                  insensitive, "no" and "No". Any other inputs will start
     *                  a new Round.
     * @return  true if the Player wants to quit.
     */
    private static boolean askGameOver(Scanner userInput) {
        System.out.println("Play again? (Y/N) \n> ");
        final String input = userInput.nextLine();
        return input.equalsIgnoreCase("N") || input.contains("no") || input.contains("No");
    }

    /**
     * Set the game up by shuffling the Deck and dealing each Hand two Cards.
     *
     * @param deck          The Deck of Cards, to be shuffled.
     * @param firstHand     The Player's Hand.
     * @param secondHand    The Opponent's Hand.
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
     * @param firstHand     The Player's Hand.
     * @param secondHand    The Opponent's Hand.
     */
    private static void gameState(Hand firstHand, Hand secondHand){
        System.out.println("You: \t" + firstHand);
        System.out.println("Opp: \t" + secondHand);
    }

    /**
     * Shows the state of two Hands on console when the Round concludes. The secondHand
     * is now fully shown.
     *
     * @param firstHand     The Player's Hand.
     * @param secondHand    The Opponent's Hand.
     * @param busted        If any of the two Players busted.
     */
    private static void showDown(Hand firstHand, OpponentsHand secondHand, boolean busted){
        if (!busted){
            System.out.println(firstHand.getTotalValue() < secondHand.getTotalValue() ?
                    "You lose!" : "You win!");
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
     * @param firstHand     The Player's Hand.
     * @param secondHand    The Opponent's Hand.
     */
    private static void reveal(Hand firstHand, OpponentsHand secondHand){
        System.out.println("You: \t" + firstHand);
        System.out.println("Opp: \t" + secondHand.reveal());
    }
}
