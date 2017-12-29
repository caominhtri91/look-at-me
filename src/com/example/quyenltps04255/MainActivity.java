package com.example.quyenltps04255;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText edUsername, edPassword;
	CheckBox ckRemember;
	Button btLogin, btCancel;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        ckRemember = (CheckBox) findViewById(R.id.checkBox1);
        btLogin = (Button) findViewById(R.id.btLogin);
        btCancel = (Button) findViewById(R.id.btCancel);
        
        SharedPreferences pref = getSharedPreferences("loginInfo", MODE_PRIVATE);
        if(pref != null){
        	edUsername.setText(pref.getString("username", ""));
        	edPassword.setText(pref.getString("password", ""));
        	ckRemember.setChecked(pref.getBoolean("check", false));
        }
        
        btLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = edUsername.getText().toString().trim();
				String password = edPassword.getText().toString().trim();
				
				if(username.isEmpty()){
					Toast.makeText(MainActivity.this, "Vui long nhap Username!", Toast.LENGTH_SHORT).show();
					//edUsername.setError("Vui long nhap Username!");
				}else{
					if(password.isEmpty()){
						Toast.makeText(MainActivity.this, "Vui long nhap Password!", Toast.LENGTH_SHORT).show();
						//edPassword.setError("Vui long nhap Password!");
					}else{
						if(username.equals("fpoly") && password.equals("poly123")){
							Toast.makeText(MainActivity.this, "Dang nhap thanh cong!", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(MainActivity.this, AdminActivity.class));
							if(ckRemember.isChecked()){
								SharedPreferences.Editor pref = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
								pref.putString("username", username);
								pref.putString("password", password);
								pref.putBoolean("check", ckRemember.isChecked());
								pref.commit();
							}else{
								SharedPreferences.Editor pref = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
								pref.clear();
								pref.commit();
							}
						}else{
							Toast.makeText(MainActivity.this, "Sai Username hoac Password!", Toast.LENGTH_SHORT).show();
						}
					}
				}
				
			}
		});
        
    }
}
