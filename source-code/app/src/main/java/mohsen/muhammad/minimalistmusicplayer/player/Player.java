package mohsen.muhammad.minimalistmusicplayer.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by muhammad.mohsen on 12/10/2016.
 * Has the main functionality for actually playing music
 */

public class Player implements MediaPlayer.OnPreparedListener {
	private MediaPlayer mPlayer;

	public Player() {
		mPlayer = new MediaPlayer();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mPlayer.setOnPreparedListener(this);
	}

	public void changeTrack(String trackPath) throws IOException {
		mPlayer.setDataSource(trackPath);
		mPlayer.prepareAsync();
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		mPlayer.start();
	}

	public void destroy() {
		mPlayer.release();
		mPlayer = null;
	}
}
