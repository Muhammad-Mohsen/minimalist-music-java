package mohsen.muhammad.minimalistmusicplayer.explorer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import mohsen.muhammad.minimalistmusicplayer.R;
import mohsen.muhammad.minimalistmusicplayer.explorer.holders.DirectoryViewHolder;
import mohsen.muhammad.minimalistmusicplayer.explorer.holders.ExplorerViewHolder;
import mohsen.muhammad.minimalistmusicplayer.explorer.holders.TrackViewHolder;
import mohsen.muhammad.minimalistmusicplayer.files.FileModel;
import mohsen.muhammad.minimalistmusicplayer.util.enums.ExplorerViewType;

/**
 * Created by muhammad.mohsen on 12/10/2016.
 * Adapter class for the explorer RecyclerView
 */
public class ExplorerRecyclerViewAdapter extends RecyclerView.Adapter<ExplorerViewHolder>  {
	private ArrayList<FileModel> mFileModels;

	public ExplorerRecyclerViewAdapter (ArrayList<FileModel> fileModels) {
		// ArrayList has to be copied in order to separate the cache reference from the Adapter's data set reference
		// otherwise, whatever cached list that was used to initialize the adapter will be changed whenever the data set is changed
		mFileModels = new ArrayList<>(fileModels);
	}

	// whether the item is gonna be TRACK, or DIRECTORY
	@Override
	public int getItemViewType(int position) {
		if (mFileModels.get(position).isDirectory())
			return ExplorerViewType.DIRECTORY.ordinal();
		else
			return ExplorerViewType.TRACK.ordinal();
	}

	// returns the proper ViewHolder class
	@Override
	public ExplorerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		if (viewType == ExplorerViewType.DIRECTORY.ordinal())
			return new DirectoryViewHolder(inflater.inflate(R.layout.directory_list_item, parent, false));

		else if (viewType == ExplorerViewType.TRACK.ordinal())
			return new TrackViewHolder(inflater.inflate(R.layout.track_list_item, parent, false));

		return null;
	}

	// binds the ViewHolder with the content
	@Override
	public void onBindViewHolder(ExplorerViewHolder holder, int position) {
		holder.bind(mFileModels.get(position)); // bind the view holder to the data
		holder.setClickListeners();
	}

	@Override
	public int getItemCount() {
		return mFileModels.size();
	}

	void update(ArrayList<FileModel> files) {
		int initialSize = mFileModels.size();

		mFileModels.retainAll(new ArrayList<>()); // remove everything
		notifyItemRangeRemoved(0, initialSize);

		for (FileModel file : files)
			mFileModels.add(file);

		notifyItemRangeInserted(0, mFileModels.size());
	}
}
