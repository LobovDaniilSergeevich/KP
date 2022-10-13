package com.parseridea.powerpointparser.Iterator;

import java.util.ArrayList;

/**
 * list of slides
 */
public class Slides implements Aggregate {
    private final ArrayList<Slide> Slides;
    public Slides(ArrayList<Slide> slides) {
        Slides = slides;
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