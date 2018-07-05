package ru.ezhov.translator.gui;

import ru.ezhov.translator.translate.Translate;
import ru.ezhov.translator.translate.TranslateLang;
import ru.ezhov.translator.util.CompoundIcon;
import ru.ezhov.translator.util.JTextFieldWithText;
import ru.ezhov.translator.util.MouseMoveWindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class Gui {

    private Translate translate;

    public Gui(Translate translate) {
        this.translate = translate;
    }

    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable ex) {
                    //
                }
                final JDialog frame = new JDialog();
                frame.setUndecorated(true);
                JPanel panelBasic = new JPanel(new BorderLayout());
                panelBasic.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.gray),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5))
                );


                JPanel panelTop = new JPanel(new BorderLayout());
                panelBasic.add(panelTop, BorderLayout.NORTH);
                final JLabel labelTitle = new JLabel("<html><b>Переводчик</b>");
                MouseMoveWindowListener mouseMoveWindowListener = new MouseMoveWindowListener(frame);
                labelTitle.addMouseListener(mouseMoveWindowListener);
                labelTitle.addMouseMotionListener(mouseMoveWindowListener);
                labelTitle.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        labelTitle.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        labelTitle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                });
                panelTop.add(labelTitle, BorderLayout.CENTER);
                final JToggleButton toggleButton = new JToggleButton();
                panelTop.add(toggleButton, BorderLayout.EAST);
                panelTop.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

                final JTextField textFieldFrom = new JTextFieldWithText("Введите слово или фразу и нажмите \"ENTER\"");

                final JTextField textFieldTo = new JTextField();
                textFieldTo.setEditable(false);
                textFieldTo.setBackground(Color.decode("#D8D8D8"));


                final Icon compoundIconEnRu = new CompoundIcon(
                        new ImageIcon(Gui.class.getResource("/flag-english_16x16.png")),
                        new ImageIcon(Gui.class.getResource("/arrow_16x16.png")),
                        new ImageIcon(Gui.class.getResource("/flag-russia_16x16.png"))
                );

                final Icon compoundIconRuEn = new CompoundIcon(
                        new ImageIcon(Gui.class.getResource("/flag-russia_16x16.png")),
                        new ImageIcon(Gui.class.getResource("/arrow_16x16.png")),
                        new ImageIcon(Gui.class.getResource("/flag-english_16x16.png"))
                );

                toggleButton.setToolTipText("EN -> RU");
                toggleButton.setIcon(compoundIconEnRu);
                toggleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (toggleButton.isSelected()) {
                            toggleButton.setToolTipText("RU -> EN");
                            toggleButton.setIcon(compoundIconRuEn);
                        } else {
                            toggleButton.setIcon(compoundIconEnRu);
                            toggleButton.setToolTipText("EN -> RU");
                        }
                    }
                });

                textFieldFrom.addKeyListener(new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            try {
                                TranslateLang translateLang;
                                if (toggleButton.isSelected()) {
                                    translateLang = TranslateLang.RU_EN;
                                } else {
                                    translateLang = TranslateLang.EN_RU;
                                }
                                String text = textFieldFrom.getText();
                                if (text != null && !"".equals(text)) {
                                    textFieldTo.setText(translate.translate(translateLang, text.trim()));
                                }
                            } catch (Exception ex) {
                                textFieldTo.setText("Error");
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                GridLayout gridLayout = new GridLayout(2, 1);
                gridLayout.setVgap(2);
                final JPanel panelCenter = new JPanel(gridLayout);
                panelBasic.add(panelCenter, BorderLayout.CENTER);

                panelCenter.add(textFieldFrom);
                panelCenter.add(textFieldTo);

                final String commonLink = "<html>Переведено сервисом «Яндекс.Переводчик»";
                final String selectLink = "<html><u><font color=\"blue\">Переведено сервисом «Яндекс.Переводчик»</font></u>";
                final String link = "http://translate.yandex.ru/";
                final JLabel labelLink = new JLabel(commonLink);
                labelLink.setHorizontalAlignment(SwingConstants.CENTER);
                labelLink.setToolTipText("Нажмите для перехода на: " + link);
                labelLink.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        labelLink.setText(selectLink);
                        labelLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        labelLink.setText(commonLink);
                        labelLink.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().browse(new URI(link));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(
                                    frame,
                                    "Ошибка перехода по ссылке",
                                    "Ошибка перехода по ссылке: " + link,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                JPanel panelBottom = new JPanel(new BorderLayout());
                panelBottom.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
                panelBottom.add(labelLink, BorderLayout.CENTER);
                final Icon iconCloseLittle = new ImageIcon(getClass().getResource("/close_10x10.png"));
                final Icon iconCloseBig = new ImageIcon(getClass().getResource("/close_12x12.png"));

                final JLabel labelClose = new JLabel();
                labelClose.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        frame.setVisible(true);
                        frame.dispose();
                        System.exit(0);
                    }
                });
                labelClose.setToolTipText("Закрыть приложение");
                labelClose.setIcon(iconCloseLittle);
                labelClose.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        labelClose.setIcon(iconCloseBig);
                        labelClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        labelClose.setBorder(BorderFactory.createRaisedBevelBorder());
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        labelClose.setIcon(iconCloseLittle);
                        labelClose.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        labelClose.setBorder(null);
                    }
                });
                panelBottom.add(labelClose, BorderLayout.EAST);
                panelBasic.add(panelBottom, BorderLayout.SOUTH);
                frame.add(panelBasic);
                frame.setAlwaysOnTop(true);
                frame.pack();
                frame.setSize(frame.getWidth() + 100, frame.getHeight());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
