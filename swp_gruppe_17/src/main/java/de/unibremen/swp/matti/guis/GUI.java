package de.unibremen.swp.matti.guis;

import de.unibremen.swp.matti.Matti;
import de.unibremen.swp.matti.controllers.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI extends JFrame {
    /**
     * Die Standardbreite des Hauptfensters.
     */
    private static final int DEFAULT_WIDTH = 800;

    /**
     * Die Standardhöhe des Hauptfensters.
     */
    private static final int DEFAULT_HEIGHT = 600;

    /**
     * Der Controller, der die Eingaben entgegennimmt und die Verarbeitung veranlasst.
     */
    private final transient Controller controller;
    /**
     * Die Grenze des Anzeigebereichs der GUI.
     */

    protected static final Border BLACK_LINE_BORDER = BorderFactory.createLineBorder(Color.BLACK);
    private JPanel startPanel;

    /**
     * Erzeugt ein neues Hauptfenster mit gegebenem Titel und Controller.
     *
     * @param title       Der Titel des neuen Hauptfensters.
     * @param pController Der Controller für das neue Hauptfenster.
     * @throws IllegalArgumentException Falls der Controller den Wert {@code null} hat.
     */
    public GUI(final String title, final Controller pController) {
        super(title);
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        controller = pController;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMenu();
        createStartPanel();
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    /**
     * Erzeugt das Main Menu. Derzeit hat es 4 Buttons:
     * Kategorie verwalten um in die GUI der Kategorieverwaltung zu gelangen.
     * Karteikarte verwalten um in die GUI der Karteikartenverwaltung zu gelangen.
     * Glossar um nach Auswahl des Sortierungsmodus das Glossar anzuzeigen.
     * Karteikasten verwalten um in dei GUI der Karteikastenverwaltung zu gelangen.
     * @author Hai Trinh
     */
    void createStartPanel() {
        startPanel = new JPanel(new GridBagLayout());
        JLabel mattiIcon = new JLabel(Matti.APP_NAME + " v" + Matti.VERSION, SwingConstants.CENTER);
        // Kategorie verwalten
        JButton manageCategory = new JButton("Kategorie verwalten");
        manageCategory.addActionListener(e -> {
            controller.manageCategory();
            removeComponent();
        });
        // Karteikarte verwalten
        final JButton manageCards = new JButton("Karteikarte verwalten");
        manageCards.addActionListener(e -> {
            controller.manageCards();
            removeComponent();
        });
        // Glossar zeigen
        final JButton glossary = new JButton("Glossar");
        glossary.addActionListener(e -> {
            controller.glossary();
            removeComponent();
        });
        // Karteikasten verwalten
        final JButton manageCardBoxes = new JButton("Karteikasten verwalten");
        manageCardBoxes.addActionListener(e -> {
            controller.manageCardBoxes();
            removeComponent();
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.weighty = 0.4;
        c.gridx = 1;
        c.gridy = 0;
        startPanel.add(mattiIcon, c);

        c.ipadx = 80;
        c.weighty = 0.15;
        c.gridx = 1;
        c.gridy = 1;
        startPanel.add(glossary, c);

        c.gridx = 1;
        c.gridy = 2;
        startPanel.add(manageCategory, c);

        c.gridx = 1;
        c.gridy = 3;
        startPanel.add(manageCards, c);

        c.gridx = 1;
        c.gridy = 4;
        startPanel.add(manageCardBoxes, c);

        add(startPanel);
    }

    private void removeComponent() {
        remove(startPanel);
        revalidate();
        repaint();
    }

    /**
     * Erzeugt die Menu Bar.
     */
    private void createMenu() {
        final JMenuBar menuBar = new JMenuBar();
        // Datei
        final JMenu fileMenu = new JMenu("Datei");
        final JMenuItem exit = new JMenuItem("beenden");
        // FIXME: Aufruf einer Controller-Methode, die Überprüfungen durchführt etc...
        exit.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }
}
