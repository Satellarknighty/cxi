package de.unibremen.swp.matti.guis;

import de.unibremen.swp.matti.controllers.ManageCardBoxesController;
import de.unibremen.swp.matti.util.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageCardBoxesGUI {
    /**
     * Der zugehörige Controller mit dem zusammengearbeitet wird.
     */
    private final ManageCardBoxesController controller;
    /**
     * Die HauptGUI der Applikation.
     */
    private final GUI mainGui;
    /**
     * Das Fenster zur Verwaltung der Karteikästen.
     */
    private JPanel manageCardBoxPanel;
    /**
     * Namen aller Kategorien als wählbare Liste.
     */
    private DefaultListModel<String> allCategories;
    /**
     * Namen aller karteikästen als wählbare Liste.
     */
    private DefaultListModel<String> allCardBoxes;
    /**
     * Namen aller Kategorien in aktuellem Karteikasten als wählbare Liste.
     */
    private DefaultListModel<String> categoriesInCurrentCardBox;
    /**
     * Array mit den Optionen die beim Löschen auswählbar sind.
     */
    private static final Object[] deleteOptions = {"Ja", "Abbrechen"};
    /**
     * Array mit den Optionen die bei einer Lernfortsetzung auswählbar sind.
     */
    private static final Object[] continueLearningOptions = {"Weiterlernen", "Neu anfangen"};

    /**
     * Erzeugt eine neue KarteikastenGUI basierend auf dem zugehörigen Controller und der HauptGUI der Applikation.
     * @param manageCardBoxesController Der Controller der die GUI steuert.
     * @param mainGui DIe HauptGUI der Applikation.
     */
    public ManageCardBoxesGUI(final ManageCardBoxesController manageCardBoxesController, final GUI mainGui) {
        controller = manageCardBoxesController;
        this.mainGui = mainGui;
    }

    /**
     * Erzeugt das Fenster zum Verwalten der Karteikästen mit Knöpfen für alle Optionen und Anzeigen für bestehendes.
     */
    public void createManageCardBoxesPanel() {
        GridBagConstraints c;
        manageCardBoxPanel = new JPanel(new GridLayout(1, 2));

        //Panel für Kategorien
        final JPanel cardBoxButtonsPanel = new JPanel(new GridLayout(5, 1));
        final JLabel cardBoxPanelLabel = new JLabel("Karteikasten", SwingConstants.CENTER);
        cardBoxPanelLabel.setBorder(GUI.BLACK_LINE_BORDER);

        final JButton createCardBox = new JButton("erstellen");
        createCardBox.addActionListener(e -> controller.createCardBox());
        final JButton deleteCardBox = new JButton("löschen");
        deleteCardBox.addActionListener(e -> controller.deleteCardBox());

        final JButton learnCardBox = new JButton("lernen");
        learnCardBox.addActionListener(e -> controller.learnCardBox());

        final JButton back = new JButton("zurück");
        back.addActionListener(e -> {
            controller.exitManageCardBoxesPanel();
            mainGui.createStartPanel();
            removeComponent();
        });

        cardBoxButtonsPanel.add(cardBoxPanelLabel);
        cardBoxButtonsPanel.add(createCardBox);
        cardBoxButtonsPanel.add(deleteCardBox);
        cardBoxButtonsPanel.add(learnCardBox);
        cardBoxButtonsPanel.add(back);

        JPanel cardBoxesPanel = new JPanel(new GridLayout(3, 1));


        JPanel allBoxesPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        JLabel allBoxesLabel = new JLabel("Alle Karteikasten", SwingConstants.CENTER);
        allBoxesLabel.setBorder(GUI.BLACK_LINE_BORDER);

        allCardBoxes = new DefaultListModel<>();
        allCardBoxes.addAll(controller.getCardBoxesNames());
        final JList<String> allCardBoxes2 = new JList<>(allCardBoxes);
        allCardBoxes2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allCardBoxes2.setLayoutOrientation(JList.VERTICAL);
        allCardBoxes2.setVisibleRowCount(-1);
        allCardBoxes2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                final int index = allCardBoxes2.getSelectedIndex();
                if (index >= 0){
                    controller.selectCardBox(allCardBoxes.get(index));
                }
            }
        });
        final JScrollPane allCardBoxes3 = new JScrollPane(allCardBoxes2);

        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;

        allBoxesPanel.add(allBoxesLabel, c);
        c.weighty = 0.9;
        c.gridx = 0;
        c.gridy = 1;

        allBoxesPanel.add(allCardBoxes3, c);

        JPanel allCategoriesPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        final JLabel allCategoriesLabel = new JLabel("Alle Kategorien", SwingConstants.CENTER);
        allCategoriesLabel.setBorder(GUI.BLACK_LINE_BORDER);

        JPanel addCategoriesPanel = new JPanel(new GridBagLayout());

        allCategories = new DefaultListModel<>();
        allCategories.addAll(controller.getCategoriesNames());

        final JList<String> allCategories2 = new JList<>(allCategories);
        allCategories2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allCategories2.setLayoutOrientation(JList.VERTICAL);
        allCategories2.setVisibleRowCount(-1);
        final JScrollPane allCategories3 = new JScrollPane(allCategories2);

        final JButton addCategory = new JButton("+");
        addCategory.addActionListener(e -> {
            final int index = allCategories2.getSelectedIndex();
            if (index >= 0){
                controller.addCategoryToCurrentCardBox(allCategories.get(index));
            }
        });

        c.weighty = 1.0;

        c.weightx = 0.945;
        c.gridx = 0;
        c.gridy = 0;

        addCategoriesPanel.add(allCategories3, c);

        c.weightx = 0.055;
        c.gridx = 1;
        c.gridy = 0;
        addCategoriesPanel.add(addCategory, c);

        c.weightx = 1.0;

        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;

        allCategoriesPanel.add(allCategoriesLabel, c);

        c.weighty = 0.9;
        c.gridx = 0;
        c.gridy = 1;
        allCategoriesPanel.add(addCategoriesPanel, c);

        JPanel categoriesInBoxPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        final JLabel categoriesInCurrentCardBoxLabel = new JLabel(
                String.format("<html><body style=\"text-align: justify;  text-justify: inter-word;\">%s</body></html>",
                        "Kategorien in diesem Karteikasten"), SwingConstants.CENTER);
        categoriesInCurrentCardBoxLabel.setBorder(GUI.BLACK_LINE_BORDER);

        JPanel removeCategoriesPanel = new JPanel(new GridBagLayout());

        categoriesInCurrentCardBox = new DefaultListModel<>();
        final JList<String> categoriesCardBox2 = new JList<>(categoriesInCurrentCardBox);
        categoriesCardBox2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriesCardBox2.setLayoutOrientation(JList.VERTICAL);
        categoriesCardBox2.setVisibleRowCount(-1);
        final JScrollPane categoriesCardBox3 = new JScrollPane(categoriesCardBox2);

        final JButton removeCategory = new JButton("-");
        removeCategory.addActionListener(e -> {
            final int index = categoriesCardBox2.getSelectedIndex();
            if (index >= 0){
                controller.removeCategoryFromCurrentCardBox(categoriesInCurrentCardBox.get(index));
            }
        });

        c.weighty = 1.0;

        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 0;
        removeCategoriesPanel.add(categoriesCardBox3, c);

        c.weightx = 0.2;
        c.gridx = 1;
        c.gridy = 0;
        removeCategoriesPanel.add(removeCategory, c);

        c.weightx = 1.0;

        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        categoriesInBoxPanel.add(categoriesInCurrentCardBoxLabel, c);

        c.weighty = 0.9;
        c.gridx = 0;
        c.gridy = 1;
        categoriesInBoxPanel.add(removeCategoriesPanel, c);

        cardBoxesPanel.add(allBoxesPanel);
        cardBoxesPanel.add(allCategoriesPanel);
        cardBoxesPanel.add(categoriesInBoxPanel);

        manageCardBoxPanel.add(cardBoxButtonsPanel);
        manageCardBoxPanel.add(cardBoxesPanel);

        mainGui.add(manageCardBoxPanel);
    }

    /**
     * Entfernt das Fenster zur Karteikastenverwaltung.
     */
    private void removeComponent() {
        mainGui.remove(manageCardBoxPanel);
        mainGui.revalidate();
        mainGui.repaint();
    }

    /**
     * Erzeugt ein Dialogfenster zur Auswahl des Lernsystems.
     *
     * @param nameLearnSystems Die Namen aller verfügbaren Lernsysteme.
     */
    public void displayChooseLearnSystemDialog(final String[] nameLearnSystems) {
        final String name = (String) JOptionPane.showInputDialog(mainGui, "Lernsystem auswählen.",
                "Lernsystem zum Lernen setzen.",
                JOptionPane.QUESTION_MESSAGE, null, nameLearnSystems, nameLearnSystems[0]);
        if (name != null){
            controller.learnCardBox(name);
            removeComponent();
        }
    }

    /**
     * Erzeugt ein Dialogfenster für das Erstellen eines neuen Karteikastens.
     */
    public void displayCreateCardBoxDialog() {
        final String cardBoxName = JOptionPane.showInputDialog(mainGui,
                "Name","Karteikasten erzeugen", JOptionPane.QUESTION_MESSAGE);
        controller.createCardBox(Validator.checkNotNullOrBlank(cardBoxName, "cardName"));
    }

    /**
     * Aktualisiert die Liste aller gespeicherten Karteikästen.
     * @param names Eine Liste von Strings bestehend aus den Karteikastennamen.
     */
    public void updateAllCardBoxes(final List<String> names) {
        allCardBoxes.clear();
        allCardBoxes.addAll(names);
    }

    /**
     * Aktualisiert die Anzeige der in aktuellem Karteikasten genutzten Kategorien.
     * @param names EIne Liste von Strings bestehend aus den Kategorienamen.
     */
    public void updateDisplayCategoriesInCurrentCardBox(final List<String> names) {
        categoriesInCurrentCardBox.clear();
        categoriesInCurrentCardBox.addAll(names);
    }

    /**
     * Erzeugt ein Zustimmungsfenster für die Abfrage des Löschens eines Karteikastens und weist ggf den Controller an diesen zu Löschen.
     */
    public void showConfirmDeleteCardBoxDialog() {
        final int confirm = JOptionPane.showOptionDialog(mainGui, "Der gewählte Karteikasten wirklich löschen?\n" +
                                                                    "(Diese Aktion kann nicht rückgängig gemacht werden!)",
                "Karteikasten löschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, deleteOptions, deleteOptions[0]);
        if (confirm == JOptionPane.OK_OPTION){
            controller.confirmedDeleteCardBox();
        }
    }

    /**
     * Erzeugt ein Dialogfenster das Anfragt wie der Nutzer mit aktuellem Lernfortschritt weiter verfahren möchte.
     * @param learnProgress Der aktuelle Lernfortschritt des Karteikastens.
     */
    public void showContinueLearningOrStartOverDialog(String learnProgress) {
        String message = learnProgress + "Weiterlernen oder neu anfangen?";
        int option = JOptionPane.showOptionDialog(mainGui, message, "Lernen im Prozess",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                continueLearningOptions, continueLearningOptions[0]);
        if (option == JOptionPane.NO_OPTION){
            controller.startNewLearningSession();
        }
        else {
            controller.resumeLearnCardBox();
        }
        removeComponent();
    }
}
