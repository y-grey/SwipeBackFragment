package com.yph.swipebackfragment;


import android.view.View;
import android.widget.Toast;

import com.yph.view.BaseSwipeFragment;


public class FragmentSlide extends BaseSwipeFragment {

	@Override
	public boolean attachSwipe() {return true;}

    @Override
    protected int getViewRootId() {return R.layout.fragment_slide;}

    @Override
    protected void init() {}

    public void addFragmentClick(View v){
        Toast.makeText(getActivity(),"!!",Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).addFragment(new FragmentSlide(),R.anim.slide_in_from_right,R.anim.slide_out_to_right);
    }
}
