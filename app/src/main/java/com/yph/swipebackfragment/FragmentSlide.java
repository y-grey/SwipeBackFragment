package com.yph.swipebackfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yph.view.BaseSwipeFragment;


public class FragmentSlide extends BaseSwipeFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);
        return attachSwipe(rootView);
    }

    public void addFragmentClick(View v){
        ((MainActivity)getActivity()).addFragment(new FragmentSlide(),R.anim.slide_in_from_right,R.anim.slide_out_to_right);
    }
}
