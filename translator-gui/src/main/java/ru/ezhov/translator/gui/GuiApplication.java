package ru.ezhov.translator.gui;

import ru.ezhov.translator.gui.translate.Engine;
import ru.ezhov.translator.gui.translate.RemoteTranslate;
import ru.ezhov.translator.gui.translate.TranslateLang;
import ru.ezhov.translator.gui.util.CompoundIcon;
import ru.ezhov.translator.gui.util.MouseMoveWindowListener;
import ru.ezhov.translator.gui.util.version.VersionInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class GuiApplication {

    private RemoteTranslate translate;

    public GuiApplication(RemoteTranslate remoteTranslate) {
        this.translate = remoteTranslate;
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
                final JDialog dialogBasic = createBasicDialog();


                final JDialog dialog = new JDialog();
                final JButton button = new JButton(new ImageIcon(getClass().getResource("/accessories-dictionary_32x32.png")));
                Dimension dimensionButton = new Dimension(45, 45);
                button.setMinimumSize(dimensionButton);
                button.setPreferredSize(dimensionButton);
                button.setMaximumSize(dimensionButton);
                button.setSize(dimensionButton);
                MouseMoveWindowListener mouseMoveWindowListener = new MouseMoveWindowListener(dialog);
                button.addMouseMotionListener(mouseMoveWindowListener);
                button.addMouseListener(mouseMoveWindowListener);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setBorder(null);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showBasicDialog(dialogBasic, dialog);
                    }
                });
                button.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            showBasicDialog(dialogBasic, dialog);
                        }
                    }
                });
                dialog.add(button);
                dialog.setAlwaysOnTop(true);
                dialog.setUndecorated(true);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }

    private void showBasicDialog(JDialog dialogBasic, JDialog dialog) {
        boolean visible = dialogBasic.isVisible();
        if (visible) {
            dialogBasic.setVisible(false);
        } else {
            dialogBasic.setVisible(true);
            int wD = dialogBasic.getWidth();

            int w = dialog.getWidth();
            Point p = dialog.getLocationOnScreen();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = p.getX() + w + wD;
            if (screenSize.getWidth() > width) {
                dialogBasic.setLocation(new Point(p.x + w, p.y));
            } else {
                dialogBasic.setLocation(new Point(p.x - wD, p.y));
            }
        }
    }


    private JDialog createBasicDialog() {
        final JDialog frame = new JDialog();
        createEscActionOnWindows(frame.getRootPane(), frame);
        frame.setUndecorated(true);
        JPanel panelBasic = new JPanel(new BorderLayout());
        panelBasic.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.gray),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );

        JPanel panelTop = new JPanel(new BorderLayout());
        panelBasic.add(panelTop, BorderLayout.NORTH);
        final JLabel labelTitle = new JLabel("<html><b>Переводчик</b> <font size=\"3\"><sup>v." + VersionInfo.version() + "<sup></font>");
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
        panelTop.add(labelTitle, BorderLayout.WEST);

        JPanel panelEast = new JPanel();
        final JCheckBox checkBoxAutoChange = new JCheckBox();
        checkBoxAutoChange.setSelected(true);
        checkBoxAutoChange.setToolTipText("<html>Выберите для автоматического переключения языка в момент ввода");
        final JToggleButton toggleButton = new JToggleButton();
        panelEast.add(checkBoxAutoChange);
        panelEast.add(toggleButton);
        panelTop.add(panelEast, BorderLayout.EAST);
        panelTop.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        final JRadioButton radioButtonYandex = new JRadioButton("Яндекс");
        JRadioButton radioButtonMultitran = new JRadioButton("Мультитран");
        radioButtonYandex.setSelected(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonYandex);
        buttonGroup.add(radioButtonMultitran);
        JPanel panelRadioButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRadioButton.add(radioButtonYandex);
