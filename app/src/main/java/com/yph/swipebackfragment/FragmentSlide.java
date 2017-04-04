package com.yph.swipebackfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yph.view.AddSwipeFragmentUtil;
import com.yph.view.BaseSwipeFragment;


public class FragmentSlide extends BaseSwipeFragment {
    int colors[] = new int[]{Color.BLUE,Color.GREEN,Color.YELLOW,Color.CYAN};
    int count = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);
        count = getActivity().getSupportFragmentManager().getBackStackEntryCount()%4;
        rootView.setBackgroundColor(colors[count]);
        rootView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0)
                    AddSwipeFragmentUtil.addFragmentFromLeft(getActivity(),new FragmentSlide());
                else if(count == 1)
                    AddSwipeFragmentUtil.addFragmentFrombBottom(getActivity(),new FragmentSlide());
                else if(count == 2)
                    AddSwipeFragmentUtil.addFragmentFromTop(getActivity(),new FragmentSlide());
                else if(count == 3)
                    AddSwipeFragmentUtil.addFragmentFromRight(getActivity(),new FragmentSlide());
            }
        });
        return attachSwipe(rootView);
    }
}
