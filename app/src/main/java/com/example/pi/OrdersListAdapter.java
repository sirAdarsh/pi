package com.example.pi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrdersListAdapter extends ArrayAdapter<Order> {

    private Activity context;
    private List<Order> ordersList;

    public OrdersListAdapter(Activity context,List<Order> ordersList){
        super(context,R.layout.order_listitem_layout,ordersList);
        this.context=context;
        this.ordersList=ordersList;

    }

    public View getView(int position,View ConvertView,ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();


        View listViewItem=inflater.inflate(R.layout.order_listitem_layout,null,true);

        TextView ordererName=listViewItem.findViewById(R.id.textViewOrdererName);
        TextView deliveryLoc=listViewItem.findViewById(R.id.textViewdeliveryLoc);
        TextView orderItem=listViewItem.findViewById(R.id.textViewOrderItem);


        Order order=ordersList.get(position);

        ordererName.setText(order.getOrdererName());
        orderItem.setText(order.getOrderItem());
        deliveryLoc.setText(order.getLocation());

        return listViewItem;
    }
}
