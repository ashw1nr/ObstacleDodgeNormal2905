package org.ashcode.obstacledodgenormal2905;

import android.widget.TextView;

public class TextViewScoreAccess {
    private TextView textView;
    public TextViewScoreAccess(TextView textView){
        this.textView = textView;
    }
    public void setText(String text){
        textView.setText(text);
    }
}
