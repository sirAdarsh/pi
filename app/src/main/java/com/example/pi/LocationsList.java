package com.example.pi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LocationsList extends ArrayAdapter<Location> {

    private Activity context;
    private List<Location> locationsList;

    public LocationsList(Activity context,List<Location> locationsList){
        super(context,R.layout.locations_list_layout,locationsList);
        this.context=context;
        this.locationsList=locationsList;
    }

    public View getView(int position, View ConvertView, ViewGroup parent){
        LayoutInflater inflator=context.getLayoutInflater();

        View listViewItem=inflator.inflate(R.layout.locations_list_layout,null,true);

        TextView textViewLocation=listViewItem.findViewById(R.id.textVieLocation);
       // TextView textViewLatlng=listViewItem.findViewById(R.id.textViewLatLng);
        TextView textViewTimeavl=listViewItem.findViewById(R.id.textViewTimeAvl);
        TextView textViewTimeofAccess=listViewItem.findViewById(R.id.textViewTimeOfAccess);

        Location location=locationsList.get(position);

        textViewLocation.setText(location.getLocationInLatLng());
       // textViewLatlng.setText(location.getLatLng());
        textViewTimeofAccess.setText(location.getTimeOfAccess());
        textViewTimeavl.setText(location.getTimeAvailable());

        return listViewItem;
    }
}
