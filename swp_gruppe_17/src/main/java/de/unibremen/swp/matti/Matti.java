package de.unibremen.swp.matti;

import de.unibremen.swp.matti.controllers.Controller;
import de.unibremen.swp.matti.logics.BusinessLogic;

public class Matti {
    /**
     * Der Name der Applikation.
     */
    public static final String APP_NAME = "Matti";

    /**
     * Die Versionsnummer der Applikation.
     */
    public static final String VERSION = "1.0";

    /**
     * Startmethode der Applikation. Erzeugt ein Controller-Objekt und ruft darauf die Methode zum Starten der
     * Applikation auf ({@link Controller#startApplication()}).
     *
     * @param args Werden ignoriert.
     */
    public static void main(final String[] args) {
        try {
            final BusinessLogic businessLogic = new BusinessLogic();
            final Controller controller = new Controller(businessLogic);
            controller.startApplication();
        }
        catch (final Exception e) {
            System.err.printf("Something went wrong: %s", e.getMessage()); // FIXME: use a logger!
            e.printStackTrace();
        }

    }
}
