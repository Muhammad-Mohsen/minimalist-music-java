package mohsen.muhammad.minimalistmusicplayer.player;

import mohsen.muhammad.minimalistmusicplayer.MainActivityFragment;
import mohsen.muhammad.minimalistmusicplayer.files.FileModel;

/**
 * Created by muhammad.mohsen on 6/25/2017.
 * In the same vein as ExplorerManager, and BreadcrumbManager, manages playback.
 * Has a hold of both the play service and the main fragment.
 * initialize, terminate methods cannot used here as the service and the fragment aren't necessarily created/destroyed together.
 */

public class PlaybackManager {

	private static PlayerService sPlayerService;
	private static MainActivityFragment sMainFragment;

	public static void setPlaybackService(PlayerService service) {
		sPlayerService = service;
	}
	public static PlayerService getPlayerService() {
		return sPlayerService;
	}

	public static void setMainFragment(MainActivityFragment fragment) {
		sMainFragment = fragment;
	}
	public static MainActivityFragment getMainFragment() {
		return sMainFragment;
	}

	// when the play/pause button is clicked
	public static void playPause(boolean play) {
		sPlayerService.playPause(play);
	}

	public static void startPlay(FileModel track) {

	}

}
