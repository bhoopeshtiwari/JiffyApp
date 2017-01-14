package com.jiffy.jiffyapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jiffy.jiffyapp.R;
import com.jiffy.jiffyapp.constant.ApplicationConstant;
/**
 * Created by Bhoopesh.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "LoginActivity";
    private LinearLayout llLogin;
    private EditText etUserName;
    private EditText etPassword;
    private AppCompatButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }
    /**
     * Method to find the Views in the layout
     */
    private void findViews() {
        llLogin = (LinearLayout) findViewById(R.id.llLogin);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        ApplicationConstant.hideKeyboard(LoginActivity.this, llLogin);
        checkContactPermission();
    }



    /****Method to check Contact permission and show rotational for android M and above.****/
    private void checkContactPermission() {
        if ((int) Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    10);
        }
    }

    /**
     * Handle button click events
     */
    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            isValidate(v);
        }
    }

    /**
     *Method to validate credentials and  proceed to tab activity
     */
    private void isValidate(View v) {
        String strUserName="";
        String strPassword="";
        ApplicationConstant.hideKeyboard(LoginActivity.this,v);
        strUserName=etUserName.getText().toString();
        strPassword=etPassword.getText().toString();
        if (ApplicationConstant.isBlank(strUserName)){
            ApplicationConstant.showSnckbar(v,getString(R.string.username_blank));
        }else if (!ApplicationConstant.LOGIN_CREDENTIALS.equals(strUserName)){
            ApplicationConstant.showSnckbar(v,getString(R.string.invalid_username));
        }else if (ApplicationConstant.isBlank(strPassword)){
            ApplicationConstant.showSnckbar(v,getString(R.string.password_blank));
        }else if (!ApplicationConstant.LOGIN_CREDENTIALS.equals(strPassword)){
            ApplicationConstant.showSnckbar(v,getString(R.string.invalid_password));
        }else {
            //network check before proceed to home
            if (ApplicationConstant.isOnline(LoginActivity.this)) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }else{
                ApplicationConstant.showSnckbar(v,getString(R.string.network_error));
            }
        }
    }
}
