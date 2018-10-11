package com.binarycase.saudiassociation.loginRegister.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.databinding.ActivityResetPasswordBinding;
import com.binarycase.saudiassociation.loginRegister.data.Constants;
import com.binarycase.saudiassociation.loginRegister.network.request.ResetData;
import com.binarycase.saudiassociation.loginRegister.network.responses.resetResponse.ResetResponse;
import com.binarycase.saudiassociation.loginRegister.utils.AppUtil;
import com.binarycase.saudiassociation.loginRegister.utils.ValidateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends BaseActivity {

    private ActivityResetPasswordBinding binding;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_reset_password);
        token=getIntent().getStringExtra("token");
    }

    public void goToHomeActivity(View view) {


        final String newPassword= AppUtil.getTextContent(binding.newPassword);
        final String confirmNewPassword=AppUtil.getTextContent(binding.confirmNewPassword);
        if (ValidateUtils.missingInputs(newPassword, confirmNewPassword)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_missing_inputs);
            return;
        }

        if (!ValidateUtils.validPassword(newPassword)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_invalid_password);
            return;
        }

        if (!newPassword.equals(confirmNewPassword)){
            AppUtil.showInfoDialog(this,R.string.two_password_not_matches);
            return;
        }

        if (!AppUtil.isInternetAvailable(this)) {
            AppUtil.showNoInternetDialog(this);
            return;
        }

        AppUtil.showProgressDialog(this);
        service.resetApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new ResetData(token,newPassword,AppUtil.getDeviceId(this)))
                .enqueue(new Callback<ResetResponse>() {
                    @Override
                    public void onResponse(Call<ResetResponse> call, Response<ResetResponse> response) {
                        AppUtil.dismissProgressDialog();
                        if (response.code()==200){
                            AppUtil.showSuccessToast(ResetPasswordActivity.this,getString(R.string.pass_reset_successfully));
//                            startActivity(new Intent(ResetPasswordActivity.this,SignInActivity.class));
                            finish();
                        }else {
                            AppUtil.showErrorToast(ResetPasswordActivity.this,getString(R.string.pass_not_reset));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetResponse> call, Throwable t) {
                        AppUtil.dismissProgressDialog();
                        AppUtil.showErrorToast(ResetPasswordActivity.this,getString(R.string.pass_not_reset));
                    }
                });

    }
}
