package mohsen.muhammad.minimalistmusicplayer.breadcrumb;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import mohsen.muhammad.minimalistmusicplayer.R;

/**
 * Created by muhammad.mohsen on 4/18/2017.
 * Adapter class for the breadcrumb bar recycler view
 */

public class BreadcrumbBarRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private ArrayList<String> mCrumbs;

	public BreadcrumbBarRecyclerAdapter(File file) {
		mCrumbs = new ArrayList<>(Arrays.asList(file.getAbsolutePath().split("/")));
		mCrumbs.remove(0); // remove the empty crumb
	}

	// where the views are inflated
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		return new CrumbViewHolder(inflater.inflate(R.layout.breadcrumb_crumb, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		CrumbViewHolder crumbViewHolder = (CrumbViewHolder) holder;
		File f = new File(TextUtils.join("/", mCrumbs.subList(0, position + 1)));
		crumbViewHolder.bind(f);
	}

	@Override
	public int getItemCount() {
		return mCrumbs.size();
	}

	void update(File file) {
		ArrayList<String> crumbList = new ArrayList<>(Arrays.asList(file.getAbsolutePath().split("/")));
		crumbList.remove(0);

		// if you're at the same directory, don't do anything
		if (crumbList.get(crumbList.size() - 1).equals(mCrumbs.get(mCrumbs.size() - 1)) && crumbList.size() == mCrumbs.size())
			return;

		// +ve means that we're going deeper
		// -ve means that we're going back
		int navigationSteps = crumbList.size() - mCrumbs.size();

		// only one can be added
		if (navigationSteps > 0) {
			mCrumbs.add(crumbList.get(crumbList.size() - 1)); // add the new crumb
			notifyItemInserted(mCrumbs.size() - 1);

		// one or more crumbs can be removed
		} else {
			int initialSize = mCrumbs.size();
			for (int i = initialSize - 1; i >= initialSize + navigationSteps; i--) {
				mCrumbs.remove(i);
			}

			// starting position will be the final size (last index of the updated list + 1)
			notifyItemRangeRemoved(mCrumbs.size(), -navigationSteps);
		}
	}
}
