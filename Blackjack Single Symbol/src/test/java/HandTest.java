import Equipments.Card;
import Equipments.Deck;
import Equipments.Hand;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;


import static org.assertj.core.api.Assertions.assertThat;

public class HandTest {
    @Test
    void testDraw1() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(10);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isFalse();
    }

    @Test
    void testDraw2() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        testHand.draw(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(20);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        expectedHand.offerFirst(new Card("Q"));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isFalse();
    }

    @Test
    void testDraw2KingAndAce() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        Card ace = deck.getCards().pollLast();
        assert ace != null;
        deck.returnToTop(ace);
        testHand.draw(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(21);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        expectedHand.offerFirst(new Card("A"));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isFalse();
    }

    @Test
    void testDraw3AndBust() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        testHand.draw(deck);
        testHand.draw(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(30);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        expectedHand.offerFirst(new Card("Q"));
        expectedHand.offerFirst(new Card("J"));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isTrue();
    }

    @Test
    void testDraw3AndReturnLastDrawn() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        testHand.draw(deck);
        testHand.draw(deck);
        testHand.returnLastDrawnCardToDeck(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(20);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        expectedHand.offerFirst(new Card("Q"));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isFalse();
    }

    @Test
    void testDraw3KingQueenAndAce() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        testHand.draw(deck);
        Card ace = deck.getCards().pollLast();
        assert ace != null;
        deck.returnToTop(ace);
        testHand.draw(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(21);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        expectedHand.offerFirst(new Card("Q"));
        expectedHand.offerFirst(new Card("A", 1));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isFalse();
    }

    @Test
    void testDraw3KingAceAndQueen() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        Card ace = deck.getCards().pollLast();
        assert ace != null;
        deck.returnToTop(ace);
        testHand.draw(deck);
        testHand.draw(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(21);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        expectedHand.offerFirst(new Card("A", 1));
        expectedHand.offerFirst(new Card("Q"));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isFalse();
    }

    @Test
    void testDraw3KingAceAndQueenThenReturnQueenBackToDeck() {
        Deck deck = new Deck();
        Hand testHand = new Hand();
        testHand.draw(deck);
        Card ace = deck.getCards().pollLast();
        assert ace != null;
        deck.returnToTop(ace);
        testHand.draw(deck);
        testHand.draw(deck);
        testHand.returnLastDrawnCardToDeck(deck);
        assertThat(testHand.getTotalValue()).isEqualTo(21);
        Deque<Card> expectedHand = new ArrayDeque<>();
        expectedHand.offerFirst(new Card("K"));
        expectedHand.offerFirst(new Card("A"));
        assertThat(CompareTwoObjects.thatAreDeque(testHand.getCards(), expectedHand)).isTrue();
        assertThat(testHand.isBusted()).isFalse();
    }
}
