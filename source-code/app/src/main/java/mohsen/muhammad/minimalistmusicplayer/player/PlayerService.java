package mohsen.muhammad.minimalistmusicplayer.player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by muhammad.mohsen on 12/10/2016.
 * Background service that's actually responsible for playing the music
 */

public class PlayerService extends Service {
	public PlayerService() {}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {


		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {

	}

	// override is mandated by the framework
	@Override
	public IBinder onBind(Intent intent) { return null; }
}
