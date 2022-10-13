package com.parseridea.powerpointparser.PresentationElements;

import javafx.scene.Group;

/**
 * abstract class for elements of presentation
 */
public abstract class PresentationNode extends Group {

    private double mouseAnchorX;
    private double mouseAnchorY;
    double x;
    double y;
    double width;
    double height;
    public PresentationNode(double x, double y)
    {
        this.x = x;
        this.y = y;
        width=prefWidth(-1);
        height=prefHeight(-1);
        setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * @param x coordinate
     * @param y coordinate
     */
    public void updatePosition(double x, double y)
    {
        setTranslateX(Math.max(getTranslateX() + x - mouseAnchorX, 0));
        setTranslateY(Math.max(getTranslateY() + y -mouseAnchorY, 0));
        this.x= getTranslateX();
        this.y= getTranslateY();
    }

    /**
     * draw on pane
     */
    public abstract void draw();
}
