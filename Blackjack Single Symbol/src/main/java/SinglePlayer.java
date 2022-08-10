import java.util.Scanner;

/**
 * Calls the class game() to start a Single Player game.
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
            System.out.println("Okay bye!");
        }
    }

    private static boolean playRound(Scanner userInput, Deck deck, Hand playersHand, Hand opponentsHand) {
        System.out.println("Hit or Stay? \n> ");
        final String input = userInput.nextLine();
        if (input.equalsIgnoreCase("Hit")){
            playersHand.draw(deck);
            if (playersHand.isBusted()){
                System.out.println("You busted!");
                showDown(playersHand,(OpponentsHand) opponentsHand, true);
                return true;
            }
            opponentsHand.draw(deck);
            if (playersHand.isBusted()){
                System.out.println("Opponent busted!");
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
        else
            System.out.println("Just say Hit or Stay man it's not that hard.");
        gameState(playersHand, opponentsHand);
        return false;
    }

    private static boolean askGameOver(Scanner userInput) {
        System.out.println("Play again? (Y/N)");
        final String input = userInput.nextLine();
        return input.equalsIgnoreCase("N") || input.contains("no") || input.contains("No");
    }

    private static void setUpBoard(Deck deck, Hand firstHand, Hand secondHand){
        deck.shuffle();
        firstHand.draw(deck);
        secondHand.draw(deck);
        firstHand.draw(deck);
        secondHand.draw(deck);
        gameState(firstHand, secondHand);
    }
    private static void gameState(Hand firstHand, Hand secondHand){
        System.out.println("You: \t" + firstHand);
        System.out.println("Opp: \t" + secondHand);
    }
    private static void showDown(Hand firstHand, OpponentsHand secondHand, boolean busted){
        if (!busted){
            System.out.println(firstHand.getTotalValue() < secondHand.getTotalValue() ?
                    "You lose!" : "You win!");
        }
        System.out.println("You: \t" + firstHand);
        System.out.println("Opp: \t" + secondHand.reveal());
    }
}
