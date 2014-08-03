package com.tanzoft.habarihub.mediaplayer;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.mediaplayer.MediaPlayerService.MediaPlayerBinder;

/**
 *
 */
public class MediaActivity extends ActionBarActivity implements
		IMediaPlayerServiceClient {
	private StatefulMediaPlayer mMediaPlayer;
	private StreamStation mSelectedStream = RadioList.DEFAULT_STREAM_STATION;
	private MediaPlayerService mService;
	private boolean mBound;

	private ProgressDialog mProgressDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		bindToService();
		mProgressDialog = new ProgressDialog(this);

		initializeButtons();
		setupStationPicker();
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radio_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menuClose:
			shutdownActivity();
			return true;
		case R.id.menuAbout:
			showAboutActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	/**
	 * Launches an activity to show the about
	 */
	private void showAboutActivity() {
		//Intent intent = new Intent(Intent.ACTION_VIEW);
		//intent.setClassName(this, AboutActivity.class.getName());
		//startActivity(intent);
	}

	/**
	 * Binds to the instance of MediaPlayerService. If no instance of
	 * MediaPlayerService exists, it first starts a new instance of the service.
	 */
	public void bindToService() {
		Intent intent = new Intent(this, MediaPlayerService.class);

		if (mediaPlayerServiceRunning()) {
			// Bind to Service
			bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		}
		// no instance of service
		else {
			// start service and bind to it
			startService(intent);
			bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

		}

	}

	/**
	 * Sets up the stationPicker spinner
	 */
	public void setupStationPicker() {
		Spinner stationPicker = (Spinner) findViewById(R.id.stationPicker);
		StreamStationSpinnerAdapter adapter = new StreamStationSpinnerAdapter(
				this, android.R.layout.simple_spinner_item);

		// populate adapter with stations
		for (StreamStation st : RadioList.ALl_STATIONS) {
			adapter.add(st);
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stationPicker.setAdapter(adapter);
		stationPicker.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				StreamStation selectedStreamStation = (StreamStation) parent
						.getItemAtPosition(pos);

				if (selectedStreamStation != mSelectedStream) {
					mService.stopMediaPlayer();
					mSelectedStream = selectedStreamStation;
					mService.initializePlayer(mSelectedStream);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing.
			}

		});
	}

	/**
	 * Initializes buttons by setting even handlers and listeners, etc.
	 */
	private void initializeButtons() {
		// PLAY/PAUSE BUTTON
		final ToggleButton playPauseButton = (ToggleButton) findViewById(R.id.playPauseButton);
		playPauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBound) {
					mMediaPlayer = mService.getMediaPlayer();

					// pressed pause ->pause
					if (!playPauseButton.isChecked()) {

						if (mMediaPlayer.isStarted()) {
							mService.pauseMediaPlayer();
						}

					}

					// pressed play
					else if (playPauseButton.isChecked()) {
						// STOPPED, CREATED, EMPTY, -> initialize
						if (mMediaPlayer.isStopped()
								|| mMediaPlayer.isCreated()
								|| mMediaPlayer.isEmpty()) {
							mService.initializePlayer(mSelectedStream);
						}

						// prepared, paused -> resume play
						else if (mMediaPlayer.isPrepared()
								|| mMediaPlayer.isPaused()) {
							mService.startMediaPlayer();
						}

					}
				}
			}

		});
	}

	/**
	 * Defines callbacks for service binding, passed to bindService()
	 */
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className,
				IBinder serviceBinder) {
			Log.d("MainActivity", "service connected");

			// bound with Service. get Service instance
			MediaPlayerBinder binder = (MediaPlayerService.MediaPlayerBinder) serviceBinder;
			mService = binder.getService();

			// send this instance to the service, so it can make callbacks on
			// this instance as a client
			mService.setClient(MediaActivity.this);
			mBound = true;

			// Set play/pause button to reflect state of the service's contained
			// player
			final ToggleButton playPauseButton = (ToggleButton) findViewById(R.id.playPauseButton);
			playPauseButton.setChecked(mService.getMediaPlayer().isPlaying());

			// Set station Picker to show currently set stream station
			Spinner stationPicker = (Spinner) findViewById(R.id.stationPicker);
			if (mService.getMediaPlayer() != null
					&& mService.getMediaPlayer().getStreamStation() != null) {
				for (int i = 0; i < RadioList.ALl_STATIONS.length; i++) {
					if (mService.getMediaPlayer().getStreamStation()
							.equals(RadioList.ALl_STATIONS[i])) {
						stationPicker.setSelection(i);
						mSelectedStream = (StreamStation) stationPicker
								.getItemAtPosition(i);
					}

				}
			}

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
			mService = null;
		}
	};

	/**
	 * Determines if the MediaPlayerService is already running.
	 * 
	 * @return true if the service is running, false otherwise.
	 */
	private boolean mediaPlayerServiceRunning() {

		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.tanzoft.habarihub.mediaplayer.MediaPlayerService"
					.equals(service.service.getClassName())) {
				return true;
			}
		}

		return false;
	}

	public void onInitializePlayerSuccess() {
		mProgressDialog.dismiss();

		final ToggleButton playPauseButton = (ToggleButton) findViewById(R.id.playPauseButton);
		playPauseButton.setChecked(true);
	}

	public void onInitializePlayerStart() {
		mProgressDialog = ProgressDialog.show(this, "Habari Hub Radio",
				"Connecting...", true);
		mProgressDialog.getWindow().setGravity(Gravity.TOP);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialogInterface) {
				MediaActivity.this.mService.resetMediaPlaer();
				final ToggleButton playPauseButton = (ToggleButton) findViewById(R.id.playPauseButton);
				playPauseButton.setChecked(false);
			}

		});

	}

	@Override
	public void onError() {
		mProgressDialog.cancel();
	}

	/**
	 * Closes unbinds from service, stops the service, and calls finish()
	 */
	public void shutdownActivity() {

		if (mBound) {
			mService.stopMediaPlayer();
			// Detach existing connection.
			unbindService(mConnection);
			mBound = false;
		}

		Intent intent = new Intent(this, MediaPlayerService.class);
		stopService(intent);

		finish();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mBound) {
			mService.unRegister();
			unbindService(mConnection);
			mBound = false;
		}
	}
	
	public void onBackPressed(){
		super.onBackPressed();
	}

}
