package com.a700apps.techmart.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by m.aboelazm on 12/29/2015.
 * Mohamed Abulazm
 */
public abstract class StandardBaseListAdapter<T> extends BaseAdapter {
    protected ArrayList<T> mList;
    protected WeakReference<Context> mContextWeakReference;
    protected final String TAG = getClass().getSimpleName();

    public StandardBaseListAdapter(ArrayList<T> mList, Context mContext) {
        this.mList = mList;
        this.mContextWeakReference = new WeakReference<>(mContext);
    }

    /**
     * Removes on item from the list and notify data set changed
     *
     * @param index of the item to be removed from the list
     */
    protected void removeItemFromAdapter(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
