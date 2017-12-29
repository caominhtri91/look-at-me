package com.example.quyenltps04255;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class AdminActivity extends Activity {

	ArrayList<Product> list = new ArrayList<Product>();
	Button btAddNew;
	ListView lv;
	ProductDAO productDAO;
	DBHelper dbHelper;
	ProductAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		lv = (ListView) findViewById(R.id.listView);
		btAddNew = (Button) findViewById(R.id.btAddNew);
		dbHelper = new DBHelper(AdminActivity.this);
		productDAO = new ProductDAO(dbHelper);
		updateList();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Product product = list.get(position);
				Intent i = new Intent(AdminActivity.this, UpdateProductActivity.class);
				Bundle b = new Bundle();
				b.putSerializable("data", product);
				i.putExtras(b);
				startActivityForResult(i, 111);
			}
		});
		
		btAddNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AdminActivity.this, AddProductActivity.class);
				startActivityForResult(i, 222);
			}
		});
		
	}
	
	public void updateList(){
		list = productDAO.getList();
		adapter = new ProductAdapter(AdminActivity.this, list);
		lv.setAdapter(adapter);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		updateList();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
