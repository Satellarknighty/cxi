package de.unibremen.swp.matti.controllers;

import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.logics.LearnLogic;

import javax.swing.*;

public class LearnControllerWaterfall extends LearnController{
    /**
     * Erstellt eine neue Version des Lerncontrollers fürs Waterfall-Lernsystem mit der gegebenen Lernlogik und der HauptGUI der Applikation.
     * @param learnLogic Die Lernlogik für den neuen Controller.
     * @param mainGui Die HauptGUI auf die der Controller Einfluss nehmen soll.
     */
    public LearnControllerWaterfall(LearnLogic learnLogic, GUI mainGui) {
        super(learnLogic, mainGui);
    }

    /**
     * Definiert die speziellen Vorbereitungen und initiiert sie um mit dem Lernen nach dem Waterfall-System zu beginnen.
     */
    @Override
    void beforeLearning() {
        logic.beforeLearning(false);
        JOptionPane.showMessageDialog(mainGui, """
                Bevor wir beginnen, musst du die Karten in 2 Kategorien einteilen:
                Eine, bei der du die Antwort sofort wusstest,
                und eine, bei der du dich schwer getan hast.
                """, "Vorbereitungsphase", JOptionPane.INFORMATION_MESSAGE);
        learnGui.createPrepareCardsPanel();
    }
}
