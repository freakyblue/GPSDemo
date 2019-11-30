package de.freakyblue.gpsdemo;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
    implements OnMapReadyCallback, LocationListener {

    private MapView map;
    private TextView text;
    private GoogleMap gmap;
    private LocationManager locationManager;
    private Location prevLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = findViewById(R.id.map);
        text = findViewById(R.id.text);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Bundle bundle = null;
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle("MAP_BUNDLE_KEY");
        }
        map.onCreate(bundle);
        map.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        if (gmap != null)
            gmap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0,0)));

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        locationManager.requestLocationUpdates(
            locationManager.getBestProvider(criteria, true),
            1000,
            10,
            this
        );
    }

    @Override
    public void onPause() {
        map.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        text.setText("test");
        LatLng currPos = new LatLng(location.getLatitude(), location.getLongitude());
        gmap.moveCamera(CameraUpdateFactory.newLatLng(currPos));
        gmap.addMarker(new MarkerOptions().position(currPos).title("marker"));
        if (prevLocation != null) {
            text.setText(String.valueOf(location.distanceTo(prevLocation)));
        }
        prevLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        gmap.setMapType(MAP_TYPE_SATELLITE);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0,0)));
    }
}
