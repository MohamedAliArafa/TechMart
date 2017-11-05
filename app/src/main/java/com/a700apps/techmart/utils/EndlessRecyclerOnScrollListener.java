package com.a700apps.techmart.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by samir.salah on 11/2/2017.
 */

public abstract class EndlessRecyclerOnScrollListener extends EmptyRecyclerView.OnScrollListener {

    private boolean loading = false;// True if we are still waiting for the last set of data to load.
    private int current_page;
    private LinearLayoutManager mLinearLayoutManager;

    protected EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = mLinearLayoutManager.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            loading = true;
            current_page++;
            onLoadMore(current_page);
        }
    }

    public abstract void onLoadMore(int current_page);

    public void setIsLoading(boolean isLoading) {
        this.loading = isLoading;
    }

}
