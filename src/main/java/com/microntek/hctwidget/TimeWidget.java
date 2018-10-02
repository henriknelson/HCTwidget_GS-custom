package com.microntek.hctwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.os.UserHandle;
//import android.os.storage.StorageManager;
import android.provider.Settings.System;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;
import com.microntek.CalendarLunar;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeWidget extends AppWidgetProvider {
    private static final int[] timeImgArray = new int[]{R.drawable.time_n0, R.drawable.time_n1, R.drawable.time_n2, R.drawable.time_n3, R.drawable.time_n4, R.drawable.time_n5, R.drawable.time_n6, R.drawable.time_n7, R.drawable.time_n8, R.drawable.time_n9};
    private CalendarLunar mCalendarLunar;
    private boolean mCnTw;
    private ComponentName mComponentName;

    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        updateTime(context, AppWidgetManager.getInstance(context), appWidgetId);
    }

    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    public void onReceive(Context context, Intent intent) {
        boolean z;
        super.onReceive(context, intent);
        this.mCalendarLunar = CalendarLunar.getInstance(context);
        if (Locale.getDefault().getCountry().equalsIgnoreCase("TW")) {
            z = true;
        } else {
            z = Locale.getDefault().getCountry().equalsIgnoreCase("CN");
        }
        this.mCnTw = z;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // Since I cant get compiling against framework.jar working, I resorted to using reflection to get things up n' running!
        try{
            Class userHandleClass = Class.forName("android.os.UserHandle");
            Log.i("nu.cliffords.widgets","Loaded class: " + userHandleClass);
            Method getCallingUserIdMethod = userHandleClass.getDeclaredMethod("getCallingUserId",null);
            Log.i("nu.cliffords.widgets","Got method: " + getCallingUserIdMethod);
            Object resultObj = getCallingUserIdMethod.invoke(null, null);
            int result = (int) resultObj;
            Log.i("nu.cliffords.widgets","Result: " + result);
        }catch(Exception e)
        {
            Log.e("nu.cliffords.widgets","Error: " + e.getMessage());
        }

        //if (StorageManager.isUserKeyUnlocked(UserHandle.getCallingUserId())) {
        if (1 == 1) {
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(getComponentName(context));
            if (appWidgetManager != null) {
                for (int appWidgetId : appWidgetIds) {
                    updateTime(context, appWidgetManager, appWidgetId);
                }
            }
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        boolean z;
        this.mCalendarLunar = CalendarLunar.getInstance(context);
        if (Locale.getDefault().getCountry().equalsIgnoreCase("TW")) {
            z = true;
        } else {
            z = Locale.getDefault().getCountry().equalsIgnoreCase("CN");
        }
        this.mCnTw = z;
        for (int appWidgetId : appWidgetIds) {
            updateTime(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateTime(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.time);
        Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        int minute = time.minute;
        if (!DateFormat.is24HourFormat(context) && hour > 12) {
            hour -= 12;
        }
        String info = "";
        if (this.mCnTw && this.mCalendarLunar != null) {
            info = this.mCalendarLunar.getDateInfoString(time.year, time.month, time.monthDay);
        }
        widget.setTextViewText(R.id.datainfo, info);
        widget.setImageViewResource(R.id.timehour_h, timeImgArray[hour / 10]);
        widget.setImageViewResource(R.id.timehour_l, timeImgArray[hour % 10]);
        widget.setImageViewResource(R.id.timeminute_h, timeImgArray[minute / 10]);
        widget.setImageViewResource(R.id.timeminute_l, timeImgArray[minute % 10]);
        String dateformat = System.getString(context.getContentResolver(), "date_format");
        if ("MM-dd-yyyy".equals(dateformat)) {
            widget.setTextViewText(R.id.yearmonthdata, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        } else if ("dd-MM-yyyy".equals(dateformat)) {
            widget.setTextViewText(R.id.yearmonthdata, new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        } else {
            widget.setTextViewText(R.id.yearmonthdata, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        widget.setOnClickPendingIntent(R.id.img_btn_speed, PendingIntent.getBroadcast(context, 0, new Intent("com.microntek.clear"), 0));
        widget.setOnClickPendingIntent(R.id.img_btn_vol, PendingIntent.getBroadcast(context, 0, new Intent("com.microntek.setVolume"), 0));
        widget.setOnClickPendingIntent(R.id.img_btn_light, PendingIntent.getBroadcast(context, 0, new Intent("com.android.intent.action.SHOW_BRIGHTNESS_DIALOG"), 0));
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }

    private ComponentName getComponentName(Context context) {
        if (this.mComponentName == null) {
            this.mComponentName = new ComponentName(context, getClass());
        }
        return this.mComponentName;
    }
}
