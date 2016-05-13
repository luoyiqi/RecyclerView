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
package com.wx.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author venshine
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;

    private ArrayList<T> mDatas = new ArrayList<>();

    private View mHeaderView = null;
    private View mFooterView = null;

    /**
     * 添加HeaderView
     *
     * @param v
     */
    public void setHeaderView(View v) {
        if (mHeaderView == null) {
            mHeaderView = v;
            notifyItemInserted(0);
        }
    }

    /**
     * 移除HeaderView
     *
     * @return
     */
    public boolean removeHeaderView() {
        if (mHeaderView != null) {
            notifyItemRemoved(0);
            mHeaderView = null;
            return true;
        }
        return false;
    }

    /**
     * 添加FooterView
     *
     * @param v
     */
    public void setFooterView(View v) {
        if (mFooterView == null) {
            mFooterView = v;
            notifyItemInserted(mDatas.size() + getHeaderCount());
        }
    }

    /**
     * 移除FooterView
     *
     * @return
     */
    public boolean removeFooterView() {
        if (mFooterView != null) {
            notifyItemRemoved(mDatas.size() + getHeaderCount());
            mFooterView = null;
            return true;
        }
        return false;
    }

    private int getHeaderCount() {
        return mHeaderView != null ? 1 : 0;
    }

    private int getFooterCount() {
        return mFooterView != null ? 1 : 0;
    }

    /**
     * 在指定位置添加数据
     *
     * @param index
     * @param datas
     */
    public void addDatas(int index, ArrayList<T> datas) {
        mDatas.addAll(index, datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addDatas(ArrayList<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 设置数据，清空原有数据
     *
     * @param datas
     */
    public void setDatas(ArrayList<T> datas) {
        mDatas.clear();
        addDatas(datas);
    }

    /**
     * 添加元素
     *
     * @param data
     */
    public void addData(T data) {
        addData(mDatas.size(), data);
    }

    /**
     * 添加元素
     *
     * @param position
     * @param data
     */
    public void addData(int position, T data) {
        mDatas.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 删除元素
     *
     * @param position
     */
    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mFooterView != null && position == getHeaderCount() + mDatas.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ViewHolder(mFooterView);
        }
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        int realPos = position - getHeaderCount();
        onBind(holder, realPos, mDatas.get(realPos));
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract RecyclerViewAdapter.ViewHolder onCreate(ViewGroup parent, int viewType);

    public abstract void onBind(RecyclerViewAdapter.ViewHolder holder, int position, T data);

    public void log(String msg) {
        Log.d("venshine", msg);
    }

}
