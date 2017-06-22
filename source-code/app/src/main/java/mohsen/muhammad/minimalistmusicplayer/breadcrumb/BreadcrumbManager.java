package mohsen.muhammad.minimalistmusicplayer.breadcrumb;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
public class BreadcrumbManager {

	private static RecyclerView sRecyclerViewBreadcrumb;
	private static ImageButton sImageButtonBack;

	public static void initialize(RecyclerView breadcrumb, ImageButton back, File currentDirectory) {
		// initialize references
		sRecyclerViewBreadcrumb = breadcrumb;
		sImageButtonBack = back;

		// set back button icon.
		// a lot of work for such a simple proposition
		int animationResourceId;
		int tag;
		if (currentDirectory.getAbsolutePath().equals(FileHelper.ROOT)) {
			animationResourceId = R.drawable.anim_root_back;
			tag = R.drawable.anim_back_root;

		} else {
			animationResourceId = R.drawable.anim_back_root;
			tag = R.drawable.anim_root_back;
		}

		Drawable drawable = ContextCompat.getDrawable(sImageButtonBack.getContext(), animationResourceId);
		sImageButtonBack.setImageDrawable(drawable);

		// because the animation is not started,
		// the tag should be the opposite of the animation drawable being set on the button
		sImageButtonBack.setTag(tag);
	}
	public static void terminate() {
		sRecyclerViewBreadcrumb = null;
		sImageButtonBack = null;
	}

	public static void update(File currentDirectory) {
		// if the class is not initialized, return
		if (sRecyclerViewBreadcrumb == null)
			return;

		BreadcrumbBarRecyclerAdapter breadcrumbAdapter = BreadcrumbManager.getBreadcrumbAdapter();
		if (breadcrumbAdapter != null)
			breadcrumbAdapter.update(currentDirectory);

		sRecyclerViewBreadcrumb.scrollToPosition(currentDirectory.getAbsolutePath().split("/").length - 2);

		// if currently at the root, animate to the root icon
		if (currentDirectory.getAbsolutePath().equals(FileHelper.ROOT))
			animateBackButton(false);

			// if not at the root AND not displaying the back icon, animate to it
		else if (!currentDirectory.getAbsolutePath().equals(FileHelper.ROOT) && (int)sImageButtonBack.getTag() != R.drawable.anim_root_back)
			animateBackButton(true);
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
}
