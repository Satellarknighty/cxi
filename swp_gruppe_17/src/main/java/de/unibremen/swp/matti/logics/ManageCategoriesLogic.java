package de.unibremen.swp.matti.logics;

import de.unibremen.swp.matti.models.Category;
import de.unibremen.swp.matti.models.Flashcard;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Slf4j
public class ManageCategoriesLogic extends BusinessLogic {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         START: METHODS OF CATEGORY                                                            //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Wählt eine bestimmte Kategorie nach ihrem Namen aus.
     * @param categoryName Name der Kategorie.
     * @throws IllegalArgumentException Wenn keine Kategorie mit diesem Namen existiert.
     */
    public void selectCategory(final String categoryName) {
        final Optional<Category> optionalCategory = categoryRepo.find(categoryName);
        final Category category = optionalCategory.orElseThrow(() -> new IllegalArgumentException("No such category with the name: " + categoryName));
        datastore.setCurrentCategory(category);
    }

    /**
     * Wählt die aktuell ausgewählte Kategorie ab.
     */
    public void deselectCategory() {
        datastore.setCurrentCategory(null);
    }

    /**
     * Erstellt eine neue Kategorie mit wählbarem Namen.
     * @param categoryName Name der neu erstellten Kategorie.
     * @throws IllegalStateException Wenn bereits eine Kategorie mit diesem Namen existiert.
     */
    public void createCategory(final String categoryName) {
        if (categoryRepo.find(categoryName).isPresent()){
            log.warn("A Category with the same name already exists.");
            throw new IllegalStateException("Es existiert bereits eine Kategorie mit demselben Namen.");
        }
        final Category toBeAdded = new Category(categoryName);
        categoryRepo.save(toBeAdded);
    }

    /**
     * Initiiert das Hinzufügen einer Unterkategorie zu aktuell ausgewählter Kategorie.
     */
    public void addSubCategory(){
        checkIfACategoryIsChosen();
    }

    /**
     * Fügt der aktuellen Kategorie eine Unterkategorie hinzu.
     * @param subCategoryName Name der hinzuzufügenden Unterkategorie.
     * @throws IllegalStateException Wenn die Kategorie sich selbst zugeordnet werden könnte
     */
    public void addSubCategory(final String subCategoryName) {
        final Category currentCategory = datastore.getCurrentCategory();
        if (currentCategory.getName().equals(subCategoryName)){
            log.warn("A category cannot be a sub category of itself.");
            throw new IllegalStateException("Eine Kategorie darf nicht auf sich selbst verweisen.");
        }

        // Überprüft, ob die gewählte Kategorie ein Kind oder Enkelkind oder so
        checkIfChosenCategoryAlreadyAChild(currentCategory, subCategoryName);

        final Optional<Category> optionalSubCategory = categoryRepo.find(subCategoryName);
        final Category toBeMadeSub = optionalSubCategory.orElseThrow(() -> new IllegalArgumentException("No such category with the name: " + subCategoryName));

        // Überprüft, ob die gewählte Kategorie einen Elternteil oder Großelternteil oder so
        checkIfChosenCategoryAlreadyAParent(toBeMadeSub, currentCategory);

        currentCategory.getSubCategories().add(toBeMadeSub);
        categoryRepo.update(currentCategory);
    }

    /**
     * Überprüft ob die Kategorie eine zu löschende Unterkategorie hat.
     * @throws java.nio.channels.IllegalSelectorException Wenn die Kategorie keine Unterkategorien hat.
     */
    public void removeSubCategory() {
        checkIfACategoryIsChosen();
        if (datastore.getCurrentCategory().getSubCategories().isEmpty()){
            log.warn("The current category doesn't have any children.");
            throw new IllegalStateException("Die gewählte Kategorie hat keine Unterkategorien.");
        }
    }

    /**
     * Initiiert das Löschen einer ausgewählten Kategorie
     */
    public void deleteCategory() {
        checkIfACategoryIsChosen();
    }

