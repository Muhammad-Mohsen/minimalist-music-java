package mohsen.muhammad.minimalistmusicplayer.player;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by muhammad.mohsen on 12/10/2016.
 * Background service that's actually responsible for playing the music
 */

public class PlayerService extends Service implements MediaPlayer.OnCompletionListener,
		MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
		AudioManager.OnAudioFocusChangeListener {

	private MediaPlayer mPlayer;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// initialize the media player
		initializeMediaPlayer();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// destroy the Player instance
	}

	public void playPause(boolean play) {
		if (play)
			mPlayer.start();

		else
			mPlayer.pause();
	}

	@Override
	public void onAudioFocusChange(int focusChange) {

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO ask the PlaybackManager to play the next track
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e(TAG, "MediaPlayer.onError: error: " + what + ", extra: " + extra);
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO start playing?
	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO don't know yet!
	}

	private void initializeMediaPlayer() {
		mPlayer = new MediaPlayer();

		//Set up MediaPlayer event listeners
		mPlayer.setOnCompletionListener(this);
		mPlayer.setOnErrorListener(this);
		mPlayer.setOnPreparedListener(this);
		mPlayer.setOnSeekCompleteListener(this);

		//Reset so that the MediaPlayer is not pointing to another data source
		mPlayer.reset();
	}

	// override is mandated by the framework
	@Override
	public IBinder onBind(Intent intent) { return null; }
}
