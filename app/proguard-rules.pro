# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android\Android_Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-keep class com.startapp.** { *; }
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep public class org.jsoup.** {
  public protected *;
}
-keep class com.nineoldandroids.** { *; }
-keep class com.afollestad.** { *; }
-keep class com.readystatesoftware.** { *; }
-keep class com.nostra13.universalimageloader.** { *; }
-dontwarn com.malinskiy.superrecyclerview.SwipeDismissRecyclerViewTouchListener*
#StartApp All bottom

-keep class com.startapp.** {
      *;
}
-dontwarn com.startapp.**
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
#Acra
-keep class org.acra.ACRA {
    *;
}

# keep this around for some enums that ACRA needs
-keep class org.acra.ReportingInteractionMode {
    *;
}

-keepnames class org.acra.sender.HttpSender$** {
    *;
}

-keepnames class org.acra.ReportField {
    *;
}

# keep this otherwise it is removed by ProGuard
-keep public class org.acra.ErrorReporter{
    public void addCustomDa ta(java.lang.String,java.lang.String);
    public void putCustomData(java.lang.String,java.lang.String);
    public void removeCustomData(java.lang.String);
}

# keep this otherwise it is removed by ProGuard
-keep public class org.acra.ErrorReporter{
    public void handleSilentException(java.lang.Throwable);
}

