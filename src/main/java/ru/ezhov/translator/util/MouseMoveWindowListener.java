package ru.ezhov.translator.util;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMoveWindowListener extends MouseAdapter {

    private Point diffOnScreen;
    private Component component;

    public MouseMoveWindowListener(Component component) {
        this.component = component;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point pressedPointLocationOnScreen = e.getLocationOnScreen();
        int x = pressedPointLocationOnScreen.x - component.getLocationOnScreen().x;
        int y = pressedPointLocationOnScreen.y - component.getLocationOnScreen().y;
        diffOnScreen = new Point(x, y);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point nowMouseLocation = e.getLocationOnScreen();
        component.setLocation(
                nowMouseLocation.x - diffOnScreen.x,
                nowMouseLocation.y - diffOnScreen.y);
    }
}