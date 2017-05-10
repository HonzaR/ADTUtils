package com.honzar.adtutils;

import com.honzar.adtutils.library.ColorUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void color_utils_test_1() throws Exception {
        assertEquals("#000000", ColorUtils.getHtmlColor(0x000000));
        assertEquals("#ff0000", ColorUtils.getHtmlColor(0xff0000));
        assertEquals("#00ff00", ColorUtils.getHtmlColor(0x00ff00));
        assertEquals("#0000ff", ColorUtils.getHtmlColor(0x0000ff));
        assertEquals("#ffffff", ColorUtils.getHtmlColor(0xffffff));
        assertEquals(null, ColorUtils.getHtmlColor(-10));
        assertEquals(null, ColorUtils.getHtmlColor(0x1000000));
    }

    @Test
    public void color_utils_test_2() throws Exception {
        assertEquals(null, ColorUtils.getTintedDrawableIcon(null, 1, 1));
    }
}
