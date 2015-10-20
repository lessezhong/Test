package com.example.adapter;

import com.example.viewpagenest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyChoseItemListViewAdaptor extends BaseAdapter {
	String code[];
	LayoutInflater inflater;
	Context context;

	MyChoseItemListViewAdaptor(Context context, String s[]) {
		code = s;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return code.length;
	}

	@Override
	public Object getItem(int arg0) {

		return code[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_view_item, null);
		}

		return null;
	}

}
