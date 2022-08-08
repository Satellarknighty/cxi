/**
 * Calls the class game() to start a Single Player game.
 */
public class SinglePlayer {
    /**
     * A Single Player game will start after this method is called.
     */
    public static void game() {
        boolean gameOver = true;
        while (gameOver){
            final Deck deck = new Deck();
            deck.shuffle();
            Hand yourHand = new Hand();
            Hand opponentsHand = new OpponentsHand();
            yourHand.draw(deck);
            opponentsHand.draw(deck);
            yourHand.draw(deck);
            opponentsHand.draw(deck);
            System.out.println(yourHand);
            System.out.println(opponentsHand);
            gameOver = false;
        }
    }
}
