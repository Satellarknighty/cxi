package de.unibremen.swp.matti.guis;

import de.unibremen.swp.matti.controllers.LearnController;
import de.unibremen.swp.matti.util.JTextAreaFactory;

import javax.swing.*;
import java.awt.*;


public class LearnGUI {
    /**
     * Der Controller mit dem die GUI zusammenarbeitet.
     */
    private final LearnController controller;
    /**
     * Die HauptGUI der Applikation
     */
    private final GUI mainGui;
    /**
     * Der Bereich in dem die Karteikartenvorderseite angezeigt wird.
     */
    private JTextArea front;
    /**
     * Der Bereich in dem die Karteikartenrückseite angezeigt wird.
     */
    private JTextArea back;
    /**
     * Der Bereich in dem der Zähler der gelernten Tage angezeigt wird
     */
    private JTextArea dayLearnedCounter;
    /**
     * Der Bereich in dem angezeigt wird in welchem Fach sich aktuell befunden wird.
     */
    private JTextArea currentCompartments;
    /**
     * Der Bereich in dem der Zähler für verbliebene ungelernte Karteikarten angezeigt wird.
     */
    private JTextArea cardsLeftCounter;
    /**
     * Array mit Optionen für die Anzeigereihenfolge der Karteikarten.
     */
    private static final String[] initializeOption = {"Alphabetisch", "Zufällig"};
    /**
     * Array mit Optionen beim Verlassen der GUI.
     */
    private static final String[] continueOrSaveOption = {"Fortfahren", "Speichern"};
    /**
     * Panel zum Vorbereiten der Karteikarten.
     */
    private JPanel prepareCardsPanel;
    /**
     * Panel zum Starten des Lernvorgangs.
     */
    private JPanel learningPanel;

    /**
     * Erzeugt eine neue Version der LernGUI basierend auf dem zugehörigen Controller und der HauptGUI der Applikation.
     * @param learnController Der Controller der die LernGUI steuert.
     * @param mainGui Die HauptGUI der Applikation.
     */
    public LearnGUI(LearnController learnController, GUI mainGui) {
        this.controller = learnController;
        this.mainGui = mainGui;
    }

