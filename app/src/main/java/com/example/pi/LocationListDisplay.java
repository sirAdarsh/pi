package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LocationListDisplay extends AppCompatActivity {

    List<Location> locationsList;
    DatabaseReference databaseLocations;
    ListView listViewLocations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_locations_activity);

        listViewLocations=findViewById(R.id.location_list);

        databaseLocations= FirebaseDatabase.getInstance().getReference("location");

        locationsList=new ArrayList<>();


        listViewLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



                Intent i=new Intent(LocationListDisplay.this,MainActivity.class);
                startActivity(i);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseLocations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                locationsList.clear();
                for(DataSnapshot locationSnapshot:dataSnapshot.getChildren()){
                    Location location=locationSnapshot.getValue(Location.class);

                    locationsList.add(location);
                }

                LocationsList adapter=new LocationsList(LocationListDisplay.this,locationsList);
                listViewLocations.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
