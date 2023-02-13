package de.unibremen.swp.matti.controllers;

import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.guis.ManageCardsGUI;
import de.unibremen.swp.matti.logics.ManageCardsLogic;
import de.unibremen.swp.matti.util.Validator;

import javax.swing.*;
import java.util.List;

public class ManageCardsController {
    /**
     * Die Logik zur Verwaltung der Karteikarten.
     */
    private final ManageCardsLogic logic;
    /**
     * Die GUI die aufgerufen wird in Zusammenhang mit Karteikarten.
     */
    private final ManageCardsGUI manageCardsGui;
    /**
     * Die HauptGUI der Applikation.
     */
    private final GUI mainGui;

    /**
     * Erzeugt einen neuen Controller zur Verwaltung der Karteikarten.
     * @param logic Die Logik für den neuen Controller.
     * @param mainGui Die HauptGUI der Applikation die vom Controller angepasst wird.
     */
    public ManageCardsController(final ManageCardsLogic logic, final GUI mainGui) {
        this.logic = logic;
        this.mainGui = mainGui;
        manageCardsGui = new ManageCardsGUI(this, mainGui);
    }

    /**
     * Weist die Logik an ein Fenster zur Karteikartenverwaltung zu schließen.
     */
    public void exitManageCardsPanel() {
        logic.deselectFlashcard();
    }

