package com.example.quyenltps04255;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProductDAO {
	DBHelper dbHelper;
	
	public ProductDAO(DBHelper dbHelper){
		this.dbHelper = dbHelper;
	}
	
	public ArrayList<Product> getList(){
		ArrayList<Product> list = new ArrayList<Product>();
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from product", null);
		if(c.moveToFirst()){
			do{
				String id = c.getString(c.getColumnIndex("id"));
				String name = c.getString(c.getColumnIndex("name"));
				Float price = c.getFloat(c.getColumnIndex("price"));
				String image = c.getString(c.getColumnIndex("image"));
				String date = c.getString(c.getColumnIndex("date"));
				Product product = new Product(id, name, image, date, price);
				list.add(product);
			}while(c.moveToNext());
		}
		
		return list;
	}
	
	public void insertProduct(Product product){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", product.productId);
		values.put("name", product.productName);
		values.put("price", product.productPrice);
		values.put("image", product.productImage);
		values.put("date", product.date);
		db.insert("product", null, values);
	}
	
	public void updateProduct(Product product){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", product.productName);
		values.put("price", product.productPrice);
		values.put("image", product.productImage);
		values.put("date", product.date);
		db.update("product", values, "id = ?", new String[]{product.productId});
	}
	
	public void deleteProduct(String id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("product", "id = ?", new String[]{id});
	}

}
