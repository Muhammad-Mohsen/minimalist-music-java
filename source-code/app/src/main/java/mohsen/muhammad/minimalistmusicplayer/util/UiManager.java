package mohsen.muhammad.minimalistmusicplayer.util;

import java.io.File;

/**
 * Created by muhammad.mohsen on 4/22/2017.
 * responsible for updating UI components and views
 */

public class UiManager {

	public static void onDirectoryChange(File previous, File currnet) {
		int navigationSteps = previous.getAbsolutePath().split("/").length;
	}

}
