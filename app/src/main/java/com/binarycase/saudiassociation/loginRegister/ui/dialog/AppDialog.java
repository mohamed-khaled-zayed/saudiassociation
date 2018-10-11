package com.binarycase.saudiassociation.loginRegister.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.binarycase.saudiassociation.R;


public class AppDialog extends Dialog {

    private String text;
    private int dialogType; // 1 one action button, 2 two actions button.
    private Action1ButtonListener action1ButtonClickListener;
    private Action2ButtonListener action2ButtonClickListener;
    private int action1ButtonText = R.string.dialog_ok, action2ButtonText = R.string.dialog_cancel;
    private boolean isCancelable = true;

    public AppDialog(@NonNull Context context, @StringRes int text) {
        super(context);
        this.text = getContext().getString(text);
    }


    public AppDialog(@NonNull Context context, String text) {
        super(context);
        this.text = text;
    }

    public AppDialog setText(@StringRes int text) {
        this.text = getContext().getString(text);
        return this;
    }

    public AppDialog setText(String text) {
        this.text = text;
        return this;
    }

    public AppDialog showOneButtonDialog() {
        dialogType = 1;
        return this;
    }

    public AppDialog showTwoButtonDialog() {
        dialogType = 2;
        return this;
    }

    public AppDialog isDialogCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public AppDialog setAction1ButtonText(@StringRes int text) {
        this.action1ButtonText = text;
        return this;
    }

    public AppDialog setAction2ButtonText(@StringRes int text) {
        this.action2ButtonText = text;
        return this;
    }

    public AppDialog setAction1ButtonClickListener(Action1ButtonListener action1ButtonClickListener) {
        this.action1ButtonClickListener = action1ButtonClickListener;
        return this;
    }

    public AppDialog setAction2ButtonClickListener(Action2ButtonListener action2ButtonClickListener) {
        this.action2ButtonClickListener = action2ButtonClickListener;
        return this;
    }

    public static interface Action1ButtonListener {
        void onAction1ButtonClick(Dialog dialog);
    }

    public static interface Action2ButtonListener {
        void onAction2ButtonClick(Dialog dialog);
    }

    public void showDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // get view.
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_app_dialog, null);
        // find views.
        TextView dialogText = dialogView.findViewById(R.id.dialog_text);
        dialogText.setText(text);
        Button action1Button = dialogView.findViewById(R.id.dialog_button_action1);
        Button action2Button = dialogView.findViewById(R.id.dialog_button_action2);

        if (dialogType == 1) {
            // single action dialog.
            action2Button.setVisibility(View.GONE);
            showAction1ButtonData(action1Button);
        } else {
            showAction1ButtonData(action1Button);
            showAction2ButtonData(action2Button);
        }
        setContentView(dialogView);
        setCancelable(isCancelable);
        show();
    }

    private void showAction2ButtonData(Button action2Button) {
        action2Button.setText(action2ButtonText);

        action2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action2ButtonClickListener != null) {
                    action2ButtonClickListener.onAction2ButtonClick(AppDialog.this);
                } else dismiss();
            }
        });
    }

    private void showAction1ButtonData(Button action1Button) {
        action1Button.setText(action1ButtonText);
        action1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action1ButtonClickListener != null) {
                    action1ButtonClickListener.onAction1ButtonClick(AppDialog.this);
                } else dismiss();
            }
        });
    }

}
