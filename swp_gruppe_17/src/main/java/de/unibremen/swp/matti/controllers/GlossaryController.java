package de.unibremen.swp.matti.controllers;


import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.guis.GlossaryGUI;
import de.unibremen.swp.matti.logics.GlossaryLogic;
import de.unibremen.swp.matti.models.Flashcard;

import javax.swing.*;
import java.util.List;

/**
 * Verarbeitet die Ereignisse aus GlossaryGUI und gibt Befehlen zu GlossaryLogic.
 */
public class GlossaryController {
    private final GlossaryLogic logic;
    private final GlossaryGUI glossaryGUI;
    private final GUI mainGui;

    public GlossaryController(final GlossaryLogic logic, final GUI mainGui) {
        this.logic = logic;
        this.mainGui = mainGui;
        this.glossaryGUI = new GlossaryGUI(mainGui, this);
    }
    public void startGlossary() {
        logic.prepareAllKeywords();
        glossaryGUI.createStartGlossaryPanel();
    }

    public void sortAlphabetically() {
        List<String> filteredCardsNames = logic.sortAlphabetically();
        updateDisplayCurrentFilter("alphabetisch sortieren", filteredCardsNames.size());
        glossaryGUI.updateDisplayFilteredCards(filteredCardsNames);
    }

    private void updateDisplayCurrentFilter(String filter, int size) {
        glossaryGUI.updateDisplayCurrentFilter(
                "Aktuelle Filter:\n" +
                filter + "\n" +
                size + " Ergebnis" + (size == 1 ? "" : "se"));
        glossaryGUI.updateDisplayContentOfSelectedCard("Vorderseite.", "Rückseite");
    }

    public void filterByCategory() {
        try {
            logic.filterByCategory();
            List<String> categoriesNames = logic.getCategoriesNames();
            glossaryGUI.showChooseCategoryDialog(categoriesNames.toArray(new String[]{}));
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void filterByCategory(String name) {
        List<String> filteredCardsNames = logic.filterByCategory(name);
        updateDisplayCurrentFilter("Kategorie: " + name, filteredCardsNames.size());
        glossaryGUI.updateDisplayFilteredCards(filteredCardsNames);
    }

    public void filterByKeyword() {
        try {
            List<String> allKeywords = logic.filterByKeyword();
            glossaryGUI.showChooseKeywordDialog(allKeywords.toArray(new String[]{}));
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void filterByKeyword(String keyword) {
        List<String> filteredCardsNames = logic.filterByKeyword(keyword);
        updateDisplayCurrentFilter("Schlagwort:  " + keyword, filteredCardsNames.size());
        glossaryGUI.updateDisplayFilteredCards(filteredCardsNames);
    }

    public void searchFor(String searchTerm) {
        try {
            List<String> filteredCardsNames = logic.searchFor(searchTerm);
            updateDisplayCurrentFilter("Suchbegriff: " + searchTerm, filteredCardsNames.size());
            glossaryGUI.updateDisplayFilteredCards(filteredCardsNames);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainGui, e.getMessage(), "Aktion nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void selectFlashcard(String flashcardName) {
        Flashcard selected = logic.selectFlashcard(flashcardName);
        glossaryGUI.updateDisplayContentOfSelectedCard(selected.getFront(), selected.getBack());
    }
}
