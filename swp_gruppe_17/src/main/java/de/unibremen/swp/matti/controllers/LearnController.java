package de.unibremen.swp.matti.controllers;

import de.unibremen.swp.matti.guis.GUI;
import de.unibremen.swp.matti.guis.LearnGUI;
import de.unibremen.swp.matti.logics.LearnLogic;
import javax.swing.*;
import java.util.NoSuchElementException;

public abstract class LearnController {
    /**
     * Die Lernlogik der Applikation.
     */
    protected final LearnLogic logic;
    /**
     * Die Haupt-GUI der Applikation.
     */
    protected final GUI mainGui;
    /**
     * Die spezielle GUI des Lernfensters der Applikation.
     */
    protected final LearnGUI learnGui;
    /**
     * Erstellt eine neue Version des Lerncontrollers mit der gegebenen Lernlogik und der HauptGUI der Applikation.
     * @param learnLogic Die Lernlogik für den neuen Controller.
     * @param mainGui Die HauptGUI auf die der Controller Einfluss nehmen soll.
     */
    public LearnController(LearnLogic learnLogic, GUI mainGui) {
        this.logic = learnLogic;
        this.mainGui = mainGui;
        learnGui = new LearnGUI(this, mainGui);
    }

    /**
     * Informiert die Lernlogik darüber, welche Karteikarten gelernt werden sollen und bringt das entsprechende Lernsystem anschließend in den benötigten Ausgangszustand.
     */
    public void startLearning() {
        logic.loadCardsFromCategoriesIntoToBeLearnedOfCurrentCardBox();
        beforeLearning();
    }

    abstract void beforeLearning();

    /**
     * Informiert die Lernlogik darüber den aktuellen Tag und die Fachbelegung zu benötigen und weist die entsprechende GUI an die Informationen zu aktualisieren.
     */
    public void getDayAndCurrentCompartments() {
        String day = logic.getDay();
        String compartments = logic.getCurrentCompartments();
        learnGui.updateDisplayDayAndCompartments(day, compartments);
    }

    /**
     * Informiert die Lernlogik darüber die Rückseite der aktuellen Karteikarte zu benötigen und aktualisiert die GUI mit der erhaltenen Kartenrückseite.
     */
    public void getBackOfCurrentCard() {
        String back = logic.getBackOfCurrentCard();
        learnGui.updateDisplayBackOfCurrentCard(back);
    }

    /**
     * Informiert die Lernlogik darüber, dass die gegebene Antwort richtig ist und aktualisiert den Zustand des Lernsystems dessen Regeln entsprechend.
     */
    public void rightAnswer() {
        logic.rightAnswer();
        getNextCardAndUpdateCardsLeft();
    }

    /**
     * Informiert die Lernlogik darüber, dass die gegebene Antwort falsch ist und aktualisiert den Zustand des Lernsystems dessen Regeln entsprechend.
     */
    public void wrongAnswer() {
        logic.wrongAnswer();
        getNextCardAndUpdateCardsLeft();
    }

    /**
     * Weist die Lernlogik an die Zahl der übrigen Karteikarten und die Vorderseite der folgenden Karteikarte im GUI anzuzeigen. Im Fall keiner verbliebenen Karteikarten wird ein Abschlussfenster angezeigt.
     * @throws NoSuchElementException falls keine weitere zu lernende Karteikarte im Lernsystem vorhanden ist.
     */
    public void getNextCardAndUpdateCardsLeft() {
        try {
            logic.nextCard();
            getFrontOfCurrentCard();
            String cardsLeft = logic.getCardsLeft();
            learnGui.updateDisplayCardsLeft(cardsLeft);
            learnGui.resetDisplayBackOfCurrentCard("Rückseite der Karteikarte");
        } catch (NoSuchElementException e) {
            boolean learnCompleted = logic.checkLearnCompleted();
            logic.deselectCurrentlyLearnedCard();
            if (learnCompleted) {
                JOptionPane.showMessageDialog(mainGui,
                        "Du hast das Lernen dieses Karteikastens abgeschlossen.",
                        "Herzlichen Glückwunsch!", JOptionPane.INFORMATION_MESSAGE);
                finishLearning();
            }
            else {
                JOptionPane.showMessageDialog(mainGui, "Du hast das Fach für heute fertig gelernt.",
                        "Fachende", JOptionPane.INFORMATION_MESSAGE);
                continueLearning();
            }
        }
    }

