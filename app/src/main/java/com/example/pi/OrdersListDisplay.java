package com.example.pi;

import android.os.Bundle;
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

public class OrdersListDisplay extends AppCompatActivity {

    List<Order> orderslist;
    DatabaseReference databaseReferenceOrders;
    ListView listViewOrders;

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orderslist.clear();

                for(DataSnapshot orderSnapshot:dataSnapshot.getChildren()){

                    Order order=orderSnapshot.getValue(Order.class);

                    orderslist.add(order);
                }

                OrdersListAdapter ordersListAdapter=new OrdersListAdapter(OrdersListDisplay.this,orderslist);

                listViewOrders.setAdapter(ordersListAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);

        listViewOrders=findViewById(R.id.orders_list);

        databaseReferenceOrders= FirebaseDatabase.getInstance().getReference("Orders");
        orderslist=new ArrayList<>();

    }



}
