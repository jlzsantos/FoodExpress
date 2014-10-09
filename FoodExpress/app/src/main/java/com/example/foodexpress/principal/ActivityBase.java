package com.example.foodexpress.principal;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by jose.santos on 07/10/2014.
 */
public class ActivityBase extends Activity {

    private static final String TAG = ActivityBase.class.getName();
    private static ArrayList<Activity> activities=new ArrayList<Activity>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        activities.remove(this);
    }

    public static void finishAll()
    {
        for(Activity activity:activities)
            activity.finish();
    }

    public static void finishAll(String excludeActivityName)
    {
        for(Activity activity:activities) {
            if (!activity.getLocalClassName().toUpperCase().equals(excludeActivityName.toUpperCase())) {
                activity.finish();
            }
        }
    }
}
