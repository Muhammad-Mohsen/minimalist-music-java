package mohsen.muhammad.minimalistmusicplayer.files;

import android.support.annotation.NonNull;

import java.io.File;

import mohsen.muhammad.minimalistmusicplayer.explorer.holders.TrackViewHolder;

/**
 * Created by muhammad.mohsen on 4/15/2017.
 * Holds a file's metadata.
 * Offers no distinction between a music file and a directory...just like java
 * Metadata is obtained asynchronously via the MetadataAsyncTask
 */

public class FileModel extends File {
	public String name;

	public String album;
	public String artist;
	public String duration;

	public int trackCount;

	public TrackViewHolder.SelectionState selectionState;

	public FileModel(@NonNull String pathname) {
		super(pathname);
		name = getName();

		album = "";
		artist = "";
		duration = "";

		trackCount = -1;

		selectionState = TrackViewHolder.SelectionState.NONE;
	}
}
