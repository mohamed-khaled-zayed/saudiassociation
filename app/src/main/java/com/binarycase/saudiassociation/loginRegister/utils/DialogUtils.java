package com.binarycase.saudiassociation.loginRegister.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;

import com.binarycase.saudiassociation.R;
import com.binarycase.saudiassociation.loginRegister.ui.activity.SignInActivity;
import com.binarycase.saudiassociation.loginRegister.ui.dialog.AppDialog;
import com.kaopiz.kprogresshud.KProgressHUD;


public class DialogUtils {

    public static void showNoInternetDialog(Context context) {
        new AppDialog(context, context.getString(R.string.dialog_error_no_internet)).showDialog();
    }

    public static void showTwoActionButtonsDialog(Context context, @StringRes int text,
                                                  @StringRes int button1Text, AppDialog.Action1ButtonListener action1ButtonListener
            , @StringRes int button2Text, AppDialog.Action2ButtonListener action2ButtonListener, boolean isCancelable) {
        new AppDialog(context, text).setAction1ButtonText(button1Text).setAction1ButtonClickListener(action1ButtonListener)
                .setAction2ButtonText(button2Text).setAction2ButtonClickListener(action2ButtonListener).showTwoButtonDialog()
                .isDialogCancelable(isCancelable).showDialog();
    }

    public static void showUserNotRegisterDialog(final Context context) {
        showTwoActionButtonsDialog(context, R.string.u_are_not_registered, R.string.register_user, new AppDialog.Action1ButtonListener() {
            @Override
            public void onAction1ButtonClick(Dialog dialog) {
                dialog.dismiss();
                context.startActivity(new Intent(context, SignInActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }, R.string.not_now, new AppDialog.Action2ButtonListener() {
            @Override
            public void onAction2ButtonClick(Dialog dialog) {
                dialog.dismiss();
            }
        }, true);
    }

    private static KProgressHUD createProgressDialog(Context context, String label, String labelDetails, boolean isCancelable) {
        KProgressHUD kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        if (label != null) {
            kProgressHUD.setLabel(label);
        }
        if (labelDetails != null) {
            kProgressHUD.setDetailsLabel(labelDetails);
        }
        kProgressHUD.setCancellable(isCancelable)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        return kProgressHUD;
    }

    public static KProgressHUD createNotCancelableProgressDialog(Context context, String label) {
        return createProgressDialog(context, label, null, false);
    }

    public static void showOneActionButtonDialog(Context context, @StringRes int text, AppDialog.Action1ButtonListener action1ButtonListener) {
        AppDialog appDialog = new AppDialog(context, text);
        appDialog.setAction1ButtonText(R.string.dialog_ok);
        appDialog.showOneButtonDialog();
        appDialog.setAction1ButtonClickListener(action1ButtonListener);
        appDialog.showDialog();
    }

}
