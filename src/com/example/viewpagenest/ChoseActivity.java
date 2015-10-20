package com.example.viewpagenest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Fragment.Fragment4;
import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.MyListViewAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

public class ChoseActivity extends FragmentActivity {
	AutoCompleteTextView myAutoCompleteTextView;
	MyListViewAdapter listViewAdapter;
	ListView listView;
	List<HashMap<String, String>> listStrings;
	AsyncHttpResponseHandler asyncHttpResponseHandler;
	SearchView serachView;
	EditText editView;
	ImageButton imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.choseactivity);
		ActionBar bar = getActionBar();
		Resources resources = getResources();
		bar.setBackgroundDrawable(resources
				.getDrawable(R.drawable.ic_item_selected_bg));
		serachView = (SearchView) findViewById(R.id.action_search1);
		editView = (EditText) findViewById(R.id.chaxungupiao);
		imageButton = (ImageButton) findViewById(R.id.serchbutton);
		imageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = editView.getText().toString();
				new ShowApiRequest("http://route.showapi.com/131-43", "1010",
						"9f86d6b5eb7e4768afb4fbd0b089e112")
						.setResponseHandler(asyncHttpResponseHandler)
						.addTextPara("name", name).post();

			}
		});
		/*
		 * myAutoCompleteTextView = (AutoCompleteTextView)
		 * findViewById(R.id.chaxungupiao);
		 */asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String res = null;
				JSONObject jsonObject;
				JSONArray list = null;
				try {
					res = new String(arg2, "utf-8");
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					jsonObject = new JSONObject(res);
					list = jsonObject.getJSONObject("showapi_res_body")
							.getJSONArray("list");
					listStrings = new ArrayList<HashMap<String, String>>();
					if (list.length() > 0) {
						for (int i = 0; i < list.length(); i++) {
							try {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("name", list.getJSONObject(i)
										.getString("name"));
								map.put("code", list.getJSONObject(i)
										.getString("code"));
								map.put("market", list.getJSONObject(i)
										.getString("market"));
								listStrings.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						UpdateUI(listStrings);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Log.e("hai", "error download");
			}
		};
		/*
		 * myAutoCompleteTextView.addTextChangedListener(new MyTextWatcher(
		 * asyncHttpResponseHandler));
		 */
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.choseactivity, menu);
		setIconEnable(menu, true); // 调用这句实现显示ICON
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		setTitleColor(Color.RED);
		switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(this, "you touch the home", Toast.LENGTH_SHORT)
					.show();
			finish();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			Method m1 = clazz.getMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);
			m1.setAccessible(true);
			// MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
			m.invoke(menu, enable);
			// m1.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class MyTextWatcher implements TextWatcher {
		boolean sflag = false;
		boolean l = false;
		AsyncHttpResponseHandler asyncHttpResponseHandler;

		public MyTextWatcher(AsyncHttpResponseHandler asyncHttpResponseHandler) {
			this.asyncHttpResponseHandler = asyncHttpResponseHandler;
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s.toString().length() > 1) {
				l = true;
			} else {
				l = false;
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			if (count > 1) {
				sflag = true;
			} else {
				sflag = false;
			}
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.toString().length() > 1) {
				l = true;
			} else {
				l = false;
			}
			if (l) {
				/*
				 * new ShowApiRequest("http://route.showapi.com/131-43", "1010",
				 * "9f86d6b5eb7e4768afb4fbd0b089e112")
				 * .setResponseHandler(asyncHttpResponseHandler)
				 * .addTextPara("name", s.toString()).post();
				 */
			}
		}
	}

	final void UpdateUI(List<HashMap<String, String>> list) {
		listViewAdapter = new MyListViewAdapter(getApplicationContext(), list);
		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(listViewAdapter);
		listView.setVisibility(0);
		listViewAdapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap<String, String> listMap = new HashMap<String, String>();
				listMap = (HashMap<String, String>) arg0
						.getItemAtPosition(arg2);
				listView.setVisibility(8);
				editView.getText().clear();
				Fragment4 fragment4 = new Fragment4(listMap.get("code"));
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.zx, fragment4,
						listMap.get("market"));
				// fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});

	}
}
