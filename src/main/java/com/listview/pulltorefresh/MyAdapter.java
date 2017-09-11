package com.listview.pulltorefresh;

import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	List items;
	Context context;
	View view;
//	BusinessVo bVo;
//	PictureLei pil;
//	DisplayImageOptions options;
//	ImageLoaderConfiguration config;
//	ImageLoader imageLoader;
	public MyAdapter(Context context, List items) {
		this.context = context;
		this.items = items;
		
//		setImg();
//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(config);// 全局初始化此配置
		
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		View view = LayoutInflater.from(context).inflate(
//				R.layout.picturelll_item, null);
//		bVo = (BusinessVo)items.get(position);
//		TextView tv = (TextView) view.findViewById(R.id.picture_new1);
//		tv.setText(bVo.getbName());
//		//TextView tv1 = (TextView) view.findViewById(R.id.picture_new3);
//		//tv1.setText("电话 ： " + bVo.getTel());
//		TextView tv2 = (TextView) view.findViewById(R.id.picture_new3);
//		tv2.setText(bVo.getAddress());
//		
//		TextView tv4 = (TextView) view.findViewById(R.id.picturellll_item_pingjiarenshu);
//		tv4.setText(bVo.getSuijishu());
//		ImageView tv3 = (ImageView) view.findViewById(R.id.picture_image);
//		System.out.println();
//		ImageLoader.getInstance().displayImage(bVo.getImgFile(),tv3 ,options);
		
		
		return view;
	}
	
	//加载图片	
//	@SuppressWarnings("deprecation")
//	public void setImg() {	
//		config = new ImageLoaderConfiguration.Builder(this.context)
//			.memoryCacheExtraOptions(480, 800)
//			// max width, max height，即保存的每个缓存文件的最大长宽
//			.threadPoolSize(3)
//			// 线程池内加载的数量
//			.threadPriority(Thread.NORM_PRIORITY - 2)
//			.denyCacheImageMultipleSizesInMemory()
//			.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
//			// You can pass your own memory cache
//			// implementation/你可以通过自己的内存缓存实现
//			.memoryCacheSize(2 * 1024 * 1024)
//			.discCacheSize(50 * 1024 * 1024)
//			.discCacheFileNameGenerator(new Md5FileNameGenerator())
//			// 将保存的时候的URI名称用MD5 加密
//			.tasksProcessingOrder(QueueProcessingType.LIFO)
//			.discCacheFileCount(100) // 缓存的文件数量
//			// .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
//			.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//			.imageDownloader(
//					new BaseImageDownloader(this.context, 5 * 1000,
//							30 * 1000)) // connectTimeout (5 s), readTimeout
//										// (30 s)超时时间
//			.writeDebugLogs() // Remove for release app
//			.build();// 开始构建
//			options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.logo5) // 设置图片在下载期间显示的图片
//			.showImageForEmptyUri(R.drawable.logo5)// 设置图片Uri为空或是错误的时候显示的图片
//			.showImageOnFail(R.drawable.logo5) // 设置图片加载/解码过程中错误时候显示的图片
//			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
//			.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
//			// .considerExifParams(true) //是否考虑JPEG图像EXIF参数（旋转，翻转）
//			// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
//			// .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//			// .decodingOptions(android.graphics.BitmapFactory.Options
//			// decodingOptions)//设置图片的解码配置
//			// //.delayBeforeLoading(int delayInMillis)//int
//			// delayInMillis为你设置的下载前的延迟时间
//			// //设置图片加入缓存前，对bitmap进行设置
//			// //.preProcessor(BitmapProcessor preProcessor)
//			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
//			.displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
//			.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
//			.build();// 构建完成
//		}

}
