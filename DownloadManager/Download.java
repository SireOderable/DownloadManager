package downloadmanager;

import java.io.*;
import java.net.*;
import java.util.*;

public class Download extends Observable implements Runnable {
	/** Max buffer size */
	private static final int MAX_BUFFER_SIZE = 1024;

	/** All state codes */
	public static final int NOTSTARTED = 0;
	public static final int DOWNLOADING = 1;
	public static final int PAUSED = 2;
	public static final int FINISHED = 3;
	public static final int CANCELLED = 4;
	public static final int ERROR = 5;

	/** Names of the state that correspond to a state code */
	public static final String[] STATES =
		{ "Not Started", "Downloading", "Paused", "Finished", "Cancelled", "Error" };

	/** Url of the downloaded file */
	private URL url;
	/** Current state of the download */
	private int state;
	/** Size of the downloaded file */
	private int size;
	/** How much bits downloaded */
	private int downloaded;

	/**
	 * Constructor
	 *
	 * @param URL u
	 *
	 * @return Download
	 */
	public Download(URL u) {
		this.url = u;
		this.state = NOTSTARTED;
		this.size = -1;
		this.downloaded = 0;
	}

	/**
	 * Notify all observers that the state of download has changed
	 */
	private void stateChanged() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Start the download (or resume it) if it's not already done
	 */
	public void download() {
		if(this.state != DOWNLOADING) {
			this.state = DOWNLOADING;
			this.stateChanged();
			Thread thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Change the state of the Observable and pause the download
	 */
	public void pause() {
		this.state = PAUSED;
		this.stateChanged();
	}

	/**
	 * Change the state of the Observable and cancel the download
	 */
	public void cancel() {
		this.state = CANCELLED;
		this.stateChanged();
	}

	/**
	 * Change the state of the Observable and stop the download because of an error
	 */
	private void error() {
		this.state = ERROR;
		this.stateChanged();
	}

	/**
	 * Get the url of the Download
	 *
	 * @return URL
	 */
	public URL getUrl() {
		return this.url;
	}

	/**
	 * Get the size of the file to download
	 *
	 * @return int
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Get the progress of the download
	 *
	 * @return float
	 */
	public float getPercentageDownloaded() {
		return ((float) this.downloaded / this.size) * 100;
	}

	/**
	 * Get the state of the download
	 *
	 * @return int
	 */
	public int getState() {
		return this.state;
	}

	/**
	 * Get name of the file to download
	 *
	 * @param url
	 *
	 * @return String
	 */
	public String getFileName() {
        String fileName = this.url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }

	/**
	 * Download file (method of Runnable)
	 */
	public void run() {
		RandomAccessFile file = null;
		InputStream stream = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
			connection.setRequestProperty("User-Agent", "NING/1.0");
			connection.connect();

			if (connection.getResponseCode() / 100 != 2) {
				this.error();
			}

			int contentLength = connection.getContentLength();
			if (contentLength < 1) {
				this.error();
			}

			if (this.size == -1) {
				this.size = contentLength;
				this.stateChanged();
			}

			file = new RandomAccessFile(this.getFileName(), "rw");
			file.seek(this.downloaded);

			stream = connection.getInputStream();
			while (this.state == DOWNLOADING && this.getPercentageDownloaded() != 100) {
				byte buffer[];
				if (this.size - this.downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}

				int read = stream.read(buffer);
				if (read == -1) {
					break;
				}

				file.write(buffer, 0, read);
				this.downloaded += read;
				this.stateChanged();
			}

			if (this.state == DOWNLOADING) {
				this.state = FINISHED;
				this.stateChanged();
			}
		} catch (Exception e) {
			this.error();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (Exception e) {}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {}
			}
		}
	}
}
