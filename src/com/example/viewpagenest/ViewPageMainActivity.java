package com.example.viewpagenest;

import java.util.ArrayList;

import Fragment.Fragment1;
import Fragment.Fragment2;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adapter.MyFragmentPagerAdapter;

public class ViewPageMainActivity extends FragmentActivity {

	ViewPager parentPager;

	MyFragmentPagerAdapter myFragmentPagerAdapter;

	ArrayList<Fragment> listFragments1;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_page_main);
		actionBar= getActionBar();
		initializeActionBar ();
		parentPager = (ViewPager) findViewById(R.id.viewparent);
		listFragments1 = new ArrayList<Fragment>();
		listFragments1.add(new Fragment2());
		listFragments1.add(new Fragment1());
		//listFragments1.add(new Fragment3(this,));
		myFragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), listFragments1);
		parentPager.setAdapter(myFragmentPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_page_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.choseyouself:
			Intent intent = new Intent(ViewPageMainActivity.this,
					ChoseActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	//≈‰÷√Actionbar
		@SuppressLint("NewApi")
		public void initializeActionBar () {
			Resources rs=getResources();
			actionBar.setBackgroundDrawable(rs.getDrawable(R.drawable.ic_item_selected_bg));
			//actionBar.addTab(tab);
		}

}
