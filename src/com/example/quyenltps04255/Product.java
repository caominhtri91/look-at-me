package com.example.quyenltps04255;

import java.io.Serializable;

import org.apache.http.entity.SerializableEntity;

public class Product implements Serializable{
	
	public String productId, productName, productImage, date;
	public float productPrice;
	
	public Product(String productName, String productImage,
			String date, float productPrice) {
		this.productName = productName;
		this.productImage = productImage;
		this.date = date;
		this.productPrice = productPrice;
	} 
	
	public Product(String productId, String productName, String productImage,
			String date, float productPrice) {
		this.productId = productId;
		this.productName = productName;
		this.productImage = productImage;
		this.date = date;
		this.productPrice = productPrice;
	} 
	
	

}
