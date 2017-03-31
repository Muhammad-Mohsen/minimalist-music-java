package mohsen.muhammad.minimalistmusicplayer.util;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

/**
 * Created by Muhammad.mohsen on 4/15/2017.
 * low level animation utility that handles vector animations, etc.
 */

public class Anim {

	public static void animateDrawable(ImageView v, int drawableResourceId) {
		Drawable drawable = ContextCompat.getDrawable(v.getContext(), drawableResourceId); // get the frame animation drawable

		((AnimationDrawable) v.getDrawable()).stop(); // stop any animation that might be running
		v.setImageDrawable(drawable);
		((AnimationDrawable) v.getDrawable()).start();

		// set the drawable ID as the tag, so we know the final state of a given view
		// (e.g. to know that the back/root button is currently displaying the root icon)
		v.setTag(drawableResourceId);
	}
}
