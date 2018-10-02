package com.microntek.hctwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.microntek.Util;

public class BtMusicWidget extends AppWidgetProvider {
    private ComponentName mComponentName;

    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        updateMusic(context, AppWidgetManager.getInstance(context), appWidgetId);
    }

    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        super.onReceive(context, intent);
        if (action.equals("com.microntek.btmusic.report")) {
            String type = intent.getStringExtra("type");
            String name;
            AppWidgetManager appWidgetManager;
            RemoteViews widget;
            if (type.equals("music.title")) {
                name = intent.getExtras().getString("value");
                if (name != null) {
                    appWidgetManager = AppWidgetManager.getInstance(context);
                    if (appWidgetManager != null) {
                        for (int appWidgetId : appWidgetManager.getAppWidgetIds(getComponentName(context))) {
                            widget = new RemoteViews(context.getPackageName(), R.layout.btmusic);
                            widget.setTextViewText(R.id.btmusicname, name);
                            appWidgetManager.partiallyUpdateAppWidget(appWidgetId, widget);
                        }
                    }
                }
            } else if (type.equals("music.albunm")) {
                name = intent.getExtras().getString("value");
                if (name != null) {
                    appWidgetManager = AppWidgetManager.getInstance(context);
                    if (appWidgetManager != null) {
                        for (int appWidgetId2 : appWidgetManager.getAppWidgetIds(getComponentName(context))) {
                            widget = new RemoteViews(context.getPackageName(), R.layout.btmusic);
                            widget.setTextViewText(R.id.btmusicinfo, name);
                            appWidgetManager.partiallyUpdateAppWidget(appWidgetId2, widget);
                        }
                    }
                }
            } else if (type.equals("music.time")) {
                int[] time = intent.getIntArrayExtra("value");
                if (time != null && time.length == 2) {
                    appWidgetManager = AppWidgetManager.getInstance(context);
                    if (appWidgetManager != null) {
                        for (int appWidgetId22 : appWidgetManager.getAppWidgetIds(getComponentName(context))) {
                            widget = new RemoteViews(context.getPackageName(), R.layout.btmusic);
                            boolean hasour = Util.getHasHour((long) time[1]);
                            widget.setTextViewText(R.id.btmusiccurtime, Util.getTimeFormatValue((long) time[0], hasour));
                            widget.setTextViewText(R.id.btmusicdurtime, context.getString(R.string.sep) + Util.getTimeFormatValue((long) time[1], hasour));
                            appWidgetManager.partiallyUpdateAppWidget(appWidgetId22, widget);
                        }
                    }
                }
            } else if (type.equals("music.state")) {
                int intExtra = intent.getIntExtra("value", 0);
            }
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateMusic(context, appWidgetManager, appWidgetId);
        }
        context.sendBroadcast(new Intent("hct.btmusic.info"));
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateMusic(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.btmusic);
        Bundle newOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        if (!(newOptions == null || newOptions.getInt("appWidgetCategory", -1) == 2)) {
            Intent musicalumb = new Intent();
            musicalumb.setComponent(new ComponentName("com.microntek.btmusic", "com.microntek.btmusic.MainActivity"));
            widget.setOnClickPendingIntent(R.id.btmusicalumb, PendingIntent.getActivity(context, 0, musicalumb, 0));
        }
        widget.setOnClickPendingIntent(R.id.btmusicpre, PendingIntent.getBroadcast(context, 0, new Intent("hct.btmusic.prev"), 0));
        widget.setOnClickPendingIntent(R.id.btmusicnext, PendingIntent.getBroadcast(context, 0, new Intent("hct.btmusic.next"), 0));
        widget.setOnClickPendingIntent(R.id.btmusicplaypause, PendingIntent.getBroadcast(context, 0, new Intent("hct.btmusic.playpause"), 0));
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }

    private ComponentName getComponentName(Context context) {
        if (this.mComponentName == null) {
            this.mComponentName = new ComponentName(context, getClass());
        }
        return this.mComponentName;
    }
}
