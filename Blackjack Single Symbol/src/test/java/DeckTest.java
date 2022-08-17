import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeckTest {
    @Test
    void testNewDeck() {
        final Deck testDeck = new Deck();
        for (String display : Card.CARD_VALUES) {
            assertThat(testDeck.getCards()).contains(new Card(display));
        }

    }

    @Test
    void testDraw() {
        final Deck testDeck = new Deck();
        Card firstCard = testDeck.draw();
        assertThat(firstCard).isEqualTo(new Card("K"));
    }

    @Test
    void testDrawEmpty() {
        final Deck testDeck = new Deck();
        for (int i = 0; i < 13; i++) {
            testDeck.draw();
        }
        Card oobCard = testDeck.draw();
        assertThat(oobCard).isNull();
    }

    @Test
    void testReturnAceToTop() {
        final Deck testDeck = new Deck();
        final Card bottomCard = testDeck.getCards().pollLast();
        assert bottomCard != null;
        bottomCard.setValue(1);
        testDeck.returnToTop(bottomCard);
        assertThat(testDeck.draw()).isEqualTo(new Card("A"));
    }

    @Test
    void testCut1() {
        final Deck testDeck = new Deck();
        testDeck.cut(1);
        assertThat(testDeck.draw()).isEqualTo(new Card("Q"));
    }

    @Test
    void testCut2(){
        final Deck testDeck = new Deck();
        testDeck.cut(2);
        assertThat(testDeck.draw()).isEqualTo(new Card("J"));
        assertThat(testDeck.getCards().pollLast()).isEqualTo(new Card("K"));
    }
}
