package com.parseridea.powerpointparser.Iterator;

import com.parseridea.powerpointparser.PresentationElements.Hyperlink;
import com.parseridea.powerpointparser.PresentationElements.Image;
import com.parseridea.powerpointparser.PresentationElements.PresentationNode;
import com.parseridea.powerpointparser.PresentationElements.Text;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * class for slide
 */
public class Slide {
    public Slide(BufferedImage img)
    {
        pptxBG=img;
    }
    public Slide(){}
    private Color background=Color.TRANSPARENT;
    BufferedImage pptxBG;
    ArrayList<Image> images = new ArrayList<>();
    ArrayList<Hyperlink> hyperlinks = new ArrayList<>();
    ArrayList<Text> texts = new ArrayList<>();

    public void setPptxBG(BufferedImage pptxBG) {
        this.pptxBG = pptxBG;
    }

    public BufferedImage getPptxBG() {
        return pptxBG;
    }

    public ArrayList<Hyperlink> getHyperlinks() {
        return hyperlinks;
    }
    public Color getBackground()
    {
        return background;
    }
    public void setBackground(Color img)
    {
        this.background = img;
    }
    public ArrayList<Image> getImages() {
        return images;
    }

    public ArrayList<Text> getTexts() {
        return texts;
    }
    public void clear()
    {
        texts.clear();
        hyperlinks.clear();
        images.clear();
    }

    /**
     * @param source copied slide
     */
    public void clone(Slide source)
    {
        images.clear();
        hyperlinks.clear();
        texts.clear();
        this.pptxBG=source.pptxBG;
        this.images.addAll(source.images);
        this.hyperlinks.addAll(source.hyperlinks);
        this.texts.addAll(source.texts);
    }
    public Image addImage(String imageSource)
    {
        Image image=new Image.ImageBuilder(imageSource,30,30).setWidth(100).setHeight(100).build();
        images.add(image);
        return image;
    }
    public Text addText(String text)
    {
        Text textBlock=new Text.TextBuilder(text,30,30).build();
        texts.add(textBlock);
        return textBlock;
    }
    public Hyperlink addLink(String address)
    {
        Hyperlink hyperlink=new Hyperlink.HyperlinkBuilder(address,30,30).build();
        hyperlinks.add(hyperlink);
        return hyperlink;
    }
    public void removeObject(PresentationNode node)
    {
        switch (node.getClass().getName()) {
            case "Hyperlink" -> hyperlinks.remove(node);
            case "Text" -> texts.remove(node);
            case "Image" -> images.remove(node);
        }

    }
}
