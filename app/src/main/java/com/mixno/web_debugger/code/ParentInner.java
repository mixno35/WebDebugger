package com.mixno.web_debugger.code;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mixno.web_debugger.R;

public class ParentInner implements View.OnClickListener {
    private EditText editText;
    private Button button;
    private String str, str2, strALL;
    private Context context;
    private UndoRedo ur;

    public ParentInner(UndoRedo ur, Context context, EditText editText, String str, String str2, Button button) {
        this.context = context;
        this.editText = editText;
        this.str = str;
        this.button = button;
        this.str2 = str2;
        this.ur = ur;
    }

    @Override
    public void onClick(View p1) {
        try {
            this.editText.setText(((Boolean) this.editText.getTag()).booleanValue() ? this.str : this.str2);
            this.strALL = (((Boolean) this.editText.getTag()).booleanValue() ? context.getString(R.string.action_parent) : context.getString(R.string.action_inner));
            this.editText.setTag(new Boolean(!((Boolean) this.editText.getTag()).booleanValue()));
            if(this.strALL.equals(context.getString(R.string.action_parent))) {
                this.button.setText(R.string.action_inner);
            } else {
                this.button.setText(R.string.action_parent);
            }
        } catch(Exception e) {
            Log.i("PARENT_INNER", e.getMessage());
        }
        ur.clearHistory();
    }
}
