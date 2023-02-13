package de.unibremen.swp.matti.controllers;

import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.guis.ManageCategoriesGUI;
import de.unibremen.swp.matti.logics.ManageCategoriesLogic;

import javax.swing.*;

public class ManageCategoriesController {
    /**
     * Die Logik zur Verwaltung von Karteikartenkategorien.
     */
    private final ManageCategoriesLogic logic;
    /**
     * Die HauptGUI der Applikation.
     */
    private final GUI mainGui;
    /**
     * Die GUI die aufgerufen wird für Fenster im Zusammenhang mit Kategorien.
     */
    private final ManageCategoriesGUI manageCategoriesGUI;

    /**
     * Erzeugt einen neuen Controller zur Verwaltung von Karteikartenkategorien.
     * @param logic Die Logik für den Controller.
     * @param mainGui DIe HauptGUI der Applikation die vom Controller angepasst wird.
     */
    public ManageCategoriesController(ManageCategoriesLogic logic, GUI mainGui) {
        this.logic = logic;
        this.mainGui = mainGui;
        manageCategoriesGUI = new ManageCategoriesGUI(this, mainGui);
    }

    /**
     * Öffnet ein Fenster zur Verwaltung von Kategorien.
     */
    public void startManagingCategories(){
        manageCategoriesGUI.createManageCategoriesPanel();
    }

