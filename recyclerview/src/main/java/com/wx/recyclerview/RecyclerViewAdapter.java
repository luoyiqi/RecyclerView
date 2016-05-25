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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
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

    private Context mContext = null;

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

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
            notifyItemInserted(getContentCount() + getHeaderCount());
        }
    }

    /**
     * 移除FooterView
     *
     * @return
     */
    public boolean removeFooterView() {
        if (mFooterView != null) {
            notifyItemRemoved(getContentCount() + getHeaderCount());
            mFooterView = null;
            return true;
        }
        return false;
    }

    private final int getHeaderCount() {
        return mHeaderView != null ? 1 : 0;
    }

    private final int getFooterCount() {
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
        addData(getContentCount(), data);
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mFooterView != null && position == getHeaderCount() + getContentCount()) {
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
        T data = mDatas.get(realPos);
        setEventListener(holder, realPos, data);
        onBind(holder, realPos, data);
    }

    public Drawable getSelectorDrawable() {
        return null;
    }

    /**
     * 设置事件监听
     *
     * @param holder
     * @param realPos
     * @param data
     */
    private void setEventListener(final ViewHolder holder, final int realPos, final T data) {
        Drawable selectorDrawable = getSelectorDrawable();
        if (selectorDrawable == null) {
            selectorDrawable = RecyclerUtil.getSelectorDrawable();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(selectorDrawable);
        } else {
            holder.itemView.setBackgroundDrawable(selectorDrawable);
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, realPos, data);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, realPos, data);
                    return true;
                }
            });
        }
    }

    public boolean isHeader(int position) {
        return mHeaderView != null ? (position == 0) : false;
    }

    public boolean isFooter(int position) {
        return mFooterView != null ? (position == getContentCount() + getHeaderCount()) : false;
    }


    /**
     * 数据项总数
     *
     * @return
     */
    public final int getContentCount() {
        return mDatas.size();
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + getContentCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        log("onAttached");
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public abstract RecyclerViewAdapter.ViewHolder onCreate(ViewGroup parent, int viewType);

    public abstract void onBind(RecyclerViewAdapter.ViewHolder holder, int position, T data);

    public void log(String msg) {
        Log.d("venshine", msg);
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View v, int position, T data);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View v, int position, T data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setLongClickable(true);
        }
    }

}
