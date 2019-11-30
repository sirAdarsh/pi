package com.example.pi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailListItemWise extends AppCompatActivity {


    TextView detailedLocationLatlng;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedinfo_activity);


        detailedLocationLatlng=findViewById(R.id.detailLocationLatLng);



    }
}
