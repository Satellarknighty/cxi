package de.unibremen.swp.matti.logics;

import de.unibremen.swp.matti.controllers.LearnController;
import de.unibremen.swp.matti.controllers.LearnControllerLeitner;
import de.unibremen.swp.matti.controllers.LearnControllerWaterfall;
import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.models.*;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static de.unibremen.swp.matti.logics.LearnLogic.learningRepo;

@Slf4j
public class ManageCardBoxesLogic extends BusinessLogic {
    /**
     * Initiiert den Lernvorgang mit ausgewähltem Karteikasten.
     */
    public void learnCardBox() {
        checkIfACardBoxIsChosen();
        checkIfACardBoxIsEmpty();
        // return testListLearnSystems().stream().map(LearnSystem::getName).toList();
    }

    /**
     * Initiiert den Lernvorgang des aktuellen Karteikastens mit einem bestimmten Lernsystem
     * @param systemName NAme des gewünschten Lernsystems.
     */
    public void learnCardBox(String systemName) {
        var current = datastore.getCurrentCardBox();
        current.setSystem(LearnSystem.valueOf(systemName));
        cardBoxRepo.update(current);
    }

    /**
     * Erstellt einen neuen Karteikasten mit gewünschtem Namen.
     * @param name Der Name des Karteikastens.
     * @throws IllegalStateException Wenn bereits ein Karteikasten mit dem Namen existiert.
     */
    public void createCardBox(final String name){
        if (cardBoxRepo.find(name).isPresent()){
            log.warn("A Card Box with the same name already exists.");
            throw new IllegalStateException("Es existiert bereits eine Karteikasten mit demselben Namen.");
        }
        cardBoxRepo.save(new CardBox(name));
    }

    /**
     * Wählt einen existierenden Karteikasten an.
     * @param name Name des angewählten Karteikastens.
     * @throws IllegalArgumentException Wenn kein Karteikasten mit diesem Namen existiert.
     */
    public void selectCardBox(final String name) {
        final Optional<CardBox> optionalCardBox = cardBoxRepo.find(name);
        final CardBox chosenCardBox = optionalCardBox.orElseThrow(() -> new IllegalArgumentException("No such card box with the name: " + name));
        datastore.setCurrentCardBox(chosenCardBox);
    }

    /**
     *Entfernt eine Kategorie aus einem Karteikasten
     *@param categoryName Der Name der zu entfernenden Kategorie.
     * @throws IllegalArgumentException Wenn keine Kategorie mit solchem Namen in dem Kasten existiert.
     */
    public void removeCategoryFromCurrentCardBox(final String categoryName) {
        CardBox current = datastore.getCurrentCardBox();
        final Optional<Category> optionalCategory = current.getCategories().stream().
                filter(c -> c.getName().equals(categoryName)).findFirst();
        final Category toBeRemoved = optionalCategory.orElseThrow(() -> new IllegalArgumentException("No such category with the name: " + categoryName));
        current.getCategories().removeIf(category -> category.getName().equals(toBeRemoved.getName()));
        cardBoxRepo.update(current);
    }

    /**
     * Wählt den aktuellen Karteikasten ab.
     */
    public void deselectCardBox() {
        datastore.setCurrentCardBox(null);
    }

    /**
     * Fügt eine Kategorie zum ausgewählten Karteikasten hinzu.
     * @param categoryName Name der hinzuzufügenden Kategorie.
     * @throws IllegalStateException Wenn die Kategorie bereits in dem Karteikasten existiert.
     * @throws IllegalArgumentException Wenn keine Kategorie mit diesem Namen existiert.
     */
    public void addCategoryToCurrentCardBox(final String categoryName) {
        checkIfACardBoxIsChosen();
        CardBox current = datastore.getCurrentCardBox();
        if (current.getCategories().stream().anyMatch(c -> c.getName().equals(categoryName))){
            log.warn("The category has already been added to this card box.");
            throw new IllegalStateException("Diese Kategorie wurde bereits zu diesem Karteikasten hinzugefügt.");
        }
        final Optional<Category> optionalCategory = categoryRepo.find(categoryName);
        final Category toBeAdded = optionalCategory.orElseThrow(() -> new IllegalArgumentException("No such category with the name: " + categoryName));
        current.getCategories().add(toBeAdded);
        cardBoxRepo.update(current);
    }

    /**
     * Getter für eine Liste von Strings die alle Karteikästen enthält
     * @return Liste von Strings mit allen Karteikästen
     */
    public List<String> getCardBoxesNames() {
        return cardBoxRepo.findAll().stream().map(CardBox::getName).toList();
    }

    /**
     * Überprüft ob ein zu löschender Karteikasten ausgewählt ist und initiiert ggf eine Bestätigungsanfrage.
     */
    public void deleteCardBox() {
        checkIfACardBoxIsChosen();
    }

    /**
     * Entfernt nach erfolgter Bestätigung den ausgewählten Karteikasten.
     */
    public void confirmedDeleteCardBox() {
        cardBoxRepo.deleteByName(datastore.getCurrentCardBox().getName());
    }

