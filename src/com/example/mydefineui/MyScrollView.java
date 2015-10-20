package com.example.mydefineui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	MyScrollViewListener myScrollViewListener;
	Context context;
	boolean iscanFresh=false;

	public MyScrollView(Context context) {
		super(context);
		this.context = context;
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		if (myScrollViewListener != null) {
			if(y<-300&&iscanFresh){
				myScrollViewListener.onMyScrollChanged(this, x, y, oldx, oldy);// 再回调
				iscanFresh=false;
			}
			else{
				iscanFresh=false;
			}
		}

		super.onScrollChanged(x, y, oldx, oldy);
	}

	public void setScrollViewListener(MyScrollViewListener scrollViewListener) {
		this.myScrollViewListener = scrollViewListener; // 先注册
	}

	public interface MyScrollViewListener {
		void onMyScrollChanged(MyScrollView scrollView, int x, int y, int oldx,
				int oldy);
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_UP){
			iscanFresh=true;
		}
		else{
			iscanFresh=false;
		}
		return super.onTouchEvent(ev);
	}

}
