package org.pattonvillerobotics;

import android.support.test.runner.AndroidJUnit4;

import com.vuforia.Vuforia;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by Mitchell on 10/31/2017.
 */

@RunWith(AndroidJUnit4.class)
public class VuforiaJNITest {
    @Test
    public void testInitJNI() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Vuforia.setInitParameters(null, 0, null);

        Vuforia.init();

        Method wasInitializedJava = Vuforia.class.getDeclaredMethod("wasInitializedJava");
        wasInitializedJava.setAccessible(true);

        assertThat(wasInitializedJava.invoke(null), is(true));
    }
}
