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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.recyclerview.RecyclerUtil;
import com.wx.recyclerview.RecyclerViewAdapter;

/**
 * @author venshine
 */
public class MyAdapter extends RecyclerViewAdapter<String> {

    public MyAdapter(Context context) {
        super(context);
    }

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

    @Override
    public Drawable getSelectorDrawable() {
        return RecyclerUtil.getSelectorDrawable();
    }

    class MyHolder extends RecyclerViewAdapter.ViewHolder {

        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
