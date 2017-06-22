package mohsen.muhammad.minimalistmusicplayer.explorer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by muhammad.mohsen on 6/13/2017.
 * custom item animator for the Explorer recycler view
 */

public class ExplorerItemAnimator extends DefaultItemAnimator {


	@Override
	public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
		return false;
	}

	@Override
	public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
		if (preLayoutInfo != null && (preLayoutInfo.left != postLayoutInfo.left || preLayoutInfo.top != postLayoutInfo.top)) {
			return animateMove(viewHolder, preLayoutInfo.left, preLayoutInfo.top, postLayoutInfo.left, postLayoutInfo.top);

		} else {
			return animateAdd(viewHolder);
		}
	}

	@Override
	public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
		return false;
	}

	@Override
	public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
		return false;
	}
}
