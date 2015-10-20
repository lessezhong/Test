package Fragment;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.adapter.MyListViewAdapter;
import com.example.adapter.TendencyChartPageAdater;
import com.example.mydefineui.MyScrollView;
import com.example.mydefineui.MyScrollView.MyScrollViewListener;
import com.example.viewpagenest.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.show.api.ShowApiRequest;


public class Fragment2 extends Fragment {
	MyScrollView mySrocView;
	View v;// 全局视图
	boolean isCanFresh = false;
	boolean isFreshing = false;
	TextView t;
	public ViewPager tendecViewPager;
	ImageLoader myImageLoader;
	LinkedList<String> LinkedListUrl;
	String url[] = new String[4];
	TendencyChartPageAdater mypagerAdapter;
	AutoCompleteTextView myAutoCompleteTextView;
	MyListViewAdapter listViewAdapter;
	AsyncHttpResponseHandler asyncHttpResponseHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		myImageLoader = ImageLoader.getInstance();
		asyncHttpResponseHandler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				try {
					String arg12 = new String(arg2, "utf-8");
					try {
						UpdateUI(arg12);
					} catch (Exception e) {
						Dialog dialog=new Dialog(getActivity().getApplicationContext());
						dialog.setTitle("request too much wait a moment");
						dialog.show();
						
					}
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Log.e("hai", "fail");

			}
			@Override
			public void onRetry(int retryNo) {
				// TODO Auto-generated method stub
				super.onRetry(retryNo);
			}
			
		};
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		applyScrollListener();
	}

	private void applyScrollListener() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment2, null);
		tendecViewPager = (ViewPager) v.findViewById(R.id.showTendencyChart);
		tendecViewPager.setFocusable(true);
		tendecViewPager.setFocusableInTouchMode(true);
		tendecViewPager.requestFocus();
		mySrocView = (MyScrollView) v.findViewById(R.id.MyFirstScrollView);
		// 设置滑动的监听器
		mySrocView.setScrollViewListener(new MyScrollViewListener() {
			@Override
			public void onMyScrollChanged(MyScrollView scrollView, int x,
					int y, int oldx, int oldy) {
			}
		});
		mypagerAdapter = new TendencyChartPageAdater(getActivity()
				.getApplicationContext(), url, myImageLoader);
		new ShowApiRequest("http://route.showapi.com/131-44", "1010",
				"9f86d6b5eb7e4768afb4fbd0b089e112")
				.setResponseHandler(asyncHttpResponseHandler)
				.addTextPara("code", "000002").addTextPara("needIndex", "1")
				.post();
		return v;
	}
	// 更新 UI
	void UpdateUI(String result) {
		Log.e("hai", "i got it");
		try {
			JSONObject AlljsonObject = new JSONObject(result);
			JSONObject showapi_res_body = AlljsonObject
					.getJSONObject("showapi_res_body");
			JSONObject stockMarket = showapi_res_body
					.optJSONObject("stockMarket");
			String S = stockMarket.getString("name");// 股票名称
			TextView tv = (TextView) v.findViewById(R.id.codename);
			tv.setText(" " + S);
			S = stockMarket.getString("nowPrice");// 当前股价
			float nowprice = Float.parseFloat(S);
			tv = (TextView) v.findViewById(R.id.nowPrice);
			tv.setText(" " + S);
			S = stockMarket.getString("closePrice");// 昨日收盘价
			tv = (TextView) v.findViewById(R.id.closePrice);
			tv.setText(" " + S);
			float closePrice = Float.parseFloat(" " + S);
			tv = (TextView) v.findViewById(R.id.increasement);
			float per = (nowprice - closePrice) / closePrice * 100;
			if (per > 0) {
				tv.setTextColor(Color.RED);
			} else {
				tv.setTextColor(Color.BLUE);
			}
			tv.setText(" " + per + "%");
			Log.e("hai", S);
			// 沪市
			JSONObject indexListArray = showapi_res_body.getJSONArray(
					"indexList").getJSONObject(0);
			String name = indexListArray.getString("name");// 名称
			tv = (TextView) v.findViewById(R.id.SH);
			tv.setText(name);
			name=indexListArray.getString("time");
			tv=(TextView) v.findViewById(R.id.freshTime);
			tv.setText(name);
			name = indexListArray.getString("nowPrice");// 上证指数
			tv = (TextView) v.findViewById(R.id.SH_nowPrice);
			tv.setText(" " + name);
			tv = (TextView) v.findViewById(R.id.SH_increasment);
			per = (Float.parseFloat(name) - Float.parseFloat(indexListArray
					.getString("yestodayClosePrice")))
					/ (Float.parseFloat(indexListArray
							.getString("yestodayClosePrice"))) * 100;
			tv.setText(" " + per + "%");
			if (per > 0) {
				tv.setTextColor(Color.RED);
			} else {
				tv.setTextColor(Color.BLUE);
			}

			// 深市
			indexListArray = showapi_res_body.getJSONArray("indexList")
					.getJSONObject(1);
			name = indexListArray.getString("name");// 名称
			tv = (TextView) v.findViewById(R.id.SZ);
			tv.setText(name);
			name = indexListArray.getString("nowPrice");// 上证指数
			tv = (TextView) v.findViewById(R.id.SZ_nowPrice);
			tv.setText(" " + name);
			tv = (TextView) v.findViewById(R.id.SZ_increasment);
			per = (Float.parseFloat(name) - Float.parseFloat(indexListArray
					.getString("yestodayClosePrice")))
					/ (Float.parseFloat(indexListArray
							.getString("yestodayClosePrice"))) * 100;
			tv.setText(" " + per + "%");
			if (per > 0) {
				tv.setTextColor(Color.RED);
			} else {
				tv.setTextColor(Color.BLUE);
			}
//			JSONObject jsonObject = AlljsonObject.getJSONObject(
//			"showapi_res_body").getJSONObject("k_pic");
//			String weekurl = url[3] = jsonObject.getString("weekurl");// K线图
//			String monthurl = url[2] = jsonObject.getString("monthurl");
//			String dayurl = url[1] = jsonObject.getString("dayurl");
//			String minurl = url[0] = jsonObject.getString("minurl");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
