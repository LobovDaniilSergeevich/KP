package com.parseridea.powerpointparser.Iterator;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class SlideTest {

    @Test
    void clear() {
        new JFXPanel();
        Slide slide=new Slide();
        slide.addImage("ds");
        Assert.assertEquals(1,slide.images.size());
        slide.clear();
        Assert.assertEquals(0,slide.images.size());
    }

    @Test
    void copy() {
        new JFXPanel();
        Slide slide=new Slide();
        Slide slide1=new Slide();
        slide1.addImage("2");
        slide1.addImage("1");
        slide.addImage("ds");
        slide.addLink("sd");
        slide.clone(slide1);
        Assert.assertEquals(slide.images.size(),2);
        Assert.assertEquals(slide.hyperlinks.size(),0);
    }
}