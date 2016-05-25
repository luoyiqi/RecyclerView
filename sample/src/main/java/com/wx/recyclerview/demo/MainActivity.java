/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wx.recyclerview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wx.recyclerview.FastRecyclerView;
import com.wx.recyclerview.ListItemDecoration;
import com.wx.recyclerview.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Demo
 *
 * @author venshine
 */
public class MainActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private FastRecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration mItemDecoration;
    private View v1, v2, v3, v4;

    private static ArrayList<String> getDatas() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (FastRecyclerView) findViewById(R.id.recyclerview);
        adapter = new MyAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemDecoration = new ListItemDecoration(this, ListItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mItemDecoration);
        adapter.addDatas(getDatas());
        v1 = LayoutInflater.from(this).inflate(R.layout.header, mRecyclerView, false);
        v2 = LayoutInflater.from(this).inflate(R.layout.header, mRecyclerView, false);
        v3 = LayoutInflater.from(this).inflate(R.layout.header, mRecyclerView, false);
        v4 = LayoutInflater.from(this).inflate(R.layout.header, mRecyclerView, false);
        ((TextView) v1.findViewById(R.id.header_text)).setText("1111");
        ((TextView) v2.findViewById(R.id.header_text)).setText("2222");
        ((TextView) v3.findViewById(R.id.header_text)).setText("3333");
        ((TextView) v4.findViewById(R.id.header_text)).setText("4444");
        adapter.setHeaderView(v1);
        adapter.setFooterView(v2);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View v, int position, String data) {
                log("click  p:" + position + ", data:" + data);
            }
        });
        adapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener<String>() {
            @Override
            public void onItemLongClick(View v, int position, String data) {
                log("long click  p:" + position + ", data:" + data);
            }
        });
    }

    public void add(View view) {
        adapter.addDatas(getDatas());
    }

    public void del(View view) {
        adapter.removeHeaderView();
    }

    public void add2(View view) {
        adapter.setFooterView(v2);
    }

    public void del2(View view) {
        adapter.removeFooterView();
    }

    public void listV(View v) {
        mRecyclerView.setListMode(FastRecyclerView.VERTICAL, true);
    }

    public void listH(View v) {
        mRecyclerView.setListMode(FastRecyclerView.HORIZONTAL, true);
    }

    public void gridV(View v) {
        mRecyclerView.setGridMode(FastRecyclerView.VERTICAL, 4, true);
    }

    public void gridH(View v) {
        mRecyclerView.setGridMode(FastRecyclerView.HORIZONTAL, 4, true);
    }

    public void staggerV(View v) {
        mRecyclerView.setStaggeredMode(FastRecyclerView.VERTICAL, 4, true);
    }

    public void staggerH(View v) {
        mRecyclerView.setStaggeredMode(FastRecyclerView.HORIZONTAL, 4, true);
    }

    public void log(String msg) {
        Log.d("venshine", msg);
    }

}