    /**
     * Weist die Lernlogik an den aktuellen Karteikasten nicht länger zu betrachten und schließt das Fenster der LErnGUI.
     */
    public void saveProgress() {
        logic.deselectCurrentCardBox();
        learnGui.exitLearningPanel();
    }

    /**
     * Weist die Lernlogik an, den gelernten Stand zu aktualisieren und spiegelt diesen anschließend in einer aktualisierten LernGUI wider.
     */
    public void prepareForNextDay() {
        logic.prepareForNextDay();
        learnGui.recreateLearningPanel();
    }

    /**
     * Informiert die Lernlogik darüber die Vorderseite der aktuellen Karteikarte zu benötigen und aktualisiert die GUI mit der erhaltenen Kartenvorderseite.
     */
    private void getFrontOfCurrentCard() {
        String front = logic.getFrontOfCurrentCard();
        learnGui.updateDisplayFrontOfCurrentCard(front);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         START: METHODS OF LearnControllerWaterfall                                            //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Versucht im Lernsystem nach Waterfall-Prinzip die nächste Karte vorzubereiten.
     * @throws NoSuchElementException Falls keine weitere Karteikarte im Lernsystem vorhanden ist.
     */
    public void preparePhaseNextCard() {
        try {
            logic.nextCard();
            String front = logic.getFrontOfCurrentCard();
            learnGui.updateDisplayFrontOfCurrentCard(front);
            learnGui.resetDisplayBackOfCurrentCard("Vorbereitungsphase - Rückseite");
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(mainGui, "Vorbereitungsphase abgeschlossen",
                    "Vorbereitungsphase", JOptionPane.INFORMATION_MESSAGE);
            learnGui.clearPreparePhasePanel();
            logic.finalPrepareToLearn();
            learnGui.createLearningPanel();
        }
    }

    /**
     * Weist die Lernlogik an die gerade gelernte Karteikarte beim weiteren Einsortieren des Waterfall-Systems in einen Stapel höherer Schwierigkeit einzusortieren.
     */
    public void preparePhaseHard() {
        logic.preparePhaseMoveCardToHardCompartment();
        preparePhaseNextCard();
    }

    /**
     * Weist die Lernlogik an die gerade gelernte Karteikarte beim weiteren Einsortieren des Waterfall-Systems in einen Stapel niedrigerer Schwierigkeit einzusortieren.
     */
    public void preparePhaseEasy() {
        logic.preparePhaseMoveCardToEasyCompartment();
        preparePhaseNextCard();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                               //
    //                         END: METHODS OF LearnControllerWaterfall                                              //
    //                                                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Weist die LernGUI an ein Fragefeld anzuzeigen ob das LErnen fortgesetzt oder beendet werden soll.
     */
    private void continueLearning() {
        learnGui.showContinueOrSaveDialog();
    }

    /**
     * Weist die Lernlogik an den Lernprozess zu beenden, den Karteikasten abzuwählen und das Lernfeld zu schließen.
     */
    private void finishLearning() {
        logic.finishLearning();
        logic.deselectCurrentCardBox();
        learnGui.exitLearningPanel();
    }

    /**
     * Weist die Lernlogik an den Lernzustand wiederherzustellen und weist dann die GUI an das entsprechende Lernfeld zu erstellen.
     */
    public void resumeLearning() {
        logic.prepareForNextDay();
        learnGui.createLearningPanel();
    }
}
