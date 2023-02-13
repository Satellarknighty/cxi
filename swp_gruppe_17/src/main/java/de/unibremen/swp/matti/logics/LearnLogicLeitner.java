package de.unibremen.swp.matti.logics;

import de.unibremen.swp.matti.models.CardBeingLearned;
import de.unibremen.swp.matti.models.CardBox;
import de.unibremen.swp.matti.models.Compartment;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;


@Slf4j
public class LearnLogicLeitner extends LearnLogic {
    /**
     * Kreiert eine neue Instanz der Lernlogik mit allgemeingültig genutzten Parametern.
     */
    public LearnLogicLeitner() {
        super();
    }

    /**
     * Mischt ggf die zu lernenden Karteikarten und fügt sie den zu lernenden Fächern hinzu.
     * @param initiallyRandomized Boolean Zustand ob die Karten im Startzustand bereits gemischt wurden.
     */
    @Override
    public void beforeLearning(boolean initiallyRandomized) {
        var all = datastore.getCurrentCardBox().getToBeLearned();
        putFlashcardsFromCategoriesIntoDailyCompartment(all);
        if (!initiallyRandomized){
            Collections.shuffle(all);
        }
        super.toBeLearnedToday.addAll(all);
    }

    /**
     * Getter für die aktuellen Fächer im Leitnersystem.
     * @return String mit den Namen der aktuellen Fächer.
     */
    @Override
    public String getCurrentCompartments() {
        int day = datastore.getCurrentCardBox().getDayLearned();
        StringBuilder builder = new StringBuilder("Aktuelle Fächer: ");
        builder.append(System.lineSeparator());
        if (day % 7 == 0){
            builder.append(Compartment.LEITNER_WEEKLY.name()).append(System.lineSeparator());
        }
        if (day % 2 == 0){
            builder.append(Compartment.LEITNER_EVERY_OTHER_DAY.name()).append(System.lineSeparator());
        }
        builder.append(Compartment.LEITNER_DAILY);
        return builder.toString();
    }

    /**
     * Bewegt die Karteikarte bei richtiger Antwort in ein nächstes Fach und aktualisiert die Betrachtung zur nächsten Karteikarte.
     */
    @Override
    public void rightAnswer() {
        moveCardToNextCompartment();
        learningRepo.update(currentlyLearnedCard);
    }

    /**
     * Bewegt die Karteikarte bei falscher Antwort in ein voriges Fach und aktualisiert die Betrachtung zur nächsten Karteikarte.
     */
    @Override
    public void wrongAnswer() {
        moveCardToPreviousCompartment();
        learningRepo.update(currentlyLearnedCard);
    }

    /**
     * Überprüft fürs Leitnersystem ob der Lernvorgang abgeschlossen ist
     * @return Wahrheitswert ob der Lernvorgang beendet ist: True falls ja, False wenn nicht.
     */
    @Override
    public boolean checkLearnCompleted() {
        return datastore.getCurrentCardBox().getToBeLearned()
                .stream()
                .allMatch(card -> card.getCurrentCompartment().equals(Compartment.LEITNER_DONE));
    }

    /**
     * Enthält die Bedingungen wie die Karteikarten im Leitnersystem hinzugefügt werden.
     * @param currentBox Der Karteikasten der die zu lernenden Karteikarten enthält.
     */
    protected void loadCardsIntoToBeLearnedToday(CardBox currentBox) {
        int dayLearned = currentBox.getDayLearned();
        List<CardBeingLearned> weekly = getToBeLearnedFromCompartment(currentBox, Compartment.LEITNER_WEEKLY);
        List<CardBeingLearned> everyOtherDay = getToBeLearnedFromCompartment(currentBox, Compartment.LEITNER_EVERY_OTHER_DAY);
        List<CardBeingLearned> daily = getToBeLearnedFromCompartment(currentBox, Compartment.LEITNER_DAILY);
        if (dayLearned % 7 == 0){
            toBeLearnedToday.addAll(weekly);
        }
        if (dayLearned % 2 == 0) {
            toBeLearnedToday.addAll(everyOtherDay);
        }
        toBeLearnedToday.addAll(daily);
    }

    private List<CardBeingLearned> getToBeLearnedFromCompartment(CardBox currentBox, Compartment comp) {
        return currentBox.getToBeLearned()
                .stream()
                .filter(card -> card.getCurrentCompartment().equals(comp))
                .toList();
    }

    private void moveCardToPreviousCompartment() {
        var comp = currentlyLearnedCard.getCurrentCompartment();
        currentlyLearnedCard.setCurrentCompartment(switch (comp){
            case LEITNER_DAILY -> comp;
            case LEITNER_EVERY_OTHER_DAY -> Compartment.LEITNER_DAILY;
            case LEITNER_WEEKLY -> Compartment.LEITNER_EVERY_OTHER_DAY;
            default -> throw new RuntimeException("wtf");
        });
    }

    private void moveCardToNextCompartment() {
        var comp = currentlyLearnedCard.getCurrentCompartment();
        currentlyLearnedCard.setCurrentCompartment(switch (comp){
            case LEITNER_DAILY -> Compartment.LEITNER_EVERY_OTHER_DAY;
            case LEITNER_EVERY_OTHER_DAY -> Compartment.LEITNER_WEEKLY;
            case LEITNER_WEEKLY -> Compartment.LEITNER_DONE;
            default -> throw new RuntimeException("wtf");
        });
    }

    private void putFlashcardsFromCategoriesIntoDailyCompartment(List<CardBeingLearned> toBeLearned) {
        toBeLearned.forEach(card -> {
            card.setCurrentCompartment(Compartment.LEITNER_DAILY);
            learningRepo.update(card);
        });
    }
}
