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
            final OpponentsHand opponentsHand = new OpponentsHand();
            final Parser userInput = new Parser();
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
     * either Hit or Stay. Afterwards if the Opponent hasn't stayed, it can decide to either hit or stay.
     * Both Hands are checked if they are busted after each hit.
     *
     * @param userInput     Valid inputs are "hit", "stay", "cheat", "reveal" and "shuffle", which are
     *                      case-insensitive. For "cheat", "peek" and "shuffle" read the source code
     *                      to see what they do.
     * @param deck          The Deck which contains the Cards for this Round.
     * @param playersHand   The Player's Hand.
     * @param opponentsHand The Opponent's Hand.
     * @return  When a winner is found, returns true.
     */
    private static boolean playRound(Parser userInput, Deck deck, Hand playersHand, OpponentsHand opponentsHand) {
        switch (userInput.getPlayCommand()){
            case HIT -> {
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
            }
            case STAY -> {
                while (!opponentsHand.checkStayed()){
                    opponentsHand.action(playersHand, deck);
                    if (opponentsHand.isBusted()){
                        showDown(playersHand, opponentsHand, true);
                        return true;
                    }
                }
                showDown(playersHand, opponentsHand, false);
                return true;
            }
            case SHUFFLE -> deck.shuffle();
            case CHEAT -> playersHand.returnLastDrawnCardToDeck(deck);
            case PEEK -> reveal(playersHand, opponentsHand);
            case QUIT -> {
                return true;
            }
            case UNKNOWN -> {
                System.out.println("Just say Hit or Stay man it's not that hard. \n");
                return false;
            }
        }
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
    private static boolean askGameOver(Parser userInput) {
        return userInput.getQuitCommand().equals(AvailableCommand.QUIT);
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
