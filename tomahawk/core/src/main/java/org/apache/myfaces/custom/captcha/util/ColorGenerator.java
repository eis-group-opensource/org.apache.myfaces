/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.captcha.util;

import java.awt.Color;

/**
 * This class is responsible for generating CAPTCHA random color...
 * 
 * @since 1.1.7
 */
public class ColorGenerator
{

    private final static int COLOR_DEGREES = 255;
    private final static int COLOR_GENERATOR_DELTA = 127;

    /**
     * This method is used for generating a random color.
     * @param startFrom -> the color that we should be away from.
     * @return the new color.
     */
    public static Color generateRandomColor(Color startFrom)
    {

        /* if the startingFrom color is null, then generate a new random color. */
        if (startFrom == null)
        {
            return new Color((int) (Math.random() * COLOR_DEGREES), (int) (Math
                    .random() * COLOR_DEGREES),
                    (int) (Math.random() * COLOR_DEGREES));
        }

        /* try to avoid the startFrom color. */
        int startingRed = (startFrom.getRed() >= 128) ? 0 : 128;
        int startingGreen = (startFrom.getGreen() >= 128) ? 0 : 128;
        int startingBlue = (startFrom.getBlue() >= 128) ? 0 : 128;

        // generate the new random colors.  
        int newRandomRed = (int) (Math.random() * (startingRed + COLOR_GENERATOR_DELTA));
        int newRandomGreen = (int) (Math.random() * (startingGreen + COLOR_GENERATOR_DELTA));
        int newRandomBlue = (int) (Math.random() * (startingBlue + COLOR_GENERATOR_DELTA));

        /* 
         * If the newly generated color is less than the starting color 
         * then add the starting color to it. 
         */
        if (newRandomRed < startingRed)
        {
            newRandomRed += startingRed;
        }

        if (newRandomGreen < startingGreen)
        {
            newRandomGreen += startingGreen;
        }

        if (newRandomBlue < startingBlue)
        {
            newRandomBlue += startingBlue;
        }

        return new Color(newRandomRed, newRandomGreen, newRandomBlue);
    }
}
