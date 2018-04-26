package com.example.chad.sharound_kin;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class navAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {
    SupportMapFragment sMapFragment;

    private String itemString;
    private Marker[] m = new Marker [10];
    private GoogleMap mMap;
    LocationManager locationManager;

    double latitudeMin = 22.3;
    double latitudeMax = 22.5;
    double longitudeMin = 114.1;
    double longitudeMax = 114.3;

    Random r = new Random();
    double [] rlat_array = new double[10];
    double [] rlong_array = new double[10];
    String [] item_array = {"物品A","物品B","物品C","物品D","物品E","物品F","物品G","物品H","物品I","物品J"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton refresh = (FloatingActionButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment sMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        sMapFragment.getMapAsync(this);

        //FragmentManager fm = getFragmentManager();
        //android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        //sFm.beginTransaction().hide(sMapFragment).commit();
        //sFm.beginTransaction().add(R.id.map,sMapFragment).commit();
        //sFm.beginTransaction().show(sMapFragment).commit();

        genLocation();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        //check the network provider is enabled
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location){
                    double myLatitude = location.getLatitude();
                    double myLongitude = location.getLongitude();
                    LatLng myLatLng = new LatLng(myLatitude,myLongitude);
                    mMap.addMarker(new MarkerOptions().position(myLatLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng,10.5f));
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras){}
                @Override
                public void onProviderEnabled(String provider){}
                @Override
                public void onProviderDisabled(String provider){}
            });
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double myLatitude = location.getLatitude();
                    double myLongitude = location.getLongitude();
                    LatLng myLatLng = new LatLng(myLatitude,myLongitude);
                    mMap.addMarker(new MarkerOptions().position(myLatLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng,10.5f));
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}
                @Override
                public void onProviderEnabled(String provider) {}
                @Override
                public void onProviderDisabled(String provider) {}
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {   return true;    }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {}
        else if (id == R.id.nav_gallery) {}
        else if (id == R.id.nav_slideshow) {}
        else if (id == R.id.nav_manage) {}
        else if (id == R.id.nav_share) {}
        else if (id == R.id.nav_send) {}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(mMapListener);

/*        LatLng [] latLngs = new LatLng[10];
        for (int i = 0; i < 10; i++) {
            latLngs[i] = new LatLng(rlat_array[i] ,rlong_array[i]);
        }
        //Geocoder geocoder = new Geocoder(getApplicationContext());
        for(int i=0;i<10;i++) {
            String str = item_array[i];
            m[i] = mMap.addMarker(new MarkerOptions().position(latLngs[i]).title(str));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs[i], 10.5f));
        }*/
    }
    private void genLocation() {
        for (int i = 0; i < 10; i++) {
            rlat_array[i] = latitudeMin + (latitudeMax - latitudeMin) * r.nextDouble();
            rlong_array[i] = longitudeMin + (longitudeMax - longitudeMin) * r.nextDouble();
        }
    }
    private GoogleMap.OnMarkerClickListener mMapListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.showInfoWindow();
            itemString = marker.getTitle();
            return true;
        }
    };
}
