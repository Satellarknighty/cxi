package de.unibremen.swp.matti.controllers;

import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.guis.ManageCardBoxesGUI;
import de.unibremen.swp.matti.logics.LearnLogic;
import de.unibremen.swp.matti.logics.ManageCardBoxesLogic;

import javax.swing.*;
import java.util.List;

public class ManageCardBoxesController {
    /**
     * Die Logik, die für die Karteikästen benutzt wird.
     */
    private final ManageCardBoxesLogic logic;
    /**
     * Die GUI, die für alle Zusammenhänge der Karteikästen genutzt wird.
     */
    private final ManageCardBoxesGUI manageCardBoxesGui;
    /**
     * Die HauptGUI der Applikation.
     */
    private final GUI mainGui;

    /**
     * Erzeugt einen neuen Controller, der für Anpassungen der Karteikästen zuständig ist.
     * @param manageCardBoxesLogic Die Logik für den neuen Controller.
     * @param gui Die HauptGUI der Applikation, die vom Controller angepasst wird.
     */
    public ManageCardBoxesController(final ManageCardBoxesLogic manageCardBoxesLogic,final GUI gui) {
        this.logic = manageCardBoxesLogic;
        this.mainGui = gui;
        manageCardBoxesGui = new ManageCardBoxesGUI(this, mainGui);
    }

    /**
     * Öffnet ein Fenster zur Verwaltung der Karteikästen.
     */
    public void startManagingCardBoxes() {
        manageCardBoxesGui.createManageCardBoxesPanel();
    }

    /**
     * Öffnet ein Fenster mit einem Dialog zum Erstellen eines neuen Karteikastens.
     */
    public void createCardBox() {
        manageCardBoxesGui.displayCreateCardBoxDialog();
    }

