package de.unibremen.swp.matti.logics;

import de.unibremen.swp.matti.models.CardBeingLearned;
import de.unibremen.swp.matti.models.CardBox;
import de.unibremen.swp.matti.models.Category;
import de.unibremen.swp.matti.models.Compartment;
import de.unibremen.swp.matti.persistence.CardBeingLearnedRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.LinkedList;


@Slf4j
public abstract class LearnLogic extends BusinessLogic {
    protected LinkedList<CardBeingLearned> toBeLearnedToday;
    protected CardBeingLearned currentlyLearnedCard;
    protected static final CardBeingLearnedRepository learningRepo;
    static {
        learningRepo = new CardBeingLearnedRepository();
    }
    protected LearnLogic() {
        toBeLearnedToday = new LinkedList<>();
    }

    /**
     * Prüft, ob die Karteikarten schon gemischt wurden und leitet dies gegebenenfalls ein.
     * @param initiallyRandomized Boolean Zustand ob die Karten im Startzustand bereits gemischt wurden.
     */
    public abstract void beforeLearning(boolean initiallyRandomized);

    /**
     * Getter für die Vorderseite der aktuellen Karteikarte.
     * @return String für den Vorderseitentext.
     */
    public String getFrontOfCurrentCard() {
        return currentlyLearnedCard.getCard().getFront();
    }

    /**
     * Getter für den mitgezählten Lerntag des Lernvorgangs.
     * @return String der den Tag ansagt.
     */
    public String getDay() {
        return "Tag: " + datastore.getCurrentCardBox().getDayLearned();
    }

    /**
     * Getter für einen String mit den Karteikarten aus dem aktuellen Fach im Karteikasten.
     * @return String mit Karteikartennamen.
     */
    public abstract String getCurrentCompartments();

    /**
     * Getter für die Anzahl der verbliebenen zu lernenden Karten.
     * @return String der ansagt wie viele Karten noch verblieben sind.
     */
    public String getCardsLeft() {
        return "Karteikarten übrig: " + toBeLearnedToday.size();
    }

    /**
     * Entfernt die letzte Karte aus dem Stapel der noch zu lernenden Karten und betrachtet anschließend die nächste.
     */
    public void nextCard() {
        currentlyLearnedCard = toBeLearnedToday.removeFirst();
    }

    /**
     * Getter für die Rückseite der aktuellen Karteikarte.
     * @return String mit dem Text der Rückseite.
     */
    public String getBackOfCurrentCard() {
        return currentlyLearnedCard.getCard().getBack();
    }

    /**
     * Wasserfallsystem-Funktion: Initiiert das Bewegen der Karteikarte in ein Fach höherer Schwierigkeit.
     */
    public void preparePhaseMoveCardToHardCompartment() {
        currentlyLearnedCard.setCurrentCompartment(Compartment.WATERFALL_HARD);
        learningRepo.update(currentlyLearnedCard);
    }

    /**
     * Wasserfallsystem-Funktion: Initiiert das Bewegen der aktuellen Karteikarte in ein Fach niedrigerer Schwierigkeit.
     */
    public void preparePhaseMoveCardToEasyCompartment() {
        currentlyLearnedCard.setCurrentCompartment(Compartment.WATERFALL_EASY);
        learningRepo.update(currentlyLearnedCard);
    }

    /**
     * Wasserfallsystem-Funktion: Initiiert das Lernen aller verbliebenen Karteikarten.
     */
    public void finalPrepareToLearn() {
        var all = datastore.getCurrentCardBox().getToBeLearned()
                .stream()
                .filter(card -> card.getCurrentCompartment().equals(Compartment.WATERFALL_HARD))
                .toList();
        toBeLearnedToday.addAll(all);
    }

    /**
     * Erkennt die gegebene Antwort als richtig an.
     */
    public abstract void rightAnswer();

    /**
     * Erkennt die gegebene Antwort als falsch an.
     */
    public abstract void wrongAnswer();

    /**
     * Überprüft ob der Lernvorgang beendet ist.
     * @return Wahrheitswert True wenn Lernen abgeschlossen, False wenn nicht.
     */
    public abstract boolean checkLearnCompleted();

    /**
     * Initiiert das Beenden des aktuellen Lernvorgangs.
     */
    public void finishLearning() {
        var currentBox = datastore.getCurrentCardBox();
        currentBox.getToBeLearned().forEach(card -> learningRepo.deleteLearningResources(card.getId(), currentBox.getName()));
    }

    /**
     * Wählt die aktuell ausgewählte zu lernende Karteikarte ab.
     */
    public void deselectCurrentlyLearnedCard() {
        currentlyLearnedCard = null;
    }

    /**
     * Wählt den aktuell zu lernenden Karteikasten ab.
     */
    public void deselectCurrentCardBox() {
        datastore.setCurrentCardBox(null);
    }

    /**
     * Bereitet die Lernfächer für den nächsten Lerntag vor.
     */
    public void prepareForNextDay() {
        var currentBox = datastore.getCurrentCardBox();
        currentBox.incrementDayLearned();
        loadCardsIntoToBeLearnedToday(currentBox);
    }

    /**
     * Fügt die Karteikarten eines Karteikastens den zu lernenden Karteikarten hinzu.
     * @param currentBox Der Karteikasten der die zu lernenden Karteikarten enthält.
     */
    protected abstract void loadCardsIntoToBeLearnedToday(CardBox currentBox);

    /**
     * Fügt die Karteikarten einer Kategorie zu den zu lernenden Karteikarten des aktuellen Karteikastens hinzu.
     */
    public void loadCardsFromCategoriesIntoToBeLearnedOfCurrentCardBox() {
        var currentBox = datastore.getCurrentCardBox();
        var all = currentBox.getCategories()
                .stream()
                .map(Category::getFlashcards)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
        all.forEach(card -> {
            var beingLearned = new CardBeingLearned(card);
            learningRepo.save(beingLearned);
            currentBox.getToBeLearned().add(beingLearned);
        });
        cardBoxRepo.update(currentBox);
    }
}
