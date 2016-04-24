package com.yph.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by _yph on 2016/1/27 0027.
 */

public abstract class BaseSwipeFragment extends Fragment {

    protected View rootView;
    private SwipeBackLayout swipeBackLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(getViewRootId(), container, false);
        if (!attachSwipe()) {
            return rootView;
        }
        swipeBackLayout = new SwipeBackLayout(getActivity());
        swipeBackLayout.setOnSwipeFinish(new SwipeBackLayout.OnSwipeFinish() {
            @Override
            public void swipeFinish() {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        swipeBackLayout.addView(rootView);
        return swipeBackLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    protected Serializable getBundleObj() {
        return getArguments().getSerializable("obj");
    }

    protected void setSwipeModel(int model) {
        swipeBackLayout.setModel(model);
    }

    protected void setSwipeEnable(boolean isEnable) {
        swipeBackLayout.setEnabled(isEnable);
    }

    protected abstract boolean attachSwipe();

    protected abstract void init();

    protected abstract int getViewRootId();
}
