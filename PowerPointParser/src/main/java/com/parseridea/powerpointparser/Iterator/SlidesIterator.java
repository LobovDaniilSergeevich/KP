package com.parseridea.powerpointparser.Iterator;

import java.util.ArrayList;


/**
 * iterator of slides
 */
public class SlidesIterator implements Iterator {
    private int current=0;
    Slide bi;
    public ArrayList<Slide> Slides;
    public SlidesIterator(ArrayList<Slide> slides)
    {
        Slides=slides;
    }
    @Override
    public boolean hasNext(int mode) {
        if (current < Slides.size()) {
            Slide selected = Slides.get(current);
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
            Slide selected = Slides.get(current);
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
            Slide temp = new Slide();
            temp.copy(bi);
            bi.copy(Slides.get(num));
            Slides.get(num).copy(temp);
            return bi;
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
