package com.parseridea.powerpointparser.Iterator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageSlideIterator implements Iterator{
    private int current=0;
    BufferedImage bi;
    public ArrayList<BufferedImage> Slides;
    public ImageSlideIterator(ArrayList<BufferedImage> slides)
    {
        Slides=slides;
    }
    @Override
    public boolean hasNext(int mode) {
        if (current < Slides.size()) {
            BufferedImage selected = Slides.get(current);
            try {
                bi = selected;
                return true;

            } catch (Exception ex) {
                System.err.println("Не удалось загрузить картинку! ");
                ex.printStackTrace();
                return false;
            }
        }
        else if(mode == 0) preview();
        return true;
    }
    @Override
    public boolean hasPrev(int mode) {
        if (current > -1) {
            BufferedImage selected = Slides.get(current);
            try {
                bi = selected;
                return true;

            } catch (Exception ex) {
                System.err.println("Не удалось загрузить картинку! ");
                ex.printStackTrace();
                return false;
            }
        }
        else if(mode == 0) preview();
        return true;
    }

    @Override
    public Object next() {
        current++;
        if(this.hasNext(0)){
            return bi;
        }
        return null;
    }
    @Override
    public Object prev() {
        current--;
        if(this.hasPrev(0)){
            return bi;
        }
        return null;
    }

    @Override
    public Object replace(int num)
    {
       return null;
    }
    @Override
    public void preview() {
        current=0;
        this.hasNext(0);
    }
    @Override
    public int getCurrent()
    {
        return current;
    }

}
