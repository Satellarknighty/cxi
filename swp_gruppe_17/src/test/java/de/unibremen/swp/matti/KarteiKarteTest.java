package de.unibremen.swp.matti;

import de.unibremen.swp.matti.models.Flashcard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KarteiKarteTest {
    @Test
    void testNormal() {
        Flashcard testFlashcard = new Flashcard("Test Name", "Test Front", "Test Back");
        assertThat(testFlashcard.getName()).isEqualTo("Test Name");
        assertThat(testFlashcard.getFront()).isEqualTo("Test Front");
        assertThat(testFlashcard.getBack()).isEqualTo("Test Back");
    }

    @Test
    void testLink() {
        Flashcard testFlashcard = new Flashcard("Test Name", "Test Front", "Test Back");
        Flashcard testLinker = new Flashcard("Keyword", "Test Front", "Test Back");
        testFlashcard.link(testLinker);
        assertThat(testFlashcard.getLinked()).contains(testLinker);
    }
}
