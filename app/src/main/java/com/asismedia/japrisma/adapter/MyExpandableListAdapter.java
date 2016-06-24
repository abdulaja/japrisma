package com.asismedia.japrisma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asismedia.japrisma.R;
import com.asismedia.japrisma.model.Tourism;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Abdulaja on 10/08/2015.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Tourism>> _listDataChild;

    private DisplayImageOptions options;
    private ImageLoaderConfiguration config;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    ImageLoader imageLoader = ImageLoader.getInstance();

    public MyExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Tourism>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .build();
        imageLoader.init(config);
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String tourname = ((Tourism) getChild(groupPosition, childPosition)).getName();
        final String toursub = ((Tourism) getChild(groupPosition, childPosition)).getSubarea();
        final String tourarea = ((Tourism) getChild(groupPosition, childPosition)).getArea();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = infalInflater.inflate(R.layout.list_item, null);
            convertView = infalInflater.inflate(R.layout.list_tour, null);
        }

        //TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        RelativeLayout listtour = (RelativeLayout) convertView.findViewById(R.id.listtour);

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        TextView tv_subarea = (TextView) convertView.findViewById(R.id.areatour).findViewById(R.id.tv_subarea);
        TextView tv_area = (TextView) convertView.findViewById(R.id.areatour).findViewById(R.id.tv_area);

        String image = "http://shiro.science/assets/images/tourism/" + ((Tourism) getChild(groupPosition, childPosition)).getImage();
        /*
        try {
            InputStream ims =null;
            ims = _context.getAssets().open("tourism/"+image);
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            thumbnail.setImageDrawable(d);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //return;
        }
        */
        imageLoader.displayImage(image, thumbnail, options, animateFirstListener);

        name.setText(tourname);
        tv_subarea.setText(toursub);
        tv_area.setText(tourarea);
        //txtListChild.setText(childText);
        return convertView;
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
}

