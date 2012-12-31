package com.staschum;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 28.12.12
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class NetworkManager {

	private static final boolean DEBUG = false;//AppConfig.DEBUG_NETWORK;
	private static final boolean TRACE = false;//AppConfig.TRACE_NETWORK;

	private static final String TAG = NetworkManager.class.getSimpleName();

	public InputStream openHttpConnection(String url, int attempts) throws IOException {
		if (DEBUG) {
			Log.d(TAG, "openHttpConnection(): url=" + url);
		}
		URL connectionUrl = new URL(url);
		for (int i = 1; i <= attempts; i++) {
			HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) connectionUrl.openConnection();
				if (TRACE) {
					byte[] contentBytes = Utils.readFully(connection.getInputStream(), true);
					connection.disconnect();
					Log.v(TAG, "Request: http get; url=" + url + "\nResponse:\n" + new String(contentBytes, "UTF-8"));
					return new ByteArrayInputStream(contentBytes);
				}
				return new DisconnectOnCloseInputStream(connection.getInputStream(), connection);
			}
			catch (IOException ex) {
				if (DEBUG) {
					Log.w(TAG, "openHttpConnection: " + i + " try", ex);
				}
				if (connection != null) {
					connection.disconnect();
				}
				if (i == attempts) {
					throw ex;
				}
			}
		}
		return null; // should never happen
	}

	private static class DisconnectOnCloseInputStream extends FilterInputStream {
		private final HttpURLConnection connection;

		protected DisconnectOnCloseInputStream(InputStream in, HttpURLConnection connection) {
			super(in);
			this.connection = connection;
		}
		@Override
		public void close() throws IOException {
			try {
				super.close();
			}
			finally {
				connection.disconnect();
			}
		}
	}
}
