package com.kingtous.wifilocate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<WiFiRecordModel> arrayList;

    RecordAdapter(ArrayList<WiFiRecordModel> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_item,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((myHolder)holder).time.setText(arrayList.get(position).getDate());
        ((myHolder)holder).bssid.setText(arrayList.get(position).getBssid());
        ((myHolder)holder).wifi.setText(arrayList.get(position).getWifiName());
        ((myHolder)holder).position.setText(arrayList.get(position).getPosition());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        public TextView time;
        public TextView wifi;
        public TextView bssid;
        public TextView position;
        public myHolder(View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.text_sign_time);
            wifi=itemView.findViewById(R.id.text_sign_wifi);
            bssid=itemView.findViewById(R.id.text_sign_wifi_bssid);
            position=itemView.findViewById(R.id.text_sign_position);
        }
    }


}
