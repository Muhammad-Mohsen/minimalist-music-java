package mohsen.muhammad.minimalistmusicplayer.breadcrumb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import mohsen.muhammad.minimalistmusicplayer.R;
import mohsen.muhammad.minimalistmusicplayer.explorer.ExplorerManager;
import mohsen.muhammad.minimalistmusicplayer.util.Util;

/**
 * Created by muhammad.mohsen on 4/18/2017.
 * view holder class for the breadcrumb bar crumbs
 */

class CrumbViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.buttonCrumb) TextView mButtonCrumb;

	CrumbViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	void bind(File file) {
		mButtonCrumb.setText(file.getName());
		setClickListener(file);
	}

	private void setClickListener(File file) {
		LinearLayout crumb = (LinearLayout) itemView;
		crumb.getChildAt(0).setOnClickListener(new CrumbOnClickListener(file));
	}

	private class CrumbOnClickListener implements View.OnClickListener {

		File mFile;

		CrumbOnClickListener(File file) {
			mFile = file;
		}

		@Override
		public void onClick(View v) {
			// clicking the same directory should do nothing
			if (mFile.getAbsolutePath().equals(Util.getCurrentDirectory().getAbsolutePath()))
				return;

			Util.setCurrentDirectory(mFile);

			// repopulate the recycler views
			ExplorerManager.update(mFile);
			BreadcrumbManager.update(mFile);
		}
	}
}
