package com.wx.recyclerview.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.recyclerview.RecyclerViewAdapter;

public class MyAdapter extends RecyclerViewAdapter<String> {

    @Override
    public RecyclerViewAdapter.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBind(RecyclerViewAdapter.ViewHolder holder, int position, String data) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).textView.setText(data);
        }
    }

    class MyHolder extends RecyclerViewAdapter.ViewHolder {

        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
