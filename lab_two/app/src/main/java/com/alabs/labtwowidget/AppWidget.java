package com.alabs.labtwowidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;


public class AppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
    }

    static void updateWidget(Context ctx, AppWidgetManager appWidgetManager, int widgetID) {
        SharedPreferences sp = ctx.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        String text = sp.getString("text" + widgetID, ctx.getString(R.string.appwidget_text));
        String colorId = sp.getString("widgetColor" + widgetID, "0");
        int color;

        switch (colorId) {
            case "0":
                color = Color.BLACK;
                break;
            case "1":
                color = Color.RED;
                break;
            case "2":
                color = Color.GREEN;
                break;
            case "3":
                color = Color.YELLOW;
                break;
            default:
                color = Color.WHITE;
        }

        RemoteViews widgetView = new RemoteViews(ctx.getPackageName(), R.layout.app_widget);

        if (!text.equals("")) {
            widgetView.setTextViewText(R.id.appwidget_text, "" + text);
        }
        else {
            widgetView.setTextViewText(R.id.appwidget_text, ctx.getString(R.string.appwidget_text));
        }
        widgetView.setTextColor(R.id.appwidget_text, color);


        Intent configIntent = new Intent(ctx, MainActivity.class);
        configIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        PendingIntent pIntent = PendingIntent.getActivity(ctx, widgetID, configIntent, 0);
        widgetView.setOnClickPendingIntent(R.id.appwidget_text, pIntent);

        appWidgetManager.updateAppWidget(widgetID, widgetView);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

