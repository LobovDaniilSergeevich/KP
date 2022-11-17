package com.parseridea.powerpointparser.Iterator;

import javafx.stage.FileChooser;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageSlides implements Aggregate{

    private final ArrayList<BufferedImage> Slides;
    XMLSlideShow presentation;
    public ImageSlides(XMLSlideShow presentation) {
        this.presentation=presentation;
        Slides=new ArrayList<>();
        setImages();
    }

    public ArrayList<BufferedImage> getSlides() {
        return Slides;
    }

    @Override
    public Iterator getIterator() {
        return new ImageSlideIterator(Slides);
    }
    private void setImages()
    {
        Dimension pgsize = presentation.getPageSize();

        List<XSLFSlide> slide = presentation.getSlides();
        for (XSLFSlide xslfShapes : slide) {

            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, 1);

            Graphics2D graphics = img.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON);

            graphics.setColor(Color.white);
            graphics.clearRect(0, 0, pgsize.width, pgsize.height);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            // render
            xslfShapes.draw(graphics);
            Slides.add(img);
        }
    }
    public void convertToImages() throws IOException {
        Dimension pgsize = presentation.getPageSize();

        List<XSLFSlide> slide = presentation.getSlides();
        for (int i = 0; i < slide.size(); i++) {

            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, 1);

            Graphics2D graphics = img.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON);

            graphics.setColor(Color.white);
            graphics.clearRect(0, 0, pgsize.width, pgsize.height);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            // render
            slide.get(i).draw(graphics);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выбор папки для сохранения");
            fileChooser.setInitialFileName("слайд-" + (i + 1) + ".png");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Слайд", "*.png"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileOutputStream out = new FileOutputStream(file);
                javax.imageio.ImageIO.write(img, "png", out);
                out.close();
            }
        }
    }
}