    /**
     * Weist die GUI an ein Fenster zur Karteikartenverwaltung zu öffnen.
     */
    public void startManagingCards() {
        manageCardsGui.createManageCardsPanel();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         START: METHODS OF FLASHCARDS                                                          //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Weist die Logik an eine Karteikarte auszuwählen und aktualisiert gegeben Anzeigen.
     * @param cardName Name der Karteikarte.
     * @throws IllegalArgumentException Falls die Auswahl der gewünschten Karteikarte nicht möglich ist.
     */
    public void selectFlashcard(final String cardName) {
        try {
            logic.selectFlashcard(cardName);
            updateDisplayContentOfCurrentFlashcard();
            updateDisplayLinkedOfCurrentFlashcard();
            updateDisplayCategoriesOfCurrentFlashcard();
        } catch (final IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Informiert die Logik darüber, dass eine neue Karteikarte
     * erzeugt werden soll.
     */
    public void createFlashcard() {
        manageCardsGui.displayCreateFlashcardDialog();
    }
    /**
     * Weist die Geschäftslogik an, eine neue Karteikarte in dieser Kategorie zu erzeugen. Danach wird in der GUI die Darstellung der Karteikarten aktualisiert und ein Bestätigungsfenster geöffnet.
     *
     * @param cardName  Der Name der Karteikarte.
     * @param front     Der Inhalt der Vorderseite der Karteikarte.
     * @param back      Der Inhalt der Rückseite der Karteikarte.
     * @throws IllegalStateException Falls das Erzeugen der Karteikarte so nicht möglich ist.
     */
    public void createFlashcard(final String cardName, final String front, final String back) {
        try {
            logic.createFlashcard(cardName, front, back);
            JOptionPane.showMessageDialog(mainGui, "Karteikarte wurde erfolgreich hinzugefügt.",
                    "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
            updateDisplayFlashcards();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an das Ergänzen einer Kategorie zu einer Karteikarte zu initiieren und öffnet ein Auswahlfenster.
     * @throws IllegalStateException Falls die Karteikarte so nicht zur Kategorie hinzugefügt werden kann.
     */
    public void addFlashcardToCategory() {
        try {
            logic.addFlashcardToCategory();
            manageCardsGui.displayAddFlashcardToCategoryDialog(logic.getCategoriesNames().toArray(new String[]{}));
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an eine Karteikarte zu gewählter Karteikartenkategorie hinzuzufügen und öffnet ggf ein Bestätigungsfenster.
     * @param newCategoryName Name der Zielkategorie.
     * @throws IllegalStateException Falls die Karteikarte so nicht zur Kategorie hinzugefügt werden kann.
     */
    public void addFlashcardToCategory(final String newCategoryName) {
        try {
            logic.addFlashcardToCategory(newCategoryName);
            JOptionPane.showMessageDialog(mainGui,"Karteikarte zu Kategorie hinzugefügt.", "Aktion erfolgreich"
                    , JOptionPane.INFORMATION_MESSAGE);
            updateDisplayCategoriesOfCurrentFlashcard();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an das Hinzufügen einer oder mehrerer Schlagworte zur Karteikarte zu initiieren und öffnet einen Auswahlbildschirm.
     * @throws IllegalStateException Falls das oder die Schlagworte so nicht hinzugefügt werden können.
     */
    public void addKeywords() {
        try {
            logic.addKeyword();
            manageCardsGui.showAddKeywordsDialog();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an ein oder mehrere Schlagworte zur Karteikarte hinzuzufügen und öffnet einen Bestätigungsbildschirm.
     * @param input Das zuzufügende Schlagwort.
     * @throws IllegalArgumentException Falls das oder die Schlagworte so nicht hinzugefügt werden können.
     */
    public void addKeywords(String input) {
        try {
            logic.addKeyword(input);
            JOptionPane.showMessageDialog(mainGui, "Schlagwörter erfolgreich hinzugefügt.",
                    "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an die Verlinkung der aktuellen Karteikarte mit anderen zu initiieren und öffnet auf der GUI einen Auswahlbildschirm für mögliche Karteikarten.
     * @throws IllegalStateException Falls die Karteikarten so nicht verlinkt werden können.
     */
    public void linkFlashcard() {
        try {
            logic.linkFlashcard();
            final List<String> cardNames = logic.getFlashcardsNames();
            manageCardsGui.showChooseFlashcardToLinkFlashcardDialog(cardNames.toArray(new String[]{}));
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Weist die Logik an die aktuelle Karteikarte mit einer anderen zu verlinken, öffnet auf der GUI ein Bestätigungsfenster und aktualisiert dei GUI erneut.
     * @param linkerName Der Name der zu verlinkenden Karteikarte.
     * @throws IllegalArgumentException Falls es keine solche Karteikarte gibt.
     * @throws IllegalStateException Falls die Karteikarten so nicht verlinkt werden können.
     */
    public void linkFlashcard(final String linkerName) {
        try {
            logic.linkFlashcard(linkerName);
            JOptionPane.showMessageDialog(mainGui, "Karteikarte wurde erfolgreich mit dem gewählten Zielkarte verlinkt.",
                    "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
            updateDisplayLinkedOfCurrentFlashcard();
        } catch (final IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Weist die Logik an eine Karteikarte zu bearbeiten und öffnet einen Auswahlbildschirm für die Bearbeitungsoptionen.
     * @throws IllegalStateException Falls die Karteikarte so nicht bearbeitet werden darf.
     */
    public void editFlashcard() {
        try {
            logic.editFlashcard();
            manageCardsGui.showOptionEditFlashcardDialog();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an, aktuelle Zustände der Karteikartenseiten anzufragen, lässt diese bearbeiten und aktualisiert die GUI mit den bearbeiteten Versionen.
     */
    public void editContentOfFlashcard() {
        final String front = (String) JOptionPane.showInputDialog(mainGui,
                "Vorderseite", "Karteikarte erzeugen", JOptionPane.QUESTION_MESSAGE, null,
                null, logic.getFrontOfCurrentFlashcard());
        final String back = (String) JOptionPane.showInputDialog(mainGui,
                "Rückseite", "Karteikarte erzeugen", JOptionPane.QUESTION_MESSAGE, null,
                null, logic.getBackOfCurrentFlashcard());
        logic.editContentOfFlashcard(Validator.checkNotNullOrBlank(front, "front"),
                Validator.checkNotNullOrBlank(back, "back"));
        JOptionPane.showMessageDialog(mainGui, "Bearbeitung erfolgreich.",
                "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        updateDisplayContentOfCurrentFlashcard();
    }

    /**
     * Weist die Logik an die Bearbeitung von Verlinkungen einer Karteikarte zu initiieren und aktualisiert die GUI mit einem entsprechenden Auswahlbildschirm.
     * @throws IllegalStateException Falls die Verlinkungen so nicht bearbeitet werden dürfen.
     */
    public void editLinkOfFlashcard() {
        try {
            final List<String> linkedName = logic.editLinkFlashcard();
            manageCardsGui.showEditLinkFlashcardDialog(linkedName.toArray(new String[]{}));
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an eine Verlinkung von einer Karteikarte zu entfernen, öffnet ein Bestätigungsfenster und aktualisiert die GUI.
     * @param linkNameToBeRemoved Der Name der Verlinkung die entfernt werden soll.
     */
    public void editLinkOfFlashcard(final String linkNameToBeRemoved) {
        logic.editLinkFlashcard(linkNameToBeRemoved);
        JOptionPane.showMessageDialog(mainGui, "Bearbeitung erfolgreich.",
                "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        updateDisplayLinkedOfCurrentFlashcard();
    }

    /**
     * Weist die Logik an das Entfernen einer Kategorie von einer Karteikarte zu initiieren und aktualisiert die GUI mit einem entsprechenden Auswahlbildschirm.
     * @throws IllegalStateException Falls die Kategorie so nicht entfernt werden darf.
     */
    public void editCategoryOfFlashcard() {
        try {
            logic.editCategoryOfFlashcard();
            manageCardsGui.showEditCategoryOfFlashcardDialog(logic.getCategoriesOfCurrentFlashcardNames().toArray(new String[]{}));
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an eine Kategorie von einer Karteikarte zu entfernen, öffnet ein Bestätigungsfenster und aktualisiert anschließend die GUI.
     * @param categoryName Name der zu entfernenden Kategorie.
     */
    public void editCategoryOfFlashcard(final String categoryName) {
        logic.editCategoryOfFlashcard(categoryName);
        JOptionPane.showMessageDialog(mainGui, "Karteikarte wurde erfolgreich aus der Kategorie entfernt.",
                "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        updateDisplayCategoriesOfCurrentFlashcard();
    }

    /**
     * Weist die Logik an die Schlagworte einer Karteikarte anzufragen und öffnet ein Auswahlfenster mit Bearbeitungsoptionen.
     */
    public void editKeywordsOfFlashcard() {
        String[] keywords = logic.editKeywordsOfFlashcard();
        manageCardsGui.showChooseKeywordToRemoveDialog(keywords);
    }

    /**
     * Weist die Logik an dei Löschung einer Karteikarte zu initiieren und öffnet ein Zustimmungsfenster.
     * @throws IllegalStateException Falls die Karteikarte so nicht gelöscht werden darf.
     */
    public void deleteFlashcard() {
        try {
            logic.deleteFlashcard();
            manageCardsGui.showConfirmDeleteFlashcardDialog();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Weist die Logik an eine Karteikarte zu löschen, öffnet ein Bestätigungsfenster, wählt die alte Karteikarte ab und aktualisiert die Anzeige der Karteikarten.
     */
    public void confirmedDeleteFlashcard() {
        logic.confirmedDeleteFlashcard();
        JOptionPane.showMessageDialog(mainGui, "Karteikarte gelöscht.", "Aktion erfolgreich",
                JOptionPane.INFORMATION_MESSAGE);
        logic.deselectFlashcard();
        updateDisplayFlashcards();
        resetDisplayCurrentFlashcard();
    }

    /**
     * Befiehlt der GUI alle existierenden Karteikarten zu aktualisieren und anzuzeigen.
     */
    public void updateDisplayFlashcards() {
        manageCardsGui.updateDisplayFlashcards(logic.getFlashcardsNames());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         END: METHODS OF FLASHCARD                                                             //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Weist die Logik an aktuelle Inhalte der Vorder- und Rückseite der ausgewählten Karteikarte anzufragen und damit die GUI zu aktualisieren.
     */
    private void updateDisplayContentOfCurrentFlashcard() {
        manageCardsGui.displayFrontAndBack(logic.getFrontOfCurrentFlashcard(),
                logic.getBackOfCurrentFlashcard());
    }

    /**
     * Aktualisiert die GUI mit den Verlinkungen der aktuellen Karteikarte.
     */
    private void updateDisplayLinkedOfCurrentFlashcard() {
        manageCardsGui.displayLinked(linkedToString());
    }

    /**
     * Aktualisiert die GUI mit den Kategorien der aktuellen Karteikarte.
     */
    private void updateDisplayCategoriesOfCurrentFlashcard() {
        manageCardsGui.displayCategories(categoriesToString());
    }

    /**
     * Weist die Logik an die Kategorien der aktuellen Karteikarte in Form eines Strings auszugeben.
     * @return String der zugehörigen Kategorien.
     */
    private String categoriesToString() {
        StringBuilder builder = new StringBuilder("Diese Karteikarte gehört zu Kategorien:");
        builder.append(System.lineSeparator());
        logic.getCategoriesOfCurrentFlashcardNames().forEach(categoryName ->
                builder.append(categoryName).append(System.lineSeparator()));
        return builder.toString();
    }

    /**
     * Weist die Logik an die Verlinkungen der aktuellen Karteikarte in Form eines Strings auszugeben.
     * @return String der zugehörigen Verlinkungen.
     */
    private String linkedToString() {
        final StringBuilder builder = new StringBuilder("Verlinkte Karteikarte:");
        builder.append(System.lineSeparator());
        for (final String linkedName : logic.getLinkedNamesOfCurrentFlashcard()) {
            builder.append(linkedName).append(System.lineSeparator());
        }
        return builder.toString();
    }

    /**
     * Aktualisiert die GUI mit den Versionen der Vorderseite, Rückseite, Verlinkungen und Kategorien einer Karteikarte.
     */
    private void resetDisplayCurrentFlashcard() {
        manageCardsGui.displayFrontAndBack("Die Vorderseite.","Die Rückseite.");
        manageCardsGui.displayLinked("Die Karteikarte(n), die nach dieser Karteikarte verlinkt sind.");
        manageCardsGui.displayCategories("Die Kategorie(n) dieser Karteikarte.");
    }

    /**
     * Weist die Logik an ein Schlagwort von einer Karteikarte zu entfernen.
     * @param keywords Liste der vorhandenen Schlagworte.
     * @param toBeRemoved Das zu entfernende Schlagwort.
     */
    public void editKeywordsOfFlashcard(String[] keywords, String toBeRemoved) {
        logic.editKeywordsOfFlashcard(keywords, toBeRemoved);
        JOptionPane.showMessageDialog(mainGui, "Bearbeitung erfolgreich", "Karteikarte bearbeiten",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
