package com.example.android.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.android.bakingtime.Model.BakingFood;
import com.example.android.bakingtime.Utils.ApiClient;
import com.example.android.bakingtime.Utils.ApiInterface;
import com.example.android.bakingtime.Utils.WidgetService;
import com.example.android.bakingtime.View.RecipeName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetProvider";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                List<BakingFood> bakingFood, int appWidgetId) {

        RemoteViews widget=new RemoteViews(context.getPackageName(),
                R.layout.widget_provider);

        Intent svcIntent=new Intent(context, WidgetService.class);

        Bundle extraBundle = new Bundle();
        extraBundle.putSerializable(RecipeName.EXTRA_FOOD_LIST_INGREDIENT, (Serializable) bakingFood.get(0).getIngredients());
        svcIntent.putExtra(RecipeName.BUNDLE_FOOD_LIST_INGREDIENT, extraBundle);

        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        widget.setTextViewText(R.id.widget_ingredient_list_title, bakingFood.get(0).getName());

        widget.setRemoteAdapter(R.id.widget_ingredients_list,
                svcIntent);

        Intent clickIntent=new Intent(context, RecipeName.class);
        clickIntent.putExtra(RecipeName.EXTRA_FOOD_LIST, (Serializable) bakingFood);
        PendingIntent clickPI=PendingIntent
                .getActivity(context, 0,
                        clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        //widget.setPendingIntentTemplate(R.id.widget_ingredients_list, clickPI);

        widget.setOnClickPendingIntent(R.id.widget_ingredient_list_title, clickPI);
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }

    @Override
    public void onUpdate(final Context ctxt, final AppWidgetManager appWidgetManager,
                         final int[] appWidgetIds) {
        updateBakingFoodRemoteWidgets(ctxt, appWidgetManager, appWidgetIds);
    }


    private static void updateBakingFoodRemoteWidgets(final Context ctxt, final AppWidgetManager appWidgetManager,
                                                      final int[] appWidgetIds) { //you can add parameters to this to be used in Service

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // There may be multiple widgets active, so update all of them
        for (final int appWidgetId : appWidgetIds) {

            Call<List<BakingFood>> call = apiInterface.getBakingFoods();
            call.enqueue(new Callback<List<BakingFood>>() {
                @Override
                public void onResponse(Call<List<BakingFood>> call, Response<List<BakingFood>> response) {
                    //list = response.body();
                    List<BakingFood> list = response.body();

                    List<BakingFood> tempList = new ArrayList<>();

                    // generate random index between 0 to (list size - 1). eg list size of 4, will an integer generate between 0 to 3
                    int randomInt = 0 + (int) (Math.random() * (((list.size() - 1) - 0) + 1));

                    tempList.add(list.get(randomInt));

                    updateAppWidget(ctxt, appWidgetManager, tempList, appWidgetId);
                }

                @Override
                public void onFailure(Call<List<BakingFood>> call, Throwable t) {
                    //Log.e(TAG, "onFailure "+ t.getLocalizedMessage());
                }
            });
        }

    }
}

