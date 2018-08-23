package ru.ezhov.translator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TargetPanel extends JPanel {
    private final JTextField textFieldTo;
    private final JButton buttonCopy;

    public TargetPanel() {
        setLayout(new BorderLayout());
        textFieldTo = new JTextField();
        textFieldTo.setEditable(false);
        textFieldTo.setBackground(Color.decode("#D8D8D8"));
        buttonCopy = new JButton(new ImageIcon(getClass().getResource("/copy_16x16.png")));
        buttonCopy.setToolTipText("Копировать в буффер результат перевода");
        Dimension dimension = new Dimension(25, 25);
        buttonCopy.setPreferredSize(dimension);
        buttonCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textFieldTo.getText();
                if (text != null && !"".equals(text)) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection stringSelection = new StringSelection(text);
                    clipboard.setContents(stringSelection, null);
                }
            }
        });
        add(textFieldTo, BorderLayout.CENTER);
        add(buttonCopy, BorderLayout.EAST);
    }

    public void setText(String text) {
        textFieldTo.setText(text);
        textFieldTo.setCaretPosition(0);
    }
}
