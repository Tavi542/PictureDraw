/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturedraw;

import javafx.scene.canvas.Canvas;

/**
 *
 * @author s1500542
 */
public class ResizableCanvas extends Canvas {

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double maxHeight(double width) {
        return Double.MAX_VALUE;
    }

    @Override
    public double maxWidth(double height) {
        return Double.MAX_VALUE;
    }

    @Override
    public double prefHeight(double width) {
        return 480;
    }

    @Override
    public double prefWidth(double height) {
        return 640;
    }

    @Override
    public double minHeight(double width) {
        return 0;
    }

    @Override
    public double minWidth(double height) {
        return 0;
    }

    @Override
    public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
    }
}
