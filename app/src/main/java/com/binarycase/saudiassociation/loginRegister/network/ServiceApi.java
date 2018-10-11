package com.binarycase.saudiassociation.loginRegister.network;


import com.binarycase.saudiassociation.loginRegister.network.request.LoginData;
import com.binarycase.saudiassociation.loginRegister.network.request.PassRestData;
import com.binarycase.saudiassociation.loginRegister.network.request.RegisterData;
import com.binarycase.saudiassociation.loginRegister.network.request.ResendSmsData;
import com.binarycase.saudiassociation.loginRegister.network.request.ResetData;
import com.binarycase.saudiassociation.loginRegister.network.request.ResetVerifyData;
import com.binarycase.saudiassociation.loginRegister.network.request.ShrootData;
import com.binarycase.saudiassociation.loginRegister.network.request.VerifyData;
import com.binarycase.saudiassociation.loginRegister.network.responses.loginResponse.LoginResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.passResetResponse.PassResetResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.registerResponse.RegisterResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.resendSmsResponse.ResendSmsResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.resetResponse.ResetResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.resetVerifyResponse.ResetVerifyResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.shrootResponse.ShrootResponse;
import com.binarycase.saudiassociation.loginRegister.network.responses.verifyResponse.VerifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ServiceApi {

  @POST("user/login")
  Call<LoginResponse> loginApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body LoginData data);

  @POST("user/register")
  Call<RegisterResponse> registerApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body RegisterData data);

  @POST("load/content")
  Call<ShrootResponse> loadShrootApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body ShrootData data);

  @POST("user/verify")
  Call<VerifyResponse> verifyApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body VerifyData data);

  @POST("user/send_sms")
  Call<ResendSmsResponse> sendSmsApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body ResendSmsData data);

  @POST("user/pass_reset")
  Call<PassResetResponse> passResetApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body PassRestData data);

  @POST("user/reset_verify")
  Call<ResetVerifyResponse> resetVerifyApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body ResetVerifyData data);

  @POST("user/reset")
  Call<ResetResponse> resetApi(@Header("Authorization") String auth, @Header("Content-Type") String type, @Body ResetData data);

}
