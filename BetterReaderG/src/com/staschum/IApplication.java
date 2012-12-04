package com.staschum;

import android.content.Context;
import com.staschum.managers.DescriptionManager;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public interface IApplication {
	Context getApplicationContext();

	DescriptionManager getDescriptionManager();
}
