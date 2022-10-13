package com.parseridea.powerpointparser.PresentationElements;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * text element of presentation
 */
public class Text extends PresentationNode {
    String text;
    Color color;
    double fontSize;
    protected TextField textField;
    protected Label label;
    public Text(TextBuilder textBuilder)
    {
        super(textBuilder.x, textBuilder.y);
        this.text = textBuilder.text;
        this.color=textBuilder.color;
        this.fontSize=textBuilder.fontSize;
        textField = new TextField(this.text);
        label = new Label(this.text);
        settingText();
    }
    private void settingText()
    {
        textField.setVisible(false);
        label.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                text = label.getText();
                textField.setText(text);
                label.setVisible(false);
                textField.setVisible(true);
                textField.requestFocus();
            }
        });
        textField.setOnKeyTyped(e -> text=textField.getText());
        textField.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue) {
                label.setVisible(true);
                textField.setVisible(false);
                label.setText(text);
                draw();
            }
        });
        textField.setOnKeyReleased(e -> {
            text=textField.getText();
            if(e.getCode() == KeyCode.ENTER)
            {
                label.setVisible(true);
                textField.setVisible(false);
                label.setText(text);
                label.toFront();
                draw();
            }
        });
        label.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        textField.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        getChildren().add(label);
        getChildren().add(textField);
        width=label.prefWidth(-1);
        height=label.prefHeight(-1);
        fontSize=label.fontProperty().get().getSize();
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public double getFontSize() {
        return fontSize;
    }

    @Override
    public void draw()
    {
        label.applyCss();
        label.layout();
        textField.layout();
        textField.applyCss();
        getChildren().clear();
        getChildren().addAll(label,textField);
        x = getTranslateX();
        y = getTranslateY();
        width = label.prefWidth(-1);
        height = label.prefHeight(-1);
        label.setTranslateX(0);
        label.setTranslateY(0);
        textField.setTranslateX(0);
        textField.setTranslateY(0);
    }
    public static class TextBuilder {
        double x, y;
        String text;
        Color color=Color.BLACK;
        double fontSize=15.;

        public TextBuilder(String text, double x, double y) {
            this.text = text;
            this.y = y;
            this.x = x;
        }

        public TextBuilder setColor(Color color) {
            this.color = color;
            return this;
        }

        public TextBuilder setFontSize(double fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Text build() {
            return new Text(this);
        }
    }
}
