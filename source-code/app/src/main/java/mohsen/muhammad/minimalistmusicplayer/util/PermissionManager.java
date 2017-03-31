package mohsen.muhammad.minimalistmusicplayer.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by muhammad.mohsen on 2/8/2017.
 * Used to manage run-time permission acquisition (android M)
 * thanks https://www.captechconsulting.com/blogs/runtime-permissions-best-practices-and-how-to-gracefully-handle-permission-removal
 */

public class PermissionManager {

	public static final String[] PERMISSIONS = new String[] { Manifest.permission.READ_EXTERNAL_STORAGE };

	private String[] mPermissions;
	private AppCompatActivity mActivity; // we need some context, don't we?!
	private Fragment mFragment;
	private SharedPreferences mSharedPreferences;

	public PermissionManager(AppCompatActivity activity, String[] permissions) {
		mActivity = activity;
		mPermissions = permissions;

		mSharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
	}
	public PermissionManager(Fragment fragment, String[] permissions) {
		mFragment = fragment;
		mActivity = (AppCompatActivity) fragment.getActivity();

		mPermissions = permissions;
		mSharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
	}

	public void setPermissions(String[] permissions) {
		mPermissions = permissions;
	}

	// is it M?
	private boolean canMakeSmores() {
		return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
	}

	// checks whether the given permission was already granted
	@TargetApi(Build.VERSION_CODES.M)
	public boolean hasPermission(String permission) {
		return !canMakeSmores() || (mActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
	}

	// checks whether the given permission was requested before
	private boolean shouldWeAsk(String permission){
		return (mSharedPreferences.getBoolean(permission, true));
	}
	// marks the given permission as "asked"
	// the above method would return false from that point on for that permission
	private void markAsAsked(String permission){
		mSharedPreferences.edit().putBoolean(permission, false).apply();
	}

	// gets a list of denied PERMISSIONS
	private String[] getDeniedPermissions(String[] wanted){
		ArrayList<String> unAskedPermissions = new ArrayList<>();

		for (String permission : wanted) {
			if(!hasPermission(permission))
				unAskedPermissions.add(permission);
		}

		String[] unAskerPermissionArray = new String[unAskedPermissions.size()];

		unAskedPermissions.toArray(unAskerPermissionArray);
		return unAskerPermissionArray;
	}

	// finally, we can request the PERMISSIONS
	// returns the request code
	@TargetApi(Build.VERSION_CODES.M)
	public int requestPermissions() {
		if (canMakeSmores()) {
			int requestCode = (new Random()).nextInt(Integer.SIZE / 2);

			String[] permissions = getDeniedPermissions(mPermissions);

			// if all PERMISSIONS are granted, simply return
			if (permissions.length == 0)
				return -1;

			if (mFragment != null)
				mFragment.requestPermissions(permissions, requestCode);

			else
				mActivity.requestPermissions(permissions, requestCode);

			return requestCode;
		}

		return -1;
	}
}