    /**
     * Löscht die ausgewählte Kategorie nachdem eine Bestätigung erfolgte.
     */
    public void confirmedDeleteCategory() {
        final Category toBeDeleted = datastore.getCurrentCategory();
        categoryRepo.findAll().forEach(category -> {
            category.getSubCategories().removeIf(sub -> sub.getName().equals(toBeDeleted.getName()));
            categoryRepo.update(category);
        });
        categoryRepo.deleteByName(toBeDeleted.getName());
    }

    /**
     * Getter für eine Liste von Strings, die die Unterkategorien der aktuellen Kategorie enthält.
     * @return Liste von Strings mit Namen aller Unterkategorien der Kategorie.
     */
    public List<String> getSubNamesOfCurrentCategory() {
        return datastore.getCurrentCategory().getSubCategories().stream().map(Category::getName).toList();
    }

    /**
     * Entfernt nachdem eine Bestätigung erfolgt ist eine Unterkategorie von der aktuellen Kategorie.
     * @param subCategoryName Name der Unterkategorie die zu entfernen ist.
     * @throws IllegalArgumentException Wenn keine Kategorie mit diesem Namen existiert.
     */
    public void removeSubCategory(final String subCategoryName) {
        Category current = datastore.getCurrentCategory();
        final Optional<Category> optionalCategory =
                current.getSubCategories().stream().
                        filter(category -> category.getName().equals(subCategoryName)).findFirst();
        final Category toBeRemoved = optionalCategory.orElseThrow(() -> new IllegalArgumentException("No such category with the name: " + subCategoryName));
        current.getSubCategories().remove(toBeRemoved);
        categoryRepo.update(current);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         END: METHODS OF CATEGORY                                                              //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void checkIfChosenCategoryAlreadyAParent(final Category toBeMadeSub, final Category currentCategory) {
        final Stack<List<Category>> stackOfParentCategories = new Stack<>();
        if (!toBeMadeSub.getSubCategories().isEmpty()) {
            stackOfParentCategories.push(toBeMadeSub.getSubCategories());
        }
        while (!stackOfParentCategories.isEmpty()){
            final List<Category> currentParentCategories = stackOfParentCategories.pop();
            if (currentParentCategories.stream().anyMatch(parent -> parent.getName().equals(currentCategory.getName()))){
                log.warn("A Category with the same name already exists.");
                throw new IllegalStateException("Die gewählte Kategorie ist schon ein Elternteil der aktuelle Kategorie.");
            }
            currentParentCategories.forEach(parent -> {
                if (!parent.getSubCategories().isEmpty()){
                    stackOfParentCategories.push(parent.getSubCategories());
                }
            });
        }
    }

    private void checkIfChosenCategoryAlreadyAChild(final Category currentCategory, final String subCategoryName) {
        final Stack<List<Category>> stackOfSubCategories = new Stack<>();
        if (!currentCategory.getSubCategories().isEmpty()) {
            stackOfSubCategories.push(currentCategory.getSubCategories());
        }
        while (!stackOfSubCategories.isEmpty()){
            final List<Category> currentSubCategories = stackOfSubCategories.pop();
            if (currentSubCategories.stream().anyMatch(sub -> sub.getName().equals(subCategoryName))){
                log.warn("A Category with the same name already exists.");
                throw new IllegalStateException("Die gewählte Kategorie wurde schon der aktuellen Kategorie zugeordnet.");
            }
            currentSubCategories.forEach(sub -> {
                if (!sub.getSubCategories().isEmpty()){
                    stackOfSubCategories.push(sub.getSubCategories());
                }
            });
        }
    }

    /**
     * Getter für eine Liste von Strings mit allen Karteikartennamen der aktuellen Kategorie.
     * @return Liste von Strings die alle Karteikartennamen der aktuellen Kategorie enthält.
     */
    public List<String> getCardNamesOfCurrentCategory() {
        return datastore.getCurrentCategory().getFlashcards().stream()
                .map(Flashcard::getName)
                .toList();
    }

    /**
     * Überprüft ob eine Kategorie ausgewählt ist.
     * @throws IllegalStateException Wenn keine Kategorie ausgewählt wurde.
     */
    private void checkIfACategoryIsChosen() {
        if (datastore.getCurrentCategory() == null){
            log.warn("A Category has not been chosen.");
            throw new IllegalStateException("Es wurde noch keine Kategorie ausgewählt.");
        }
    }
}
