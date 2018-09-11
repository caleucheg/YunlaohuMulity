package com.yang.yunwang.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *  on 2017/3/21.
 *
 * description：
 * update by:
 * update day:
 */
public abstract class BaseRecyclerFragment extends BaseFragment {
    protected RecyclerView mRecyclerView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = findViewById(R.id.core_recycler_view);
        if (mRecyclerView == null) {
            throw new IllegalArgumentException("必须包含id 为 core_recycler_view 的RecyclerView");
        }
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(getAdapter());
    }

    protected abstract RecyclerView.Adapter getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

}
