package com.toan_itc.baoonline.Activity;

import android.app.Application;

import com.toan_itc.baoonline.R;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Toan_Kul on 12/22/2014.
 */
@ReportsCrashes(
        mailTo = "huynhvantoan.itc@gmail.com",
        customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)
public class BaoOnline_Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // ACRA.init(this);
    }
}