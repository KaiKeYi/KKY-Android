package xyz.yikai.kky.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.io.File;
import xyz.yikai.kky.config.PathConfig;

/**
 * @Author: Jason
 * @Time: 2018/4/24 20:33
 * @Description:图片工具
 */
public class ImageDisplayOptions {

	public static DisplayImageOptions imageOptions(int imageRes) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(imageRes) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(imageRes)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(imageRes) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
				// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// .delayBeforeLoading(100)//int delayInMillis为你设置的下载前的延迟时间设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.considerExifParams(true)
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();// 构建完成

		return options;
	}
	public static DisplayImageOptions imageRoundOptions(int imageRes) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(imageRes) // 设置图片在下载期间显示的图片
		.showImageForEmptyUri(imageRes)// 设置图片Uri为空或是错误的时候显示的图片
//		.showImageOnFail(imageRes) // 设置图片加载/解码过程中错误时候显示的图片
		.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
		.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
		// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
		// 设置图片以如何的编码方式显示
		.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
		// .decodingOptions(android.graphics.BitmapFactory.Options
		// decodingOptions)//设置图片的解码配置
		// .delayBeforeLoading(100)//int delayInMillis为你设置的下载前的延迟时间设置图片加入缓存前，对bitmap进行设置
		// .preProcessor(BitmapProcessor preProcessor)
		// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
		.considerExifParams(true)
		 .displayer(new RoundedBitmapDisplayer(10))
		.build();// 构建完成
		
		return options;
	}

	public static DisplayImageOptions imageRoundedOptions(int imageRes) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(imageRes) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(imageRes)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(imageRes) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
				// .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
				// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// .delayBeforeLoading(100)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();// 构建完成

		return options;
	}

	public static ImageLoaderConfiguration imageConfig(Context context) {
		File cacheDir = PathConfig.getImageFile();

		Builder builder = new ImageLoaderConfiguration.Builder(context)
				// default = device screen dimensions
				.memoryCacheExtraOptions(400, 300)
				.diskCacheExtraOptions(480, 800, null) 
				// 线程池内加载的数量
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(4 * 1024 * 1024))
				.memoryCacheSize(8 * 1024 * 1024)
				// 缓存的文件数量
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 自定义缓存路径
//				.diskCache(new UnlimitedDiskCache(cacheDir)).diskCacheSize(30 * 1024 * 1024)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)); // connectTimeout

		// if (BuildConfig.DEBUG) {
		// builder.writeDebugLogs();
		// }
		ImageLoaderConfiguration config = builder.build();
		return config;
	}

	public static void changeMetrics(Context context) {
		String model = android.os.Build.MODEL;
		Log.e("MODEL", "" + model);

		if (!"X1 7.0".equals(model)) {
			return;
		}

		DisplayMetrics curMetrics = context.getResources().getDisplayMetrics();

		DisplayMetrics metrics = new DisplayMetrics();
		metrics.scaledDensity = 2f;
		metrics.density = 2f;
		metrics.densityDpi = 320;
		metrics.xdpi = 320;
		metrics.ydpi = 320;
		metrics.heightPixels = curMetrics.heightPixels;
		metrics.widthPixels = curMetrics.widthPixels;
		context.getResources().getDisplayMetrics().setTo(metrics);
	}

}
