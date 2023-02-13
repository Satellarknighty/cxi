package de.unibremen.swp.matti.logics;

import de.unibremen.swp.matti.models.Category;
import de.unibremen.swp.matti.models.Flashcard;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class GlossaryLogic extends BusinessLogic {
    private final List<String> allKeywords = new ArrayList<>();
    public void prepareAllKeywords() {
        List<Flashcard> allCards = cardRepo.findAll();
        var all = allCards.stream()
                .map(Flashcard::getKeywords)
                .map(string -> string.split(","))
                .map(List::of)
                .flatMap(Collection::stream)
                .distinct()
                .filter(s -> !s.isEmpty())
                .toList();
        allKeywords.addAll(all);
    }

    public List<String> sortAlphabetically() {
        List<Flashcard> result = cardRepo.findAll();
        return cardsToName(result);
    }

    private List<String> cardsToName(List<Flashcard> result) {
        return result.stream().map(Flashcard::getName).toList();
    }

    public void filterByCategory() {
        if (categoryRepo.findAll().stream().findAny().isEmpty()){
            log.warn("There isn't any categories to choose from.");
            throw new IllegalStateException("Es gibt derzeit keine Kategorie.");
        }
    }

    public List<String> filterByCategory(String name) {
        var optionalCategory = categoryRepo.find(name);
        var category = optionalCategory.orElseThrow(() -> new IllegalArgumentException("Kategorie existiert nicht."));
        var result = new ArrayList<>(addCardsFromAllSubCategories(category));
        return cardsToName(result);
    }

    private Set<Flashcard> addCardsFromAllSubCategories(Category root) {
        var result = new HashSet<Flashcard>();
        var stack = new Stack<Category>();
        stack.push(root);
        while (!stack.isEmpty()){
            Category current = stack.pop();
            result.addAll(current.getFlashcards());
            current.getSubCategories().forEach(stack::push);
        }
        return result;
    }

    public List<String> filterByKeyword() {
        if (allKeywords.isEmpty()){
            log.warn("No keyword");
            throw new IllegalStateException("Es gibt kein Schlagwort.");
        }
        return allKeywords;
    }

    public List<String> filterByKeyword(String keyword) {
        var cardsWithKeyword = cardRepo.findAll()
                .stream()
                .filter(card -> {
                    var keywordsList = List.of(card.getKeywords().split(","));
                    return keywordsList.contains(keyword);
                })
                .toList();
        return cardsToName(cardsWithKeyword);
    }

    public List<String> searchFor(String searchTerm) {
        if (searchTerm.length() <= 1) {
            log.warn("The search term must contain at least 2 characters.");
            throw new IllegalArgumentException("Der Suchbegriff muss mindestens 2 Zeichen enthalten.");
        }
        var cardsWithTerm = cardRepo.findAll()
                .stream()
                .filter(card -> card.getName().equalsIgnoreCase(searchTerm)
                             || card.getFront().contains(searchTerm)
                             || card.getBack().contains(searchTerm))
                .toList();
        return cardsToName(cardsWithTerm);
    }

    public Flashcard selectFlashcard(String flashcardName) {
        var optionalFlashcard = cardRepo.find(flashcardName);
        return optionalFlashcard.orElseThrow(() ->
                new IllegalArgumentException("Keine Karteikarte namens: " + flashcardName));
    }
}
