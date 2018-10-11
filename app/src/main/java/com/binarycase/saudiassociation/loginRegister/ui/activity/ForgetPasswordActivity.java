package com.binarycase.saudiassociation.loginRegister.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.databinding.ActivityForgetPasswordBinding;
import com.binarycase.saudiassociation.loginRegister.data.Constants;
import com.binarycase.saudiassociation.loginRegister.network.request.PassRestData;
import com.binarycase.saudiassociation.loginRegister.network.responses.passResetResponse.PassResetResponse;
import com.binarycase.saudiassociation.loginRegister.utils.AppUtil;
import com.binarycase.saudiassociation.loginRegister.utils.ValidateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends BaseActivity {

    private ActivityForgetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forget_password);
    }

    public void CheckCode(View view) {

        final String user_phone=AppUtil.getTextContent(binding.phoneNumber);

        if (ValidateUtils.missingInputs(user_phone)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_missing_inputs);
            return;
        }

        if (!ValidateUtils.validPhone(user_phone)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_invalid_phone);
            return;
        }

        if (!AppUtil.isInternetAvailable(this)) {
            AppUtil.showNoInternetDialog(this);
            return;
        }

        AppUtil.showProgressDialog(this);
        service.passResetApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new PassRestData(AppUtil.getFullNumber(user_phone),AppUtil.getDeviceId(this)))
                .enqueue(new Callback<PassResetResponse>() {
                    @Override
                    public void onResponse(Call<PassResetResponse> call, Response<PassResetResponse> response) {
                        AppUtil.dismissProgressDialog();
                        if (response.code()==201){
                            AppUtil.showSuccessToast(ForgetPasswordActivity.this,getString(R.string.code_send_successfully));
                            Intent intent=new Intent(ForgetPasswordActivity.this,CheckCodeActivity.class);
                            intent.putExtra("token",response.body().getToken());
                            intent.putExtra("code",response.body().getResetCode());
                            startActivity(intent);
                            finish();
                        }else {
                            AppUtil.showErrorToast(ForgetPasswordActivity.this,getString(R.string.code_not_send));
                        }
                    }

                    @Override
                    public void onFailure(Call<PassResetResponse> call, Throwable t) {
                        AppUtil.dismissProgressDialog();
                        AppUtil.showErrorToast(ForgetPasswordActivity.this,getString(R.string.code_not_send));
                    }
                });

    }
}
