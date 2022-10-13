package com.parseridea.powerpointparser.PresentationElements;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * element of image
 */
public class Image extends PresentationNode {
    String imageSource;
    public Image(ImageBuilder imageBuilder)
    {
        super(imageBuilder.x,imageBuilder.y);
        this.imageSource=imageBuilder.imageSource;
        this.width=imageBuilder.width;
        this.height=imageBuilder.height;
    }
    @Override
    public void draw()
    {
        getChildren().clear();
        Rectangle image=new Rectangle(0,0,width,height);
        image.setFill(new ImagePattern(new javafx.scene.image.Image(imageSource)));
        image.setOnScroll(e -> {
            double zoomFactor = 1.05;
            double deltaY = e.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            image.setScaleX(image.getScaleX() * zoomFactor);
            image.setScaleY(image.getScaleY() * zoomFactor);
        });
        getChildren().add(image);
        x = getTranslateX();
        y = getTranslateY();
        width = image.getWidth();
        height = image.getHeight();
    }

    public String getImageSource() {
        return imageSource;
    }

    public static class ImageBuilder {
        double x, y, width,height;
        String imageSource;

        public ImageBuilder(String imageSource, double x, double y) {
            this.imageSource = imageSource;
            this.y = y;
            this.x = x;
        }

        public ImageBuilder setWidth(double width) {
            this.width = width;
            return this;
        }

        public ImageBuilder setHeight(double height) {
            this.height = height;
            return this;
        }

        public Image build() {
            return new Image(this);
        }
    }
}
