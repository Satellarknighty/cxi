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
    void testCut1() {
        final Deck testDeck = new Deck();
        testDeck.cut(1);
        assertThat(testDeck.draw()).isEqualTo(new Card("Q"));
    }
}
