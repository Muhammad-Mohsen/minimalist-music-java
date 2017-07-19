package mohsen.muhammad.minimalistmusicplayer.player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by muhammad.mohsen on 12/10/2016.
 * Holds playlist items, information about the playlist (shuffle, repeat modes, current index, current directory...
 */

public class Playlist {
	private int mIndex;
	private int mStart; // starting index - to try and do circular playlist

	private boolean mIsShuffle;
	private RepeatMode mRepeatMode;

	private ArrayList<String> mPlaylistItems;

	public Playlist() {
		mIndex = 0;
		mStart = 0;

		mIsShuffle = false;
	}

	public ArrayList<String> getPlaylistItems() {
		return mPlaylistItems;
	}
	public void setPlaylistItems(ArrayList<String> playlistItems) {
		mPlaylistItems = playlistItems;
	}

	public boolean getIsShuffle() {
		return mIsShuffle;
	}
	public void toggleShuffle() {
		mIsShuffle = !mIsShuffle;
	}

	public RepeatMode getRepeatMode() {
		return mRepeatMode;
	}
	public void updateRepeatMode() {
		switch (mRepeatMode) {
			case NONE:
				mRepeatMode = RepeatMode.REPEAT;
				break;

			case REPEAT:
				mRepeatMode = RepeatMode.ONE;
				break;

			case ONE:
				mRepeatMode = RepeatMode.NONE;
		}
	}

	// the onComplete param indicates whether we're requesting the next track upon completion of playing the current track,
	// or by clicking the "Next" button
	public String getNextItem(boolean onComplete) {
		// first things first, check that the playlist is initialized
		if (mPlaylistItems == null)
			return null;

		int playlistSize = mPlaylistItems.size();

		// first, check the shuffle state
		if (mIsShuffle)
			mIndex = ThreadLocalRandom.current().nextInt(0, playlistSize); // nextInt max is exclusive.

		// next up, if we're yet to hit the end of the playlist AND we're not repeating the same track, simply increment the index.
		else if (mIndex < playlistSize - 1 && mRepeatMode != RepeatMode.ONE)
			mIndex++;

		// if we did hit the end, and we're repeating, go back to the start.
		else if (mRepeatMode == RepeatMode.REPEAT)
			mIndex = 0;

		// if we hit the end, and we're not repeating, we look into the onComplete argument:
		// if it is true, meaning that we're looking for the next track after finishing playing the current one, we'll stop.
		// otherwise, it means that the user clicked the Next button, so, we'll return the first track index.
		else if (mRepeatMode == RepeatMode.NONE) {
			if (onComplete)
				mIndex = -1;

			else
				mIndex = 0;
		}

		// finally, if the index isn't invalid, we return the track.
		if (mIndex != -1)
			return mPlaylistItems.get(mIndex);

		return null;
	}

	public String getPreviousItem() {
		if (mPlaylistItems == null)
			return null;

		int playlistSize = mPlaylistItems.size();

		// first, check the shuffle state
		if (mIsShuffle)
			mIndex = ThreadLocalRandom.current().nextInt(0, playlistSize); // nextInt max is exclusive.

		// then, if we're not at the first track of the playlist, decrement by one!
		else if (mIndex > 0)
			mIndex--;

		// otherwise, rotate the index to the end of the list
		else
			mIndex = playlistSize - 1;

		return null;
	}

	public String getItem(int index) {
		if (index < mPlaylistItems.size())
			return mPlaylistItems.get(index);

		return null;
	}

	public enum RepeatMode {
		NONE,
		ONE,
		REPEAT
	}
}
