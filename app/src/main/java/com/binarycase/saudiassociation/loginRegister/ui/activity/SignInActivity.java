package com.binarycase.saudiassociation.loginRegister.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.databinding.ActivitySignInBinding;
import com.binarycase.saudiassociation.loginRegister.data.CacheUtils;
import com.binarycase.saudiassociation.loginRegister.data.Constants;
import com.binarycase.saudiassociation.loginRegister.network.request.LoginData;
import com.binarycase.saudiassociation.loginRegister.network.responses.loginResponse.LoginResponse;
import com.binarycase.saudiassociation.loginRegister.ui.dialog.AppDialog;
import com.binarycase.saudiassociation.loginRegister.utils.AppUtil;
import com.binarycase.saudiassociation.loginRegister.utils.DialogUtils;
import com.binarycase.saudiassociation.loginRegister.utils.ValidateUtils;
import com.binarycase.saudiassociation.ui.screens.mainScreen.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends BaseActivity {

    private ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_in);

        AppUtil.setHtmlText(R.string.forget_password_html, binding.forgetPasswordTextView);
    }

    public void signIn(View view) {


        final String user_phone=AppUtil.getTextContent(binding.signInPhoneNumber);
        final String user_password=AppUtil.getTextContent(binding.passwordInput);
        if (ValidateUtils.missingInputs(user_phone, user_password)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_missing_inputs);
            return;
        }

        if (!ValidateUtils.validPhone(user_phone)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_invalid_phone);
            return;
        }

        if (!ValidateUtils.validPassword(user_password)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_invalid_password);
            return;
        }

        if (!AppUtil.isInternetAvailable(this)) {
            AppUtil.showNoInternetDialog(this);
            return;
        }

        AppUtil.showProgressDialog(this);

        service.loginApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new LoginData(AppUtil.getFullNumber(user_phone),user_password,AppUtil.getDeviceId(this)))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        AppUtil.dismissProgressDialog();
                        if (response.code()==200){
                            AppUtil.showSuccessToast(SignInActivity.this,getString(R.string.login_successfully));
                            CacheUtils.getSharedPreferences(SignInActivity.this).edit().putString("isLogin","true").apply();
                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else if (response.code()==403){
                            verifyPhone(AppUtil.getFullNumber(user_phone));
                        }else if (response.code()==401){
                            AppUtil.showErrorToast(SignInActivity.this,getString(R.string.check_email_pass));
                        }else {
                            AppUtil.showErrorToast(SignInActivity.this,getString(R.string.something_went_wrong));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        AppUtil.dismissProgressDialog();
                        AppUtil.showErrorToast(SignInActivity.this,getString(R.string.something_went_wrong));
                    }
                });
    }

    private void verifyPhone(final String phone) {
        DialogUtils.showTwoActionButtonsDialog(SignInActivity.this, R.string.verify_code,
                R.string.dialog_ok, new AppDialog.Action1ButtonListener() {
                    @Override
                    public void onAction1ButtonClick(Dialog dialog) {
                        dialog.dismiss();
                        Intent intent=new Intent(SignInActivity.this, CompleteAccountCreatationActivity.class);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                        finish();
                    }
                }, R.string.dialog_cancel, new AppDialog.Action2ButtonListener() {
                    @Override
                    public void onAction2ButtonClick(Dialog dialog) {
                        finish();
                    }
                }, false);
    }

    public void signUp(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
        finish();
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    public void discoverApp(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
