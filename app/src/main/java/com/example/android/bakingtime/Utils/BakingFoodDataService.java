package com.example.android.bakingtime.Utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.bakingtime.BakingWidgetProvider;
import com.example.android.bakingtime.Model.BakingFood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingFoodDataService extends IntentService {

    private static final String TAG = "WidgetService";

    public static final String UPDATE_BAKING_WIDGETS =
            "com.example.android.bakingtime.action.update_baking_widgets";

    private List<BakingFood> list = new ArrayList<>();

    private List<BakingFood> tempList = new ArrayList<>();
    private Context context = this;

    public BakingFoodDataService() {
        super("BakingFoodDataService");
    }

    /**
     * Starts this service to perform fetching random baking food from the web API action with the given
     * parameters. If the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateBakingWidgets(Context context) {
        Intent intent = new Intent(context, BakingFoodDataService.class);
        intent.setAction(UPDATE_BAKING_WIDGETS);
        context.startService(intent);
    }


    /**
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (UPDATE_BAKING_WIDGETS.equals(action)) {
                handleActionUpdateBakingWidgets();
            }
        }

    }

    public void handleActionUpdateBakingWidgets(){

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<BakingFood>> call = apiInterface.getBakingFoods();
        call.enqueue(new Callback<List<BakingFood>>() {
            @Override
            public void onResponse(Call<List<BakingFood>> call, Response<List<BakingFood>> response) {
                list = response.body();

                // generate random index between 0 to (list size - 1). eg list size of 4, will an integer generate between 0 to 3
                int randomInt = 0 + (int)(Math.random() * (((list.size() - 1) - 0) + 1));

                tempList.add(list.get(randomInt)); //TODO not random index yet

                Log.e(TAG, "onResponse "+ list.get(randomInt).getName());

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));
                //Now update all widgets
                BakingWidgetProvider.updateBakingFoodWidgets(context, appWidgetManager, tempList, appWidgetIds);
            }

            @Override
            public void onFailure(Call<List<BakingFood>> call, Throwable t) {
                Log.e(TAG, "onFailure "+ t.getLocalizedMessage());
            }
        });

    }
}
