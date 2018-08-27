package ru.ezhov.translator.gui;

import ru.ezhov.translator.gui.util.JTextFieldWithText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SourcePanel extends JPanel {
    private final JTextField textFieldFrom;
    private final JButton buttonClean;

    SourcePanel() {
        setLayout(new BorderLayout());
        textFieldFrom = new JTextFieldWithText("Введите слово или фразу и нажмите \"ENTER\"");
        textFieldFrom.setToolTipText("<html> Для очистки поля нажмите <b>ESC</b><br>Для переключения языка нажмите <b>Ctrl + W</b>");
        buttonClean = new JButton(new ImageIcon(getClass().getResource("/clean_16x16.png")));
        buttonClean.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textFieldFrom.setText("");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                textFieldFrom.setText("");
            }
        });
        buttonClean.setToolTipText("Очистить поле (достаточно навести на кнопку)");
        Dimension dimension = new Dimension(25, 25);
        textFieldFrom.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = textFieldFrom.getText();
                if (text != null && !"".equals(text)) {
                    textFieldFrom.select(0, text.length());
                }
            }
        });
        buttonClean.setPreferredSize(dimension);
        add(textFieldFrom, BorderLayout.CENTER);
        add(buttonClean, BorderLayout.EAST);
    }

    public void addKeyListener(KeyListener keyListener) {
        textFieldFrom.addKeyListener(keyListener);
    }

    public String getText() {
        return textFieldFrom.getText();
    }

    public void clearText() {
        textFieldFrom.setText("");
    }
}
