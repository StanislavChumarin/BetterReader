package com.staschum.html2view.action;

import com.codeslap.groundy.CallResolver;
import com.codeslap.groundy.Groundy;
import com.codeslap.groundy.GroundyUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 25.12.12
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public class DownloadResolver extends CallResolver {

	public static final String PARAM_URL = "com.groundy.sample.param.url";

	@Override
	protected void updateData() {
		try {
			File dest = new File(getContext().getFilesDir(), "downloaded_file.ext");
			String url = getParameters().getString(PARAM_URL);
			GroundyUtils.downloadFile(getContext(), url, dest, GroundyUtils.fromResolver(this));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void prepareResult() {
		setResultCode(Groundy.STATUS_FINISHED);
	}
}
