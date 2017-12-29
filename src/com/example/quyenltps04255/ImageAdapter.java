package com.example.quyenltps04255;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quyenltps04255.ProductAdapter.OneRow;

public class ImageAdapter extends BaseAdapter {
	
	ArrayList<String> list;
	Context context;
	
	public ImageAdapter(Context context, ArrayList<String> list){
		this.list = list;
		this.context = context;
	}
	
	public static class OneRow{
		public ImageView ivProduct;
		public TextView tvName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		OneRow onerow;
		
		if(convertView == null){
			onerow = new OneRow();
			LayoutInflater inf = LayoutInflater.from(context);
			convertView = inf.inflate(R.layout.one_image, null);
			onerow.ivProduct = (ImageView) convertView.findViewById(R.id.ivImageName);
			onerow.tvName = (TextView) convertView.findViewById(R.id.tvImageName);
			//onerow.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			
			convertView.setTag(onerow);
		}else{
			onerow = (OneRow) convertView.getTag();
		}
		
		//int imageName = Integer.parseInt(list.get(position).productImage);
		//onerow.ivProduct.setImageDrawable(context.getResources().getDrawable(imageName));
		//Drawable drawable = context.getResources().getDrawable(context.getResources()
        //        .getIdentifier(list.get(position).productImage, "drawable", context.getPackageName()));
		Drawable drawable = context.getResources().getDrawable(context.getResources()
		                .getIdentifier(list.get(position), "drawable", context.getPackageName()));
		onerow.ivProduct.setImageDrawable(drawable);
		onerow.tvName.setText(list.get(position).substring(list.get(position).indexOf("/")+1));
		//onerow.tvPrice.append(list.get(position).productPrice+"");
		
		return convertView;
	}

}
