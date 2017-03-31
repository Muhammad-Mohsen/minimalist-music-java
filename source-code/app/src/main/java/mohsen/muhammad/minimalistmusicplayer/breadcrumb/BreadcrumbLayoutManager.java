package mohsen.muhammad.minimalistmusicplayer.breadcrumb;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import java.io.File;

import mohsen.muhammad.minimalistmusicplayer.R;
import mohsen.muhammad.minimalistmusicplayer.util.Anim;
import mohsen.muhammad.minimalistmusicplayer.util.FileHelper;

/**
 * Created by muhammad.mohsen on 6/15/2017.
 * Controls layout properties (scroll position, back button)
 * for the breadcrumb bar layout
 */
public class BreadcrumbLayoutManager {

	private static RecyclerView sRecyclerViewBreadcrumb;
	private static ImageButton sImageButtonBack;

	public static void initialize(RecyclerView breadcrumb, ImageButton back) {
		sRecyclerViewBreadcrumb = breadcrumb;
		sImageButtonBack = back;
	}
	public static void terminate() {
		sRecyclerViewBreadcrumb = null;
		sImageButtonBack = null;
	}

	private static BreadcrumbBarRecyclerAdapter getBreadcrumbAdapter() {
		if (sRecyclerViewBreadcrumb != null)
			return (BreadcrumbBarRecyclerAdapter) sRecyclerViewBreadcrumb.getAdapter();

		return null;
	}

	// forward means that we're going deeper into the directory hierarchy
	private static void animateBackButton(boolean forward) {
		if (forward)
			Anim.animateDrawable(sImageButtonBack, R.drawable.anim_root_back);

		else
			Anim.animateDrawable(sImageButtonBack, R.drawable.anim_back_root);
	}

	public static void update(File currentDirectory) {
		BreadcrumbBarRecyclerAdapter breadcrumbAdapter = BreadcrumbLayoutManager.getBreadcrumbAdapter();
		if (breadcrumbAdapter != null)
			breadcrumbAdapter.update(currentDirectory);

		// if currently at the root, animate to the root icon
		if (currentDirectory.getAbsolutePath().equals(FileHelper.ROOT))
			animateBackButton(false);

		// if at a root direct child AND displaying the root icon, animate to the back icon
		else if (currentDirectory.getParent().equals(FileHelper.ROOT) && (int)sImageButtonBack.getTag() == R.drawable.anim_back_root)
			animateBackButton(true);
	}
}
