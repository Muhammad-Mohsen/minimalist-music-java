package mohsen.muhammad.minimalistmusicplayer.explorer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import mohsen.muhammad.minimalistmusicplayer.files.FileCache;

/**
 * Created by muhammad.mohsen on 6/13/2017.
 * controls layout properties (e.g. scroll position for each directory, display of permission request layout)
 * for the explorer view
 */
public class ExplorerManager {

	private static RecyclerView sRecyclerViewExplorer;
	private static LinearLayout sLinearLayoutPermission;
	private static LinearLayout sLinearLayoutEmptyDir;

	private static HashMap<String, Integer> sScrollPositionCache = new HashMap<>();

	public static void initialize(RecyclerView recyclerViewExplorer, LinearLayout linearLayoutPermission, LinearLayout linearLayoutEmptyDir) {
		sRecyclerViewExplorer = recyclerViewExplorer;
		sLinearLayoutPermission = linearLayoutPermission;
		sLinearLayoutEmptyDir = linearLayoutEmptyDir;

		// doesn't do anything right now.
		// scrolling has a noticeable performance hit
		sRecyclerViewExplorer.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}
		});
	}
	public static void terminate() {
		sRecyclerViewExplorer.clearOnScrollListeners();

		sRecyclerViewExplorer = null;
		sLinearLayoutPermission = null;
		sLinearLayoutEmptyDir = null;
	}

	// called when the current directory is changed
	public static void onDirectoryChange(File dir) {
		ExplorerRecyclerViewAdapter adapter = getExplorerAdapter();
		if (adapter != null)
			adapter.update(FileCache.getPathFileModels(dir));
	}

	// called when the current track changes (playback completes, or another track is selected)
	// if the directory was changed between track changes, the oldPosition will be -1 (check is made in PlaybackManager)
	public static void onCurrentTrackChange(int newPosition, int oldPosition) {
		ExplorerRecyclerViewAdapter adapter = getExplorerAdapter();
		if (adapter != null)
			adapter.updateCurrentTrack(newPosition, oldPosition);
	}

	// called when the current playlist is changed
	// if the directory was changed between playlist changes, the oldPositionList will be an empty list (check is made in PlaybackManager)
	public static void onPlaylistChange(List<Integer> newPositionList, List<Integer> oldPositionList) {
		ExplorerRecyclerViewAdapter adapter = getExplorerAdapter();
		if (adapter != null)
			adapter.updatePlaylist(newPositionList, oldPositionList);
	}

	public static void showHidePermissionLayout(boolean show) {
		if (sLinearLayoutPermission != null) {
			if (show)
				sLinearLayoutPermission.setVisibility(View.VISIBLE);

			else
				sLinearLayoutPermission.setVisibility(View.GONE);
		}

	}

	public static ExplorerRecyclerViewAdapter getExplorerAdapter() {
		// if the object is not initialized, return.
		if (sRecyclerViewExplorer == null)
			return null;

		return (ExplorerRecyclerViewAdapter) sRecyclerViewExplorer.getAdapter();
	}

	public static void scrollToCachedPosition(String dir) {
		sRecyclerViewExplorer.scrollToPosition(getScrollPosition(dir));
	}
	private static void setScrollPosition(String dir, int scrollPosition) {
		sScrollPositionCache.put(dir, scrollPosition);
	}
	private static int getScrollPosition(String dir) {
		if (sScrollPositionCache.containsKey(dir))
			return sScrollPositionCache.get(dir);

		return 0;
	}
}
