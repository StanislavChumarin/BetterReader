package com.staschum;

import android.app.Application;
import android.content.Context;
import com.staschum.html2view.managers.DescriptionManager;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class App extends Application implements IApplication {

	private static IApplication instance;

	public App() {
		instance = this;
	}

	public static IApplication getInstance() {
		return instance;
	}

	public static Context getContext() {
		return instance.getApplicationContext();
	}

	@Override
	public DescriptionManager getDescriptionManager() {
		return null;
	}
}
