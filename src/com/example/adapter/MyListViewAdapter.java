package com.example.adapter;

import java.util.HashMap;
import java.util.List;

import com.example.viewpagenest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context;
	List<HashMap<String, String>> lists;

	/**
	 * 构造函数
	 * @param Context
	 * @param List<HashMap<String, String>>
	 */
	public MyListViewAdapter(Context context,
			List<HashMap<String, String>> lists) {
		this.context = context;
		this.lists = lists;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.group, null);// 适配器inflate的布局文件
		TextView text = (TextView) convertView.findViewById(R.id.groupTo);// item
																			// 控件
		text.setText(lists.get(position).get("name"));// 设置item控件的值
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
