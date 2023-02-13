package de.unibremen.swp.matti.controllers;

import de.unibremen.swp.matti.Matti;
import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.logics.*;

import javax.swing.*;

public class Controller {
    /**
     * Das Hauptfenster der Applikation.
     */
    private GUI gui;

    /**
     * Die Geschäftslogik der Applikation.
     */
    private final BusinessLogic businessLogic;

    /**
     * Erzeugt einen neuen Controller mit der gegebenen Geschäftslogik.
     *
     * @param logic Die Geschäftslogik für den neuen Controller.
     * @throws IllegalArgumentException Falls die Geschäftslogik den Wert {@code null} hat.
     */
    public Controller(final BusinessLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        businessLogic = logic;
    }
    /**
     * Informiert die Geschäftslogik darüber, dass die Applikation startet und zeigt dann das Hauptfenster an.
     */
    public void startApplication() {
        businessLogic.startApplication();
        SwingUtilities.invokeLater(this::createAndDisplayMainWindow);
    }
    /**
     * Erzeugt das Hauptfenster und zeigt es an.
     */
    public void createAndDisplayMainWindow() {
        gui = new GUI(String.format("%s (%s)", Matti.APP_NAME, Matti.VERSION), this);
        gui.setVisible(true);
    }

    /**
     * Fordert die Glossareinträge bei der Geschäftslogik an und zeigt diese anschließend auf der GUI.
     */
    public void glossary() {
        GlossaryLogic glossaryLogic = new GlossaryLogic();
        GlossaryController glossaryController = new GlossaryController(glossaryLogic, gui);
        glossaryController.startGlossary();
    }

    /**
     * Initiiert das Managen der Karteikarten für weitere Verwendung.
     */
    public void manageCards() {
        final ManageCardsLogic manageCardsLogic = new ManageCardsLogic();
        final ManageCardsController manageCardsController = new ManageCardsController(manageCardsLogic, gui);
        manageCardsController.startManagingCards();
    }

    /**
     * Initiiert das Managen der Karteikästen für weitere Verwendung.
     */
    public void manageCardBoxes() {
        final ManageCardBoxesLogic manageCardBoxesLogic = new ManageCardBoxesLogic();
        final ManageCardBoxesController manageCardBoxesController = new ManageCardBoxesController(manageCardBoxesLogic, gui);
        manageCardBoxesController.startManagingCardBoxes();
    }

    /**
     * Initiiert das Managen der Kategorien von Karteikarten für weitere Verwendung.
     */
    public void manageCategory() {
        var manageCategoryLogic = new ManageCategoriesLogic();
        var manageCategoryController = new ManageCategoriesController(manageCategoryLogic, gui);
        manageCategoryController.startManagingCategories();
    }
}
