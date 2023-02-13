package de.unibremen.swp.matti.util;

import javax.swing.*;
import java.awt.*;

public class JTextAreaFactory {
    public static void upgrade(JTextArea comp){
        comp.setEnabled(false);
        comp.setDisabledTextColor(Color.BLACK);
        comp.setLineWrap(true);
        comp.setWrapStyleWord(true);
    }
}
