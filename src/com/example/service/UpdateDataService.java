package com.example.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class UpdateDataService extends Service {
	Context context;
	public UpdateDataService(Context context) {
		this.context=context;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return super.onStartCommand(intent, flags, startId);
	}

//	public String getGuPiaoDate(String GuPiaoCode) {
//		String res = new ShowApiRequest("http://route.showapi.com/131-44",
//				"1010", "9f86d6b5eb7e4768afb4fbd0b089e112").addTextPara("code",
//				"GuPiaoCode").post();
//		try {
//			JSONArray jsonArray=new JSONObject(res).getJSONArray("indexList");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return res;
//	}
	
}
