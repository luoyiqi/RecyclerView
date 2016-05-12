package com.wx.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;

    private ArrayList<T> mDatas = new ArrayList<>();

    private ArrayList<View> mHeaderViews = new ArrayList<>();

    private ArrayList<View> mFooterViews = new ArrayList<>();

    public void addHeaderView(View v) {
        mHeaderViews.add(v);
        notifyItemInserted(getHeaderViewsCount());
    }

    public boolean removeHeaderView(View v) {
        return getHeaderViewsCount() > 0 ? mHeaderViews.remove(v) : false;
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
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
        log("count:" + getHeaderViewsCount() + ", pos:" + position);
        if (getHeaderViewsCount() > 0 && position < getHeaderViewsCount()) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        log("onCreateViewHolder:" + viewType);
        if (getHeaderViewsCount() > 0 && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderViews.get(0));
        }
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
//        log("onBindViewHolder:" + position);
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        onBind(holder, position, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
//        log("getItemCount:" + mDatas.size());
        return getHeaderViewsCount() > 0 ? getHeaderViewsCount() + mDatas.size() : mDatas.size();
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
