package downloadmanager;

import java.net.URL;

public class DownloadFactory {
	/**
	 * Creates an instance of Download with a valid URL
	 *
	 * @param url
	 *
	 * @return Download
	 */
	public static Download createDownload(String url) {
		URL verifiedURL = verifyUrl(url);
		if(verifiedURL != null) {
			return new Download(verifiedURL);
		}

		return null;
	}

	/**
	 * Returns a valid url
	 *
	 * @param String url
	 *
	 * @return URL
	 */
	private static URL verifyUrl(String url) {
		if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
			return null;
		}

		URL verifiedUrl = null;
		try {
			verifiedUrl = new URL(url);
		} catch (Exception e) {
			return null;
		}

		if (verifiedUrl.getFile().length() < 2) {
			return null;
		}

		return verifiedUrl;
	}
}
