package com.microntek;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class Util {
    public static String getTimeFormatValue(long time, boolean hashour) {
        long h = ((time / 1000) / 60) / 60;
        long m = ((time / 1000) / 60) % 60;
        long s = (time / 1000) % 60;
        if (hashour) {
            return getNumberValue(h) + ":" + getNumberValue(m) + ":" + getNumberValue(s);
        }
        return getNumberValue(m) + ":" + getNumberValue(s);
    }

    public static boolean getHasHour(long time) {
        return ((time / 1000) / 60) / 60 > 0;
    }

    public static String getNumberValue(long number) {
        String ss = "";
        return "" + (number / 10) + (number % 10);
    }

    public static Bitmap getMp3Art(Context context, long album_id, long song_id) {
        Bitmap bm = null;
        if (album_id < 0 && song_id < 0) {
            return null;
        }
        ParcelFileDescriptor pfd;
        if (album_id > 0) {
            try {
                pfd = context.getContentResolver().openFileDescriptor(Uri.parse("content://media/external/audio/media/" + song_id + "/albumart"), "r");
                if (pfd != null) {
                    bm = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            try {
                pfd = context.getContentResolver().openFileDescriptor(ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), album_id), "r");
                if (pfd != null) {
                    bm = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                }
            } catch (Exception e22) {
                e22.printStackTrace();
            }
        }
        return bm;
    }

    public static Bitmap getCicleProgress(int w, int[] time) {
        Bitmap output = Bitmap.createBitmap(w, w, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        float centreX = ((float) w) / 2.0f;
        float centreY = ((float) w) / 2.0f;
        float radius = (((float) w) - 8.0f) / 2.0f;
        paint.setColor(Color.argb(48, 255, 255, 255));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4.0f);
        paint.setAntiAlias(true);
        canvas.drawCircle(centreX, centreY, radius, paint);
        paint.setColor(Color.rgb(49, 226, 124));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4.0f);
        RectF oval = new RectF(centreX - radius, centreY - radius, centreX + radius, centreY + radius);
        float sweepAngle = 0.0f;
        if (time[1] > 0) {
            sweepAngle = 360.0f * (((float) time[0]) / ((float) time[1]));
        }
        canvas.drawArc(oval, 270.0f, sweepAngle, false, paint);
        if (time[0] != 0) {
            paint.setColor(Color.parseColor("#fafb9a"));
            paint.setStyle(Style.FILL);
            paint.setStrokeWidth(8.0f);
            double dotRadians = Math.toRadians((double) (270.0f + sweepAngle));
            canvas.drawCircle(centreX + ((float) (((double) radius) * Math.cos(dotRadians))), centreY + ((float) (((double) radius) * Math.sin(dotRadians))), 4.0f, paint);
        }
        return output;
    }
}
