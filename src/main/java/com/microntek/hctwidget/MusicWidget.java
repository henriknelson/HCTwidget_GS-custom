package com.microntek.hctwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.microntek.Util;

public class MusicWidget extends AppWidgetProvider {
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
        if (action.equals("com.microntek.music.report")) {
            String type = intent.getStringExtra("type");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            if (appWidgetManager != null) {
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(getComponentName(context));
                if (appWidgetIds != null && appWidgetIds.length != 0) {
                    RemoteViews remoteViews;
                    if (type.equals("music.title")) {
                        String name = intent.getExtras().getString("value");
                        if (name != null) {
                            for (int appWidgetId : appWidgetIds) {
                                remoteViews = new RemoteViews(context.getPackageName(), R.layout.music);
                                remoteViews.setTextViewText(R.id.musicname, name);
                                appWidgetManager.partiallyUpdateAppWidget(appWidgetId, remoteViews);
                            }
                        }
                    } else if (type.equals("music.time")) {
                        int[] time = intent.getIntArrayExtra("value");
                        if (time != null && time.length == 2) {
                            int w = context.getResources().getDimensionPixelSize(R.dimen.widget_alumb);
                            for (int appWidgetId2 : appWidgetIds) {
                                remoteViews = new RemoteViews(context.getPackageName(), R.layout.music);
                                boolean hasour = Util.getHasHour((long) time[1]);
                                remoteViews.setTextViewText(R.id.musiccurtime, Util.getTimeFormatValue((long) time[0], hasour));
                                remoteViews.setTextViewText(R.id.musicdurtime, context.getString(R.string.sep) + Util.getTimeFormatValue((long) time[1], hasour));
                                if (time[0] <= time[1]) {
                                    remoteViews.setImageViewBitmap(R.id.musiccicle, Util.getCicleProgress(w, time));
                                }
                                appWidgetManager.partiallyUpdateAppWidget(appWidgetId2, remoteViews);
                            }
                        }
                    } else if (type.equals("music.state")) {
                        int playflag = intent.getIntExtra("value", 0);
                        for (int appWidgetId22 : appWidgetIds) {
                            remoteViews = new RemoteViews(context.getPackageName(), R.layout.music);
                            if (playflag == 1) {
                                remoteViews.setImageViewResource(R.id.musicplay, R.drawable.music_pause);
                            } else {
                                remoteViews.setImageViewResource(R.id.musicplay, R.drawable.music_play);
                            }
                            appWidgetManager.partiallyUpdateAppWidget(appWidgetId22, remoteViews);
                        }
                    } else if (type.equals("music.alumb")) {
                        long[] data = intent.getLongArrayExtra("value");
                        if (data != null && data.length == 2) {
                            Bitmap btmp = Util.getMp3Art(context, data[0], data[1]);
                            for (int appWidgetId222 : appWidgetIds) {
                                remoteViews = new RemoteViews(context.getPackageName(), R.layout.music);
                                if (btmp != null) {
                                    remoteViews.setImageViewBitmap(R.id.musicalumb, btmp);
                                } else {
                                    remoteViews.setImageViewResource(R.id.musicalumb, R.drawable.music_alumb);
                                }
                                try {
                                    appWidgetManager.partiallyUpdateAppWidget(appWidgetId222, remoteViews);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateMusic(context, appWidgetManager, appWidgetId);
        }
        context.sendBroadcast(new Intent("hct.music.info"));
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateMusic(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.music);
        Bundle newOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        if (!(newOptions == null || newOptions.getInt("appWidgetCategory", -1) == 2)) {
            Intent musicalumb = new Intent();
            musicalumb.setComponent(new ComponentName("com.microntek.music", "com.microntek.music.MainActivity"));
            widget.setOnClickPendingIntent(R.id.musicalumb, PendingIntent.getActivity(context, 0, musicalumb, 0));
        }
        widget.setOnClickPendingIntent(R.id.musicpre, PendingIntent.getBroadcast(context, 0, new Intent("hct.music.last"), 0));
        widget.setOnClickPendingIntent(R.id.musicnext, PendingIntent.getBroadcast(context, 0, new Intent("hct.music.next"), 0));
        widget.setOnClickPendingIntent(R.id.musicplay, PendingIntent.getBroadcast(context, 0, new Intent("hct.music.playpause"), 0));
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }

    private ComponentName getComponentName(Context context) {
        if (this.mComponentName == null) {
            this.mComponentName = new ComponentName(context, getClass());
        }
        return this.mComponentName;
    }
}
