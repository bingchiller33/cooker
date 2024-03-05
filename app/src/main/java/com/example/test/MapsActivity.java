package com.example.test;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.test.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getCurrentLocation();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-24, 123);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in ??"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        onMapLoaded();
    }

    @Override
    public void onMapLoaded() {
        LatLng latLng = new LatLng(21.51858158, 105.5125232);
        if (location == null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        } else {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        MarkerOptions options = new MarkerOptions().position(latLng).title("Your mom house!");
        mMap.addMarker(options);

        PolylineOptions polylineOptions = new PolylineOptions();
        LatLng latLng1 = new LatLng(21.51858158, 105.5125232);
        polylineOptions.add(latLng1);
        LatLng latLng2 = new LatLng(21.51858158, 105.5125232);
        polylineOptions.add(latLng2);
        mMap.addPolyline(polylineOptions);
    }

    private Location location = null;


    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location = location;
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }


}