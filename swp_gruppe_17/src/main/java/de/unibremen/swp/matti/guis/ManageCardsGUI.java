package de.unibremen.swp.matti.guis;

import de.unibremen.swp.matti.controllers.ManageCardsController;
import de.unibremen.swp.matti.util.JTextAreaFactory;
import de.unibremen.swp.matti.util.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageCardsGUI {
    /**
     * Die HauptGUI der Applikation.
     */
    private final GUI mainGui;
    /**
     * Der Controller der die Verwaltung der Karteikarten steuert.
     */
    private final transient ManageCardsController controller;
    /**
     * Auf diesem Textfeld stehen die String-Repräsentationen aller Karteikarten der gewählten Kategorie.
     */
    private DefaultListModel<String> allCards;
    /**
     * Bereich für die Anzeige der Kartenvorderseite.
     */
    private JTextArea frontContent;
    /**
     * Bereich für die Anzeige der Kartenrückseite.
     */
    private JTextArea backContent;
    /**
     * Bereich für die Anzeige der verlinkten Karteikarten
     */
    private JTextArea linked;
    /**
     * Bereich für die Anzeige der zugehörigen Kategorien.
     */
    private JTextArea categories;
    /**
     * Array mit allen editierbaren Eigenschaften der Karteikarten.
     */
    private static final Object[] editOptions = {"Vorder-/Rückseite", "Verlinkung", "Aus eine Kategorie entfernen",
    "Schlagwörter"};
    /**
     * Array mit allen Optionen bei Auswahl des Löschens einer Karteikarte.
     */
    private static final Object[] deleteOptions = {"Ja", "Abbrechen"};

    /**
     * Erzeugt eine neue Version der GUI zur Verwaltung von Karteikarten basierend auf dem sie steuernden Controller und der HauptGUI der Applikation.
     * @param controller Der Controller der die GUI steuert.
     * @param mainGui Die HauptGUI der Applikation.
     */
    public ManageCardsGUI(final ManageCardsController controller, final GUI mainGui){
        this.controller = controller;
        this.mainGui = mainGui;
    }
    /**
     * Erzeugt das Menü zur Karteikartenverwaltung mit Optionen zum hinzufügen, bearbeiten, verlinken oder löschen von Karteikarten.
     */
    public void createManageCardsPanel() {
        GridBagConstraints c;
        final JPanel manageCardPanel = new JPanel(new GridLayout(1, 2));

        //Panel für Karteikarten
        final JPanel cardButtonsPanel = new JPanel(new GridLayout(8, 1));
        final JLabel cardPanelLabel = new JLabel("Karteikarte", SwingConstants.CENTER);
        cardPanelLabel.setBorder(GUI.BLACK_LINE_BORDER);

        final JButton createCard = new JButton("hinzufügen");
        createCard.addActionListener(e -> controller.createFlashcard());
        final JButton addToCategory = new JButton("Kategorie zuordnen");
        addToCategory.addActionListener(e -> controller.addFlashcardToCategory());
        JButton addKeywords = new JButton("Schlagwort hinzufügen");
        addKeywords.addActionListener(e -> controller.addKeywords());
        final JButton linkCard = new JButton("verlinken");
        linkCard.addActionListener(e -> controller.linkFlashcard());
        final JButton editCard = new JButton("bearbeiten");
        editCard.addActionListener(e -> controller.editFlashcard());
        final JButton deleteCard = new JButton("löschen");
        deleteCard.addActionListener(e -> controller.deleteFlashcard());
        final JButton back = new JButton("zurück");
        back.addActionListener(e -> {
            controller.exitManageCardsPanel();
            mainGui.createStartPanel();
            mainGui.remove(manageCardPanel);
            mainGui.revalidate();
            mainGui.repaint();
        });

        cardButtonsPanel.add(cardPanelLabel);
        cardButtonsPanel.add(createCard);
        cardButtonsPanel.add(addToCategory);
        cardButtonsPanel.add(addKeywords);
        cardButtonsPanel.add(linkCard);
        cardButtonsPanel.add(editCard);
        cardButtonsPanel.add(deleteCard);
        cardButtonsPanel.add(back);

        JPanel allCardsPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        JLabel allCardsLabel = new JLabel("Alle Karteikarten", SwingConstants.CENTER);
        allCardsLabel.setBorder(GUI.BLACK_LINE_BORDER);

        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;

        allCardsPanel.add(allCardsLabel, c);

        //Panels für die Vorder- und Rückseite gewählter Karteikarte
        final JPanel displayCard = new JPanel(new GridLayout(3, 1));
        //Panel für das Auswählen der Karteikarten.
        allCards = new DefaultListModel<>();
        controller.updateDisplayFlashcards();
        final JList<String> allCards2 = new JList<>(allCards);
        allCards2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allCards2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        allCards2.setVisibleRowCount(-1);
        allCards2.setFixedCellWidth(160);
        allCards2.setFixedCellHeight(30);
        allCards2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                final int index = allCards2.getSelectedIndex();
                if (index >= 0){
                    controller.selectFlashcard(allCards.get(index));
                }

            }
        });
        final JScrollPane allCards3 = new JScrollPane(allCards2);

        c.weighty = 0.9;
        c.gridx = 0;
        c.gridy = 1;

        allCardsPanel.add(allCards3, c);

        JPanel cardContentPanel = new JPanel(new GridLayout(1, 2));
        frontContent = new JTextArea("Die Vorderseite.");
        JTextAreaFactory.upgrade(frontContent);
        final JScrollPane frontContent2 = new JScrollPane(frontContent);
        backContent = new JTextArea("Die Rückseite.");
        JTextAreaFactory.upgrade(backContent);
        final JScrollPane backContent2 = new JScrollPane(backContent);
        cardContentPanel.add(frontContent2);
        cardContentPanel.add(backContent2);

        JPanel cardPropertiesPanel = new JPanel(new GridLayout(2, 1));
        linked = new JTextArea("Die Karteikarte(n), die nach dieser Karteikarte verlinkt sind.");
        JTextAreaFactory.upgrade(linked);
        final JScrollPane linked2 = new JScrollPane(linked);
        categories = new JTextArea("Die Kategorie(n) dieser Karteikarte.");
        JTextAreaFactory.upgrade(categories);
        JScrollPane categories2 = new JScrollPane(categories);
        cardPropertiesPanel.add(linked2);
        cardPropertiesPanel.add(categories2);

        displayCard.add(allCardsPanel);
        displayCard.add(cardContentPanel);
        displayCard.add(cardPropertiesPanel);

        manageCardPanel.add(cardButtonsPanel);
        manageCardPanel.add(displayCard);

        mainGui.add(manageCardPanel);
    }
    /**
     * Erzeugt ein Dialogfenster in dem nach Angaben für Name, Vorderseite und Rückseite einer neu zu erstellenden Karteikarte gefragt wird. Die Werte dürfen weder null noch leer sein.
     */
    public void displayCreateFlashcardDialog() {
        final String cardName = JOptionPane.showInputDialog(mainGui,
                "Name","Karteikarte erzeugen", JOptionPane.QUESTION_MESSAGE);
        final String front = JOptionPane.showInputDialog(mainGui,
                "Vorderseite", "Karteikarte erzeugen", JOptionPane.QUESTION_MESSAGE);
        final String back = JOptionPane.showInputDialog(mainGui,
                "Rückseite", "Karteikarte erzeugen", JOptionPane.QUESTION_MESSAGE);
        controller.createFlashcard(Validator.checkNotNullOrBlank(cardName, "cardName"),
                Validator.checkNotNullOrBlank(front, "front"),
                Validator.checkNotNullOrBlank(back, "back"));
    }

    /**
     * Aktualisiert die Darstellung aller Karteikarten.
     *
     * @param cardsString Eine Liste von Strings zur Repräsentation aller Karteikarten.
     */
    public void updateDisplayFlashcards(final List<String> cardsString) {
        allCards.clear();
        allCards.addAll(cardsString);
    }

    /**
     * Erzeugt ein Fenster zur Darstellung der Vorder- und Rückseite der Karteikarte.
     * @param front Der Inhalt der Karteikartenvorderseite.
     * @param back Der Inhalt der Karteikartenrückseite.
     */
    public void displayFrontAndBack(final String front, final String back) {
        frontContent.setText(front);
        backContent.setText(back);
    }

    /**
     * Erzeugt ein Zustimmungsfenster das eine Bestätigung erfragt bevor eine Karteikarte gelöscht wird.
     */
    public void showConfirmDeleteFlashcardDialog() {
        final int confirm = JOptionPane.showOptionDialog(mainGui, "Die gewählte Karteikarte wirklich löschen?\n (Diese Aktion kann nicht rückgängig gemacht werden!)",
                "Karteikarte löschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, deleteOptions, deleteOptions[0]);
        if (confirm == JOptionPane.OK_OPTION){
            controller.confirmedDeleteFlashcard();
        }
    }

    /**
     * Erzeugt ein Dialogfenster zur Auswahl der Karteikarten zu denen eine Verlinkung erstellt werden soll.
     * @param cardNames Liste von Strings die alle Karteikartennamen repräsentieren.
     */
    public void showChooseFlashcardToLinkFlashcardDialog(final String[] cardNames) {
        final Object linkerName = JOptionPane.showInputDialog(mainGui, "Karteikarte auswählen", "Karteikarte verlinken",
                JOptionPane.QUESTION_MESSAGE, null, cardNames, cardNames[0]);
        controller.linkFlashcard((String) linkerName);
    }

    /**
     * Erzeugt ein Dialogfenster zur Auswahl zwischen den editierbaren Optionen einer Karteikarte. (Aktuell Vorder-/Rückseite, Verlinkung, aus Kategorie entfernen, Verschlagwortung)
     */
    public void showOptionEditFlashcardDialog() {
        final String option = (String) JOptionPane.showInputDialog(mainGui, "Wie möchtest du die Karteikarte bearbeiten?",
                "Karteikarte bearbeiten", JOptionPane.QUESTION_MESSAGE, null, editOptions, editOptions[0]);

        if (option != null) {
            if (option.equals(editOptions[0])){
                controller.editContentOfFlashcard();
            }
            else if (option.equals(editOptions[1])) {
                controller.editLinkOfFlashcard();
            }
            else if (option.equals(editOptions[2])) {
                controller.editCategoryOfFlashcard();
            }
            else if (option.equals(editOptions[3])){
                controller.editKeywordsOfFlashcard();
            }
        }
    }

    /**
     * Erzeugt ein Dialogfenster zur Auswahl der zu entfernenden Verlinkung einer Karteikarte.
     * @param linkerNames Karteikarte von der die Verlinkung aufgehoben werden soll.
     */
    public void showEditLinkFlashcardDialog(final String[] linkerNames) {
        final String linkNameToBeRemoved = (String) JOptionPane.showInputDialog(mainGui, "Karteikarte zum Aufheben der Verlinkung wählen:",
                "Karteikarte bearbeiten", JOptionPane.QUESTION_MESSAGE, null, linkerNames, linkerNames[0]);
        controller.editLinkOfFlashcard(linkNameToBeRemoved);
    }

    /**
     * Erzeugt ein Dialogfenster zur Bearbeitung der Kategoriebezogenen Eigenschaften der Karteikarte
     *
     * @param whichCategory zu bearbeitende Kategorie
     */
    public void showEditCategoryOfFlashcardDialog(final String[] whichCategory) {
        final String categoryName = (String) JOptionPane.showInputDialog(mainGui, "aus welcher Kategorie entfernen?",
                "Karteikarte bearbeiten", JOptionPane.QUESTION_MESSAGE, null, whichCategory, whichCategory[0]);
        controller.editCategoryOfFlashcard(categoryName);
    }

    /**
     * Erzeugt ein Dialogfenster, das das Hinzufügen einer Karteikarte zu einer Kategorie ermöglicht.
     * @param allCategories Array von Strings der alle Kategorien enthält.
     */
    public void displayAddFlashcardToCategoryDialog(final String[] allCategories) {
        final String newCategoryName = (String) JOptionPane.showInputDialog(mainGui, "Kategorie auswählen:", "Kategorie zuordnen",
                JOptionPane.QUESTION_MESSAGE, null, allCategories, allCategories[0]);
        controller.addFlashcardToCategory(newCategoryName);
    }

    /**
     * Zeigt die Verlinkung der Karteikarte in Form eines Strings an.
     * @param linkedAsString String der verlinkten Karteikarte.
     */
    public void displayLinked(String linkedAsString) {
        linked.setText(linkedAsString);
    }

    /**
     * Zeigt die Kategorie der Karteikarte als String an.
     * @param categoriesAsString String der zugehörigen Karteikarte.
     */
    public void displayCategories(String categoriesAsString) {
        categories.setText(categoriesAsString);
    }

    /**
     * Erzeugt ein Dialogsfenster zum Hinzufügen von Schlagwörtern.
     */
    public void showAddKeywordsDialog() {
        String message = """
                Schlagwörter werden durch Kommas getrennt.
                Zeichen, Zahlen und Unterstrich sind erlaubt.
                (Leerzeichen werden ignoriert.)
                """;
        final String input = JOptionPane.showInputDialog(mainGui, message,
                "Schlagwörter hinzufügen", JOptionPane.QUESTION_MESSAGE);
        controller.addKeywords(input);
    }

    /**
     * Erzeugt ein Dialogsfenster um von ausgewählter Karteikarte Schlagwörter zu entfernen.
     * @param keywords Array von Strings der alle Schlagwörter der Karteikarte repräsentiert.
     */
    public void showChooseKeywordToRemoveDialog(String[] keywords) {
        final String keyword = (String) JOptionPane.showInputDialog(mainGui, "Schlagwort zum Entfernen wählen:",
                "Karteikarte bearbeiten.", JOptionPane.QUESTION_MESSAGE, null, keywords, keywords[0]);
        if (keyword != null){
            controller.editKeywordsOfFlashcard(keywords, keyword);
        }

    }
}
