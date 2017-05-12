package com.honzar.adtutils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.honzar.adtutils.library.ColorUtils;
import com.honzar.adtutils.library.DeviceUtils;
import com.honzar.adtutils.library.EqualsUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void useAppContext() throws Exception {
        assertEquals("com.honzar.adtutils.library.test", context.getPackageName());
    }

    @Test
    public void color_utils_test() throws Exception {
        assertEquals(null, ColorUtils.getTintedDrawableIcon(null, 1, 1));
        assertEquals(null, ColorUtils.getTintedDrawableIcon(context, -1, 1));
        assertEquals(null, ColorUtils.getTintedDrawableIcon(context, 1, -1));
        assertNotEquals(null, ColorUtils.getTintedDrawableIcon(context, android.R.drawable.ic_delete, android.R.color.black));
    }

    @Test
    public void device_utils_test() throws Exception {
        assertEquals(false, DeviceUtils.checkWifiEnabled(null));
        assertEquals(true, DeviceUtils.checkWifiEnabled(context));
        assertEquals(true, DeviceUtils.checkInternetConnection(context));
        assertEquals(false, DeviceUtils.checkInternetConnection(null));
        assertEquals(false, DeviceUtils.checkMobileDataConnectionEnabled(null));
        assertEquals(false, DeviceUtils.checkMobileDataConnectionEnabled(context));
        assertEquals(false, DeviceUtils.checkLocationEnabled(context));
        assertEquals(false, DeviceUtils.checkLocationEnabled(null));
        assertEquals(false, DeviceUtils.checkDeviceHasBackCamera(null));
        assertEquals(true, DeviceUtils.checkDeviceHasBackCamera(context));
        assertEquals(false, DeviceUtils.checkDeviceHasAnyCamera(null));
        assertEquals(true, DeviceUtils.checkDeviceHasAnyCamera(context));
        assertEquals(false, DeviceUtils.checkNfcSupported(null));
        assertEquals(false, DeviceUtils.checkNfcSupported(context));
        assertEquals(false, DeviceUtils.checkNfcEnabled(null));
        assertEquals(false, DeviceUtils.checkNfcEnabled(context));
        assertEquals(false, DeviceUtils.checkCanDisplayPdf(context));
        assertEquals(false, DeviceUtils.checkCanDisplayPdf(null));
        assertEquals(true, DeviceUtils.checkCanDisplayImage(context));
        assertEquals(false, DeviceUtils.checkCanDisplayImage(null));

        assertEquals(false, DeviceUtils.isTablet(context));
        assertEquals(false, DeviceUtils.isTablet(null));
        assertEquals(true, DeviceUtils.isSmartphone(context));
        assertEquals(false, DeviceUtils.isSmartphone(null));
    }

    @Test
    public void equals_utils_test() throws Exception {
        assertNotEquals(0, EqualsUtils.hashCodeCheckNull(context));
        assertEquals(0, EqualsUtils.hashCodeCheckNull(null));
    }

}
