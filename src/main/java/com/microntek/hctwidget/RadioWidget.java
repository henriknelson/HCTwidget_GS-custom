package com.microntek.hctwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class RadioWidget extends AppWidgetProvider {
    private static final int[] radioBandImgArray = new int[]{R.drawable.radio_fm1, R.drawable.radio_fm2, R.drawable.radio_fm3, R.drawable.radio_am1, R.drawable.radio_am2};
    private static final int[] radioNumImgArray = new int[]{R.drawable.radio_n0, R.drawable.radio_n1, R.drawable.radio_n2, R.drawable.radio_n3, R.drawable.radio_n4, R.drawable.radio_n5, R.drawable.radio_n6, R.drawable.radio_n7, R.drawable.radio_n8, R.drawable.radio_n9};
    private ComponentName mComponentName;

    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        updateRadio(context, AppWidgetManager.getInstance(context), appWidgetId);
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
        if (action.equals("com.microntek.radio.report")) {
            String report = intent.getStringExtra("type");
            AppWidgetManager appWidgetManager;
            if (report.equals("content")) {
                int freq = intent.getIntExtra("freq", -1);
                int channel = intent.getIntExtra("channel", -1);
                if (freq != -1) {
                    appWidgetManager = AppWidgetManager.getInstance(context);
                    if (appWidgetManager != null) {
                        for (int appWidgetId : appWidgetManager.getAppWidgetIds(getComponentName(context))) {
                            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.radio);
                            int group = (-268435456 & freq) >> 28;
                            freq &= 268435455;
                            int val;
                            if (freq > 10000000) {
                                remoteViews.setImageViewResource(R.id.radio_band, radioBandImgArray[group % 3]);
                                remoteViews.setImageViewResource(R.id.radio_dan, R.drawable.radio_mhz);
                                val = freq / 10000;
                                if (val / 10000 == 0) {
                                    remoteViews.setViewVisibility(R.id.radio_f1, 8);
                                    remoteViews.setViewVisibility(R.id.radio_f2, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f2, radioNumImgArray[val / 1000]);
                                    remoteViews.setImageViewResource(R.id.radio_f3, radioNumImgArray[(val / 100) % 10]);
                                    remoteViews.setViewVisibility(R.id.dot, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f4, radioNumImgArray[(val / 10) % 10]);
                                    remoteViews.setViewVisibility(R.id.radio_f5, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f5, radioNumImgArray[val % 10]);
                                } else {
                                    remoteViews.setViewVisibility(R.id.radio_f1, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f1, radioNumImgArray[val / 10000]);
                                    remoteViews.setViewVisibility(R.id.radio_f2, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f2, radioNumImgArray[(val / 1000) % 10]);
                                    remoteViews.setImageViewResource(R.id.radio_f3, radioNumImgArray[(val / 100) % 10]);
                                    remoteViews.setViewVisibility(R.id.dot, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f4, radioNumImgArray[(val / 10) % 10]);
                                    remoteViews.setViewVisibility(R.id.radio_f5, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f5, radioNumImgArray[val % 10]);
                                }
                            } else {
                                remoteViews.setImageViewResource(R.id.radio_band, radioBandImgArray[((group - 3) % 2) + 3]);
                                remoteViews.setImageViewResource(R.id.radio_dan, R.drawable.radio_khz);
                                val = freq / 1000;
                                if (val / 1000 == 0) {
                                    remoteViews.setViewVisibility(R.id.radio_f1, 8);
                                    remoteViews.setImageViewResource(R.id.radio_f2, radioNumImgArray[0]);
                                    remoteViews.setViewVisibility(R.id.radio_f2, 8);
                                    remoteViews.setImageViewResource(R.id.radio_f3, radioNumImgArray[val / 100]);
                                    remoteViews.setViewVisibility(R.id.radio_f3, 0);
                                    remoteViews.setViewVisibility(R.id.dot, 8);
                                    remoteViews.setImageViewResource(R.id.radio_f4, radioNumImgArray[(val / 10) % 10]);
                                    remoteViews.setViewVisibility(R.id.radio_f4, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f5, radioNumImgArray[val % 10]);
                                    remoteViews.setViewVisibility(R.id.radio_f5, 0);
                                } else {
                                    remoteViews.setViewVisibility(R.id.radio_f1, 8);
                                    remoteViews.setViewVisibility(R.id.radio_f2, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f2, radioNumImgArray[val / 1000]);
                                    remoteViews.setImageViewResource(R.id.radio_f3, radioNumImgArray[(val / 100) % 10]);
                                    remoteViews.setViewVisibility(R.id.dot, 8);
                                    remoteViews.setImageViewResource(R.id.radio_f4, radioNumImgArray[(val / 10) % 10]);
                                    remoteViews.setViewVisibility(R.id.radio_f5, 0);
                                    remoteViews.setImageViewResource(R.id.radio_f5, radioNumImgArray[val % 10]);
                                }
                            }
                            appWidgetManager.partiallyUpdateAppWidget(appWidgetId, remoteViews);
                        }
                    }
                }
            } else if (report.equals("power")) {
                boolean state = intent.getBooleanExtra("state", false);
                appWidgetManager = AppWidgetManager.getInstance(context);
                if (appWidgetManager != null) {
                    for (int appWidgetId2 : appWidgetManager.getAppWidgetIds(getComponentName(context))) {
                        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.radio);
                        if (state) {
                            widget.setViewVisibility(R.id.radio_pre, 0);
                            widget.setViewVisibility(R.id.radio_next, 0);
                        } else {
                            widget.setViewVisibility(R.id.radio_pre, 4);
                            widget.setViewVisibility(R.id.radio_next, 4);
                        }
                        appWidgetManager.partiallyUpdateAppWidget(appWidgetId2, widget);
                    }
                }
            }
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateRadio(context, appWidgetManager, appWidgetId);
        }
        context.sendBroadcast(new Intent("hct.radio.request.data"));
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private ComponentName getComponentName(Context context) {
        if (this.mComponentName == null) {
            this.mComponentName = new ComponentName(context, getClass());
        }
        return this.mComponentName;
    }

    private void updateRadio(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.radio);
        Bundle newOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        if (!(newOptions == null || newOptions.getInt("appWidgetCategory", -1) == 2)) {
            Intent radioalumb = new Intent();
            radioalumb.setComponent(new ComponentName("com.microntek.radio", "com.microntek.radio.MainActivity"));
            widget.setOnClickPendingIntent(R.id.radioalumb, PendingIntent.getActivity(context, 0, radioalumb, 0));
        }
        widget.setOnClickPendingIntent(R.id.radio_pre, PendingIntent.getBroadcast(context, 0, new Intent("hct.radio.channel.prev"), 0));
        widget.setOnClickPendingIntent(R.id.radio_next, PendingIntent.getBroadcast(context, 0, new Intent("hct.radio.channel.next"), 0));
        widget.setOnClickPendingIntent(R.id.radio_power, PendingIntent.getBroadcast(context, 0, new Intent("hct.radio.power.switch"), 0));
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }
}
