package com.asismedia.japrisma;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asismedia.japrisma.ext.ScaleImage;
import com.asismedia.japrisma.ext.ZoomImage;
import com.asismedia.japrisma.model.Tourism;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TourismDetail extends Fragment {

	Activity context;

	private DisplayImageOptions options;
	private ImageLoaderConfiguration config;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();

	TextView txt_descript;

	ImageView tourimage;
	
	public TourismDetail(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
		context = getActivity();

		Bundle bundle = this.getArguments();
		String tourname = bundle.getString("tourname");
		String tourarea = bundle.getString("tourarea");
		String subarea = bundle.getString("subarea");
		String indes = bundle.getString("indescript");
		String ininfo = bundle.getString("ininfo");
		String endes = bundle.getString("endescript");
		String eninfo = bundle.getString("eninfo");
		String image = bundle.getString("image");
		final String bitmap = "http://shiro.science/assets/images/tourism/" + image;
		//Toast.makeText(getActivity(), "position:" + indes, Toast.LENGTH_SHORT).show();
 
        View rootView = inflater.inflate(R.layout.tourism_detail, container, false);

		tourimage = (ImageView) rootView.findViewById(R.id.tourimage);
		//((ImageView) rootView.findViewById(R.id.tourimage));
		//tourimage.setImageBitmap(getImage());


		((TextView) rootView.findViewById(R.id.tourname)).setText(tourname);

		((TextView) rootView.findViewById(R.id.areadata).findViewById(R.id.tv_subarea)).setText(subarea);
		((TextView) rootView.findViewById(R.id.areadata).findViewById(R.id.tv_area)).setText(tourarea);

		((TextView) rootView.findViewById(R.id.deskripsi).findViewById(R.id.title).findViewById(R.id.tv_title)).setText("Deskripsi");
		((TextView) rootView.findViewById(R.id.informasi).findViewById(R.id.title).findViewById(R.id.tv_title)).setText("Informasi");
		((TextView) rootView.findViewById(R.id.description).findViewById(R.id.title).findViewById(R.id.tv_title)).setText("Description");
		((TextView) rootView.findViewById(R.id.information).findViewById(R.id.title).findViewById(R.id.tv_title)).setText("Information");

		ExpandableTextView expTv1 = (ExpandableTextView) rootView.findViewById(R.id.deskripsi)
				.findViewById(R.id.expand_text_view);
		ExpandableTextView expTv2 = (ExpandableTextView) rootView.findViewById(R.id.informasi)
				.findViewById(R.id.expand_text_view);
		ExpandableTextView expTv3 = (ExpandableTextView) rootView.findViewById(R.id.description)
				.findViewById(R.id.expand_text_view);
		ExpandableTextView expTv4 = (ExpandableTextView) rootView.findViewById(R.id.information)
				.findViewById(R.id.expand_text_view);


		//expTv1.setText(getString(R.string.cdeskripsi));
		expTv1.setText(indes);
		expTv2.setText(ininfo);
		expTv3.setText(endes);
		expTv4.setText(eninfo);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.no_image)
				.showImageOnFail(R.drawable.no_image)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.build();
		config = new ImageLoaderConfiguration.Builder(getActivity())
				.defaultDisplayImageOptions(options)
				.build();
		imageLoader.init(config);

		imageLoader.displayImage(bitmap, tourimage, options, animateFirstListener);


		tourimage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), bitmap, Toast.LENGTH_SHORT).show();
				//Bundle imbundle = new Bundle();
				//imbundle.putString("bitmap", bitmap);

				Intent intent = new Intent(context, ZoomImage.class);
				intent.putExtra("bitmap", bitmap);
				startActivity(intent);
				// Create new fragment and transaction
				/*
				Fragment newFragment = new ScaleImage();
				newFragment.setArguments(imbundle);
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().setCustomAnimations(R.animator.zoom_in, R.animator.fade_out, R.animator.fade_in, R.animator.zoom_out)
						.add(R.id.frame_container, newFragment).addToBackStack(null).commit();
				*/
			}
		});


		//loadImagesFromAsset();
		AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("ADF0EA0DA64D412D5B3EE242FC0A0F01")
				.addTestDevice("AA0A433677481AA864239179C3205E1E")
				.build();
		mAdView.loadAd(adRequest);
         
        return rootView;
    }


	public void loadImagesFromAsset() {

		Bundle bundle = this.getArguments();
		String image = bundle.getString("image");

		try {
			InputStream ims =null;
			ims = getActivity().getAssets().open("tourism/"+image);
			Drawable d = Drawable.createFromStream(ims, null);
			// set image to ImageView
			tourimage.setImageDrawable(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return;
		}
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public Bitmap loadBitmap(Context context, String image){
		Bitmap b = null;
		FileInputStream fis;

		Bundle bundle = this.getArguments();
		image = bundle.getString("image");

		try {
			fis = context.openFileInput(image);
			b = BitmapFactory.decodeStream(fis);
			fis.close();

		}
		catch (FileNotFoundException e) {
			Log.d("File Status", "file not found");
			e.printStackTrace();
		}
		catch (IOException e) {
			Log.d("IO E Stat", "io exception");
			e.printStackTrace();
		}
		return b;
	}

	public Bitmap getImage() {

		Bitmap imageTour = null;
		Context context = null;

		Bundle bundle = this.getArguments();
		String image = bundle.getString("image");

		// look in internal storage
		if (imageTour == null) {
			try {
				File filePath = context.getFileStreamPath(image);
				FileInputStream fi = new FileInputStream(filePath);
				imageTour = BitmapFactory.decodeStream(fi);
			} catch (FileNotFoundException ex) {
				Log.d("File Status : ", "file not found");
				ex.printStackTrace();
			} catch (IOException ex) {
				Log.e("getThumbnail() on internal storage", ex.getMessage());
				ex.printStackTrace();
			}
		}
		return imageTour;
	}

	
	public void onBackPressed() {
	    FragmentManager fm = getActivity().getFragmentManager();
	    fm.popBackStack();
	}


}

