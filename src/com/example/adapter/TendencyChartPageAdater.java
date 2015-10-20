package com.example.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.example.viewpagenest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TendencyChartPageAdater extends PagerAdapter {
	String imageurl[];
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	Holder holder;

	public Holder getHolder() {
		return holder;
	}

	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	public TendencyChartPageAdater(Context context, String imageurl[],
			ImageLoader imageLoader) {
		holder = new Holder();
		this.imageurl = imageurl;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return imageurl.length;

	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
		// super.destroyItem(container, position, object);
	}

	/** 图片加载监听事件 **/
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500); // 设置image隐藏动画500ms
					displayedImages.add(imageUri); // 将图片uri添加到集合中
				}
			}
		}
	}

	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		// TODO Auto-generated method stub
		return view.equals(arg1);
	}

	class Holder {
		String urlOnPage;

		public String getUrlOnPage() {
			return urlOnPage;
		}

		public void setUrlOnPage(String urlOnPage) {
			this.urlOnPage = urlOnPage;
		}

	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = inflater.inflate(R.layout.item_pager_image, view,
				false);
		holder.setUrlOnPage(imageurl[position]);
		imageLayout.setTag(holder);
		assert imageLayout != null;
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(false).resetViewBeforeLoading(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		imageLoader.displayImage(imageurl[position], imageView, options,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub

					}
				});
		view.addView(imageLayout, 0);
		return imageLayout;
	}

}
