package downloadmanager;


public class Main {
	/**
	 * Test the methods of the DownloadManager
	 *
	 * @throws InterruptedException
	 */
	public static void test() throws InterruptedException {
		DownloadManager manager = new DownloadManager();

		manager.addDownload("https://webstockreview.net/images/brick-clipart-super-mario-19.png");
		Thread.sleep(1200);
		for(int i = 0; i < 4; i++) {
			System.out.println(manager.getAttributesOfDownload(0, i));
		}

		manager.pauseDownload(0);
		Thread.sleep(500);
		for(int i = 0; i < 4; i++) {
			System.out.println(manager.getAttributesOfDownload(0, i));
		}

		manager.startDownload(0);
		Thread.sleep(1000);
		for(int i = 0; i < 4; i++) {
			System.out.println(manager.getAttributesOfDownload(0, i));
		}
	}

	/**
	 * Main (tests)
	 *
	 * @param String args
	 *
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws InterruptedException {
		test();
	}
}
