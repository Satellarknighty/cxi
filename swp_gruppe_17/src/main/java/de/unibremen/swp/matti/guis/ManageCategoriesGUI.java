package de.unibremen.swp.matti.guis;

import de.unibremen.swp.matti.controllers.ManageCategoriesController;
import de.unibremen.swp.matti.util.JTextAreaFactory;
import de.unibremen.swp.matti.util.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageCategoriesGUI {
    /**
     * Der Controller der die Kategorien steuert.
     */
    private final ManageCategoriesController controller;
    /**
     * Die HauptGUI der Applikation.
     */
    private final GUI mainGui;
    /**
     * Namen aller Kategorien als wählbare Liste.
     */
    private DefaultListModel<String> allCategories;
    /**
     * Bereich der alle Unterkategorien enthält.
     */
    private JTextArea allSubCategories;
    /**
     * Bereich der alle Karteikarten enthält.
     */
    private JTextArea allFlashcards;
    /**
     * Array, das alle Optionen enthält, die dem Nutzer beim Löschen einer Kategorie geboten werden.
     */
    private static final Object[] deleteOptions = {"Ja", "Abbrechen"};

    /**
     * Erzeugt eine neue GUI für alles in Verbindung mit den Kategorien stehende.
     * @param controller Der Controller der die GUI steuert.
     * @param mainGui Die HauptGUI der Applikation.
     */
    public ManageCategoriesGUI(ManageCategoriesController controller, GUI mainGui) {
        this.controller = controller;
        this.mainGui = mainGui;
    }

    /**
     * Erzeugt ein Fenster zur Verwaltung von Kategorien mit den Optionen Kategorien hinzuzufügen oder zu löschen, sowie gleiches mit Unterkategorien zu veranlassen.
     */
    public void createManageCategoriesPanel() {
        GridBagConstraints c;
        JPanel manageCategoriesPanel = new JPanel(new GridLayout(1, 2));

        //Panel für Kategorien
        final JPanel categoryButtonsPanel = new JPanel(new GridLayout(6, 1));
        final JLabel categoryPanelLabel = new JLabel("Kategorie", SwingConstants.CENTER);
        categoryPanelLabel.setBorder(GUI.BLACK_LINE_BORDER);

        final JButton createCategory = new JButton("hinzufügen");
        createCategory.addActionListener(e -> controller.createCategory());
        final JButton addSubCategory = new JButton("Unterkategorie zuordnen");
        addSubCategory.addActionListener(e -> controller.addSubCategory());
        final JButton removeSubCategory = new JButton("Unterkategorie entfernen");
        removeSubCategory.addActionListener(e -> controller.removeSubCategory());
        final JButton deleteCategory = new JButton("löschen");
        deleteCategory.addActionListener(e -> controller.deleteCategory());
        JButton back = new JButton("zurück");
        back.addActionListener(e -> {
            controller.exitManageCategoriesPanel();
            mainGui.createStartPanel();
            mainGui.remove(manageCategoriesPanel);
            mainGui.revalidate();
            mainGui.repaint();
        });


        categoryButtonsPanel.add(categoryPanelLabel);
        categoryButtonsPanel.add(createCategory);
        categoryButtonsPanel.add(addSubCategory);
        categoryButtonsPanel.add(removeSubCategory);
        categoryButtonsPanel.add(deleteCategory);
        categoryButtonsPanel.add(back);

        JPanel displayCategory = new JPanel(new GridLayout(3, 1));
        JPanel allCategoriesPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        JLabel allCategoriesLabel = new JLabel("Alle Kategorien", SwingConstants.CENTER);
        allCategoriesLabel.setBorder(GUI.BLACK_LINE_BORDER);

        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;

        allCategoriesPanel.add(allCategoriesLabel, c);

        //Panel für das Auswählen der Kategorien.
        allCategories = new DefaultListModel<>();
        controller.updateDisplayCategories();

        final JList<String> allCategories2 = new JList<>(allCategories);
        allCategories2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allCategories2.setLayoutOrientation(JList.VERTICAL);
        allCategories2.setVisibleRowCount(-1);
        allCategories2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                final int index = allCategories2.getSelectedIndex();
                if (index >= 0){
                    controller.selectCategory(allCategories.get(index));
                }
            }
        });
        final JScrollPane allCategories3 = new JScrollPane(allCategories2);

        c.weighty = 0.9;
        c.gridx = 0;
        c.gridy = 1;

        allCategoriesPanel.add(allCategories3, c);

        allSubCategories = new JTextArea("Die Unterkategorie(n) von der gewählten Kategorie.");
        JTextAreaFactory.upgrade(allSubCategories);
        final JScrollPane allSubCategories2 = new JScrollPane(allSubCategories);

        allFlashcards = new JTextArea("Die Karteikarte(n) von der gewählten Kategorie.");
        JTextAreaFactory.upgrade(allFlashcards);
        JScrollPane allFlashcards2 = new JScrollPane(allFlashcards);

        displayCategory.add(allCategoriesPanel);
        displayCategory.add(allSubCategories2);
        displayCategory.add(allFlashcards2);

        manageCategoriesPanel.add(categoryButtonsPanel);
        manageCategoriesPanel.add(displayCategory);

        mainGui.add(manageCategoriesPanel);
    }
    /**
     * Erzeugt ein Dialogfenster zur Erstellung einer Kategorie mit frei wählbarem Namen. Er darf nicht null oder leer sein.
     */
    public void displayCreateCategoryDialog() {
        final String categoryName = JOptionPane.showInputDialog(mainGui, "Name", "Kategorie erzeugen", JOptionPane.QUESTION_MESSAGE);
        controller.createCategory(Validator.checkNotNullOrBlank(categoryName, "categoryName"));
    }
    /**
     * Aktualisiert die Liste aller Kategorien.
     */
    public void updateAllCategories(List<String> categoriesString) {
        allCategories.clear();
        allCategories.addAll(categoriesString);
    }

    /**
     * Zeigt alle Unterkategorien repräsentativ als String an.
     * @param subAsString Die Unterkategorien als String.
     */
    public void displaySubCategoriesOfCurrentCategory(final String subAsString) {
        allSubCategories.setText(subAsString);
    }

    /**
     * Erzeugt ein Zustimmungsfenster, das vom Nutzer eine Bestätigung fordert, wenn eine Kategorie gelöscht werden soll.
     */
    public void showConfirmDeleteCategoryDialog() {
        final int confirm = JOptionPane.showOptionDialog(mainGui, "Die gewählte Kategorie wirklich löschen?\n (Diese Aktion kann nicht rückgängig gemacht werden!)",
                "Kategorie löschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, deleteOptions, deleteOptions[0]);
        if (confirm == JOptionPane.OK_OPTION){
            controller.confirmedDeleteCategory();
        }
    }

    /**
     * Erzeugt ein Dialogfenster, in dem eine Kategorie einer anderen als Unterkategorie zugeordnet werden kann.
     * @param categoryNames Ein Array von Strings mit Kategorienamen.
     */
    public void displayAddSubCategoryDialog(final String[] categoryNames) {
        final Object categoryName = JOptionPane.showInputDialog(mainGui, "Kategorie auswählen",
                "Unterkategorie zuordnen",
                JOptionPane.QUESTION_MESSAGE, null, categoryNames, categoryNames[0]);
        controller.addSubCategory((String) categoryName);
    }

    /**
     * Erzeugt ein Dialogfenster in dem eine zu entfernende Unterkategorie gewählt werden kann.
     * @param subCategoryNames Ein Array von Strings der alle Unterkategorienamen enthält.
     */
    public void chooseSubCategoryToRemoveDialog(final String[] subCategoryNames) {
        final Object subCategory = JOptionPane.showInputDialog(mainGui, "Unterkategorie auswählen",
                "Unterkategorie entfernen",
                JOptionPane.QUESTION_MESSAGE, null, subCategoryNames, subCategoryNames[0]);
        controller.removeSubCategory((String) subCategory);
    }

    /**
     * Zeigt alle Karteikarten der aktuellen Kategorie repräsentativ als String an.
     * @param flashcardsAsString Die Karteikarten als String.
     */
    public void displayFlashcardsOfCurrentCategory(String flashcardsAsString) {
        allFlashcards.setText(flashcardsAsString);
    }
}
