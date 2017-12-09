package co.biogram.main.ui.general;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

import co.biogram.main.App;
import co.biogram.main.fragment.FragmentBase;
import co.biogram.main.R;
import co.biogram.main.handler.Misc;
import co.biogram.main.ui.view.TextView;

public class VideoPreviewUI extends FragmentBase
{
    private SimpleExoPlayerView SimpleExoPlayerViewMain;
    private SimpleExoPlayer SimpleExoPlayerMain;
    private boolean IsLocal = false;
    private String VideoURL = "";
    private Runnable runnable;

    public VideoPreviewUI(String URL, boolean isLocal)
    {
        VideoURL = URL;
        IsLocal = isLocal;
    }

    @Override
    public void OnCreate()
    {
        RelativeLayout RelativeLayoutMain = new RelativeLayout(GetActivity());
        RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        RelativeLayoutMain.setBackgroundResource(R.color.Black);
        RelativeLayoutMain.setClickable(true);

        final RelativeLayout RelativeLayoutHeader = new RelativeLayout(GetActivity());
        RelativeLayoutHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Misc.ToDP(GetActivity(), 56)));
        RelativeLayoutHeader.setBackgroundColor(Color.parseColor("#20ffffff"));

        RelativeLayoutMain.addView(RelativeLayoutHeader);

        RelativeLayout.LayoutParams ImageViewBackParam = new RelativeLayout.LayoutParams(Misc.ToDP(GetActivity(), 56), Misc.ToDP(GetActivity(), 56));
        ImageViewBackParam.addRule(Misc.Align("R"));

        ImageView ImageViewBack = new ImageView(GetActivity());
        ImageViewBack.setPadding(Misc.ToDP(GetActivity(), 12), Misc.ToDP(GetActivity(), 12), Misc.ToDP(GetActivity(), 12), Misc.ToDP(GetActivity(), 12));
        ImageViewBack.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewBack.setLayoutParams(ImageViewBackParam);
        ImageViewBack.setImageResource(Misc.IsRTL() ? R.drawable.ic_back_white_rtl : R.drawable.ic_back_white);
        ImageViewBack.setId(Misc.GenerateViewID());
        ImageViewBack.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { GetActivity().onBackPressed(); } });

        RelativeLayoutHeader.addView(ImageViewBack);

        RelativeLayout.LayoutParams TextViewTitleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTitleParam.addRule(Misc.AlignTo("R"), ImageViewBack.getId());
        TextViewTitleParam.addRule(RelativeLayout.CENTER_VERTICAL);

        TextView TextViewTitle = new TextView(GetActivity(), 16, true);
        TextViewTitle.setLayoutParams(TextViewTitleParam);
        TextViewTitle.setPadding(0, Misc.ToDP(GetActivity(), 6), 0, 0);
        TextViewTitle.setText(GetActivity().getString(R.string.VideoPreviewUI));

        RelativeLayoutHeader.addView(TextViewTitle);

        RelativeLayout.LayoutParams RelativeLayoutControlParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Misc.ToDP(GetActivity(), 56));
        RelativeLayoutControlParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        final RelativeLayout RelativeLayoutControl = new RelativeLayout(GetActivity());
        RelativeLayoutControl.setLayoutParams(RelativeLayoutControlParam);
        RelativeLayoutControl.setBackgroundColor(Color.parseColor("#20ffffff"));

        RelativeLayoutMain.addView(RelativeLayoutControl);

        final ImageView ImageViewPlay = new ImageView(GetActivity());
        ImageViewPlay.setPadding(Misc.ToDP(GetActivity(), 10), Misc.ToDP(GetActivity(), 10), Misc.ToDP(GetActivity(), 10), Misc.ToDP(GetActivity(), 10));
        ImageViewPlay.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewPlay.setLayoutParams(new RelativeLayout.LayoutParams(Misc.ToDP(GetActivity(), 56), Misc.ToDP(GetActivity(), 56)));
        ImageViewPlay.setImageResource(R.drawable.ic_pause);
        ImageViewPlay.setId(Misc.GenerateViewID());

        RelativeLayoutControl.addView(ImageViewPlay);

        RelativeLayout.LayoutParams TextViewTimeParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTimeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        TextViewTimeParam.addRule(RelativeLayout.CENTER_VERTICAL);
        TextViewTimeParam.setMargins(Misc.ToDP(GetActivity(), 10), Misc.ToDP(GetActivity(), 3), Misc.ToDP(GetActivity(), 10), 0);

        final TextView TextViewTime = new TextView(GetActivity(), 14, false);
        TextViewTime.setLayoutParams(TextViewTimeParam);
        TextViewTime.setId(Misc.GenerateViewID());
        TextViewTime.setText((StringForTime(0) + " / " + StringForTime(0)));

        RelativeLayoutControl.addView(TextViewTime);

        RelativeLayout.LayoutParams SeekBarMainParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        SeekBarMainParam.addRule(RelativeLayout.RIGHT_OF, ImageViewPlay.getId());
        SeekBarMainParam.addRule(RelativeLayout.LEFT_OF, TextViewTime.getId());
        SeekBarMainParam.addRule(RelativeLayout.CENTER_VERTICAL);

        final SeekBar SeekBarMain = new SeekBar(GetActivity(), null, android.R.attr.progressBarStyleHorizontal);
        SeekBarMain.setLayoutParams(SeekBarMainParam);
        SeekBarMain.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(GetActivity(), R.color.White), PorterDuff.Mode.MULTIPLY));
        SeekBarMain.setMax(1000);
        SeekBarMain.setProgress(1);

        RelativeLayoutControl.addView(SeekBarMain);

        RelativeLayout.LayoutParams SimpleExoPlayerViewMainParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        SimpleExoPlayerViewMainParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        SimpleExoPlayerMain = ExoPlayerFactory.newSimpleInstance(GetActivity(), new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter())));

        SimpleExoPlayerViewMain = new SimpleExoPlayerView(GetActivity());
        SimpleExoPlayerViewMain.setLayoutParams(SimpleExoPlayerViewMainParam);
        SimpleExoPlayerViewMain.setUseController(false);
        SimpleExoPlayerViewMain.setVisibility(View.GONE);
        SimpleExoPlayerViewMain.setPlayer(SimpleExoPlayerMain);
        SimpleExoPlayerViewMain.getVideoSurfaceView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                long Current = SimpleExoPlayerMain.getCurrentPosition();
                long Duration = SimpleExoPlayerMain.getDuration();

                TextViewTime.setText((StringForTime(Current) + " / " + StringForTime(Duration)));
                SeekBarMain.setProgress((int) (1000L * Current / Duration));

                if (SimpleExoPlayerMain.getPlayWhenReady())
                {
                    RelativeLayoutHeader.setVisibility(View.VISIBLE);
                    RelativeLayoutControl.setVisibility(View.VISIBLE);
                    ImageViewPlay.setImageResource(R.drawable.ic_play_white);
                    SimpleExoPlayerMain.setPlayWhenReady(false);
                    SimpleExoPlayerViewMain.removeCallbacks(runnable);
                }
                else
                {
                    RelativeLayoutHeader.setVisibility(View.GONE);
                    RelativeLayoutControl.setVisibility(View.GONE);
                    ImageViewPlay.setImageResource(R.drawable.ic_pause);
                    SimpleExoPlayerMain.setPlayWhenReady(true);
                    SimpleExoPlayerViewMain.post(runnable);
                }
            }
        });

        RelativeLayoutMain.addView(SimpleExoPlayerViewMain);

        MediaSource MediaSourceMain = null;

        try
        {
            if (IsLocal)
            {
                final FileDataSource fileDataSource = new FileDataSource();
                fileDataSource.open(new DataSpec(Uri.parse(VideoURL)));
                DataSource.Factory DataSourceMain = new DataSource.Factory() { @Override public DataSource createDataSource() { return fileDataSource; } };
                MediaSourceMain = new ExtractorMediaSource(fileDataSource.getUri(), DataSourceMain, new DefaultExtractorsFactory(), null, null);
            }
            else
            {
                Cache CacheMain = new SimpleCache(new File(GetActivity().getCacheDir(), "BiogramVideo"), new LeastRecentlyUsedCacheEvictor(256 * 1024 * 1024));
                DataSource.Factory DataSourceMain = new CacheDataSourceFactory(CacheMain, new OkHttpDataSourceFactory(App.GetOKClient(), "BioGram", null), CacheDataSource.FLAG_BLOCK_ON_CACHE, 256 * 1024 * 1024);
                MediaSourceMain = new ExtractorMediaSource(Uri.parse(VideoURL), DataSourceMain, new DefaultExtractorsFactory(), null, null);
            }
        }
        catch (Exception e)
        {
            Misc.Debug("VideoPreviewUI-MediaSource: " + e.toString());
        }

        SimpleExoPlayerMain.prepare(MediaSourceMain);
        SimpleExoPlayerMain.setPlayWhenReady(true);
        SimpleExoPlayerMain.addListener(new Player.EventListener()
        {
            @Override public void onTimelineChanged(Timeline timeline, Object manifest) { }
            @Override public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }
            @Override public void onLoadingChanged(boolean isLoading) { Misc.Debug("IsLoading: " + isLoading); }
            @Override public void onRepeatModeChanged(int repeatMode) { }
            @Override public void onPlayerError(ExoPlaybackException error) { }
            @Override public void onPositionDiscontinuity() { }
            @Override public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }
            @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
            {
                if (playbackState == 4)
                {
                    SeekBarMain.setProgress(1000);
                    RelativeLayoutHeader.setVisibility(View.VISIBLE);
                    RelativeLayoutControl.setVisibility(View.VISIBLE);
                    ImageViewPlay.setImageResource(R.drawable.ic_play_white);
                    SimpleExoPlayerMain.setPlayWhenReady(false);
                    SeekBarMain.setProgress(0);
                    SimpleExoPlayerMain.seekTo(0);
                    SimpleExoPlayerViewMain.removeCallbacks(runnable);
                }
                else if (playbackState == 3)
                {
                    if (SimpleExoPlayerViewMain.getVisibility() == View.GONE)
                        SimpleExoPlayerViewMain.setVisibility(View.VISIBLE);

                    TextViewTime.setText((StringForTime(SimpleExoPlayerMain.getCurrentPosition()) + " / " + StringForTime(SimpleExoPlayerMain.getDuration())));
                }
            }
        });

        SeekBarMain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    long Duration = SimpleExoPlayerMain.getDuration();
                    long NewPosition = (Duration * progress) / 1000L;

                    SimpleExoPlayerMain.seekTo((int) NewPosition);
                    TextViewTime.setText((StringForTime(NewPosition) + " / " + StringForTime(Duration)));
                }
            }
        });

        ImageViewPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                long Current = SimpleExoPlayerMain.getCurrentPosition();
                long Duration = SimpleExoPlayerMain.getDuration();

                SeekBarMain.setProgress((int) (1000L * Current / Duration));
                TextViewTime.setText((StringForTime(Current) + " / " + StringForTime(Duration)));

                if (SimpleExoPlayerMain.getPlayWhenReady())
                {
                    RelativeLayoutControl.setVisibility(View.VISIBLE);
                    RelativeLayoutHeader.setVisibility(View.VISIBLE);
                    ImageViewPlay.setImageResource(R.drawable.ic_play_white);
                    SimpleExoPlayerMain.setPlayWhenReady(false);
                    SimpleExoPlayerViewMain.removeCallbacks(runnable);
                }
                else
                {
                    RelativeLayoutControl.setVisibility(View.GONE);
                    RelativeLayoutHeader.setVisibility(View.GONE);
                    ImageViewPlay.setImageResource(R.drawable.ic_pause);
                    SimpleExoPlayerMain.setPlayWhenReady(true);
                    SimpleExoPlayerViewMain.post(runnable);
                }
            }
        });

        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if (SimpleExoPlayerMain.getPlayWhenReady())
                    {
                        long Current = SimpleExoPlayerMain.getCurrentPosition();
                        long Duration = SimpleExoPlayerMain.getDuration();
                        final long Position = 1000L * Current / Duration;

                        SeekBarMain.setProgress((int) Position);
                        SeekBarMain.setSecondaryProgress(SimpleExoPlayerMain.getBufferedPercentage()* 10);
                        TextViewTime.setText((StringForTime(Current) + " / " + StringForTime(Duration)));
                    }
                }
                catch (Exception e)
                {
                    Misc.Debug("VideoPreviewUI-Runnable: " + e.toString());
                }

                SimpleExoPlayerViewMain.postDelayed(runnable, 500);
            }
        };

        RelativeLayoutHeader.bringToFront();
        RelativeLayoutControl.bringToFront();

        ViewMain = RelativeLayoutMain;
    }

    @Override
    public void OnResume()
    {
        SimpleExoPlayerViewMain.postDelayed(runnable, 500);
        GetActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        GetActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        GetActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void OnPause()
    {
        SimpleExoPlayerViewMain.removeCallbacks(runnable);
        SimpleExoPlayerMain.setPlayWhenReady(false);
        GetActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        GetActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void OnDestroy()
    {
        super.OnDestroy();
        SimpleExoPlayerMain.release();
    }

    private String StringForTime(long Time)
    {
        if (Time < 0)
            Time = 0;

        long TotalSeconds = Time / 1000;
        long Seconds = TotalSeconds % 60;
        long Minutes = (TotalSeconds / 60) % 60;
        long Hours   = TotalSeconds / 3600;

        if (Hours > 0)
            return String.valueOf(Hours) + ":" + String.valueOf(Minutes) + ":" + String.valueOf(Seconds);

        return String.valueOf(Minutes) + ":" + String.valueOf(Seconds);
    }
}
