package com.binarycase.saudiassociation.loginRegister.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;


import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.databinding.ActivityCheckCodeBinding;
import com.binarycase.saudiassociation.loginRegister.data.Constants;
import com.binarycase.saudiassociation.loginRegister.network.request.ResetVerifyData;
import com.binarycase.saudiassociation.loginRegister.network.responses.resetVerifyResponse.ResetVerifyResponse;
import com.binarycase.saudiassociation.loginRegister.utils.AppUtil;
import com.binarycase.saudiassociation.loginRegister.utils.ValidateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckCodeActivity extends BaseActivity {


    private ActivityCheckCodeBinding binding;
    private String token;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_check_code);
        token=getIntent().getStringExtra("token");
        code=getIntent().getStringExtra("code");
        binding.checkCode.setText(code);
    }

    public void next(View view) {

        final String smsCode= AppUtil.getTextContent(binding.checkCode);

        if (ValidateUtils.missingInputs(smsCode)) {
            AppUtil.showInfoDialog(this,R.string.error_dialog_missing_inputs);
            return;
        }

        if (!AppUtil.isInternetAvailable(this)) {
            AppUtil.showNoInternetDialog(this);
            return;
        }



        AppUtil.showProgressDialog(this);
        service.resetVerifyApi(Constants.APP_AUTH,Constants.CONTENT_TYPE,
                new ResetVerifyData(token,code,AppUtil.getDeviceId(this)))
                .enqueue(new Callback<ResetVerifyResponse>() {
                    @Override
                    public void onResponse(Call<ResetVerifyResponse> call, Response<ResetVerifyResponse> response) {
                        AppUtil.dismissProgressDialog();
                        if (response.code()==200){
                            Intent intent=new Intent(CheckCodeActivity.this,ResetPasswordActivity.class);
                            intent.putExtra("token",response.body().getToken());
                            startActivity(intent);
                            finish();
                        }else {
                            AppUtil.showErrorToast(CheckCodeActivity.this,getString(R.string.code_not_correct));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetVerifyResponse> call, Throwable t) {
                        AppUtil.dismissProgressDialog();
                        AppUtil.showErrorToast(CheckCodeActivity.this,getString(R.string.something_went_wrong));
                    }
                });

    }
}
