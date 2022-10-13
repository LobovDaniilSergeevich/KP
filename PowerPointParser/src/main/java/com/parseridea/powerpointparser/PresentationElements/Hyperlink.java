package com.parseridea.powerpointparser.PresentationElements;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * element of hyperlink
 */
public class Hyperlink extends PresentationNode {
    String address;
    javafx.scene.control.Hyperlink hyperlinkBlock;
    TextField textField;
    Label label;
    public Hyperlink(HyperlinkBuilder hyperlinkBuilder)
    {
        super(hyperlinkBuilder.x,hyperlinkBuilder.y);
        this.address=hyperlinkBuilder.address;
        textField =new TextField(this.address);
        hyperlinkBlock = new javafx.scene.control.Hyperlink(this.address);
        label=new Label(this.address);
        settingText();
    }
    private void settingText()
    {
        label.setVisible(false);
        textField.setVisible(false);
        hyperlinkBlock.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                address = hyperlinkBlock.getText();
                textField.setText(address);
                hyperlinkBlock.setVisible(false);
                label.setText(address);
                textField.setVisible(true);
                textField.requestFocus();
            }
            else if(e.isControlDown())
            {
                hyperlinkBlock.setVisible(false);
                label.setVisible(true);
            }
        });
        label.setOnMouseClicked(e->{
            if(e.isControlDown())
            {
                hyperlinkBlock.setVisible(true);
                label.setVisible(false);
            }
        });
        textField.setOnKeyTyped(e -> address=textField.getText());
        textField.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue) {
                hyperlinkBlock.setVisible(true);
                textField.setVisible(false);
                hyperlinkBlock.setText(address);
                label.setText(address);
                draw();
            }
        });
        textField.setOnKeyReleased(e -> {
            address=textField.getText();
            if(e.getCode() == KeyCode.ENTER)
            {
                hyperlinkBlock.setVisible(true);
                textField.setVisible(false);
                hyperlinkBlock.setText(address);
                label.setText(address);
                hyperlinkBlock.toFront();
                draw();
            }
        });
        hyperlinkBlock.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        textField.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        getChildren().add(hyperlinkBlock);
        getChildren().add(textField);
        getChildren().add(label);
        width=prefWidth(-1);
        height=prefHeight(-1);
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void draw()
    {
        hyperlinkBlock.applyCss();
        hyperlinkBlock.layout();
        textField.layout();
        textField.applyCss();
        getChildren().clear();
        getChildren().addAll(label,hyperlinkBlock,textField);
        x = getTranslateX();
        y = getTranslateY();
        width = hyperlinkBlock.prefWidth(-1);
        height = hyperlinkBlock.prefHeight(-1);
        hyperlinkBlock.setTranslateX(0);
        hyperlinkBlock.setTranslateY(0);
        textField.setTranslateX(0);
        textField.setTranslateY(0);
    }
    public static class HyperlinkBuilder {
        double x, y;
        String address;
        Color color=Color.BLACK;
        double fontSize=10;

        public HyperlinkBuilder(String address, double x, double y) {
            this.address = address;
            this.y = y;
            this.x = x;
        }

        public HyperlinkBuilder setColor(Color color) {
            this.color = color;
            return this;
        }

        public HyperlinkBuilder setFontSize(double fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Hyperlink build() {
            return new Hyperlink(this);
        }
    }
}
