package de.unibremen.swp.matti.logics;

import de.unibremen.swp.matti.models.CardBox;
import de.unibremen.swp.matti.models.Compartment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LearnLogicWaterfall extends LearnLogic {
    /**
     * Erzeugt eine neue Instanz der Lernlogik mit allgemein genutzten Parametern.
     */
    public LearnLogicWaterfall() {
        super();
    }

    /**
     * Überschreibt die Funktionen zur Vorbereitung des Lernvorgangs im Wasserfall-System.
     * @param initiallyRandomized Boolean Zustand ob die Karten im Startzustand bereits gemischt wurden.
     */
    @Override
    public void beforeLearning(boolean initiallyRandomized) {
        var current = datastore.getCurrentCardBox();
        super.toBeLearnedToday.addAll(current.getToBeLearned());
    }

    /**
     * Überschreibt die Funktion passend fürs Wasserfall-System, welches Fach der Getter als aktuelles Fach auswirft.
     * @return Fach das aktuell betrachtet wird.
     */
    @Override
    public String getCurrentCompartments() {
        StringBuilder builder = new StringBuilder("Aktuelles Fach:");
        builder.append(System.lineSeparator());
        var currentBox = datastore.getCurrentCardBox();
        if (currentBox.getDayLearned() == CardBox.FIRST_DAY
         || toBeLearnedToday.stream().anyMatch(card -> card.getCurrentCompartment().equals(Compartment.WATERFALL_HARD))){
            return builder.append(Compartment.WATERFALL_HARD.name()).toString();
        }
        return builder.append(Compartment.WATERFALL_EASY).toString();
    }

    /**
     * Bewegt die Karteikarte nach richtiger Antwort in ein FAch mit geringerer Schwierigkeit.
     */
    @Override
    public void rightAnswer() {
        moveCardToEasierCompartment();
        learningRepo.update(currentlyLearnedCard);
    }

    /**
     * Bewegt die Karteikarte nach falscher Antwort in ein Fach mit höherer Schwierigkeit.
     */
    @Override
    public void wrongAnswer() {
        moveCardToHarderCompartment();
        learningRepo.update(currentlyLearnedCard);
    }

    /**
     * Überprüft angepasst auf das Wasserfall-System ob der Lernvorgang abgeschlossen wurde.
     * @return Wahrheitswert: True falls der Lernvorgang beendet ist, false wenn nicht.
     */
    @Override
    public boolean checkLearnCompleted() {
        return datastore.getCurrentCardBox().getToBeLearned()
                .stream()
                .allMatch(card -> card.getCurrentCompartment().equals(Compartment.WATERFALL_DONE));
    }

    /**
     * Überschreibt wie die Funktion passend zum Wasserfall-System erkennt welche Karteikarten gelernt werden müssen.
     * @param currentBox Der Karteikasten der die zu lernenden Karteikarten enthält.
     */
    @Override
    protected void loadCardsIntoToBeLearnedToday(CardBox currentBox) {
        if (currentBox.getToBeLearned()
                .stream()
                .anyMatch(card -> card.getCurrentCompartment().equals(Compartment.WATERFALL_HARD))){
            toBeLearnedToday.addAll(currentBox.getToBeLearned()
                    .stream()
                    .filter(card -> card.getCurrentCompartment().equals(Compartment.WATERFALL_HARD))
                    .toList());
        }
        else {
            toBeLearnedToday.addAll(currentBox.getToBeLearned());
        }
    }

    private void moveCardToEasierCompartment() {
        var comp = currentlyLearnedCard.getCurrentCompartment();
        currentlyLearnedCard.setCurrentCompartment(switch (comp){
            case WATERFALL_HARD -> Compartment.WATERFALL_EASY;
            case WATERFALL_EASY -> Compartment.WATERFALL_DONE;
            default -> throw new RuntimeException("wtf");
        });
    }
    private void moveCardToHarderCompartment() {
        var comp = currentlyLearnedCard.getCurrentCompartment();
        currentlyLearnedCard.setCurrentCompartment(switch (comp){
            case WATERFALL_HARD -> comp;
            case WATERFALL_EASY -> Compartment.WATERFALL_HARD;
            default -> throw new RuntimeException("wtf");
        });
    }
}
