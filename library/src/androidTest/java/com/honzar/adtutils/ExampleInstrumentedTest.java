package com.honzar.adtutils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.honzar.adtutils.library.DeviceUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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
    public void device_utils_test_1() throws Exception {
//        assertEquals(false, DeviceUtils.checkWifiEnabled(null));
//        assertEquals(false, DeviceUtils.checkWifiEnabled(context));
//        assertEquals(true, DeviceUtils.checkInternetConnection(context));
//        assertEquals(false, DeviceUtils.checkInternetConnection(null));
//        assertEquals(false, DeviceUtils.checkMobileDataConnectionEnabled(null));
//        assertEquals(true, DeviceUtils.checkMobileDataConnectionEnabled(context));
//        assertEquals(true, DeviceUtils.checkLocationEnabled(context));
//        assertEquals(false, DeviceUtils.checkLocationEnabled(null));
//        assertEquals(false, DeviceUtils.checkDeviceHasBackCamera(null));
//        assertEquals(true, DeviceUtils.checkDeviceHasBackCamera(context));
//        assertEquals(false, DeviceUtils.checkDeviceHasAnyCamera(null));
//        assertEquals(true, DeviceUtils.checkDeviceHasAnyCamera(context));
//        assertEquals(false, DeviceUtils.checkNfcSupported(null));
//        assertEquals(false, DeviceUtils.checkNfcSupported(context));
//        assertEquals(false, DeviceUtils.checkNfcEnabled(null));
//        assertEquals(false, DeviceUtils.checkNfcEnabled(context));
        assertEquals(true, DeviceUtils.checkCanDisplayPdf(context));
        assertEquals(false, DeviceUtils.checkCanDisplayPdf(null));
    }

}
