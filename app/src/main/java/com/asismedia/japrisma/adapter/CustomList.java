package com.asismedia.japrisma.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asismedia.japrisma.R;

public class CustomList extends ArrayAdapter<String> {
	 
	private final Activity context;
	private final String[] menu;
	private final Integer[] icId;
	
	public CustomList(Activity context,String[] menu, Integer[] icId) {
		super(context, R.layout.list_single, menu);
		this.context = context;
		this.menu = menu;
		this.icId = icId;
	 
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_menu);
		 
		ImageView imageView = (ImageView) rowView.findViewById(R.id.ic_menu);
		txtTitle.setText(menu[position]);
		 
		imageView.setImageResource(icId[position]);
		return rowView;
	}

}
