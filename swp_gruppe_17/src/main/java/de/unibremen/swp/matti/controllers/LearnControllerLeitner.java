package de.unibremen.swp.matti.controllers;

import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.logics.LearnLogic;

public class LearnControllerLeitner extends LearnController {
    /**
     * Erstellt eine neue Version des Lerncontrollers fürs Leitner-Lernsystem mit der gegebenen Lernlogik und der HauptGUI der Applikation.
     * @param learnLogic Die Lernlogik für den neuen Controller.
     * @param mainGui Die HauptGUI auf die der Controller Einfluss nehmen soll.
     */
    public LearnControllerLeitner(LearnLogic learnLogic, GUI mainGui) {
        super(learnLogic, mainGui);
    }

    /**
     * Definiert die speziellen Vorbereitungen und initiiert sie um mit dem Lernen nach dem Leitnersystem zu beginnen.
     */
    @Override
    void beforeLearning() {
        boolean initiallyRandomized = learnGui.displayAskInitiallyRandomizedDialog();
        logic.beforeLearning(initiallyRandomized);
        learnGui.createLearningPanel();
    }
}
