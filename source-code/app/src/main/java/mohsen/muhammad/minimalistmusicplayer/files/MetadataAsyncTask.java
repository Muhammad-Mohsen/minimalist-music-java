package mohsen.muhammad.minimalistmusicplayer.files;

import android.os.AsyncTask;

import java.util.ArrayList;

import mohsen.muhammad.minimalistmusicplayer.explorer.ExplorerLayoutManager;
import mohsen.muhammad.minimalistmusicplayer.explorer.ExplorerRecyclerViewAdapter;
import mohsen.muhammad.minimalistmusicplayer.util.FileHelper;
import mohsen.muhammad.minimalistmusicplayer.util.Util;

/**
 * Created by muhammad.mohsen on 4/16/2017.
 * gets a file metadata asynchronously and updates the UI.
 * obtaining metadata is dog slow. Also, notifyItemChanged has to be called somewhere.
 */

public class MetadataAsyncTask extends AsyncTask<ArrayList<FileModel>, Void, ArrayList<FileModel>> {

	@SafeVarargs
	@Override
	protected final ArrayList<FileModel> doInBackground(ArrayList<FileModel>... params) {
		for (FileModel model: params[0]) {
			FileHelper helper = new FileHelper(model);

			if (!model.isDirectory()) {
				model.artist = helper.getArtist();
				model.album = helper.getAlbum();
				model.duration = helper.getDuration();

			} else {
				model.trackCount = helper.getTrackCount();
			}
		}

		return params[0];
	}

	@Override
	protected void onPostExecute(ArrayList<FileModel> fileModels) {
		// call notifyItemRangeChanged if the current directory is the same, and the adapter is not null.
		boolean isOnCurrentDirectory = false;
		if (fileModels != null && fileModels.size() > 0)
			isOnCurrentDirectory = Util.getCurrentDirectory().getAbsolutePath().equals(fileModels.get(0).getParentFile().getAbsolutePath());

		ExplorerRecyclerViewAdapter adapter = ExplorerLayoutManager.getExplorerAdapter();
		if (adapter != null && isOnCurrentDirectory)
			adapter.notifyItemRangeChanged(0, fileModels.size());
	}
}
