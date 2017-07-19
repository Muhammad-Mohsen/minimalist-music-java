package mohsen.muhammad.minimalistmusicplayer.explorer.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mohsen.muhammad.minimalistmusicplayer.files.FileModel;

/**
 * Created by muhammad.mohsen on 3/19/2017.
 * base class for the explorer recycler adapter
 * defines the bind method to set the data from a given file to the view
 */

public class ExplorerViewHolder extends RecyclerView.ViewHolder {

	private FileModel mFileModel;
	private Context mContext;

	ExplorerViewHolder(View itemView) {
		super(itemView);
		mContext = itemView.getContext();
	}

	FileModel getFileModel() {
		return mFileModel;
	}
	Context getContext() {
		return mContext;
	}

	public void bind (FileModel fileModel) {
		mFileModel = fileModel;
	}

	public void setClickListeners() {}

	/**
	 * defines the explorer recycler view adapter view types
	 */
	public enum Type {
		DIRECTORY,
		TRACK
	}

}