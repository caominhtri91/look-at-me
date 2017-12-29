package com.example.quyenltps04255;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UpdateProductActivity extends Activity {

	EditText edUpdateId, edUpdateName, edUpdatePrice, edUpdateImage, edUpdateDate;
	Button btUpdateImage, btUpdateDate, btUpdate, btDelete, btUpdateCancel;
	Calendar cal;
    Date date;
    int imagepos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_product);
		edUpdateId = (EditText) findViewById(R.id.edUpdateId);
		edUpdateName = (EditText) findViewById(R.id.edUpdateName);
		edUpdatePrice = (EditText) findViewById(R.id.edUpdatePrice);
		edUpdateImage = (EditText) findViewById(R.id.edUpdateImage);
		edUpdateDate = (EditText) findViewById(R.id.edUpdateDate);
		btUpdateImage = (Button) findViewById(R.id.btUpdateImage);
		btUpdateDate = (Button) findViewById(R.id.btUpdateDate);
		btUpdate = (Button) findViewById(R.id.btUpdate);
		btDelete = (Button) findViewById(R.id.btDelete);
		btUpdateCancel = (Button) findViewById(R.id.btUpdateCancel);
		
		btUpdateImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Field[] drawablesFields = com.example.quyenltps04255.R.drawable.class.getFields();
				final ArrayList<String> drawables = new ArrayList<String>();
				final R.drawable drawableResources = new R.drawable();
				for (Field field : drawablesFields) {
				    try {
				    	int resourceId = field.getInt(drawableResources);
				    	String imageName = getResources().getResourceName(resourceId);
				        drawables.add(imageName);
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				}
				
				final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UpdateProductActivity.this);
				// ...Irrelevant code for customizing the buttons and title
				LayoutInflater inflater = UpdateProductActivity.this.getLayoutInflater();
				View dialogView = inflater.inflate(R.layout.image_dialog, null);
				dialogBuilder.setView(dialogView);

				ListView imagelv = (ListView) dialogView.findViewById(R.id.imageListView);
				ImageAdapter adapter = new ImageAdapter(UpdateProductActivity.this, drawables);
				imagelv.setAdapter(adapter);
				imagelv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						view.setBackgroundColor(Color.parseColor("#c9edff"));
						imagepos = position;
						
						
					}
				});
				
				dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						edUpdateImage.setText(drawables.get(imagepos).substring(drawables.get(imagepos).indexOf("/")+1));
						dialog.dismiss();
					}
				});
				
				dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						dialog.dismiss();
					}
				});
				AlertDialog alertDialog = dialogBuilder.create();
				alertDialog.show();
				
			}
		});
		
		cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        edUpdateDate.setText(strDate);
		
        edUpdateDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {

	                @Override
	                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
	                	edUpdateDate.setText(day + "/" + (month +1) + "/" + year);
	                    cal.set(year, month, day);
	                    date = cal.getTime();
	                }
	            };
	            String s=edUpdateDate.getText()+"";
	            String strArrtmp[]=s.split("/");
	            int ngay=Integer.parseInt(strArrtmp[0]);
	            int thang=Integer.parseInt(strArrtmp[1]) - 1;
	            int nam=Integer.parseInt(strArrtmp[2]);
	            DatePickerDialog pic=new DatePickerDialog(
	                    UpdateProductActivity.this,
	                    callback, nam, thang, ngay);
	            pic.setTitle("Chon ngay");
	            pic.show();
				
			}
		});
		
		Product product = (Product)getIntent().getExtras().getSerializable("data");
		if(product != null){
			edUpdateId.setText(product.productId);
			edUpdateName.setText(product.productName);
			edUpdatePrice.setText(product.productPrice+"");
			edUpdateImage.setText(product.productImage);
			edUpdateDate.setText(product.date);
		}
		
		btDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DBHelper dbHelper = new DBHelper(UpdateProductActivity.this);
				ProductDAO productDAO = new ProductDAO(dbHelper);
				productDAO.deleteProduct(edUpdateId.getText().toString());
				finish();
				
			}
		});
		
		btUpdateCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		btUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = edUpdateId.getText().toString().trim();
				String name = edUpdateName.getText().toString().trim();
				String price = edUpdatePrice.getText().toString().trim();
				String image = edUpdateImage.getText().toString().trim();
				String date = edUpdateDate.getText().toString().trim();
				
				if(id.isEmpty()){
					Toast.makeText(UpdateProductActivity.this, "Vui long nhap Ma san pham!", Toast.LENGTH_SHORT).show();
				}else{
					if(id.length()>6){
						Toast.makeText(UpdateProductActivity.this, "Ma san pham chi gom 6 ky tu!", Toast.LENGTH_SHORT).show();
					}else{
						if(!id.startsWith("SP")){
							Toast.makeText(UpdateProductActivity.this, "Ma san pham phai bat dau tu SP va 4 gia tri so!", Toast.LENGTH_SHORT).show();
						}else{
							String maSp = id.substring(2);
							if(!isValidInteger(maSp)){
								Toast.makeText(UpdateProductActivity.this, "Ma san pham phai bat dau tu SP va 4 gia tri so!", Toast.LENGTH_SHORT).show();
							}else{
								if(name.isEmpty()){
									Toast.makeText(UpdateProductActivity.this, "Vui long nhap Ten san pham!", Toast.LENGTH_SHORT).show();
								}else{
									if(getSpecialCharacterCount(name)){
										if(price.isEmpty()){
											Toast.makeText(UpdateProductActivity.this, "Vui long nhap Gia san pham!", Toast.LENGTH_SHORT).show();
										}else{
											float giaSp = Float.parseFloat(price);
											if(giaSp < 200 || giaSp > 800){
												Toast.makeText(UpdateProductActivity.this, "Gia san pham chi trong khoang tu 200 - 800!", Toast.LENGTH_SHORT).show();
											}else{
												if(image.isEmpty()){
													Toast.makeText(UpdateProductActivity.this, "Vui long chon hinh!", Toast.LENGTH_SHORT).show();
												}else{
													try {
														Drawable drawable = getResources().getDrawable(getResources()
												                .getIdentifier(image, "drawable", getPackageName()));
														if(drawable==null){
															Toast.makeText(UpdateProductActivity.this, "Hinh anh khong ton tai!", Toast.LENGTH_SHORT).show();
														}else{
															if(date.isEmpty()){
																Toast.makeText(UpdateProductActivity.this, "Vui long vhon ngay dang!", Toast.LENGTH_SHORT).show();
															}else{
																SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
																Date strDate;
																try {
																	strDate = sdf.parse(date);
																	if (new Date().before(strDate)) {
																		Toast.makeText(UpdateProductActivity.this, "Ngay dang khong duoc lon hon ngay hien tai!", Toast.LENGTH_SHORT).show();
																	}else{
																		float gia = Float.parseFloat(price);
																		Product product = new Product(id, name, image, date, gia);
																		DBHelper dbHelper = new DBHelper(UpdateProductActivity.this);
																		ProductDAO productDAO = new ProductDAO(dbHelper);
																		productDAO.updateProduct(product);
																		finish();
																	}
																} catch (ParseException e) {
																	Toast.makeText(UpdateProductActivity.this, "Hinh anh khong ton tai!", Toast.LENGTH_SHORT).show();
																	e.printStackTrace();
																}
																
															}
														}
													} catch (Exception e) {
														// TODO: handle exception
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
		});
		
	}
	
	public static Boolean isValidInteger(String value) {
	    try {
	        Integer val = Integer.valueOf(value);
	        if (val != null)
	            return true;
	        else
	            return false;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public Boolean getSpecialCharacterCount(String s) {
	     Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
	     Matcher m = p.matcher(s);
	    // boolean b = m.matches();
	     boolean b = m.find();
	     if (b == true){
	    	 Toast.makeText(UpdateProductActivity.this, "Ten san pham khong duoc chua ky tu dac biet!", Toast.LENGTH_SHORT).show();
	        return false;
	     }else{
	         return true;
	     }
	 }
}
