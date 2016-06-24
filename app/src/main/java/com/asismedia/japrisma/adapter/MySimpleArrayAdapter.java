package com.asismedia.japrisma.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asismedia.japrisma.R;
import com.asismedia.japrisma.TourismDetail;
import com.asismedia.japrisma.model.Tourism;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class MySimpleArrayAdapter extends ArrayAdapter<Tourism> implements Filterable{
    private final Context context;
    private Activity activity;
    private Filter tourismFilter;
    private List<Tourism> tourism;
    private List<Tourism> tourismorig;
    private DisplayImageOptions options;
    private ImageLoaderConfiguration config;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    ImageLoader imageLoader = ImageLoader.getInstance();

    public MySimpleArrayAdapter(Activity activity, List<Tourism> tourism) {
        super(activity.getBaseContext(), R.layout.list_tour, tourism);

        this.activity = activity;
        this.context = activity.getBaseContext();
        this.tourism = tourism;
        //this.tourismorig = new ArrayList<Tourism>(tourism);
        this.tourismorig = tourism;
    }

    @Override
    public int getCount() {
        return tourism.size();
    }

    @Override
    public Tourism getItem(int position) {
        return tourism.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tourism.get(position).hashCode();
    }

    private class ViewHolder {
        ImageView thumbnail;
        RelativeLayout listtour;
        TextView name, tv_subarea, tv_area;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*
        View rowView = inflater.inflate(R.layout.list_tour, parent, false);

        TextView tourname = (TextView) rowView.findViewById(R.id.name);
        //TextView area = (TextView) rowView.findViewById(R.id.tv_area);

        ((TextView) rowView.findViewById(R.id.areatour).findViewById(R.id.tv_subarea)).setText(tourism.get(position).getSubarea());
        ((TextView) rowView.findViewById(R.id.areatour).findViewById(R.id.tv_area)).setText(tourism.get(position).getArea());

        // Setting the text to display
        tourname.setText(tourism.get(position).getName());
        //area.setText(tourism.get(position).getArea());

        return rowView;
        */
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        config = new ImageLoaderConfiguration.Builder(getContext())
                .defaultDisplayImageOptions(options)
                .build();
        imageLoader.init(config);


        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_tour, null);
            holder.listtour = (RelativeLayout) convertView.findViewById(R.id.listtour);

            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.name = (TextView) convertView.findViewById(R.id.name);

            holder.tv_subarea = (TextView) convertView.findViewById(R.id.areatour).findViewById(R.id.tv_subarea);
            holder.tv_area = (TextView) convertView.findViewById(R.id.areatour).findViewById(R.id.tv_area);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String image = "http://shiro.science/assets/images/tourism/" + tourism.get(position).getImage();
        /*
        try {
            InputStream ims =null;
            ims = context.getAssets().open("tourism/"+image);
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            holder.thumbnail.setImageDrawable(d);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //return;
        }
        */

        imageLoader.displayImage(image, holder.thumbnail, options, animateFirstListener);

        holder.name.setText(tourism.get(position).getName());

        holder.tv_subarea.setText(tourism.get(position).getSubarea());
        holder.tv_area.setText(tourism.get(position).getArea());

        holder.listtour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, tourism.get(position).getName(), Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("tourname", tourism.get(position).getName());
                bundle.putString("tourarea", tourism.get(position).getArea());
                bundle.putString("subarea", tourism.get(position).getSubarea());
                bundle.putString("indescript", tourism.get(position).getIndescript());
                bundle.putString("ininfo", tourism.get(position).getIninfo());
                bundle.putString("endescript", tourism.get(position).getEndescript());
                bundle.putString("eninfo", tourism.get(position).getEninfo());
                bundle.putString("image", tourism.get(position).getImage());

                Fragment newFragment = new TourismDetail();
                newFragment.setArguments(bundle);
                FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_out_left, R.animator.slide_in_right)
                        .replace(R.id.frame_container, newFragment).addToBackStack(null).commit();


            }
        });


        return convertView;

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

    public void resetData() {
        tourism = tourismorig;
    }

    /*
    public void filter(String tourKeyword){
        tourKeyword = tourKeyword.toLowerCase();
        tourism.clear();

        if (tourKeyword.length() == 0) {
            tourism.addAll(tourismorig);
        } else {
            for (int i = 0; i < tourism.size(); i++) {
                String tourname = tourism.get(i).getName();
                String toursub = tourism.get(i).getSubarea();
                String tourarea = tourism.get(i).getArea();

                if (tourname.toLowerCase().contains(tourKeyword) ||
                        toursub.toLowerCase().contains(tourKeyword) ||
                        tourarea.toLowerCase().contains(tourKeyword) ) {
                    tourism.add(tourism.get(i));
                }
            }
        }

        notifyDataSetChanged();
    }
    */

    @Override
    public Filter getFilter() {

        if (tourismFilter == null)
            tourismFilter = new TourismFilter();

        return tourismFilter;
    }

    private class TourismFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            //List<Tourism> nTourismList = new ArrayList<Tourism>();

                /*
                if (tourismorig == null) {
                    tourismorig = new ArrayList<Tourism>(tourism); // saves the original data in mOriginalValues
                }
                */

            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = tourismorig;
                results.count = tourismorig.size();

            } else {
                // We perform filtering operation
                List<Tourism> nTourismList = new ArrayList<Tourism>();
                constraint = constraint.toString().toLowerCase();

                for (Tourism t : tourism) {
                    if (t.getName().toString().toLowerCase().contains(constraint) ||
                            t.getSubarea().toString().toLowerCase().contains(constraint) ||
                            t.getArea().toString().toLowerCase().contains(constraint))
                        nTourismList.add(t);
                }


                results.values = nTourismList;
                results.count = nTourismList.size();

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {

            //tourism = (ArrayList<Tourism>) results.values; // has the filtered values
            //notifyDataSetChanged();  // notifies the data with new filtered values

            if(results.count == 0){
                notifyDataSetInvalidated();

            } else {

                tourism =(List<Tourism>) results.values;
                notifyDataSetChanged();
            }

        }

    }


}
