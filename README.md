# SwipeBackFragment
submit first
SwipeBackFragment,实现拖拽返回的fragment，一个类似于SwipeBackActivity的开源项目。

支持四个方向拖拽


用法：继承BaseSwipeFragment，实现三个方法

attachSwipe()：是否支持拖拽
getViewRootId()：返回fragment根布局
init()：初始化代码写在这里

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
