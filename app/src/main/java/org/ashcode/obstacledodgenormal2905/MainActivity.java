package org.ashcode.obstacledodgenormal2905;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static TextView currScoreTVMA1;

    public static void setCurrScoreTVMA1Text(String currScore) {
        currScoreTVMA1.setText(currScore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MyCanvasView canvasView = new MyCanvasView(this,null);
        //canvasView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //canvasView.setRectangleSize(100,300); //can have it even after setContentView
        //setContentView(canvasView);

        setContentView(R.layout.activity_main);

        MyCanvasView canvasViewMA1 = findViewById(R.id.canvasViewMA1);
        currScoreTVMA1 = findViewById(R.id.currScoreTVMA1);
        TextViewScoreAccess TVScoreMA1 = new TextViewScoreAccess(currScoreTVMA1);
        currScoreTVMA1.setX(50);
        currScoreTVMA1.setY(100);
        currScoreTVMA1.setText("Hello, World!");
        currScoreTVMA1.setTextSize(20);

    }}