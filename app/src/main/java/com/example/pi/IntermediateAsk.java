package com.example.pi;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class IntermediateAsk extends AppCompatActivity {

    private int NETWORK_PERMISSION_CODE=1; //for checking if the permission is granted


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intermediate_activity);

        //for checking if network permission was granted
        Button btnOrderer=findViewById(R.id.buttonOrderer);
        Button btnDeliverer=findViewById(R.id.buttonDeliverer);

        btnOrderer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(IntermediateAsk.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    //
                    //
                    Intent i=new Intent(IntermediateAsk.this,Selection.class);
                    startActivity(i);
                }
                else{
                    requestNetworkPermission();
                }
            }
        });

        btnDeliverer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(IntermediateAsk.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    //
                    //
                    Intent i=new Intent(IntermediateAsk.this,DelivererStatus.class);
                    startActivity(i);
                }
                else{
                    requestNetworkPermission();
                }
            }
        });


    }
    private void requestNetworkPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this).setTitle("Network permission Needed").setMessage("This end is needed because of this and that").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    ActivityCompat.requestPermissions(IntermediateAsk.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},NETWORK_PERMISSION_CODE);

                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }
        else{

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},NETWORK_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==NETWORK_PERMISSION_CODE){

            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Permission is not granted",Toast.LENGTH_SHORT);
        }
    }
}
