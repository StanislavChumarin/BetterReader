package com.staschum;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 02.12.12
 * Time: 16:37
 *
 */
public abstract class Utils {

	public static byte[] readFully(InputStream inputStream, boolean closeStream) throws IOException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			final byte[] buffer = new byte[8192];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			return outputStream.toByteArray();
		}
		finally {
			if (closeStream) {
				inputStream.close();
			}
		}
	}

	public static String getHtml(String url) {
		String result = null;
		try {
			InputStream is = new NetworkManager().openHttpConnection(url, 5);
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);

			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			result = new String(baf.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}
}
