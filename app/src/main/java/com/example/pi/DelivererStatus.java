package com.example.pi;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DelivererStatus extends AppCompatActivity implements LocationListener {

    EditText editTextDelivererLocation;
    EditText editTextTimeAvailable;
    Switch simpleSwitch;
    Button delivererSubmit;
    TextView textViewCurrentTime;

    Button buttonToSeeOrdersList;

    TextView delivererLatLang;
    DatabaseReference databaseLocation;


    int isAvailable;

    protected LocationManager locationManager;
    protected LocationListener locationListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverer_activity);

        editTextDelivererLocation = findViewById(R.id.editTextDelivererLocation);
        editTextTimeAvailable = findViewById(R.id.editTextTimeAvailable);
        delivererLatLang = findViewById(R.id.deliveryLatLng);
        textViewCurrentTime=findViewById(R.id.textViewCurrentTime);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        delivererSubmit=findViewById(R.id.delivererSubmit);

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

      //  final Button btnForDelivererMap=(Button) findViewById(R.id.delivererMapIntent);
      //  btnForDelivererMap.setOnClickListener(new View.OnClickListener() {
       //     @Override
      //      public void onClick(View view) {
      //          Uri gmmIntentUri=Uri.parse(btnForDelivererMap.getText().toString());
      //          Intent mapIntent=new Intent(Intent.ACTION_VIEW,gmmIntentUri);
        //        mapIntent.setPackage("com.google.android.apps.maps");
      //          if (mapIntent.resolveActivity(getPackageManager()) != null) {
        //            startActivity(mapIntent);
        //        }
         //   }
       // });
        simpleSwitch=findViewById(R.id.switchforAvailability);

      // if(simpleSwitch.isChecked()){
       //     isAvailable=1;
       // }
       //else isAvailable=0;



        databaseLocation= FirebaseDatabase.getInstance().getReference("location");
        delivererSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(DelivererStatus.this).setTitle("Confirmation").setMessage("Are you sure about your details and availability?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addLocation();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(DelivererStatus.this,"Update Cancelled",Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();

            }
        });


        String currentTime=new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        textViewCurrentTime.setText(currentTime);

        buttonToSeeOrdersList=findViewById(R.id.buttonToSeeTheList);
        buttonToSeeOrdersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DelivererStatus.this,OrdersListDisplay.class);
                startActivity(i);
            }
        });





    }

    private void addLocation(){

        String latLng=delivererLatLang.getText().toString().trim();
        String currentTime=textViewCurrentTime.getText().toString().trim();



        String location=editTextDelivererLocation.getText().toString().trim();
        int status;
        if(simpleSwitch.isChecked()){
            status=1;
        }
        else status=0;

        String timeAvl=editTextTimeAvailable.getText().toString().trim();
        if (!TextUtils.isEmpty(location)){
            String id=databaseLocation.push().getKey();
            com.example.pi.Location location1=new com.example.pi.Location(location,status,timeAvl,id,latLng,currentTime);

            databaseLocation.child(id).setValue(location1);
            Toast.makeText(this, "Data Added to the Database", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        delivererLatLang.setText("Latitude: "+location.getLatitude()+" , Longitude: "+location.getLongitude());



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");

    }

    @Override
    public void onProviderEnabled(String s) {Log.d("Latitude","enable");

    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","enable");
    }

}
