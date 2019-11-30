package com.example.pi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Selection extends AppCompatActivity implements LocationListener {


    DatabaseReference databaseForOrders;


    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    TextView testLoc;
    String lat;

    Button buttonSubmitOrder;
    EditText orderItem;
    EditText ordererName;
    EditText deliveryLocation;
    Button delivererListButton;


    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        //EditText nameEnterd=findViewById(R.id.editName);
        //TextView welcomeName = (TextView) findViewById(R.id.welcomeName);
        //welcomeName.setText("Welcome "+nameEnterd.getText()+"!");


        txtLat = (TextView) findViewById(R.id.locationInLatLng);
        testLoc=findViewById(R.id.testLoc);

        databaseForOrders= FirebaseDatabase.getInstance().getReference("Orders"); //create a new node for storing database for orders.

        buttonSubmitOrder=findViewById(R.id.submitOrder);

        ordererName=findViewById(R.id.ordererName);

        deliveryLocation=findViewById(R.id.deliveryLocation);

        delivererListButton=findViewById(R.id.deliverersList);

        delivererListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Selection.this,LocationListDisplay.class);
                startActivity(i);
            }
        });



        orderItem=findViewById(R.id.item);

        buttonSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder();
            }
        });



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        Button btnForMap=(Button) findViewById(R.id.mapIntent);
        btnForMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri=Uri.parse(testLoc.getText().toString());
                Intent mapIntent=new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

    }

    private void addOrder(){
        String orderedItem=orderItem.getText().toString().trim();

        String nameOrderer=ordererName.getText().toString().trim();

        String locationDelivery=deliveryLocation.getText().toString().trim();

        if(!TextUtils.isEmpty(orderedItem)){

            String id=databaseForOrders.push().getKey();
            Order order=new Order(nameOrderer,locationDelivery,orderedItem);

            databaseForOrders.child(id).setValue(order);

            Toast.makeText(Selection.this, "Order Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void feedback(View view){
        Intent gmailIntent=new Intent(Intent.ACTION_SENDTO);
        gmailIntent.setData(Uri.parse("mailto:"));
        gmailIntent.putExtra(Intent.EXTRA_EMAIL,"abcd@gmail.com");
        gmailIntent.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
        if(gmailIntent.resolveActivity(getPackageManager())!=null){
            startActivity(gmailIntent);
        }
    }

    public void call(View view){
        String phone="0101010101";
        Intent intentCall=new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:"+"0101010101"));
        if(intentCall.resolveActivity(getPackageManager())!= null){
            startActivity(intentCall);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat=(TextView)findViewById(R.id.locationInLatLng);
        txtLat.setText("Latitude: "+location.getLatitude()+" , Longitude: "+location.getLongitude());
        testLoc.setText("geo:"+location.getLatitude()+","+location.getLongitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");

    }
}
