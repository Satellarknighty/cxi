import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OpponentsHandTest {
    @Test
    void testHandWithTwoAndThree(){
        OpponentsHand testHand = new OpponentsHand();
        Hand playersHand = new Hand();
        Deck deck = new Deck();
        testHand.getCards().offerFirst(new Card("2"));
        testHand.getCards().offerFirst(new Card("3"));
        testHand.calculateTotalValue();
        testHand.action(playersHand, deck);
        assertThat(testHand.getCards().size()).isSameAs(3);
    }
    @Test
    void testHandWithAceAndKing(){
        OpponentsHand testHand = new OpponentsHand();
        Hand playersHand = new Hand();
        Deck deck = new Deck();
        testHand.getCards().offerFirst(new Card("A"));
        testHand.getCards().offerFirst(new Card("K"));
        testHand.calculateTotalValue();
        testHand.action(playersHand, deck);
        assertThat(testHand.getCards().size()).isSameAs(2);
    }
}
