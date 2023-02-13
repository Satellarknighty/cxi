package de.unibremen.swp.matti.logics;

import de.unibremen.swp.matti.models.Category;
import de.unibremen.swp.matti.models.Flashcard;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ManageCardsLogic extends BusinessLogic {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         START: METHODS OF FLASHCARD                                                           //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter für eine Liste von Strings die alle Karteikartennamen enthält.
     * @return Liste von Strings mit allen Karteikarten.
     */
    public List<String> getFlashcardsNames() {
        return cardRepo.findAll().stream().map(Flashcard::getName).toList();
    }

    /**
     * Sucht eine bestimmte Karteikarte um diese auszuwählen
     * @param cardName Name der Karteikarte.
     */
    public void selectFlashcard(final String cardName) {
        final Optional<Flashcard> optionalCard = cardRepo.find(cardName);
        final Flashcard card = optionalCard.orElseThrow(() -> new IllegalArgumentException("No such card with the name " + cardName));
        datastore.setCurrentFlashcard(card);
    }

    /**
     * Wählt die aktuelle Karteikarte ab.
     */
    public void deselectFlashcard() {
        datastore.setCurrentFlashcard(null);
    }

    /**
     * Getter für den Vorderseitentext der aktuellen Karteikarte.
     * @return String mit dem Text der Karteikartenvorderseite.
     */
    public String getFrontOfCurrentFlashcard() {
        return datastore.getCurrentFlashcard().getFront();
    }

    /**
     * Getter für den Rückseitentext der aktuellen Karteikarte.
     * @return String mit dem Text der Karteikartenrückseite.
     */
    public String getBackOfCurrentFlashcard() {
        return datastore.getCurrentFlashcard().getBack();
    }

    /**
     * Erzeugt eine neue Karteikarte mit ihren Parametern die nicht null sein dürfen.
     * @param cardName Name der Karteikarte.
     * @param front Vorderseite der Karteikarte.
     * @param back Rückseite der Karteikarte.
     * @throws IllegalStateException Wenn bereits eine Karteikarte mit diesem Namen existiert.
     */
    public void createFlashcard(final String cardName, final String front, final String back) {
        if (cardRepo.find(cardName).isPresent()){
            log.warn("A Flash card with the same name already exists.");
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit demselben Namen.");
        }
        cardRepo.save(new Flashcard(cardName, front, back));
    }

    /**
     * Fügt die aktuelle Karteikarte zu einer bestehenden Kategorie hinzu.
     * @throws IllegalStateException Wenn keine auswählbare Kategorie existiert.
     */
    public void addFlashcardToCategory() {
        checkIfAFlashcardIsChosen();
        if (categoryRepo.findAll().stream().findAny().isEmpty()){
            log.warn("There isn't any categories to choose from.");
            throw new IllegalStateException("Es gibt derzeit keine Kategorie.");
        }
    }

    /**
     * Fügt die aktuelle Karteikarte einer Kategorie hinzu
     * @param newCategoryName Name der Kategorie zu der sie hinzugefügt wurde.
     * @throws IllegalStateException Wenn bereits eine Kategorie mit gleichem Namen in der Kategorie existiert.
     */
    public void addFlashcardToCategory(final String newCategoryName) {
        final Optional<Category> optionalCategory = categoryRepo.find(newCategoryName);
        final Category newCategory = optionalCategory.orElseThrow(() -> new IllegalArgumentException("Kein Kategorie namens: " + newCategoryName));
        if (newCategory.getFlashcards().stream().
                anyMatch(card -> card.getName().equals(datastore.getCurrentFlashcard().getName()))){
            log.warn("The card already exists in the chosen category.");
            throw new IllegalStateException("In der Kategorie existiert schon eine Karteikarte mit dem gleichen Namen: "
                    + datastore.getCurrentFlashcard().getName());
        }
        newCategory.getFlashcards().add(datastore.getCurrentFlashcard());
        categoryRepo.update(newCategory);
    }
    public void addKeyword() {
        checkIfAFlashcardIsChosen();
    }

    public void addKeyword(String input) {
        input = input.replaceAll("\\s", "");
        checkInputValidity(input);
        String[] keywords = input.split(",");
        Flashcard currentCard = datastore.getCurrentFlashcard();
        String existingKeywords = currentCard.getKeywords();
        String updated = addWordsToExisting(existingKeywords, keywords);
        currentCard.setKeywords(updated);
        cardRepo.update(currentCard);
    }
    public void linkFlashcard() {
        checkIfAFlashcardIsChosen();
    }

    /**
     * Verlinkt eine ausgewählte Karteikarte mit einer anderen
     * @param linkerName Name der zu verlinkenden Karteikarte
     * @throws IllegalArgumentException Wenn die Karteikarte mit sich selbst verlinkt werden soll.
     * @throws IllegalStateException Wenn die Karteikarte bereits mit der Zielkarte verlinkt ist.
     */
    public void linkFlashcard(final String linkerName) {
        Flashcard current = datastore.getCurrentFlashcard();
        if (linkerName.equals(current.getName())){
            log.warn("A flash card cannot be linked to itself.");
            throw new IllegalArgumentException("Eine Karteikarte kann nicht mit sich selbst verlinkt werden.");
        }
        final Optional<Flashcard> optionalLinker = cardRepo.find(linkerName);
        final Flashcard linker = optionalLinker.orElseThrow(() -> new IllegalArgumentException("No such card with the name " + linkerName));
        if (linker.getLinked().stream().anyMatch(card -> card.getName().equals(current.getName()))){
            log.warn("The current flash card has already been linked to the target flash card.");
            throw new IllegalStateException("Die aktuelle Karteikarte wurde bereits mit der gewählten Karteikarte verlinkt.");
        }

        linker.getLinked().add(current);
        current.getLinked().add(linker);
        cardRepo.update(current);
        cardRepo.update(linker);
    }

    /**
     * Überprüft ob eine Karteikarte bereits ausgewählt ist und bearbeitet diese dann ggf.
     */
    public void editFlashcard() {
        checkIfAFlashcardIsChosen();
    }

    /**
     * Initiiert die Bearbeitung der Vorder- und Rückseitentexte der aktuellen Karteikarte.
     * @param front Der bearbeitete Text der Vorderseite der Karteikarte.
     * @param back Der bearbeitete Text der Rückseite der Karteikarte.
     */
    public void editContentOfFlashcard(final String front, final String back) {
        Flashcard current = datastore.getCurrentFlashcard();
        current.setFront(front);
        current.setBack(back);
        cardRepo.update(current);
    }

    /**
     * Getter für alle Verlinkungen der aktuellen Karteikarte
     * @return Liste von Strings die alle mit der aktuellen Karteikarte verlinkten Karteikarten enthält.
     */
    public List<String> getLinkedNamesOfCurrentFlashcard() {
        return datastore.getCurrentFlashcard().getLinked().stream().map(Flashcard::getName).toList();
    }

    /**
     * Bearbeitet die Verlinkungen der aktuellen Karteikarte.
     * @return Liste von Strings die alle verlinkten Karteikarten enthält.
     * @throws IllegalStateException Falls keine Verlinkung existiert.
     */
    public List<String> editLinkFlashcard() {
        final List<String> names = getLinkedNamesOfCurrentFlashcard();
        if (names.isEmpty()){
            log.warn("There are no linked flashcards.");
            throw new IllegalStateException("Es gibt keine verlinkte Karteikarte.");
        }
        return names;
    }

    /**
     * Entfernt eine Verlinkung zu einer Karteikarte von der aktuellen Karteikarte.
     * @param linkNameToBeRemoved Der Name der zu trennenden verlinkten Karteikarte.
     * @throws IllegalArgumentException Wenn keine Karteikarte mit diesem namen verlinkt ist.
     */
    public void editLinkFlashcard(final String linkNameToBeRemoved) {
        Flashcard current = datastore.getCurrentFlashcard();
        final Optional<Flashcard> optionalLinker = current.getLinked().stream().filter(linked -> linked.getName().equals(linkNameToBeRemoved)).findFirst();
        final Flashcard linkerToBeRemoved = optionalLinker.orElseThrow(() -> new IllegalArgumentException("No such card with the name: " + linkNameToBeRemoved));
        current.getLinked().remove(linkerToBeRemoved);
        linkerToBeRemoved.getLinked().remove(current);
        cardRepo.update(current);
        cardRepo.update(linkerToBeRemoved);
    }

    /**
     * Überprüft ob die Karteikarte einer Kategorie zugeordnet ist, die entfernt werden könnte.
     * @throws IllegalStateException Wenn die Karteikarte keiner Kategorie zugeordnet ist.
     */
    public void editCategoryOfFlashcard() {
        if (streamOfCategoriesOfCurrentFlashcard().findAny().isEmpty()){
            log.warn("The flashcard doesn't have any category.");
            throw new IllegalStateException("Keine Kategorie zugeordnet.");
        }
    }

    /**
     * Entfernt eine der Karteikarte zugeordnete Kategorie.
     * @param categoryName Der Name der zu entfernenden Kategorie die noch zugeordnet ist.
     */
    public void editCategoryOfFlashcard(final String categoryName) {
        final Optional<Category> optionalCategory = categoryRepo.find(categoryName);
        final Category category = optionalCategory.orElseThrow(() -> new IllegalArgumentException("No such category with the name: " + categoryName));
        category.getFlashcards().removeIf(card -> card.getName().equals(datastore.getCurrentFlashcard().getName()));
        categoryRepo.update(category);
    }
    public String[] editKeywordsOfFlashcard() {
        var currentCard = datastore.getCurrentFlashcard();
        if (currentCard.getKeywords().isEmpty()){
            log.warn("No keywords");
            throw new IllegalStateException("Es wurde noch kein Schlagwort zugeordnet.");
        }
        return currentCard.getKeywords().split(",");
    }

    public void editKeywordsOfFlashcard(String[] keywords, String toBeRemoved) {
        var currentCard = datastore.getCurrentFlashcard();
        String updated = Arrays.stream(keywords).
                filter(word -> !word.equals(toBeRemoved))
                .collect(Collectors.joining(","));
        currentCard.setKeywords(updated);
        cardRepo.update(currentCard);
    }

    /**
     * Überprüft ob eine zu löschende Karteikarte ausgewählt ist und initiiert die Bestätigungsanfrage diese zu löschen.
     */
    public void deleteFlashcard() {
        checkIfAFlashcardIsChosen();
    }

    /**
     * Entfernt die ausgewählte Karteikarte sofern zuvor eine Zustimmung erfolgte.
     */
    public void confirmedDeleteFlashcard() {
        final Flashcard toBeDeleted = datastore.getCurrentFlashcard();
        toBeDeleted.getLinked().forEach(linker -> {
            linker.getLinked().removeIf(linked -> linked.getName().equals(toBeDeleted.getName()));
            cardRepo.update(linker);
        });
        streamOfCategoriesOfCurrentFlashcard().forEach(category -> {
            category.getFlashcards().removeIf(card -> card.getName().equals(toBeDeleted.getName()));
            categoryRepo.update(category);
        });
        cardRepo.deleteByName(toBeDeleted.getName());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         END: METHODS OF FLASHCARD                                                             //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter für alle der aktuellen Karteikarte zugeordneten Kategorien.
     * @return Liste von Strings die alle zugeordneten Kategorien enthält.
     */
    public List<String> getCategoriesOfCurrentFlashcardNames() {
        return streamOfCategoriesOfCurrentFlashcard()
                .map(Category::getName)
                .toList();
    }

    private Stream<Category> streamOfCategoriesOfCurrentFlashcard(){
        return categoryRepo.findAll().stream()
                .filter(category -> category.getFlashcards().stream()
                        .map(Flashcard::getName)
                        .anyMatch(name -> name.equals(datastore.getCurrentFlashcard().getName())));
    }
    private void checkIfAFlashcardIsChosen() {
        if (datastore.getCurrentFlashcard() == null){
            log.warn("A Karteikarte has not been chosen.");
            throw new IllegalStateException("Eine Karteikarte wurde nicht gewählt.");
        }
    }

    private String addWordsToExisting(String existingKeywords, String[] keywordsInput) {
        StringBuilder builder = new StringBuilder(existingKeywords);
        List<String> existing = List.of(existingKeywords.split(","));
        for (String keyword : keywordsInput) {
            keyword = keyword.toLowerCase();
            if (!existing.contains(keyword)){
                if (!builder.isEmpty()){
                    builder.append(",");
                }
                builder.append(keyword);
            }
        }
        return builder.toString();
    }



    private void checkInputValidity(String input) {
        if (!input.matches("^[a-zA-Z0-9_,]+$")){
            log.warn("Invalid input.");
            throw new IllegalArgumentException("Ungültige Eingabe.");
        }
    }
}
