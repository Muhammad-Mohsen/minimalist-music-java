package mohsen.muhammad.minimalistmusicplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// update the theme https://developer.android.com/topic/performance/launch-time.html
		setTheme(R.style.AppTheme);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	}
}
