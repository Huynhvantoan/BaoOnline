package com.toan_itc.baoonline.About_Library;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.toan_itc.baoonline.R;

/**
 * Created by Toan_Kul on 2/1/2015.
 */
public class About_Activity extends AppCompatActivity {
    private SystemBarTintManager tintManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.theme_default_primary_dark));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LibsFragment fragment = new LibsBuilder()
                .withFields(R.string.class.getFields())
                .withLibraries("facebook", "baoonline")
                .withAboutIconShown(true)
                .withActivityTitle("Thông tin ứng dụng")
                .withAboutVersionShown(true)
                .withAboutDescription("Ứng dụng đọc báo online.<br /><b>Đọc báo theo phong cách Material :D</b>")
                .fragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }
}
