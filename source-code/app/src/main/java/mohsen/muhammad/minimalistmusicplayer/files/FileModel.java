package mohsen.muhammad.minimalistmusicplayer.files;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by muhammad.mohsen on 4/15/2017.
 * holds a file's metadata.
 * metadata is obtained asynchronously via the MetadataAsyncTask
 */

public class FileModel extends File {
	public String name;

	public String album;
	public String artist;
	public String duration;

	public int trackCount;

	public FileModel(@NonNull String pathname) {
		super(pathname);
		name = getName();

		album = "";
		artist = "";
		duration = "";

		trackCount = -1;
	}
}
