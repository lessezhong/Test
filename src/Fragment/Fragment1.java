package Fragment;

import java.util.ArrayList;
import com.example.adapter.MyFragmentPagerAdapter;
import com.example.viewpagenest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class Fragment1 extends Fragment implements OnTouchListener{
	ViewPager childPage;
	MyFragmentPagerAdapter myFragmentPagerAdapter1;
	ArrayList<Fragment> listFragments;
	View view;
	String code[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		code = new String[] { "sh601006", "sh601007", "sh601008", "sh601009",
				"sz000018", "hk00941", "sh601001", "sh601002", "sh601003",
				"sh601004" };
		listFragments = new ArrayList<Fragment>();
		listFragments.add(new Fragment3(this.getActivity()
				.getApplicationContext(), code));
		// listFragments.add(new Fragment3());
		// listFragments.add(new Fragment3());
		myFragmentPagerAdapter1 = new MyFragmentPagerAdapter(
				getChildFragmentManager(), listFragments);
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment1, null);
		childPage = (ViewPager) view.findViewById(R.id.viewchild);
		childPage.setAdapter(myFragmentPagerAdapter1);
		return view;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		if (event.getAction()==MotionEvent.ACTION_DOWN&&v instanceof ViewGroup) {
//			 ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
//		}
		return false;
	}
}