    /**
     * Erzeugt ein Dialogfenster zur Auswahl des Ausgangszustands der Karteikartenordnung.
     * @return Wahrheitswert der zwischen den beiden Optionen unterscheidet.
     */
    public boolean displayAskInitiallyRandomizedDialog() {
        int option = JOptionPane.showOptionDialog(mainGui, "Karteikarten alphabetisch oder zufällig sortiert?",
                "Lernen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, initializeOption, initializeOption[0]);
        return option == JOptionPane.NO_OPTION;
    }

    /**
     * Erzeugt das Fenster, das während des Lernvorgangs geöffnet ist mit Anzeigen zu Informationen der aktuellen Karteikarte und den Buttons fürs Fortsetzen des Lernvorgangs ausgestattet ist.
     */
    public void createLearningPanel() {
        learningPanel = new JPanel();

        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.setPreferredSize(new Dimension(700, 100));
        dayLearnedCounter = new JTextArea("Tag");
        JTextAreaFactory.upgrade(dayLearnedCounter);
        JScrollPane day2 = new JScrollPane(dayLearnedCounter);
        currentCompartments = new JTextArea("Aktuelle Fächer");
        JTextAreaFactory.upgrade(currentCompartments);
        JScrollPane comps2 = new JScrollPane(currentCompartments);
        cardsLeftCounter = new JTextArea("Karten übrig");
        JTextAreaFactory.upgrade(cardsLeftCounter);
        JScrollPane cardsLeft2 = new JScrollPane(cardsLeftCounter);
        controller.getDayAndCurrentCompartments();

        headerPanel.add(day2);
        headerPanel.add(comps2);
        headerPanel.add(cardsLeft2);

        JPanel questionAnswer = new JPanel(new GridLayout(1, 2));
        questionAnswer.setPreferredSize(new Dimension(700, 300));
        front = new JTextArea("Vorderseite der Karteikarte");
        JTextAreaFactory.upgrade(front);
        JScrollPane front2 = new JScrollPane(front);
        back = new JTextArea("Rückseite der Karteikarte");
        JTextAreaFactory.upgrade(back);
        JScrollPane back2 = new JScrollPane(back);
        questionAnswer.add(front2);
        questionAnswer.add(back2);

        JPanel buttons = new JPanel(new GridLayout(1, 2));
        buttons.setPreferredSize(new Dimension(700, 100));
        final JButton right = new JButton("Richtig!");
        final JButton wrong = new JButton("Falsch");
        JButton showAnswer = new JButton("Lösung zeigen");

        right.setEnabled(false);
        right.addActionListener(e -> {
            controller.rightAnswer();
            right.setEnabled(false);
            wrong.setEnabled(false);
            showAnswer.setEnabled(true);
        });

        wrong.setEnabled(false);
        wrong.addActionListener(e -> {
            controller.wrongAnswer();
            right.setEnabled(false);
            wrong.setEnabled(false);
            showAnswer.setEnabled(true);
        });

        showAnswer.addActionListener(e -> {
            right.setEnabled(true);
            wrong.setEnabled(true);
            showAnswer.setEnabled(false);
            controller.getBackOfCurrentCard();
        });

        buttons.add(showAnswer);
        buttons.add(right);
        buttons.add(wrong);

        learningPanel.add(headerPanel, BorderLayout.PAGE_START);
        learningPanel.add(questionAnswer, BorderLayout.CENTER);
        learningPanel.add(buttons, BorderLayout.PAGE_END);
        mainGui.add(learningPanel);
        mainGui.revalidate();
        mainGui.repaint();
        controller.getNextCardAndUpdateCardsLeft();
    }

    /**
     * Aktualisiert die Anzeige der Vorderseite der aktuellen Karteikarte.
     * @param string Die aktualisierte Version des Vorderseitentexts.
     */
    public void updateDisplayFrontOfCurrentCard(String string) {
        this.front.setText(string);
    }

    /**
     * Erzeugt das Fenster zur Vorbereitung der Karteikartensortierung.
     */
    public void createPrepareCardsPanel() {
        prepareCardsPanel = new JPanel();
        JLabel mainLabel = new JLabel("War diese Karteikarte leicht für dich?", SwingConstants.CENTER);
        mainLabel.setPreferredSize(new Dimension(700, 100));
        mainLabel.setBorder(GUI.BLACK_LINE_BORDER);

        JPanel cardPanel = new JPanel(new GridLayout(1, 2));
        cardPanel.setPreferredSize(new Dimension(700, 300));
        front = new JTextArea("Vorbereitungsphase - Vorderseite");
        JTextAreaFactory.upgrade(front);
        JScrollPane front2 = new JScrollPane(front);
        back = new JTextArea("Vorbereitungsphase - Rückseite");
        JTextAreaFactory.upgrade(back);
        JScrollPane back2 = new JScrollPane(back);

        cardPanel.add(front2);
        cardPanel.add(back2);
        controller.preparePhaseNextCard();

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
        buttonsPanel.setPreferredSize(new Dimension(700, 100));

        JButton yesButton = new JButton("Ja, war leicht.");
        JButton noButton = new JButton("Nein, war schwierig.");
        JButton showButton = new JButton("Lösung anzeigen");
        showButton.addActionListener(e -> {
            showButton.setEnabled(false);
            yesButton.setEnabled(true);
            noButton.setEnabled(true);
            controller.getBackOfCurrentCard();
        });
        yesButton.addActionListener(e -> {
            controller.preparePhaseEasy();
            showButton.setEnabled(true);
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
        });
        noButton.addActionListener(e -> {
            controller.preparePhaseHard();
            showButton.setEnabled(true);
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
        });


        buttonsPanel.add(showButton);
        buttonsPanel.add(yesButton);
        buttonsPanel.add(noButton);

        prepareCardsPanel.add(mainLabel, BorderLayout.PAGE_START);
        prepareCardsPanel.add(cardPanel, BorderLayout.CENTER);
        prepareCardsPanel.add(buttonsPanel, BorderLayout.PAGE_END);

        mainGui.add(prepareCardsPanel);
    }

    /**
     * Aktualisiert die Anzeigen für Lerntag und Fächer des Lernsystems.
     * @param day Der aktuelle Lerntag.
     * @param compartments Die Fächer des Lernsystems.
     */
    public void updateDisplayDayAndCompartments(String day, String compartments) {
        dayLearnedCounter.setText(day);
        currentCompartments.setText(compartments);
    }

    /**
     * Aktualisiert die Anzeige für die Anzahl verbliebener Karteikarten.
     * @param cardsLeft Der angezeigte Text zu verbliebenen Karteikarten.
     */
    public void updateDisplayCardsLeft(String cardsLeft) {
        cardsLeftCounter.setText(cardsLeft);
    }

    /**
     * Aktualisiert die Anzeige der Rückseite der Karteikarte mit neuem Text.
     * @param string Der anzuzeigende Text für die Rückseite.
     */
    public void updateDisplayBackOfCurrentCard(String string) {
        this.back.setText(string);
    }

    /**
     * Entfernt das Fenster zur Karteikartenvorbereitung.
     */
    public void clearPreparePhasePanel() {
        mainGui.remove(prepareCardsPanel);
        mainGui.revalidate();
        mainGui.repaint();
    }

    /**
     * Schließt das Fenster zum Lernprozess und öffnet das Startfenster der LernGUI.
     */
    public void exitLearningPanel() {
        mainGui.remove(learningPanel);
        mainGui.createStartPanel();
        mainGui.revalidate();
        mainGui.repaint();
    }

    /**
     * Erzeugt ein Dialogfenster das den Nutzer wählen lässt wie er nach beendetem Lernen verfahren möchte.
     */
    public void showContinueOrSaveDialog() {
        int option = JOptionPane.showOptionDialog(mainGui, "Weiterlernen oder den Fortschritt speichern?",
                "Fachende", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                continueOrSaveOption, continueOrSaveOption[0]);
        if (option == JOptionPane.YES_OPTION){
            controller.prepareForNextDay();
        }
        else {
            controller.saveProgress();
        }
    }

    /**
     * Erneuert die Anzeige des Lernfensters.
     */
    public void recreateLearningPanel() {
        mainGui.remove(learningPanel);
        createLearningPanel();
        mainGui.revalidate();
        mainGui.repaint();
    }

    /**
     * Erneuert die Darstellung der Anzeige von aktuellem Kartenrücken.
     * @param s Der Text der Rückseite.
     */
    public void resetDisplayBackOfCurrentCard(String s) {
        back.setText(s);
    }
}
