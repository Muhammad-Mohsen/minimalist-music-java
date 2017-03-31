package mohsen.muhammad.minimalistmusicplayer.files;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import mohsen.muhammad.minimalistmusicplayer.util.FileHelper;

/**
 * Created by muhammad.mohsen on 3/17/2017.
 * holds a list of visited directory items.
 */
public class FileCache {

	// cache
	private static HashMap<String, ArrayList<FileModel>> sFileCache = new HashMap<>();
	private static HashMap<String, Long> sLastModifiedCache = new HashMap<>();

	// cache API
	public static ArrayList<FileModel> getPathFileModels(File f) {
		return getPathFileModels(f.getAbsolutePath());
	}
	private static ArrayList<FileModel> getPathFileModels(String path) {
		ArrayList<FileModel> fileModels = sFileCache.get(path);

		File f = new File(path);

		// if not cached, or directory was modified more recently than the cache
		if (fileModels == null || f.lastModified() > sLastModifiedCache.get(path)) {
			fileModels = FileHelper.listFileModels(path);
			sFileCache.put(path, fileModels);
			sLastModifiedCache.put(path, f.lastModified());
		}

		return fileModels;
	}
}
