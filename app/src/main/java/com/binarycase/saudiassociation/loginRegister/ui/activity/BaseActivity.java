package com.binarycase.saudiassociation.loginRegister.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.loginRegister.network.RetrofitModule;
import com.binarycase.saudiassociation.loginRegister.network.ServiceApi;

public class BaseActivity extends AppCompatActivity {

    public static ServiceApi service= RetrofitModule.getInstance().getService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
