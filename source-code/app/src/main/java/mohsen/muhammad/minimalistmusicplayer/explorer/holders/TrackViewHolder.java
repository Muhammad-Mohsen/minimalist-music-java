package mohsen.muhammad.minimalistmusicplayer.explorer.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mohsen.muhammad.minimalistmusicplayer.R;
import mohsen.muhammad.minimalistmusicplayer.files.FileModel;

/**
 * Created by muhammad.mohsen on 12/10/2016.
 * TRACK ViewHolder class for the Explorer RecyclerView
 */
	public class TrackViewHolder extends ExplorerViewHolder {

	@BindView(R.id.textViewTrackTitle) TextView mTextViewTrackTitle;
	@BindView(R.id.textViewTrackAlbumArtist) TextView mTextViewTrackAlbumArtist;
	@BindView(R.id.textViewTrackDuration) TextView mTextViewTrackDuration;
	@BindView(R.id.imageViewIcon) ImageView mImageViewIcon;

	public TrackViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	@Override
	public void bind(FileModel fileModel) {
		super.bind(fileModel); // set the member variables

		mTextViewTrackTitle.setText(fileModel.name);
		mTextViewTrackDuration.setText(fileModel.duration);

		String trackAlbumArtist;
		if (!fileModel.album.equals(""))
			trackAlbumArtist = getContext().getResources().getString(R.string.trackAlbumArtist, fileModel.album, fileModel.artist);
		else
			trackAlbumArtist = getContext().getResources().getString(R.string.loading_metadata);

		mTextViewTrackAlbumArtist.setText(trackAlbumArtist);
	}

	@Override
	public void setClickListeners() {
		itemView.setOnClickListener(new TrackItemOnClickListener());
	}

	// has direct access to members
	private class TrackItemOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// play the track

			// select the track
		}
	}
}
