package mohsen.muhammad.minimalistmusicplayer.explorer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.util.HashMap;

import mohsen.muhammad.minimalistmusicplayer.files.FileCache;
import mohsen.muhammad.minimalistmusicplayer.util.Util;

/**
 * Created by muhammad.mohsen on 6/13/2017.
 * controls layout properties (e.g. scroll position for each directory, display of permission request layout)
 * for the explorer view
 */
public class ExplorerLayoutManager {

	private static RecyclerView sRecyclerViewExplorer;
	private static LinearLayout sLinearLayoutPermission;
	private static LinearLayout sLinearLayoutEmptyDir;

	private static HashMap<String, Integer> sScrollPositionCache = new HashMap<>();

	public static void initialize(RecyclerView recyclerViewExplorer, LinearLayout linearLayoutPermission, LinearLayout linearLayoutEmptyDir) {
		sRecyclerViewExplorer = recyclerViewExplorer;
		sLinearLayoutPermission = linearLayoutPermission;
		sLinearLayoutEmptyDir = linearLayoutEmptyDir;

		// store the scroll position
		sRecyclerViewExplorer.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);

				String dir = Util.getCurrentDirectory().getName();
				int originalScrollPosition = getScrollPosition(dir);
				setScrollPosition(dir, originalScrollPosition + dy);
			}
		});
	}
	public static void terminate() {
		sRecyclerViewExplorer.clearOnScrollListeners();

		sRecyclerViewExplorer = null;
		sLinearLayoutPermission = null;
		sLinearLayoutEmptyDir = null;
	}

	public static RecyclerView getRecyclerViewExplorer() {
		return sRecyclerViewExplorer;
	}
	public static ExplorerRecyclerViewAdapter getExplorerAdapter() {
		if (sRecyclerViewExplorer != null)
			return (ExplorerRecyclerViewAdapter) sRecyclerViewExplorer.getAdapter();

		return null;
	}
	private static RecyclerView.LayoutManager getExplorerRecyclerLayoutManager() {
		if (sRecyclerViewExplorer != null)
			return sRecyclerViewExplorer.getLayoutManager();

		return null;
	}

	public static void scrollToCachedPosition(String dir) {
		RecyclerView.LayoutManager layoutManager = getExplorerRecyclerLayoutManager();

		if (layoutManager != null)
			layoutManager.scrollToPosition(getScrollPosition(dir));
	}
	private static void setScrollPosition(String dir, int scrollPosition) {
		sScrollPositionCache.put(dir, scrollPosition);
	}
	private static int getScrollPosition(String dir) {
		if (sScrollPositionCache.containsKey(dir))
			return sScrollPositionCache.get(dir);

		return 0;
	}

	public static void showHidePermissionLayout(boolean show) {
		if (sLinearLayoutPermission != null) {
			if (show)
				sLinearLayoutPermission.setVisibility(View.VISIBLE);

			else
				sLinearLayoutPermission.setVisibility(View.GONE);
		}

	}

	public static void update(File dir) {
		ExplorerRecyclerViewAdapter adapter = getExplorerAdapter();
		if (adapter != null)
			adapter.update(FileCache.getPathFileModels(dir));
	}
}
