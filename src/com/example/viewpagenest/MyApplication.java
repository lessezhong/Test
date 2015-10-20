package com.example.viewpagenest;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
	}

	/** ��ʼ��ͼƬ������������Ϣ **/
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)// ����ͼƬ���߳���
				.denyCacheImageMultipleSizesInMemory() // ����ͼ��Ĵ�ߴ罫���ڴ��л�����ǰ����ͼ���С�ߴ硣
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// ���ô��̻����ļ�����
				.tasksProcessingOrder(QueueProcessingType.LIFO)// ���ü�����ʾͼƬ���н���
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
