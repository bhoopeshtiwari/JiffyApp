package com.jiffy.jiffyapp.constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.content.pm.ResolveInfo;
        import android.content.res.AssetManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Typeface;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.Uri;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.FragmentActivity;
        import android.support.v7.app.AlertDialog;
        import android.util.Base64;
        import android.util.Log;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.Toast;



        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.ByteArrayOutputStream;
        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;
        import java.util.TimeZone;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;



public class ApplicationConstant {
    public static final String LOGIN_CREDENTIALS = "test05media";
    // YouTube video id
    public static final String YOUTUBE_VIDEO_CODE = "_oEA18Y8gM0";
    public static final String YOUTUBE_URL_TO_SHARE = "https://youtu.be/"+YOUTUBE_VIDEO_CODE;
    // Google Console APIs developer key for youtube player
    public static final String YOUTUBE_DEVELOPER_KEY = "AIzaSyDzQ4Zcx2A2M1PcRjQAJiCLR3qCgXWuDWc";
    public static final String M3U8_URL = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4";
    public static final String EMAIL_SUBJECT = "Jiffy App shared a new video link";


    /**********
     * Method to check the field is blank or not (i.e. if the field is blank then method will return true otherwise false).
     **********/
    public static boolean isBlank(String field) {
        return field.trim().isEmpty();
    }

    /**********
     * Method to show toast message by passing message on it.
     *********/
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }



    /**********
     * Method to print Log message by passing tag and message.
     **********/
    public static void log(String tag, String message) {
        try {
          //  Log.d(tag, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**********
     * Method to hide keyboard.
     **********/
    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {

        }
    }


    /**********
     * Method to check data connection availability (if network is available then it will return true otherwise false).
     **********/
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }



    public static void showSnckbar(View view, String str) {
        Snackbar snackbar = Snackbar.make(view, str, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
