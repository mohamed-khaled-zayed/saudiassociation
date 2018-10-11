package com.binarycase.saudiassociation.loginRegister.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.databinding.ActivitySignUpBinding;
import com.binarycase.saudiassociation.loginRegister.data.Constants;
import com.binarycase.saudiassociation.loginRegister.interfaces.OnAcceptUseAgreementListener;
import com.binarycase.saudiassociation.loginRegister.network.request.RegisterData;
import com.binarycase.saudiassociation.loginRegister.network.request.ShrootData;
import com.binarycase.saudiassociation.loginRegister.network.responses.registerResponse.RegisterResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.shrootResponse.ShrootResponse;
import com.binarycase.saudiassociation.loginRegister.ui.dialog.UseAgreementDialog;
import com.binarycase.saudiassociation.loginRegister.utils.AppUtil;
import com.binarycase.saudiassociation.loginRegister.utils.ValidateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements OnAcceptUseAgreementListener {
    private boolean acceptAgreement;
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        AppUtil.setHtmlText(R.string.agree_shroot_html, binding.agreeShroot);

        binding.agreeShrootImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadShroot();
            }
        });
        binding.agreeShroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadShroot();
            }
        });

    }

    private void loadShroot() {

        if (!AppUtil.isInternetAvailable(this)) {
            AppUtil.showNoInternetDialog(this);
            return;
        }

        AppUtil.showProgressDialog(this);
        service.loadShrootApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new ShrootData(AppUtil.getDeviceId(this),Constants.TERMS))
                .enqueue(new Callback<ShrootResponse>() {
            @Override
            public void onResponse(Call<ShrootResponse> call, Response<ShrootResponse> response) {
                AppUtil.dismissProgressDialog();
                if (response.code()==200){
                    setShroot(response.body().getContent());
                }else {
                    AppUtil.showErrorToast(SignUpActivity.this,getString(R.string.terms_not_loaded));
                }
            }

            @Override
            public void onFailure(Call<ShrootResponse> call, Throwable t) {
                AppUtil.dismissProgressDialog();
                AppUtil.showErrorToast(SignUpActivity.this,getString(R.string.terms_not_loaded));
            }
        });
    }

    public void next(View view) {

        final String user_name=AppUtil.getTextContent(binding.signUpName);
        final String user_phone=AppUtil.getTextContent(binding.signUpNumber);
        final String user_email=AppUtil.getTextContent(binding.signUpEmail);
        final String user_password=AppUtil.getTextContent(binding.signUpPassword);
        if (ValidateUtils.missingInputs(user_name, user_phone, user_email, user_password)) {
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


        if (!acceptAgreement){
            AppUtil.showInfoDialog(this,R.string.error_toast_accept_agreement);
            return;
        }



        AppUtil.showProgressDialog(this);
        service.registerApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new RegisterData(user_name,AppUtil.getFullNumber(user_phone), user_email,user_password,
                        AppUtil.getDeviceId(this))).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                AppUtil.dismissProgressDialog();
                if (response.code()==201){
                    AppUtil.showSuccessToast(SignUpActivity.this,getString(R.string.add_new_account_successfully));
                    Intent intent=new Intent(SignUpActivity.this,CompleteAccountCreatationActivity.class);
                    intent.putExtra("phone",response.body().getUser().getPhone());
                    intent.putExtra("token",response.body().getUser().getApiToken());
                    intent.putExtra("code",response.body().getUser().getSmsCode());
                    startActivity(intent);
                    finish();
                }else if (response.code()==409){
                    AppUtil.showInfoToast(SignUpActivity.this,getString(R.string.account_already_exist));
                }else {
                    AppUtil.showInfoToast(SignUpActivity.this,getString(R.string.check_email_pass));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                AppUtil.dismissProgressDialog();
                AppUtil.showInfoToast(SignUpActivity.this,getString(R.string.check_email_pass));
            }
        });
    }

    @Override
    public void acceptUseAgreement() {
        acceptAgreement = true;
        binding.agreeShrootImageView.setImageResource(R.drawable.ic_agreement_check);
    }

    private void setShroot(String shroot) {
        new UseAgreementDialog(SignUpActivity.this,SignUpActivity.this, shroot).showDialog();
    }

}
