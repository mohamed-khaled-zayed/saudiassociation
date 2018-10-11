package com.binarycase.saudiassociation.loginRegister.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.Html;
import android.widget.TextView;

import com.binarycase.saudiassociation.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class AppUtil {
    private static KProgressHUD progressHUD;


    public static void setHtmlText(@StringRes int text, TextView textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(textView.getContext().getString(text), Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(textView.getContext().getString(text)));
        }
    }


    private static void showToast(Context context, String text, @ColorRes int textColor,
                                  @ColorRes int backgroundColor, int icon, Typeface font) {
        final StyleableToast.Builder builder = new StyleableToast
                .Builder(context)
                .text(text)
                .textColor(context.getResources().getColor(textColor))
                .backgroundColor(context.getResources().getColor(backgroundColor));
        if (font != null) {
            builder.typeface(font);
        }
        if (icon != 0) {
            builder.icon(icon);
        }
        builder.build().show();
    }

    public static void showInfoToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.info_toast_color, 0, null);
    }

    public static void showErrorToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.fail_toast_color, 0, null);
    }

    public static void showSuccessToast(Context context, String text) {
        showToast(context, text, android.R.color.white, R.color.success_toast_color, 0, null);
    }

    public static String getTextContent(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static String getFullNumber(String number) {
        return "966"+number;
    }

    public static String getDeviceId(Context context){
        return  Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void showInfoDialog(Context context,int text){
        DialogUtils.showOneActionButtonDialog(context,text,null);
    }

    public static void showNoInternetDialog(Context context) {
        DialogUtils.showNoInternetDialog(context);
    }

    public static boolean isInternetAvailable(Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        boolean is=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean i=connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        if (i){
            return true;
        } else if (is){
            return true;
        } else {
            return false;
        }

    }

    public static void showProgressDialog(Context context) {
        createProgress(context,null);
        progressHUD.show();
    }

    private static void createProgress(Context context, String label) {
        progressHUD = DialogUtils.createNotCancelableProgressDialog(context, label);
    }

    public static void dismissProgressDialog() {
        if (progressHUD != null && progressHUD.isShowing()) {
            progressHUD.dismiss();
        }
    }


}
