package com.parseridea.powerpointparser.Iterator;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * list of slides
 */
public class Slides implements Aggregate {
    private final ArrayList<Slide> Slides;
    public Slides(ArrayList<Slide> slides) {
        Slides = slides;
    }
    public Slides(XMLSlideShow ppt)
    {
        Slides=new ArrayList<>();
        Dimension pgsize = ppt.getPageSize();

        List<XSLFSlide> slide = ppt.getSlides();
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
            Slides.add(new Slide(img));
        }

    }
    public void addSlide(Slide slide)
    {
        Slides.add(slide);
    }

    public ArrayList<Slide> getSlides() {
        return Slides;
    }

    @Override
    public Iterator getIterator() {
        return new SlidesIterator(Slides);
    }
}