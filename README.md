# SwipeBackFragment

SwipeBackFragment,实现拖拽返回的fragment，一个类似于SwipeBackActivity的开源项目。

支持四个方向拖拽，通过setSwipeModel(SwipeBackLayout.SWIPE_RIGHT)...


用法：继承BaseSwipeFragment，实现三个方法


public class FragmentSlide extends BaseSwipeFragment {

	@Override
	public boolean attachSwipe() {return true;}

    @Override
    protected int getViewRootId() {return R.layout.fragment_slide;}

    @Override
    protected void init() {}
}


attachSwipe()：是否支持拖拽

getViewRootId()：返回fragment根布局

init()：初始化代码写在这里

Contact Author：

E-mail:aa999999999@vip.qq.com
QQ:542391099
csdn: blog.csdn.net/u012874222
