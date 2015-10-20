package Fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.IHaveChoseListViewAdapter;
import com.example.viewpagenest.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.myui.MyListView;
import com.show.api.ShowApiRequest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class Fragment3 extends Fragment {
	MyListView listView;
	IHaveChoseListViewAdapter adapter;
	LayoutInflater layoutInflater;
	View v;// 
	StringBuffer sb ;
	Context context;
	String code[];//测试用的股票代码
	MyAsyncHttpResponseHandler asyncHttpResponseHandler;
	View popWindowsView;
	PopupWindow popupWindow;
	JSONArray indexListArray;
	int position;//所点击的listview 的第position 个item
	public Fragment3(Context context, String code[]) {
		this.context = context;
		this.code = code;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// popWindowsView 布局文件
		popWindowsView= LayoutInflater.from(
				getActivity().getApplicationContext()).inflate(
				R.layout.popwindows, null);
		popupWindow= new PopupWindow(popWindowsView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.star_on));
		//设置Popwindows 内部的监听事件
		popWindowsView.findViewById(R.id.click_cancle_button)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						popupWindow.dismiss();

					}
				});
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment3, null);
		listView = (MyListView) v.findViewById(R.id.haveChosedListView);
		asyncHttpResponseHandler = new MyAsyncHttpResponseHandler();
		getDateAndUpdateMyListView(v);
		listView.setonRefreshListener(new MyListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getDateAndUpdateMyListView(v);// 递归调用更新自己
			}
		});
		return v;
	}
	
	/**
	 * @param v
	 *            -list view 所在的view
	 */
	public void getDateAndUpdateMyListView(View v) {

		sb = new StringBuffer();
		for (int i = 0; i < code.length; i++) {
			String s = code[i];
			sb = sb.append(s);
			if (i == code.length - 1) {
				continue;
			}
			sb = sb.append(",");

		}
		new ShowApiRequest("http://route.showapi.com/131-46", "1010",
				"9f86d6b5eb7e4768afb4fbd0b089e112")
				.setResponseHandler(asyncHttpResponseHandler)
				.addTextPara("stocks", sb.toString())
				.addTextPara("needIndex", "0").post();
	}

	public class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			if(listView!=null&&adapter!=null){
				listView.onRefreshFail();	
			}
			
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			setUI(new String(arg2));
		}
	}
	private void setUI(String result){

		JSONObject AlljsonObject = null;
		try {
			Log.e("hai", "we got fresh data");
			AlljsonObject = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONObject showapi_res_body = AlljsonObject
					.getJSONObject("showapi_res_body");
			indexListArray = showapi_res_body.getJSONArray("list");
			adapter = new IHaveChoseListViewAdapter(context, indexListArray);
			listView.setAdapter(adapter);
			listView.onRefreshComplete();	
			adapter.notifyDataSetChanged();
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0,
						View arg1, int arg2, long arg3) {
					int[] location = new int[2];
					v.getLocationOnScreen(location);
					position = arg2;
					// popupWindow.showAsDropDown(arg1);
					// popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,
					// location[0],
					// location[1] - popupWindow.getHeight());
					// popupWindow.showAtLocation(arg1, Gravity.NO_GRAVITY,
					// Gravity.CENTER, Gravity.CENTER);
					int w = arg1.getWidth();
					int h=arg1.getHeight();
					int popHeight=popupWindow.getHeight();
					int xoffset = popWindowsView.getWidth() / 2;
					int xset = w / 2 - xoffset;
					int yset=0-(h+popHeight);
					popupWindow.showAsDropDown(arg1, xset, -300,
							Gravity.CENTER_HORIZONTAL);
					popWindowsView.findViewById(R.id.click_chose_button)
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									popupWindow.dismiss();
									adapter.getItem(position);
									indexListArray.remove(position-1);
									adapter.notifyDataSetChanged();
								}
							});
					return false;
				}
			});
			;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}