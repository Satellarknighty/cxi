package de.unibremen.swp.matti.guis;

import de.unibremen.swp.matti.controllers.GlossaryController;
import de.unibremen.swp.matti.util.JTextAreaFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Verwaltet die GUI beim Glossar. Erzeugt Ereignisse, die von GlossaryController
 * behandelt werden.
 */
public class GlossaryGUI {
    private final GUI mainGui;
    private final transient GlossaryController controller;
    private DefaultListModel<String> filteredCards;
    private JTextArea currentFilter;
    private JTextArea frontContent;
    private JTextArea backContent;

    public GlossaryGUI(final GUI mainGUI, final GlossaryController controller1){
        this.mainGui = mainGUI;
        this.controller = controller1;
    }

    /**
     * Erzeugt das Panel fürs Glossar.
     */

    public void createStartGlossaryPanel(){
        GridBagConstraints c;
        JPanel glossaryPanel = new JPanel(new GridLayout(1,2));

        JPanel buttons = new JPanel(new GridLayout(6,1));
        JLabel glossaryLabel = new JLabel("Glossar",SwingConstants.CENTER);
        glossaryLabel.setBorder(GUI.BLACK_LINE_BORDER);
        JButton alphabetical = new JButton("alphabetisch sortieren");
        alphabetical.addActionListener(e -> controller.sortAlphabetically());
        JButton filterByCategory = new JButton("nach Kategorie filtern");
        filterByCategory.addActionListener(e -> controller.filterByCategory());
        JButton filterByKeyword = new JButton("nach Schlagwörter filtern");
        filterByKeyword.addActionListener(e -> controller.filterByKeyword());
        JPanel search = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        JTextArea searchText = new JTextArea();
        searchText.setLineWrap(true);

        c.weightx = 0.7;
        c.gridx = 0;
        c.gridy = 0;
        search.add(searchText, c);
        JButton searchButton = new JButton("suchen");
        searchButton.addActionListener(e -> {
            String searchTerm = searchText.getText();
            if (!searchTerm.isEmpty()){
                controller.searchFor(searchText.getText());
            }
        });

        c.weightx = 0.3;
        c.gridx = 1;
        c.gridy = 0;
        search.add(searchButton, c);
        JButton back = new JButton("zurück");
        back.addActionListener(e -> {
            mainGui.createStartPanel();
            mainGui.remove(glossaryPanel);
            mainGui.revalidate();
            mainGui.repaint();
        });

        buttons.add(glossaryLabel);
        buttons.add(alphabetical);
        buttons.add(filterByCategory);
        buttons.add(filterByKeyword);
        buttons.add(search);
        buttons.add(back);

        glossaryPanel.add(buttons);

        JPanel cardPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        currentFilter = new JTextArea("Aktuelle Filter:");
        JTextAreaFactory.upgrade(currentFilter);
        JScrollPane cFilter2 = new JScrollPane(currentFilter);
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        cardPanel.add(cFilter2, c);

        filteredCards = new DefaultListModel<>();
        JList<String> fCards2 = new JList<>(filteredCards);
        fCards2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fCards2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        fCards2.setVisibleRowCount(-1);
        fCards2.setFixedCellWidth(160);
        fCards2.setFixedCellHeight(30);
        fCards2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                final int index = fCards2.getSelectedIndex();
                if (index >= 0){
                    controller.selectFlashcard(filteredCards.get(index));
                }
            }
        });
        final JScrollPane fCards3 = new JScrollPane(fCards2);

        c.weighty = 0.6;
        c.gridx = 0;
        c.gridy = 1;
        cardPanel.add(fCards3, c);

        JPanel frontAndBack = new JPanel(new GridLayout(1, 2));
        frontContent = new JTextArea("Vorderseite.");
        JTextAreaFactory.upgrade(frontContent);
        JScrollPane front2 = new JScrollPane(frontContent);
        backContent = new JTextArea("Rückseite.");
        JTextAreaFactory.upgrade(backContent);
        JScrollPane back2 = new JScrollPane(backContent);
        frontAndBack.add(front2);
        frontAndBack.add(back2);

        c.weighty = 0.3;
        c.gridx = 0;
        c.gridy = 2;
        cardPanel.add(frontAndBack, c);

        glossaryPanel.add(cardPanel);

        mainGui.add(glossaryPanel);
    }

    /**
     * Aktualisiert die Darstellung den aktuellen Filter.
     * @param s die Darstellung im Form von String.
     */
    public void updateDisplayCurrentFilter(String s) {
        currentFilter.setText(s);
    }

    /**
     * Alle gefilterten Karteikarten werden dargestellt.
     * @param fromDb    Liste von Karten im Form von String.
     */
    public void updateDisplayFilteredCards(List<String> fromDb) {
        filteredCards.clear();
        filteredCards.addAll(fromDb);
    }

    /**
     * Filtert nach welche Kategorie.
     *
     * @param categories    alle Kategorien.
     */
    public void showChooseCategoryDialog(String[] categories) {
        String input = (String) JOptionPane.showInputDialog(mainGui, "Kategorie auswählen",
                "Nach Kategorie filtern", JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);
        controller.filterByCategory(input);
    }

    /**
     * Filtert nach Schlagwort.
     *
     * @param keywords  alle Schlagwörter.
     */
    public void showChooseKeywordDialog(String[] keywords) {
        String input = (String) JOptionPane.showInputDialog(mainGui, "Schlagwort auswählen",
                "Nach Schlagwort filtern", JOptionPane.QUESTION_MESSAGE, null, keywords, keywords[0]);
        controller.filterByKeyword(input);
    }

    /**
     * Stellt die Vorder-/Rückseite der aktuellen Karteikarte dar.
     *
     * @param front Die Vorderseite.
     * @param back  Die Rückseite.
     */
    public void updateDisplayContentOfSelectedCard(String front, String back) {
        frontContent.setText(front);
        backContent.setText(back);
    }
}
