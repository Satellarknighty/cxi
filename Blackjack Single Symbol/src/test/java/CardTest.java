import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CardTest {
    @Test
    void testCard2() {
        Card testCard = new Card("2");
        assertThat(testCard.getDisplay()).isEqualTo("2");
        assertThat(testCard.getValue()).isEqualTo(2);
    }

    @Test
    void testCard10() {
        Card testCard = new Card("10");
        assertThat(testCard.getDisplay()).isEqualTo("10");
        assertThat(testCard.getValue()).isEqualTo(10);
    }

    @Test
    void testCardJ() {
        Card testCard = new Card("J");
        assertThat(testCard.getDisplay()).isEqualTo("J");
        assertThat(testCard.getValue()).isEqualTo(10);
    }

    @Test
    void testCardA() {
        Card testCard = new Card("A");
        assertThat(testCard.getDisplay()).isEqualTo("A");
        assertThat(testCard.getValue()).isEqualTo(11);
    }

    @Test
    void test2Cards() {
        Card testCard = new Card("A");
        Card expectedCard = new Card("A");
        assertThat(testCard).isEqualTo(expectedCard);
    }
}