    /**
     * Initiiert das eigentliche Erstellen eines Karteikastens.
     * @param name Der Name des Karteikastens.
     * @throws IllegalStateException Falls der Karteikasten so nicht erstellbar ist.
     */
    public void createCardBox(final String name){
        try {
            logic.createCardBox(name);
            JOptionPane.showMessageDialog(mainGui, "Karteikasten wurde erfolgreich erstellt.",
                    "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
            updateDisplayAllCardBoxes();
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die GUI an die Anzeige der Karteikästen zu aktualisieren.
     */
    private void updateDisplayAllCardBoxes() {
        manageCardBoxesGui.updateAllCardBoxes(logic.getCardBoxesNames());
    }

    /**
     * Weist die Logik an einen Karteikasten nach Bestätigungsabfrage wieder zu löschen.
     * @throws IllegalStateException Falls der Karteikasten so nicht löschbar ist.
     */
    public void deleteCardBox() {
        try {
            logic.deleteCardBox();
            manageCardBoxesGui.showConfirmDeleteCardBoxDialog();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an den Lernprozess mit dem aktuellen Karteikasten zu starten und ggf. ein Lernsystem dafür auszuwählen.
     * @throws IllegalStateException Falls das Starten in der Form nicht möglich ist.
     */
    public void learnCardBox() {
        try {
            logic.learnCardBox();
            boolean beingLearned = logic.checkIfCurrentCardBoxIsBeingLearned();
            if (beingLearned){
                String learnProgress = logic.getLearnProgress();
                manageCardBoxesGui.showContinueLearningOrStartOverDialog(learnProgress);
            }
            else {
                chooseLearnSystem();
            }
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Weist die Logik an einen neuen Lernprozess zu starten und dafür ein Lernsystem auszuwählen.
     */
    public void startNewLearningSession() {
        logic.startNewLearningSession();
        chooseLearnSystem();
    }

    /**
     * Zeigt ein Auswahlfenster für implementierte Lernsysteme an.
     */
    private void chooseLearnSystem() {
        List<String> learnSystems = logic.getLearnSystemsNames();
        manageCardBoxesGui.displayChooseLearnSystemDialog(learnSystems.toArray(new String[]{}));
    }

    /**
     * Initiiert die Auswahl eines Karteikastens unter allen bestehenden Karteikästen.
     * @param name Der Name des Karteikastens.
     */
    public void selectCardBox(final String name) {
        logic.selectCardBox(name);
        updateDisplayCategoriesInCurrentCardBox();
    }

    /**
     * Weist die GUI an die Anzeige der Kategorien in gewähltem Karteikasten zu aktualisieren.
     */
    private void updateDisplayCategoriesInCurrentCardBox() {
        manageCardBoxesGui.updateDisplayCategoriesInCurrentCardBox(logic.getCategoriesNamesInCurrentCardBox());
    }

    /**
     * Getter für die Namen aller Karteikästen.
     * @return Liste von Strings von allen Karteikästennamen.
     */
    public List<String> getCardBoxesNames() {
        return logic.getCardBoxesNames();
    }

    /**
     * Weist die Logik an die ausgewählten Karteikasten und Kategorie abzuwählen.
     */
    public void exitManageCardBoxesPanel() {
        logic.deselectCardBox();
        logic.deselectCurrentCategory();
        logic.deselectCurrentCategoryInCurrentCardBox();
    }

    /**
     * Weist die Logik an eine Kategorie aus dem ausgewählten Karteikasten zu entfernen.
     * @param categoryName Der Name der zu entfernenden Kategorie.
     */
    public void removeCategoryFromCurrentCardBox(final String categoryName) {
        logic.removeCategoryFromCurrentCardBox(categoryName);
        updateDisplayCategoriesInCurrentCardBox();
    }

    /**
     * Fügt ausgewähltem Karteikasten eine neue Kategorie hinzu.
     * @param categoryName Der Namen der neuen Kategorie.
     * @throws IllegalStateException Falls so keine neue Kategorie hinzugefügt werden kann.
     */
    public void addCategoryToCurrentCardBox(final String categoryName) {
        try {
            logic.addCategoryToCurrentCardBox(categoryName);
            updateDisplayCategoriesInCurrentCardBox();
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Getter für eine Liste aller Kategorienamen.
     * @return Eine Liste von Strings aller Kategorienamen.
     */
    public List<String> getCategoriesNames() {
        return logic.getCategoriesNames();
    }

    /**
     * Weist die Logik an einen Karteikasten zu entfernen, zeigt eine Bestätigungsnachricht dafür an und aktualisiert die dargestellten Karteikästen.
     */
    public void confirmedDeleteCardBox() {
        logic.confirmedDeleteCardBox();
        logic.deselectCardBox();
        JOptionPane.showMessageDialog(mainGui, "Karteikasten wurde erfolgreich gelöscht.",
                "Aktion erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        updateDisplayAllCardBoxes();
    }

    /**
     * Weist die Logik an den ausgewählten Karteikasten zu lernen und initiiert den Lernprozess.
     * @param systemName Name des Lernsystems nach dem gelernt werden soll.
     */
    public void learnCardBox(String systemName) {
        logic.learnCardBox(systemName);
        logic.deselectCurrentCategory();
        logic.deselectCurrentCategoryInCurrentCardBox();
        learn();
    }

    /**
     * Weist die Logik an den Lernprozess zu initiieren.
     */
    private void learn() {
        LearnLogic learnLogic = logic.getLearnLogic();
        LearnController learnController = logic.getLearnController(learnLogic, mainGui);
        learnController.startLearning();
    }

    /**
     * Weist die Logik an das Lernen des gewählten Karteikastens fortzusetzen.
     */
    public void resumeLearnCardBox() {
        logic.deselectCurrentCategory();
        logic.deselectCurrentCategoryInCurrentCardBox();
        resumeLearn();
    }

    /**
     * Weist die Logik an den aktuellen Lernprozess fortzusetzen.
     */
    private void resumeLearn() {
        LearnLogic learnLogic = logic.getLearnLogic();
        LearnController learnController = logic.getLearnController(learnLogic, mainGui);
        learnController.resumeLearning();
    }
}
