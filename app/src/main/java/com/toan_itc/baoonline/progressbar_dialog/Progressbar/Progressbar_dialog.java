package com.toan_itc.baoonline.progressbar_dialog.Progressbar;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.toan_itc.baoonline.R;

/**
 * Created by Toan_Kul on 1/12/2015.
 */
public class Progressbar_dialog extends android.app.Dialog {
    Context context;
    View view;
    View backView;
    int color;
    ProgressBar_custom progressBarCircular;
    public Progressbar_dialog(Context context,int color) {
        super(context, android.R.style.Theme_Translucent);
        this.context = context;
        this.color=color;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressdialog);
        view = (LinearLayout) findViewById(R.id.contentDialog);
        backView = (RelativeLayout) findViewById(R.id.dialog_rootView);
        progressBarCircular=(ProgressBar_custom)findViewById(R.id.progressBar);
        progressBarCircular.setBackgroundColor(getContext().getResources().getColor(color));
        backView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() < view.getLeft()
                        || event.getX() > view.getRight()
                        || event.getY() > view.getBottom()
                        || event.getY() < view.getTop()) {
                    dismiss();
                }
                return false;
            }
        });
    }
    @Override
    public void show() {
        super.show();
        // set dialog_exit enter animations
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_main_show_amination));
        backView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
    }
}