//        panelRadioButton.add(radioButtonMultitran);
        panelTop.add(panelRadioButton, BorderLayout.CENTER);

        final TargetPanel targetPanel = new TargetPanel();

        final Icon compoundIconEnRu = new CompoundIcon(
                new ImageIcon(GuiApplication.class.getResource("/flag-english_16x16.png")),
                new ImageIcon(GuiApplication.class.getResource("/arrow_16x16.png")),
                new ImageIcon(GuiApplication.class.getResource("/flag-russia_16x16.png"))
        );

        final Icon compoundIconRuEn = new CompoundIcon(
                new ImageIcon(GuiApplication.class.getResource("/flag-russia_16x16.png")),
                new ImageIcon(GuiApplication.class.getResource("/arrow_16x16.png")),
                new ImageIcon(GuiApplication.class.getResource("/flag-english_16x16.png"))
        );

        final ActionListener toggleButtonActionListener = new ActionListener() {

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
        };

        toggleButton.setToolTipText("EN -> RU");
        toggleButton.setIcon(compoundIconEnRu);
        toggleButton.addActionListener(toggleButtonActionListener);

        final JLabel labelLink = new JLabel();

        final SourcePanel sourcePanel = new SourcePanel();
        sourcePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionTranslate(
                            toggleButton,
                            sourcePanel,
                            targetPanel,
                            radioButtonYandex,
                            labelLink
                    );
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    sourcePanel.clearText();
                } else if (e.getKeyCode() == KeyEvent.VK_W && e.isControlDown()) {
                    toggleButton.setSelected(!toggleButton.isSelected());
                    toggleButtonActionListener.actionPerformed(null);
                } else {
                    if (checkBoxAutoChange.isSelected()) {
                        String text = sourcePanel.getText();
                        if (text != null && !"".equals(text)) {
                            boolean matches = text.matches(".*[А-Яа-я].*");
                            if (matches) {
                                toggleButton.setSelected(true);
                            } else {
                                toggleButton.setSelected(false);
                            }
                            toggleButtonActionListener.actionPerformed(null);
                        }
                    }
                }
            }
        });

        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(2);
        final JPanel panelCenter = new JPanel(gridLayout);
        panelBasic.add(panelCenter, BorderLayout.CENTER);

        panelCenter.add(sourcePanel);
        panelCenter.add(targetPanel);

        ActionListener actionListenerRadioButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionTranslate(
                        toggleButton,
                        sourcePanel,
                        targetPanel,
                        radioButtonYandex,
                        labelLink
                );
            }
        };

        radioButtonYandex.addActionListener(actionListenerRadioButton);
        radioButtonMultitran.addActionListener(actionListenerRadioButton);

        labelLink.setHorizontalAlignment(SwingConstants.CENTER);
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
        return frame;
    }

    private void actionTranslate(
            JToggleButton langButton,
            SourcePanel sourcePanel,
            final TargetPanel targetPanel,
            JRadioButton radioButtonYandex,
            final JLabel labelInfo
    ) {
        try {
            TranslateLang translateLang;
            if (langButton.isSelected()) {
                translateLang = TranslateLang.RU_EN;
            } else {
                translateLang = TranslateLang.EN_RU;
            }
            String text = sourcePanel.getText();
            if (text != null && !"".equals(text)) {
                Engine engine;
                if (radioButtonYandex.isSelected()) {
                    engine = Engine.YANDEX;
                } else {
                    engine = Engine.MULTITRAN;
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        labelInfo.setText("Получение перевода...");
                    }
                });
                Thread thread = new Thread(new TranslateWorker(
                        new OutputResult() {
                            @Override
                            public void setText(final String text) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        targetPanel.setText(text);
                                    }
                                });
                            }
                        },
                        new OutputResult() {
                            @Override
                            public void setText(final String text) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        labelInfo.setText(text);
                                    }
                                });
                            }
                        },
                        translate,
                        engine,
                        translateLang,
                        text
                ));
                thread.setDaemon(true);
                thread.start();
            }
        } catch (Exception ex) {
            targetPanel.setText("Error");
            ex.printStackTrace();
        }
    }

    public synchronized static final void createEscActionOnWindows(JRootPane rootPane, final Window window) {
        InputMap inputMap = rootPane.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWindow");
        ActionMap actionMap = rootPane.getActionMap();
        actionMap.put("closeWindow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
            }
        });
    }
}
