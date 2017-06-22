package mohsen.muhammad.minimalistmusicplayer.explorer.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import mohsen.muhammad.minimalistmusicplayer.R;
import mohsen.muhammad.minimalistmusicplayer.breadcrumb.BreadcrumbManager;
import mohsen.muhammad.minimalistmusicplayer.explorer.ExplorerManager;
import mohsen.muhammad.minimalistmusicplayer.files.FileModel;
import mohsen.muhammad.minimalistmusicplayer.util.Util;

/**
 * Created by muhammad.mohsen on 2/18/2017.
 * DIRECTORY ViewHolder class for the Explorer RecyclerView
 */

public class DirectoryViewHolder extends ExplorerViewHolder {

	@BindView(R.id.textViewDirectoryName) TextView mTextViewDirectoryName;
	@BindView(R.id.textViewMediaFileCount) TextView mTextViewMediaFileCount;
	@BindView(R.id.imageViewIcon) ImageView mImageViewIcon;

	public DirectoryViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	@Override
	public void bind(FileModel fileModel) {
		super.bind(fileModel); // set the member variable

		// this one's pretty easy
		mTextViewDirectoryName.setText(fileModel.name);

		// we have to jump a few hoops for this one
		String trackCount;
		if (fileModel.trackCount != -1)
			trackCount = getContext().getResources().getQuantityString(R.plurals.trackCount, fileModel.trackCount, String.valueOf(fileModel.trackCount));
		else
			trackCount = getContext().getResources().getString(R.string.loading_metadata);

		mTextViewMediaFileCount.setText(trackCount);
	}

	@Override
	public void setClickListeners() {
		itemView.setOnClickListener(new DirectoryItemOnClickListener());
	}

	// has direct access to members
	private class DirectoryItemOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			File f = getFileModel();
			Util.setCurrentDirectory(f);

			// repopulate the recycler views
			ExplorerManager.update(f);
			BreadcrumbManager.update(f);
		}
	}
}
