package de.freakyblue.gpsdemo;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainTest {

    private Context context;
    private LocationManager locationManager;

    @Rule
    public ActivityTestRule<MainActivity> testRule =
        new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() {
        context = InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext();

        locationManager = (LocationManager) context
            .getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(
            LocationManager.GPS_PROVIDER,
            false,
            false,
            false,
            false,
            true,
            true,
            true,
            Criteria.NO_REQUIREMENT,
            0
        );
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

        updateLocation(0, 0, 100);
    }

    private void updateLocation(double lat, double lng, double alt) {
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAltitude(alt);
        location.setAccuracy(0);
        //location.setVerticalAccuracyMeters(1);
        //location.setSpeedAccuracyMetersPerSecond(1);
        //location.setBearingAccuracyDegrees(2);
        location.setTime(System.currentTimeMillis());
        location.setElapsedRealtimeNanos(System.nanoTime() + 200);
        locationManager.setTestProviderLocation(
            LocationManager.GPS_PROVIDER,
            location
        );
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
    }

    @Test
    public void testTracking() throws InterruptedException {
        Thread.sleep(4000);
        updateLocation(0,50,200);
        Thread.sleep(2000);
    }

}
