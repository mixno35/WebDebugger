package com.mixno.web_debugger.widget;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CodeView extends AppCompatTextView {

    public CodeView(Context context) {
        super(context);
        init();
    }
    public CodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CodeView(Context context, AttributeSet attrs, int def) {
        super(context, attrs, def);
        init();
    }


    private void init() {
        this.setMovementMethod(new ScrollingMovementMethod());
        this.setHorizontallyScrolling(true);
        this.setHorizontalScrollBarEnabled(true);
        this.setVerticalScrollBarEnabled(true);
        this.setSelected(true);
    }
}
