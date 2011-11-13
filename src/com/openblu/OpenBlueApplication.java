package com.openblu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;


public class OpenBlueApplication extends Application {

	public Intent createHomeIntent(Context c)
	{
		Intent home = new Intent(c, OpenBluActivity.class);
		return home;
	}
	public static String getAppName(Context context){
		String name = context.getResources().getString(R.string.app_name);
		return name;
	}
}
