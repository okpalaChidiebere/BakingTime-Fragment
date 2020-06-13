package com.example.android.bakingtime.Utils;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new WidgetIngredientListFactory(this.getApplicationContext(),
                intent));
    }
}
