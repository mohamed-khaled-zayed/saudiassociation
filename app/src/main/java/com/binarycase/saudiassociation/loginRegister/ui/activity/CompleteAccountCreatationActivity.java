package com.binarycase.saudiassociation.loginRegister.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.binarycase.saudiassociation.databinding.ActivityCompleteAccountCreatationBinding;
import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.loginRegister.data.CacheUtils;
import com.binarycase.saudiassociation.loginRegister.data.Constants;
import com.binarycase.saudiassociation.loginRegister.network.request.ResendSmsData;
import com.binarycase.saudiassociation.loginRegister.network.request.VerifyData;
import com.binarycase.saudiassociation.loginRegister.network.responses.resendSmsResponse.ResendSmsResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.verifyResponse.VerifyResponse;
import com.binarycase.saudiassociation.loginRegister.utils.AppUtil;
import com.binarycase.saudiassociation.loginRegister.utils.ValidateUtils;
import com.binarycase.saudiassociation.ui.screens.mainScreen.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteAccountCreatationActivity extends BaseActivity {

    private String phone;
    private String token;
    private ActivityCompleteAccountCreatationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_complete_account_creatation);
        binding.confirmCodeTxt.setText(getIntent().getStringExtra("code"));
        phone=getIntent().getStringExtra("phone");
        token=getIntent().getStringExtra("token");
        AppUtil.setHtmlText(R.string.sms_code,binding.smsText);

    }

    public void confirmCreateAccount(View view) {
        final String smsCode=AppUtil.getTextContent(binding.confirmCodeTxt);

        if (ValidateUtils.missingInputs(smsCode)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_missing_inputs);
            return;
        }

        if (!AppUtil.isInternetAvailable(this)) {
            AppUtil.showNoInternetDialog(this);
            return;
        }

        AppUtil.showProgressDialog(this);
        service.verifyApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new VerifyData(token,smsCode,AppUtil.getDeviceId(this)))
                .enqueue(new Callback<VerifyResponse>() {
                    @Override
                    public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                        AppUtil.dismissProgressDialog();
                        if (response.code()==200){
                            AppUtil.showSuccessToast(CompleteAccountCreatationActivity.this,getString(R.string.account_verified_successfully));
                            CacheUtils.getSharedPreferences(CompleteAccountCreatationActivity.this).edit().putString("isLogin","true").apply();
                            startActivity(new Intent(CompleteAccountCreatationActivity.this, MainActivity.class));
                            finish();
                        }else if (response.code()==203) {

                        } else {
                            AppUtil.showErrorToast(CompleteAccountCreatationActivity.this,getString(R.string.account_not_verified));
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyResponse> call, Throwable t) {
                        AppUtil.dismissProgressDialog();
                        AppUtil.showErrorToast(CompleteAccountCreatationActivity.this,getString(R.string.account_not_verified));
                    }
                });
    }

    public void sendCode(View view) {

        AppUtil.showProgressDialog(this);
        service.sendSmsApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new ResendSmsData(phone,AppUtil.getDeviceId(this)))
                .enqueue(new Callback<ResendSmsResponse>() {
                    @Override
                    public void onResponse(Call<ResendSmsResponse> call, Response<ResendSmsResponse> response) {
                        AppUtil.dismissProgressDialog();
                        if (response.code()==201){
                            AppUtil.showSuccessToast(CompleteAccountCreatationActivity.this,getString(R.string.verify_code_send_successfully));
                            binding.confirmCodeTxt.setText(response.body().getSmsCode());
                            token=response.body().getToken();
                        }else {
                            AppUtil.showErrorToast(CompleteAccountCreatationActivity.this, getString(R.string.verify_code_not_send));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResendSmsResponse> call, Throwable t) {
                        AppUtil.dismissProgressDialog();
                        AppUtil.showErrorToast(CompleteAccountCreatationActivity.this,getString(R.string.verify_code_not_send));
                    }
                });
    }
}
