package com.example.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.viewpagenest.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IHaveChoseListViewAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context;
	JSONArray code;

	/**
	 * ���캯��
	 * 
	 * @param Context
	 * @param String
	 *            code[]
	 */
	public IHaveChoseListViewAdapter(Context context, JSONArray code) {
		this.context = context;
		this.code = code;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return code.length();
	}

	/*
	 * return Jason object
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		try {
			return code.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.choseyourselfitem, null);// ������inflate�Ĳ����ļ�
		}
		try {
			freshUI(convertView, code.getJSONObject(position));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	public void freshUI(View view, JSONObject stockMarket) {
		try {
			String S = stockMarket.getString("name");// ��Ʊ����
			TextView text = (TextView) view.findViewById(R.id.name);// item
			text.setText(S);
			text = (TextView) view.findViewById(R.id.yestodayClosePrices);//
			S = stockMarket.getString("closePrice");// �������̼�
			float closePrice = Float.parseFloat(S);
			text.setText("����" + S);
			S = stockMarket.getString("nowPrice");// ��ǰ�ɼ�
			float nowprice = Float.parseFloat(S);
			text = (TextView) view.findViewById(R.id.nowPrice);
			text.setText("��ǰ" + S);
			// �Ƿ�
			text = (TextView) view.findViewById(R.id.increasementbyPer);
			float per = (nowprice - closePrice) / closePrice * 100;
			if (per > 0) {
				text.setTextColor(Color.RED);
			} else {
				text.setTextColor(Color.BLUE);
			}
			text.setText(" " + per + "%");
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
}
