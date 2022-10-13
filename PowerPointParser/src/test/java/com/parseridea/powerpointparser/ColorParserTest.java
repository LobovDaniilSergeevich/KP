package com.parseridea.powerpointparser;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(JfxRunner.class)
class ColorParserTest {

    @Test
    void colorParseToAWT() {
        new JFXPanel();
        Assert.assertEquals(ColorParser.colorParseToAWT(Color.BLACK), java.awt.Color.BLACK);
    }
}