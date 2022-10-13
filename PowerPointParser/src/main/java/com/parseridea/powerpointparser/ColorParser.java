package com.parseridea.powerpointparser;

import javafx.scene.paint.Color;

/**
 * class for parse color from jfx to awt
 */
public class ColorParser {
    /**
     * @param jfxColor javafxcolor
     * @return awtcolor
     */
    public static java.awt.Color colorParseToAWT(Color jfxColor)
    {
        return new java.awt.Color((float) jfxColor.getRed(),
                (float) jfxColor.getGreen(),
                (float) jfxColor.getBlue(),
                (float) jfxColor.getOpacity());
    }
}
