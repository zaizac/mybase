package com.bestinet.mycare.customs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bestinet.mycare.R;
import com.bestinet.mycare.util.BaseUtil;

public class CustomAlertDialog implements ICustomsAlertDialog {

    private AlertDialog dialog;
    private ICustomsAlertDialog iCustomsAlertDialog;

    public CustomAlertDialog(ICustomsAlertDialog iCustomsAlertDialog) {
        if (!BaseUtil.isObjNull(iCustomsAlertDialog)) {
            this.iCustomsAlertDialog = iCustomsAlertDialog;
        } else {
            this.iCustomsAlertDialog = this;
        }

    }

    public void dismissDialog() {
        if (!BaseUtil.isObjNull(dialog) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showAlert(Context context, String content,
                          String leftLabel, String rightLabel,
                          DialogInterface.OnCancelListener onCancelListener) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
//        mBuilder.setTitle("IFACE");
//        mBuilder.setMessage(content);
//        Button negButton = (dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
//        mBuilder.setPositiveButton(rightLabel, (dialog1, which) -> iCustomsAlertDialog.onOk());
//        mBuilder.setNegativeButton(leftLabel, (dialog1, which) -> iCustomsAlertDialog.onCancel());
        View theDialog = layoutInflater.inflate(R.layout.dialog_message, null);
        mBuilder.setCancelable(false);
        TextView message = theDialog.findViewById(R.id.txtMessage);
        message.setText(content);
        mBuilder.setView(message);
//
//
//        // region BUTTON LEFT
        Button btnLeft = theDialog.findViewById(R.id.btnLeft);
        btnLeft.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        btnLeft.setVisibility(View.GONE);
        if (!BaseUtil.isObjNull(btnLeft)) {
            if (!BaseUtil.isObjNull(leftLabel)) {
                btnLeft.setVisibility(View.VISIBLE);
                btnLeft.setText(leftLabel);
                btnLeft.setOnClickListener(view -> iCustomsAlertDialog.onCancel());
            }
            mBuilder.setView(theDialog);
        }
        //endregion

        // region BUTTON RIGHT
        Button btnRight = theDialog.findViewById(R.id.btnRight);
        btnRight.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        btnRight.setVisibility(View.GONE);
        if (!BaseUtil.isObjNull(btnRight)) {
            if (!BaseUtil.isObjNull(rightLabel)) {
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText(rightLabel);
                btnRight.setOnClickListener(view -> iCustomsAlertDialog.onOk());
            }
            mBuilder.setView(theDialog);
        }
//        endregion

        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnCancelListener(onCancelListener);
        dialog.show();

        int currentWidth = context.getResources().getDisplayMetrics().widthPixels;
        double ratioWidth = context.getResources().getDisplayMetrics().widthPixels * 0.1;

        int widthRequired = (int) (currentWidth - ratioWidth);

        dialog.getWindow().setLayout(widthRequired, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onCancel() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onOk() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}


