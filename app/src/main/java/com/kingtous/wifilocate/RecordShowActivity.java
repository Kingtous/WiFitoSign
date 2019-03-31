package com.kingtous.wifilocate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecordShowActivity extends Activity {

    SQLiteClient client;
    ArrayList<WiFiRecordModel> arrayRecord;
    RecyclerView recyclerView;
    RecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        client=new SQLiteClient(this);
        arrayRecord=client.onQuery();
        //装填进RecyclerView
        recyclerView=findViewById(R.id.recyclerview_wifi);
        adapter=new RecordAdapter(arrayRecord);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
