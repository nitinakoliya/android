package com.example.nitinakoliya.mapsandintent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    positionTheMapOnLocation(latLng, "Your Location");
                }
            }
        }
    }

    public void positionTheMapOnLocation(LatLng latLng, String title) {
        // Add a marker in Sydney and move the camera
        mMap.clear();
        if (title != "Your Location") {
            mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        mMap.setOnMapLongClickListener(this);

        Intent intent = getIntent();

        if (intent.getIntExtra("index", 0) == 0) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    positionTheMapOnLocation(latLng, "Your Location");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if (Build.VERSION.SDK_INT < 23) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
            else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    positionTheMapOnLocation(latLng, "Your Location");
                }
            }
        }
        else {
            positionTheMapOnLocation(MainActivityMemorable.locationList.get(intent.getIntExtra("index", 0)), MainActivityMemorable.arrayList.get(intent.getIntExtra("index", 0)));
        }


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String addressText = "";
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address address = addressList.get(0);

            addressText += (address.getSubThoroughfare() != null) ? address.getSubThoroughfare() + " " : "";
            addressText += (address.getThoroughfare() != null) ? address.getThoroughfare() : "";

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressText == "") {
            addressText += latLng.toString();
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(addressText));

        saveLocation(addressText, latLng);

        Toast.makeText(getApplicationContext(), "Location Saved...", Toast.LENGTH_SHORT).show();
    }

    public void saveLocation(String addressText, LatLng latLng) {

        MainActivityMemorable.arrayList.add(addressText);
        MainActivityMemorable.locationList.add(latLng);
        MainActivityMemorable.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), MODE_PRIVATE);
        try {

            ArrayList<String> latitude = new ArrayList<>();
            ArrayList<String> longitude = new ArrayList<>();

            for (LatLng latLng1 : MainActivityMemorable.locationList) {
                latitude.add(Double.toString(latLng1.latitude));
                longitude.add(Double.toString(latLng1.longitude));
            }

            String placeNames = ObjectSerializer.serialize(MainActivityMemorable.arrayList);
            String latitudes = ObjectSerializer.serialize(latitude);
            String longitudes= ObjectSerializer.serialize(longitude);

            sharedPreferences.edit().putString("placeNames", placeNames).apply();
            sharedPreferences.edit().putString("latitudes", latitudes).apply();
            sharedPreferences.edit().putString("longitudes", longitudes).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
