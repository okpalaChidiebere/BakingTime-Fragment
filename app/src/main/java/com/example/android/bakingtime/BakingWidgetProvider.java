package com.example.android.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingtime.Model.BakingFood;
import com.example.android.bakingtime.Utils.BakingFoodDataService;
import com.example.android.bakingtime.View.MainActivity;
import com.example.android.bakingtime.View.RecipeName;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                List<BakingFood> bakingFood, int appWidgetId) {

        Intent intent = new Intent(context, RecipeName.class);
        intent.putExtra(RecipeName.EXTRA_FOOD_LIST, (Serializable) bakingFood);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        // Update widget text title
        views.setTextViewText(R.id.widget_ingredient_title, bakingFood.get(0).getName());

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.tv_widget_ingredient_list, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingFoodDataService.startActionUpdateBakingWidgets(context);
    }

    //we call this in the service to update all app widgets
    public static void updateBakingFoodWidgets(Context context, AppWidgetManager appWidgetManager,
                                          List<BakingFood> bakingFood, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, bakingFood, appWidgetId);
        }
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

