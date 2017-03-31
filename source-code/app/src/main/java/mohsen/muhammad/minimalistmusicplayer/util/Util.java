package mohsen.muhammad.minimalistmusicplayer.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * Created by muhammad.mohsen on 4/16/2017.
 * holds application-wide state variables (such as current directory, active context variables)
 * TODO rename the thing
 */
public class Util {

	private static final String EMPTY_STRING = "";
	private static final String MINIMALIST_SHARED_PREFERENCE = "Minimalist";
	private static final String DIRECTORY_SHARED_PREFERENCE = "CurrentDirectory";

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

		if (savedPath.equals(EMPTY_STRING))
			return new File(FileHelper.ROOT);
		else
			return new File(savedPath);
	}
	public static void saveCurrentDirectory(Context context, File currentDirectory) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(MINIMALIST_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(DIRECTORY_SHARED_PREFERENCE, currentDirectory.getAbsolutePath());
		editor.apply();
	}
}
