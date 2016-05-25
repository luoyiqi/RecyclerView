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
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * @author venshine
 */
public class FastRecyclerView extends RecyclerView {

    public static final int VERTICAL = 0x0000;
    public static final int HORIZONTAL = 0x0001;

    private ItemDecoration mItemDecoration;

    public FastRecyclerView(Context context) {
        super(context);
    }

    public FastRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FastRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 列表模式
     *
     * @param orientation
     * @param divider
     */
    public void setListMode(int orientation, boolean divider) {
        if (orientation == VERTICAL) {
            setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            if (divider) {
                if (null != mItemDecoration) {
                    removeItemDecoration(mItemDecoration);
                }
                mItemDecoration = new ListItemDecoration(getContext(), ListItemDecoration.VERTICAL);
                addItemDecoration(mItemDecoration);
            }
        } else if (orientation == HORIZONTAL) {
            setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            if (divider) {
                if (null != mItemDecoration) {
                    removeItemDecoration(mItemDecoration);
                }
                mItemDecoration = new ListItemDecoration(getContext(), ListItemDecoration.HORIZONTAL);
                addItemDecoration(mItemDecoration);
            }
        }
    }

    /**
     * 网格模式
     *
     * @param orientation
     * @param spanCount
     * @param divider
     */
    public void setGridMode(int orientation, int spanCount, boolean divider) {
        if (orientation == VERTICAL) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount, GridLayoutManager
                    .VERTICAL, false);
            final RecyclerViewAdapter adapter = (RecyclerViewAdapter) getAdapter();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (adapter.isHeader(position) || adapter.isFooter(position)) ? gridLayoutManager
                            .getSpanCount() : 1;
                }
            });
            setLayoutManager(gridLayoutManager);
            if (divider) {
                if (null != mItemDecoration) {
                    removeItemDecoration(mItemDecoration);
                }
                mItemDecoration = new GridItemDecoration(getContext());
                addItemDecoration(mItemDecoration);
            }
        } else if (orientation == HORIZONTAL) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount, GridLayoutManager
                    .HORIZONTAL, false);
            final RecyclerViewAdapter adapter = (RecyclerViewAdapter) getAdapter();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (adapter.isHeader(position) || adapter.isFooter(position)) ? gridLayoutManager
                            .getSpanCount() : 1;
                }
            });
            setLayoutManager(gridLayoutManager);
            if (divider) {
                if (null != mItemDecoration) {
                    removeItemDecoration(mItemDecoration);
                }
                mItemDecoration = new GridItemDecoration(getContext());
                addItemDecoration(mItemDecoration);
            }
        }
    }

    /**
     * 瀑布流模式
     *
     * @param orientation
     * @param spanCount
     * @param divider
     */
    public void setStaggeredMode(int orientation, int spanCount, boolean divider) {
        if (orientation == VERTICAL) {
            setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
            if (divider) {
                if (null != mItemDecoration) {
                    removeItemDecoration(mItemDecoration);
                }
                mItemDecoration = new GridItemDecoration(getContext());
                addItemDecoration(mItemDecoration);
            }
        } else if (orientation == HORIZONTAL) {
            setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL));
            if (divider) {
                if (null != mItemDecoration) {
                    removeItemDecoration(mItemDecoration);
                }
                mItemDecoration = new GridItemDecoration(getContext());
                addItemDecoration(mItemDecoration);
            }
        }
    }


}
