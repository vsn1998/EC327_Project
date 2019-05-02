package com.EC327Project;


        import android.location.Address;
        import android.location.Geocoder;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;

        import android.Manifest;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AlertDialog;
        import android.util.Log;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.model.LatLng;
        import com.karumi.dexter.Dexter;
        import com.karumi.dexter.PermissionToken;
        import com.karumi.dexter.listener.PermissionDeniedResponse;
        import com.karumi.dexter.listener.PermissionGrantedResponse;
        import com.karumi.dexter.listener.PermissionRequest;
        import com.karumi.dexter.listener.single.PermissionListener;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;
        import java.util.Locale;
        import java.util.Timer;
        import java.util.TimerTask;
        import java.util.Date;
        import android.util.ArrayMap;

        import static android.widget.Toast.makeText;
        import java.util.Calendar;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;

    private static final String TAG = "MainActivity";
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 30000; /* 30 minutes*/
    double lat,lng;

    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;

    ImageButton save;
    ArrayList<String> addArray = new ArrayList<>();
    ArrayList<String> namesArray = new ArrayList<>();
    ArrayList<String> locationsArray = new ArrayList<>();


    ArrayList<Double> timeSpentAtLocation = new ArrayList<>(Collections.nCopies(15, 0.0));

    EditText txt;
    ListView show;
    String st;


    long timestamp = System.currentTimeMillis(); //initialize timestamp as time when app is opened
    long timeDiff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        txt = (EditText)findViewById(R.id.textInput);
        show = (ListView)findViewById(R.id.listView);
        save = (ImageButton)findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gets user input to name locations and send names to other activity

                String getInput = txt.getText().toString();
                String address = getAddress(MapsActivity.this,lat,lng );
                String addText = getInput + ": " + address;
                if(addArray.contains(getInput)) {
                    makeText(getBaseContext(), "Name already added", Toast.LENGTH_LONG).show();
                }
                else if(addArray.contains(address)) {
                    makeText(getBaseContext(), "Location already added", Toast.LENGTH_LONG).show();
                }
                else if(getInput == null || getInput.trim().equals("")){
                    makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
                }
                else{
                    addArray.add(getInput);
                    addArray.add(address);
                    namesArray.add(getInput);
                    locationsArray.add(address);


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapsActivity.this,android.R.layout.simple_list_item_1, addArray);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.textInput)).setText("");
                }
            }
        });

        //Tracks time spent at location














        if (requestSinglePermission()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            //it was pre written
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            checkLocation(); //check whether location service is enable or not in your  phone
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near the users location with the address displayed
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String address = getAddress(this,lat,lng );
        if (latLng != null) {
            float zoomLevel = 14.0f; //This goes up to 21
            mMap.addMarker(new MarkerOptions().position(latLng).title(address));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
    }

    // Gets address from latitude and longitude
    public String getAddress(Context ctx, double lat, double lng){
        String fullAdd = null;
        try{
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(lat, lng,1);
            if(addresses.size()>0){
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);

            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return fullAdd;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
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

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    double firstLat = lat; //initial latitude (before change)
    double firstLng = lng; //initial longitude
    double timeDiffHour; //time spent at location in hours

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        lat = location.getLatitude(); //current latitude
        lng = location.getLongitude(); //current longitude
        latLng = new LatLng(lat, lng);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //it was pre written
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //keep track of time spent at location
        double totalTime;
        int length = locationsArray.size();


        if(!isUserInRegion(firstLat, firstLng,lat,lng)) {
            timeDiff = System.currentTimeMillis() - timestamp; // calc time spent at location

            if(timeDiff>30000) {  //if a significant amount of time is spent here //30 secs
                timeDiffHour = timeDiff * 0.0000002777; //convert millis to hr for storage

                //if significant time is spent at a saved location, add the elapsed time to the total time spent here
                if(length != 0) {
                    for (int i = 0; i < locationsArray.size(); i++) {
                        if(locationsArray.get(i) != null) {
                            if (locationsArray.get(i).equals(getAddress(MapsActivity.this, firstLat, firstLng))) {
                                totalTime = timeSpentAtLocation.get(i) + timeDiffHour; //aggregate time spent at location
                                timeSpentAtLocation.set(i, totalTime);
                            }
                        }
                    }
                }

                timestamp = System.currentTimeMillis(); //reset initial time since we are now in a new location
                firstLat = lat; //reset initial latitude
                firstLng = lng; //reset initial longitude
            }
        }
    }

    private boolean isUserInRegion(double firstLat, double firstLog, double curLat, double curLog){
        double r = 0.0005; // Any radious //200ft
        double dx = curLat - firstLat;
        double dy = curLog - firstLog;
        return dx * dx + dy * dy <= r * r;
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Single Permission is granted
                        makeText(MapsActivity.this, "Single permission is granted!", Toast.LENGTH_SHORT).show();
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        return isPermission;

    }

    public void tHClicked(View v) {

        File location = new File(getFilesDir(),"loc_name.txt");
        File timespan = new File(getFilesDir(),"loc_time.txt");

        try{
            if(!location.exists()){
                location.createNewFile();
            }

            if(!timespan.exists()){
                timespan.createNewFile();
            }

            FileOutputStream f_name = new FileOutputStream(location,true);
            int i = 0;
            String name = "";
            for(String temp:addArray){
                name = name + "|---|" + temp;
                i++;
            }
            f_name.write(name.getBytes());
            f_name.close();

            FileOutputStream f_time = new FileOutputStream(timespan,true);
            i = 0;
            String time = "";
            for(double temp:timeSpentAtLocation){
                time = time + "|---|" + temp;
                i++;
            }
            f_time.write(time.getBytes());
            f_time.close();

        }catch(IOException e){
            e.printStackTrace();;
        }

        Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}

