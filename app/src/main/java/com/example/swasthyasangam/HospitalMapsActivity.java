package com.example.swasthyasangam;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.swasthyasangam.databinding.ActivityHospitalMapsBinding;

public class HospitalMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityHospitalMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHospitalMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for hospitals
        LatLng nashik = new LatLng(20.000, 73.780); // Example coordinates for Nashik
        mMap.addMarker(new MarkerOptions().position(nashik).title("Hospital in Nashik"));

        LatLng pune = new LatLng(18.520, 73.857); // Example coordinates for Pune
        mMap.addMarker(new MarkerOptions().position(pune).title("Hospital in Pune"));

        LatLng mumbai = new LatLng(19.076, 72.877); // Example coordinates for Mumbai
        mMap.addMarker(new MarkerOptions().position(mumbai).title("Hospital in Mumbai"));

        LatLng kolhapur = new LatLng(16.704, 74.243); // Example coordinates for Kolhapur
        mMap.addMarker(new MarkerOptions().position(kolhapur).title("Hospital in Kolhapur"));

        LatLng thane = new LatLng(19.2183, 72.9781); // Example coordinates for Thane
        mMap.addMarker(new MarkerOptions().position(thane).title("Hospital in Thane"));

        LatLng nagpur = new LatLng(21.1458, 79.0882); // Example coordinates for Nagpur
        mMap.addMarker(new MarkerOptions().position(nagpur).title("Hospital in Nagpur"));

        LatLng bhiwandi = new LatLng(19.2993, 73.0588); // Example coordinates for Bhiwandi
        mMap.addMarker(new MarkerOptions().position(bhiwandi).title("Hospital in Bhiwandi"));

        LatLng amravati = new LatLng(20.9374, 77.7796); // Example coordinates for Amravati
        mMap.addMarker(new MarkerOptions().position(amravati).title("Hospital in Amravati"));

        LatLng jalgaon = new LatLng(21.0100, 75.5681); // Example coordinates for Jalgaon
        mMap.addMarker(new MarkerOptions().position(jalgaon).title("Hospital in Jalgaon"));

        // Move camera to a default location (e.g., Nashik)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nashik, 7)); // Zoom level can be adjusted as needed
    }
}