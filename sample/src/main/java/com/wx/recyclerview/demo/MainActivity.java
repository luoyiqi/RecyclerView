package com.wx.recyclerview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.wx.recyclerview.FastRecyclerView;
import com.wx.recyclerview.ListItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FastRecyclerView mRecyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (FastRecyclerView) findViewById(R.id.recyclerview);
        adapter = new MyAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ListItemDecoration(this, ListItemDecoration.VERTICAL));
        adapter.addDatas(getDatas());
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header, mRecyclerView, false));
    }

    private static ArrayList<String> getDatas() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }
        return list;
    }

    public void onButton(View view) {
//        adapter.addDatas(getDatas());
        adapter.addData(0, "1");
    }

}
