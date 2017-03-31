package mohsen.muhammad.minimalistmusicplayer.util;

import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import mohsen.muhammad.minimalistmusicplayer.files.FileModel;
import mohsen.muhammad.minimalistmusicplayer.files.MetadataAsyncTask;

/**
 * Created by muhammad.mohsen on 3/19/2017.
 * contains methods to help with listing files, sorting, etc
 */
public class FileHelper {

	public static final String ROOT = "/storage"; // root directory
	private static final List<String> MEDIA_EXTENSIONS = Arrays.asList("mp3", "wav"); // supported media extensions

	private MediaMetadataRetriever mMetadataRetriever;
	private File mFile;

	// ctor
	public FileHelper(File file) {
		mFile = file;

		mMetadataRetriever = new MediaMetadataRetriever();

		if (isTrack(file))
			mMetadataRetriever.setDataSource(file.getPath());
	}

	// metadata getters
	public String getArtist() {
		String artist = mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

		if (artist == null || artist.equals(""))
			artist = "Unknown"; // TODO extract string resource

		return artist;
	}
	public String getAlbum() {
		String album = mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);

		if (album == null || album.equals(""))
			album = "Unknown"; // TODO extract string resource

		return album;
	}
	public String getDuration() {
		String duration = mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

		if (duration != null) {
			long longDuration = Long.parseLong(duration);

			// format the duration string
			duration = String.format(new Locale("US"), "%02d:%02d",
					TimeUnit.MILLISECONDS.toMinutes(longDuration),
					TimeUnit.MILLISECONDS.toSeconds(longDuration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(longDuration))
			);
		}

		return duration;
	}
	public int getTrackCount() {
		if (mFile.isDirectory()) {
			File[] tracks = mFile.listFiles(new FileHelper.MediaFileFilter());
			if (tracks != null)
				return tracks.length;
		}

		return 0;
	}

	private static boolean isTrack(File f) {
		return MEDIA_EXTENSIONS.contains(getExtension(f));
	}

	private static String getExtension(File file) {
		String filenameArray[] = file.getName().split("\\.");
		return filenameArray[filenameArray.length - 1];
	}

	// sigh
	// http://stackoverflow.com/questions/1445233/is-it-possible-to-solve-the-a-generic-array-of-t-is-created-for-a-varargs-param
	@SuppressWarnings("unchecked")
	public static ArrayList<FileModel> listFileModels(String path) {
		ArrayList<FileModel> fileModels = new ArrayList<>();

		File[] files = (new File(path)).listFiles(new ExplorerFileFilter());

		if (files == null)
			return new ArrayList<>();

		Arrays.sort(files, new FileComparator());

		for (File f : files)
			fileModels.add(new FileModel(f.getAbsolutePath()));

		new MetadataAsyncTask().execute(fileModels);

		return fileModels;
	}

	// FileFilter implementation that accepts media files defined by the media extensions string array
	private static class MediaFileFilter implements FileFilter {

		@Override
		public boolean accept(File file) {
			return MEDIA_EXTENSIONS.contains(getExtension(file));
		}
	}

	// FileFilter implementation that accepts directory/media files.
	private static class ExplorerFileFilter implements FileFilter {

		@Override
		public boolean accept(File file) {
			return file.isDirectory() || MEDIA_EXTENSIONS.contains(getExtension(file));
		}
	}

	private static class FileComparator implements Comparator<File> {
		@Override
		public int compare(File o1, File o2) {
			if (o1.isDirectory() && o2.isDirectory()) // if both are directories, compare their names
				return o1.getName().compareToIgnoreCase(o2.getName());

			else if (o1.isDirectory() && !o2.isDirectory()) // if the first is a directory, it's always first
				return -1;

			else if (o2.isDirectory()) // if the second is a directory, it's always first
				return 1;

			else
				return o1.getName().compareToIgnoreCase(o2.getName()); // if both are tracks, compare their names
		}
	}
}