    /**
     * Wählt die ausgewählte Kategorie im Karteikasten ab.
     */
    public void deselectCurrentCategoryInCurrentCardBox() {
        datastore.setCurrentCategoryInCurrentCardBox(null);
    }

    /**
     * Getter für eine Liste von Strings mit allen Kategorien in dem aktuellen Karteikasten.
     * @return Liste von Strings die alle Kategorien im Karteikasten enthält.
     */
    public List<String> getCategoriesNamesInCurrentCardBox() {
        return datastore.getCurrentCardBox().getCategories().stream()
                .map(Category::getName)
                .toList();
    }
    private void checkIfACardBoxIsChosen() {
        if (datastore.getCurrentCardBox() == null){
            log.warn("A Card Box has not been chosen.");
            throw new IllegalStateException("Es wurde noch keine Karteikasten ausgewählt.");
        }
    }
    private void checkIfACardBoxIsEmpty() {
        if (datastore.getCurrentCardBox().getCategories().isEmpty()){
            log.warn("This card box doesn't have any category.");
            throw new IllegalStateException("Dieser Karteikasten enthält keine Kategorie.");
        } else if (datastore.getCurrentCardBox().getCategories()
                .stream()
                .map(Category::getFlashcards)
                .flatMap(Collection::stream)
                .findAny()
                .isEmpty()) {
            log.warn("There isn't any flashcards.");
            throw new IllegalStateException("Dieser Karteikasten enthält keine Karteikarte.");
        }
    }

    /**
     * Wählt die aktuell ausgewählte Kategorie ab.
     */
    public void deselectCurrentCategory() {
        datastore.setCurrentCategory(null);
    }

    /**
     * Getter für eine Liste von Strings mit den Namen der Lernsysteme.
     * @return Liste von Strings mit allen Lernsystemen.
     */
    public List<String> getLearnSystemsNames() {
        return Arrays.stream(LearnSystem.values()).map(Enum::name).toList();
    }

    /**
     * Erfragt das gewünschte Lernsystem und initiiert das Erstellen einer entsprechenden Lernlogik.
     * @return Die Lernlogik zum Lernsystem.
     */
    public LearnLogic getLearnLogic() {
        return switch (datastore.getCurrentCardBox().getSystem()){
            case LEITNER -> new LearnLogicLeitner();
            case WATERFALL -> new LearnLogicWaterfall();
        };
    }

    /**
     * Erfragt das gewünschte Lernsystem und initiiert das Erstellen eines entsprechenden Lerncontrollers.
     * @param learnLogic Die Lernlogik die mit dem Lerncontroller zusammenarbeitet.
     * @param mainGui Die HauptGUI der Applikation
     * @return Der Lerncontroller zum Lernsystem.
     */
    public LearnController getLearnController(LearnLogic learnLogic, GUI mainGui) {
        return switch (datastore.getCurrentCardBox().getSystem()){
            case LEITNER -> new LearnControllerLeitner(learnLogic, mainGui);
            case WATERFALL -> new LearnControllerWaterfall(learnLogic, mainGui);
        };
    }

    /**
     * Überprüft ob der aktuelle Karteikasten gerade gelernt wird
     * @return Wahrheitswert: True falls er gelernt wird, false falls nicht.
     */
    public boolean checkIfCurrentCardBoxIsBeingLearned() {
        return datastore.getCurrentCardBox().getSystem() != null;
    }

    /**
     * Getter für den aktuellen Lernfortschritt des Lernvorgangs
     * @return String mit dem aktuellen Lernfortschritt.
     */
    public String getLearnProgress() {
        return "Der Lernfortschritt für\n" +
                datastore.getCurrentCardBox().getName() +
                "\nist " + calculateLearnProgress() + "%.\n" +
                "Lernsystem " + datastore.getCurrentCardBox().getSystem() + "wird verwendet.";
    }

    /**
     * Berechnet den bisherigen Lernfortschritt in Prozent.
     * @return String mit dem aktuellen Lernfortschritt.
     */
    private String calculateLearnProgress() {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        datastore.getCurrentCardBox().getToBeLearned().forEach(card -> total.set(total.get() + switch (card.getCurrentCompartment()){
            case LEITNER_DAILY -> 0.0;
            case LEITNER_EVERY_OTHER_DAY -> 0.33;
            case LEITNER_WEEKLY -> 0.66;
            case LEITNER_DONE -> 1.0;

            case WATERFALL_HARD -> 0.0;
            case WATERFALL_EASY -> 0.5;
            case WATERFALL_DONE -> 1.0;
        }));
        double percentage = (total.get() / datastore.getCurrentCardBox().getToBeLearned().size()) * 100;
        return String.format("%.2f", percentage);
    }

    /**
     * Initiiert einen neuen Lernvorgang mit aktuellem Karteikasten.
     */
    public void startNewLearningSession() {
        var currentBox = datastore.getCurrentCardBox();
        currentBox.getToBeLearned().forEach(card -> learningRepo.deleteLearningResources(card.getId(), currentBox.getName()));
    }
}
