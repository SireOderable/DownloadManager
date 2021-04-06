package downloadmanager;

import java.util.*;

public class DownloadManager implements Observer {
	private ArrayList<Download> downloadList;

	public DownloadManager() {
		this.downloadList = new ArrayList<Download>();
	}

	/**
	* Add download object on the list and immediatly start it
	*
	* @param url
	*/
	public void addDownload(String url) {
		Download download = DownloadFactory.createDownload(url);
		if(download != null) {
			download.addObserver(this);
			this.downloadList.add(download);

			this.startDownload(this.downloadList.indexOf(download));
		}
	}

	/**
	* Start a download (or resume it)
	*
	* @param int index
	*/
	public void startDownload(int index) {
		if(index < this.downloadList.size()) {
			this.downloadList.get(index).download();
		}
	}

	/**
	* Pause a download
	*
	* @param int index
	*/
	public void pauseDownload(int index) {
		if(index < this.downloadList.size()) {
			this.downloadList.get(index).pause();
		}
	}

	/**
	* Cancel a download
	*
	* @param int index
	*/
	public void cancelDownload(int index) {
		if(index < this.downloadList.size()) {
			this.downloadList.get(index).cancel();
		}
	}

	/**
	* Remove a download object from the list
	*
	* @param int index
	*/
	public void clearDownload(int index) {
		if(index < this.downloadList.size()) {
			this.downloadList.remove(index);
		}
	}

	/**
	* Get an attribute of a download object from the list
	*
	* @param int index
	* @param int attribute
	*
	* @return Object
	*/
	public Object getAttributesOfDownload(int index, int attribute) {
		Download download = (Download) downloadList.get(index);

		switch (attribute) {
			case 0:
			return download.getUrl();
			case 1:
			int size = download.getSize();
			return (size == -1) ? "" : Integer.toString(size);
			case 2:
			return new Float(download.getPercentageDownloaded());
			case 3:
			return Download.STATES[download.getState()];
		}
		return "";
	}

	/**
	* Return the downloadList
	*
	* @return ArrayList<Download>
	*/
	public ArrayList<Download> getDownloadList(){
		return this.downloadList;
	}

	/**
	* Update the download object in the list that has changed
	*/
	public void update(Observable o, Object arg) {
		this.downloadList.set(this.downloadList.indexOf(o), (Download)o);
	}
}
