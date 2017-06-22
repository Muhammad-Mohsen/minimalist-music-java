package mohsen.muhammad.minimalistmusicplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import mohsen.muhammad.minimalistmusicplayer.breadcrumb.BreadcrumbBarRecyclerAdapter;
import mohsen.muhammad.minimalistmusicplayer.breadcrumb.BreadcrumbManager;
import mohsen.muhammad.minimalistmusicplayer.explorer.ExplorerManager;
import mohsen.muhammad.minimalistmusicplayer.explorer.ExplorerRecyclerViewAdapter;
import mohsen.muhammad.minimalistmusicplayer.files.FileCache;
import mohsen.muhammad.minimalistmusicplayer.util.FileHelper;
import mohsen.muhammad.minimalistmusicplayer.util.PermissionManager;
import mohsen.muhammad.minimalistmusicplayer.util.Util;

/**
 * Where all the magic happens.
 * That'll get bloated fairly quickly
 */
public class MainActivityFragment extends Fragment {

	@BindView(R.id.imageButtonBack) ImageButton mImageButtonBack;
	@BindView(R.id.recyclerViewBreadcrumbs) RecyclerView mRecyclerViewBreadcrumbs;
	@BindView(R.id.recyclerViewExplorer) RecyclerView mRecyclerViewExplorer;

	@BindView(R.id.textViewCurrentSeek) TextView mTextViewCurrentSeek;
	@BindView(R.id.textViewDuration) TextView mTextViewDuration;
	@BindView(R.id.seekBar) SeekBar mSeekBar;

	@BindView(R.id.imageButtonRepeat) ImageButton mImageButtonRepeat;
	@BindView(R.id.imageButtonPrevious) ImageButton mImageButtonPrevious;
	@BindView(R.id.imageButtonPlayPause) ImageButton mImageButtonPlayPause;
	@BindView(R.id.imageButtonNext) ImageButton mImageButtonNext;
	@BindView(R.id.imageButtonShuffle) ImageButton mImageButtonShuffle;

	@BindView(R.id.layoutPermission) LinearLayout mLayoutPermission;
	@BindView(R.id.buttonGrantPermission) Button mButtonGrantPermission;

	public MainActivityFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.main_fragment, container, false);
		initializeViews(root);

		PermissionManager permissionManager = new PermissionManager(this, PermissionManager.PERMISSIONS);
		permissionManager.requestPermissions();

		return root;
	}

	@Override
	public void onStart() {
		super.onStart();

		PermissionManager permissionManager = new PermissionManager((AppCompatActivity) getActivity(), PermissionManager.PERMISSIONS);
		if (permissionManager.hasPermission(PermissionManager.PERMISSIONS[0])) { // read external storage

			// TODO do it better
			File currentDirectory = Util.getSavedCurrentDirectory(getContext());
			Util.setCurrentDirectory(currentDirectory);

			BreadcrumbBarRecyclerAdapter breadcrumbAdapter = new BreadcrumbBarRecyclerAdapter(currentDirectory);
			mRecyclerViewBreadcrumbs.setAdapter(breadcrumbAdapter);
			BreadcrumbManager.initialize(mRecyclerViewBreadcrumbs, mImageButtonBack, currentDirectory);

			ExplorerRecyclerViewAdapter explorerAdapter = new ExplorerRecyclerViewAdapter(FileCache.getPathFileModels(currentDirectory));
			mRecyclerViewExplorer.setAdapter(explorerAdapter);
			mRecyclerViewExplorer.setItemAnimator(new SlideInLeftAnimator());

			ExplorerManager.initialize(mRecyclerViewExplorer, mLayoutPermission, null);
			ExplorerManager.showHidePermissionLayout(false);

		} else {
			ExplorerManager.showHidePermissionLayout(true);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		// try to reinitialize
		// onStart checks whether the permissions were granted
		onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
		BreadcrumbManager.terminate();
		ExplorerManager.terminate();

		// TODO do it better
		Util.saveCurrentDirectory(getContext(), Util.getCurrentDirectory());
	}

	// attach click handlers, among other things
	private void initializeViews(View root) {
		ButterKnife.bind(this, root);

		mImageButtonBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if at the root, do nothing
				if (Util.getCurrentDirectory().getAbsolutePath().equals(FileHelper.ROOT))
					return;

				File parent = Util.getCurrentDirectory().getParentFile();
				Util.setCurrentDirectory(parent);

				// repopulate the recycler views
				// the null checks are useless, but they'll never hurt!
				ExplorerManager.update(Util.getCurrentDirectory());
				BreadcrumbManager.update(Util.getCurrentDirectory());
			}
		});

		mButtonGrantPermission.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PermissionManager permissionManager = new PermissionManager(MainActivityFragment.this, PermissionManager.PERMISSIONS);
				permissionManager.requestPermissions();
			}
		});
	}
}
