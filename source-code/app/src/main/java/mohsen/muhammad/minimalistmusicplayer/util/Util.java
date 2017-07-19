package mohsen.muhammad.minimalistmusicplayer.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by muhammad.mohsen on 4/16/2017.
 * holds application-wide state variables (such as current directory, active context variables)
 * it's also responsible for persisting those variables (in shared preferences)
 * TODO rename the thing to PersistenceManager or something
 */
public class Util {

	private static final String EMPTY_STRING = "";
	private static final String MINIMALIST_SHARED_PREFERENCE = "Minimalist";

	private static final String DIRECTORY_SHARED_PREFERENCE = "CurrentDirectory";

	private static final String TRACK_SHARED_PREFERENCE= "CurrentTrack";
	private static final String PLAYLIST_SHARED_PREFERENCE = "Playlist";
	//
	// current directory
	//
	private static File sCurrentDirectory = new File(FileHelper.ROOT);
	public static File getCurrentDirectory() {
		return sCurrentDirectory;
	}
	public static void setCurrentDirectory(File directory) {
		if (!directory.isDirectory())
			throw new IllegalArgumentException("File must be a directory.");

		sCurrentDirectory = directory;
	}

	public static File getSavedCurrentDirectory(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(MINIMALIST_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		String savedPath = sharedPreferences.getString(DIRECTORY_SHARED_PREFERENCE, EMPTY_STRING);

		if (!savedPath.equals(EMPTY_STRING)) {
			File savedFile = new File(savedPath);

			// only return the saved file if it exists
			// it may not exist due to the file being removed, or the SD card being unmounted!
			if (savedFile.exists())
				return savedFile;
		}

		return new File(FileHelper.ROOT);
	}
	public static void saveCurrentDirectory(Context context, File currentDirectory) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(MINIMALIST_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(DIRECTORY_SHARED_PREFERENCE, currentDirectory.getAbsolutePath());
		editor.apply();
	}
	//
	// current track
	//
	private static String sCurrentTrack;
	public static String getCurrentTrack() {
		return sCurrentTrack;
	}
	public void setCurrentTrack(String track) {
		sCurrentTrack = track;
	}

	public static String getSavedCurrentTrack(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(MINIMALIST_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		String savedCurrentTrack = sharedPreferences.getString(TRACK_SHARED_PREFERENCE, EMPTY_STRING);

		if (!savedCurrentTrack.equals(EMPTY_STRING))
			return savedCurrentTrack;

		return null;
	}
	public static void saveCurrentTrack(Context context, String currentTrack) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(MINIMALIST_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(TRACK_SHARED_PREFERENCE, currentTrack);
		editor.apply();
	}
	//
	// playlist
	//
	// we may need to store the playlist into SQLite. We'll see.
	public static ArrayList<String> getSavedPlaylistItems(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(MINIMALIST_SHARED_PREFERENCE, Context.MODE_PRIVATE);

		// get a semi colon-separated string
		String savedPlaylist = sharedPreferences.getString(PLAYLIST_SHARED_PREFERENCE, EMPTY_STRING);

		// this'll most likely throw, but let's see!
		return new ArrayList<>(Arrays.asList(savedPlaylist.split(";")));
	}
	public static void savePlaylist(Context context, ArrayList<String> playlist) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(MINIMALIST_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		StringBuilder serialized = new StringBuilder();
		for (String s : playlist) {
			serialized
					.append(s)
					.append(";");
		}

		editor.putString(TRACK_SHARED_PREFERENCE, serialized.toString());
		editor.apply();
	}
}
