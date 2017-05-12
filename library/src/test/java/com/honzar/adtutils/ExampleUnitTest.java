package com.honzar.adtutils;

import com.honzar.adtutils.library.ColorUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void color_utils_test_1() throws Exception {
        assertEquals("#000000", ColorUtils.getHtmlColor(0x000000));

        assertTrue(
                ("#000").equals(ColorUtils.getHtmlColor(0x000000)) ||
                ("#000000").equals(ColorUtils.getHtmlColor(0x000000))
        );

        assertTrue(
                ("#f00").equals(ColorUtils.getHtmlColor(0xff0000)) ||
                ("#ff0000").equals(ColorUtils.getHtmlColor(0xff0000))
        );

        assertTrue(
                ("#0f0").equals(ColorUtils.getHtmlColor(0x00ff00)) ||
                ("#00ff00").equals(ColorUtils.getHtmlColor(0x00ff00))
        );

        assertTrue(
                ("#00f").equals(ColorUtils.getHtmlColor(0x0000ff)) ||
                ("#0000ff").equals(ColorUtils.getHtmlColor(0x0000ff))
        );

        assertTrue(
                ("#fff").equals(ColorUtils.getHtmlColor(0xffffff)) ||
                ("#ffffff").equals(ColorUtils.getHtmlColor(0xffffff))
        );

        assertEquals(null, ColorUtils.getHtmlColor(-10));
        assertEquals(null, ColorUtils.getHtmlColor(0x1000000));
    }

    @Test
    public void color_utils_test_2() throws Exception {
        assertEquals(null, ColorUtils.getTintedDrawableIcon(null, 1, 1));
    }

    @Test
    public void color_utils_test_3() throws Exception {
        assertNotEquals(0x000000, ColorUtils.makeColorTransparent(0x000000, 0.5f));
        assertEquals(2130706432, ColorUtils.makeColorTransparent(0x000000, 0.5f));
    }
}
