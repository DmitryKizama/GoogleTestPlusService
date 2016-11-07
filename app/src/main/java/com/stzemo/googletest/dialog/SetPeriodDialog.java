package com.stzemo.googletest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.stzemo.googletest.R;

public class SetPeriodDialog extends Dialog implements View.OnClickListener {

    public interface DialogListener {
        void onApplyPressed(int sec);
    }

    private DialogListener dialogListener;
    private EditText etSec;
    private Button btnApply, btnCancel;

    public SetPeriodDialog(Context context, DialogListener dlistener) {
        super(context);
        this.dialogListener = dlistener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_set_period);

        etSec = (EditText) findViewById(R.id.etSec);
        btnApply = (Button) findViewById(R.id.btnPositive);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnApply.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPositive:
                if (etSec.getText().toString().equals("")) {
                    dismiss();
                    break;
                }
                int sec;
                try {
                    sec = Integer.parseInt(etSec.getText().toString());
                } catch (NumberFormatException e) {
                    sec = 2;
                }
                dialogListener.onApplyPressed(sec);
                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