    /**
     * Schließt das Fenster zur Verwaltung von Kategorien und weist die Logik an die Kategorie abzuwählen.
     */
    public void exitManageCategoriesPanel() {
        logic.deselectCategory();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         START: METHODS OF CATEGORY                                                            //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Weist die Logik an die aktuell ausgewählte Kategorie zu wechseln und aktualisiert dann dei GUI mit den neuen Informationen.
     * @param categoryName  Der Name der neu gewählten Kategorie.
     * @throws IllegalArgumentException Falls die Kategorie so nicht ausgewählt werden kann.
     */
    public void selectCategory(final String categoryName) {
        try {
            logic.selectCategory(categoryName);
            updateDisplaySubCategoriesOfCurrentCategory();
            updateDisplayFlashcardsOfCurrentCategory();
        } catch (final IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Weist die GUI an den Dialog für das Hinzufügen einer neuen Kategorie anzuzeigen.
     */
    public void createCategory() {
        manageCategoriesGUI.displayCreateCategoryDialog();
    }

    /**
     * Weist die Logik an eine neue Kategorie zu erstellen und aktualisiert die GUI mit der neuen Kategorie.
     *@param categoryName  Der Name der zu erzeugten Kategorie.
     *@throws IllegalStateException Falls die neue Kategorie so nicht erstellt werden kann.
     */
    public void createCategory(final String categoryName) {
        try {
            logic.createCategory(categoryName);
            JOptionPane.showMessageDialog(mainGui, "Kategorie wurde erfolgreich hinzugefügt.",
                    "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
            updateDisplayCategories();
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an das Erstellen einer Unterkategorie zu initiieren und öffnet ein entsprechendes Auswahlfenster auf der GUI.
     * @throws IllegalStateException Falls das Erstellen der Unterkategorie so nicht möglich ist.
     */
    public void addSubCategory() {
        try {
            logic.addSubCategory();
            manageCategoriesGUI.displayAddSubCategoryDialog(logic.getCategoriesNames().toArray(new String[]{}));
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an eine Unterkategorie zu erstellen, öffnet ein Bestätigungsfenster und aktualisiert anschließend die GUI.
     * @param subCategoryName Der Name der Unterkategorie.
     * @throws IllegalStateException Falls die Unterkategorie so nicht zugeordnet werden kann.
     */
    public void addSubCategory(final String subCategoryName) {
        try {
            logic.addSubCategory(subCategoryName);
            JOptionPane.showMessageDialog(mainGui, "Unterkategorie wurde erfolgreich zugeordnet.",
                    "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
            updateDisplaySubCategoriesOfCurrentCategory();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an das Entfernen einer Unterkategorie zu initiieren und öffnet ein entsprechendes Auswahlfenster.
     * @throws IllegalStateException Falls die Unterkategorie so nicht entfernt werden kann.
     */
    public void removeSubCategory() {
        try {
            logic.removeSubCategory();
            manageCategoriesGUI.chooseSubCategoryToRemoveDialog(logic.getSubNamesOfCurrentCategory().toArray(new String[]{}));
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an eine Unterkategorie zu entfernen, öffnet ein Bestätigungsfenster und aktualisiert anschließend die GUI.
     * @param subCategoryName Der Name der zu entfernenden Unterkategorie.
     */
    public void removeSubCategory(final String subCategoryName) {
        logic.removeSubCategory(subCategoryName);
        JOptionPane.showMessageDialog(mainGui, "Unterkategorie wurde erfolgreich entfernt.",
                "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        updateDisplaySubCategoriesOfCurrentCategory();
    }

    /**
     * Weist die Logik an das Löschen einer Kategorie zu initiieren und öffnet ein Zustimmungsfenster.
     * @throws IllegalStateException Falls die Kategorie so nicht gelöscht werden darf.
     */
    public void deleteCategory() {
        try {
            logic.deleteCategory();
            manageCategoriesGUI.showConfirmDeleteCategoryDialog();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an die Kategorie nach Bestätigung zu löschen, öffnet ein Bestätigungsfenster für die Aktion, wählt die gelöschte Kategorie ab und aktualisiert die GUI.
     */
    public void confirmedDeleteCategory() {
        logic.confirmedDeleteCategory();
        JOptionPane.showMessageDialog(mainGui, "Kategorie wurde erfolgreich gelöscht.",
                "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        updateDisplayCategories();
        logic.deselectCategory();
        resetDisplaySubCategories();
        resetDisplayFlashcards();
    }

    /**
     * Weist die Logik an die Namen aller Kategorien anzufragen und aktualisiert die GUI mit diesen Informationen.
     */
    public void updateDisplayCategories() {
        manageCategoriesGUI.updateAllCategories(logic.getCategoriesNames());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         END: METHODS OF CATEGORY
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Aktualisiert die GUI mit der Menge der Karteikarten in gewählter Kategorie.
     */
    private void updateDisplayFlashcardsOfCurrentCategory() {
        manageCategoriesGUI.displayFlashcardsOfCurrentCategory(flashcardsToString());
    }

    /**
     * Weist die Logik an einen String mit der Liste aller passenden Karteikarten zu erstellen.
     * @return String mit allen Karteikarten aus der Kategorie.
     */
    private String flashcardsToString() {
        StringBuilder builder = new StringBuilder("Karteikarten:");
        builder.append(System.lineSeparator());
        logic.getCardNamesOfCurrentCategory().forEach(cardName -> builder.append(cardName).append(System.lineSeparator()));
        return builder.toString();
    }

    /**
     * Aktualisiert die GUI mit der neuen Version der zugehörigen Unterkategorien.
     */
    private void updateDisplaySubCategoriesOfCurrentCategory() {
        manageCategoriesGUI.displaySubCategoriesOfCurrentCategory(subCategoriesToString());
    }

    /**
     * Weist die Logik an einen String mit der Liste aller zugehörigen Unterkategorien zu erstellen.
     * @return String mit allen passenden Unterkategorien der Kategorie.
     */
    private String subCategoriesToString() {
        final StringBuilder builder = new StringBuilder("Unterkategorien:");
        builder.append(System.lineSeparator());
        logic.getSubNamesOfCurrentCategory().forEach(subName -> builder.append(subName).append(System.lineSeparator()));
        return builder.toString();
    }

    /**
     * Aktualisiert die GUI mit den angezeigten Unterkategorien der Kategorie.
     */
    private void resetDisplaySubCategories() {
        manageCategoriesGUI.displaySubCategoriesOfCurrentCategory("Die Unterkategorie(n) von der gewählten Kategorie.");
    }

    /**
     * Aktualisiert die GUI mit den existenten Karteikarten in der gewählten Kategorie.
     */
    private void resetDisplayFlashcards() {
        manageCategoriesGUI.displayFlashcardsOfCurrentCategory("Die Karteikarte(n) von der gewählten Kategorie.");
    }


}
