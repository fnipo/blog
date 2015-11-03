package com.wordpress.felipenipo.android;

import com.wordpress.felipenipo.network.NetworkQueue;

import android.app.Application;

public class GoogleVolleyApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		NetworkQueue.getInstance().init(this);
	}
	
}
