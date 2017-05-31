package com.rgnik.mymap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//    private boolean mPermissionDenied = false;

    EditText editText;
    Button submitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        editText = (EditText)findViewById(R.id.edittxt);
        submitbutton = (Button)findViewById(R.id.submitbtn);
    }

    public void onSearch(View view)
    {
        List<Address> addressList = null;
        String location = editText.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        Log.i("TAG"," : "+geocoder);

        try
        {
           addressList  = geocoder.getFromLocationName(location,1);
            Log.i("TAG"," : "+addressList);        }
        catch (Exception e)
        {
            Log.i("TAG"," : "+e);
        }

        Address address = addressList.get(0);
        Log.i("TAG"," : "+address);
        Log.i("TAG"," : Latitude : "+address.getLatitude());
        Log.i("TAG"," : Longitude : "+address.getLongitude());
        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
        Log.i("TAG"," : "+latLng);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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
        LatLng sydney = new LatLng(28.594757, 77.33624);
        mMap.addMarker(new MarkerOptions().position(sydney).title("My Home Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        enableMyLocation();
    }


    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else if(mMap != null)
            mMap.setMyLocationEnabled(true);
    }


}
