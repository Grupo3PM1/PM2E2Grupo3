package com.aplicacion.pm2e1grupo3;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.aplicacion.pm2e1grupo3.databinding.ActivityPantalla3Binding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Pantalla3 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityPantalla3Binding binding;
    private String id, nombre, telefono, latitud, longitud;
    private Double lat, lgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        id  = getIntent().getExtras().getString("ID");
        nombre  = getIntent().getExtras().getString("Nombre");
        telefono  = getIntent().getExtras().getString("Telefono");
        latitud  = getIntent().getExtras().getString("Latitud");
        longitud  = getIntent().getExtras().getString("Longitud");

        lat = Double.parseDouble(latitud);
        lgt = Double.parseDouble(longitud);

        binding = ActivityPantalla3Binding.inflate(getLayoutInflater());
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lgt);
        mMap.addMarker(new MarkerOptions().position(sydney).title(nombre+"\n"+telefono));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}