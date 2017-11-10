package co.biogram.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import co.biogram.main.fragment.FragmentActivity;
import co.biogram.main.fragment.FragmentBase;
import co.biogram.main.R;
import co.biogram.main.handler.MiscHandler;
import co.biogram.main.ui.social.InboxUI;
import co.biogram.main.ui.social.MomentUI;
import co.biogram.main.ui.social.NotificationUI;
import co.biogram.main.ui.social.PostUI;
import co.biogram.main.ui.social.ProfileUI;

public class SocialActivity extends FragmentActivity
{
    private boolean NotificationEnable = false;

    private ImageView ImageViewMoment;
    private ImageView ImageViewInbox;
    private ImageView ImageViewNotification;
    private ImageView ImageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        RelativeLayout RelativeLayoutMain = new RelativeLayout(this);
        RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        RelativeLayoutMain.setBackgroundResource(MiscHandler.IsDark(this) ? R.color.GroundDark : R.color.GroundWhite);

        RelativeLayout.LayoutParams LinearLayoutMenuParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(this, 56));
        LinearLayoutMenuParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        LinearLayout LinearLayoutMenu = new LinearLayout(this);
        LinearLayoutMenu.setLayoutParams(LinearLayoutMenuParam);
        LinearLayoutMenu.setBackgroundResource(MiscHandler.IsDark(this) ? R.color.ActionBarDark : R.color.ActionBarWhite);
        LinearLayoutMenu.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayoutMenu.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(LinearLayoutMenu);

        ImageViewMoment = new ImageView(this);
        ImageViewMoment.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        ImageViewMoment.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewMoment.setPadding(MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15));
        ImageViewMoment.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { ChangePage(1); } });

        LinearLayoutMenu.addView(ImageViewMoment);

        ImageViewInbox = new ImageView(this);
        ImageViewInbox.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        ImageViewInbox.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewInbox.setPadding(MiscHandler.ToDimension(this, 17), MiscHandler.ToDimension(this, 17), MiscHandler.ToDimension(this, 17), MiscHandler.ToDimension(this, 17));
        ImageViewInbox.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { ChangePage(2); } });

        LinearLayoutMenu.addView(ImageViewInbox);

        ImageView ImageViewChat = new ImageView(this);
        ImageViewChat.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        ImageViewChat.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewChat.setImageResource(R.drawable.ic_chat_gray);
        ImageViewChat.setPadding(MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15));
        ImageViewChat.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { /* TODO Change Activity To Chat */ } });

        LinearLayoutMenu.addView(ImageViewChat);

        ImageViewNotification = new ImageView(this);
        ImageViewNotification.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        ImageViewNotification.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewNotification.setPadding(MiscHandler.ToDimension(this, 17), MiscHandler.ToDimension(this, 17), MiscHandler.ToDimension(this, 17), MiscHandler.ToDimension(this, 17));
        ImageViewNotification.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { ChangePage(4); } });

        LinearLayoutMenu.addView(ImageViewNotification);

        ImageViewProfile = new ImageView(this);
        ImageViewProfile.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        ImageViewProfile.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewProfile.setPadding(MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15), MiscHandler.ToDimension(this, 15));
        ImageViewProfile.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { ChangePage(5); } });

        LinearLayoutMenu.addView(ImageViewProfile);

        RelativeLayout.LayoutParams ViewLineParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(this, 1));
        ViewLineParam.addRule(RelativeLayout.ABOVE, LinearLayoutMenu.getId());

        View ViewLine = new View(this);
        ViewLine.setLayoutParams(ViewLineParam);
        ViewLine.setId(MiscHandler.GenerateViewID());
        ViewLine.setBackgroundResource(MiscHandler.IsDark(this) ? R.color.LineDark : R.color.LineWhite);

        RelativeLayoutMain.addView(ViewLine);

        RelativeLayout.LayoutParams FrameLayoutContentParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        FrameLayoutContentParam.addRule(RelativeLayout.ABOVE, ViewLine.getId());

        FrameLayout FrameLayoutContent = new FrameLayout(this);
        FrameLayoutContent.setLayoutParams(FrameLayoutContentParam);
        FrameLayoutContent.setId(R.id.SocialActivityContainer);

        RelativeLayoutMain.addView(FrameLayoutContent);

        FrameLayout FrameLayoutContentFull = new FrameLayout(this);
        FrameLayoutContentFull.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        FrameLayoutContentFull.setId(R.id.SocialActivityContainerFull);

        RelativeLayoutMain.addView(FrameLayoutContentFull);

        setContentView(RelativeLayoutMain);

        ChangePage(getIntent().getIntExtra("Tab", 1));
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (getIntent() == null)
            return;

        switch (getIntent().getIntExtra("Type", 0))
        {
            case 1:
            {
                String Username = getIntent().getStringExtra("Data");
                GetManager().OpenView(new ProfileUI(Username), R.id.SocialActivityContainer, "ProfileUI");
                break;
            }
            case 2:
            {
                String PostID = getIntent().getStringExtra("Data");
                GetManager().OpenView(new PostUI(PostID), R.id.SocialActivityContainer, "PostUI");
                break;
            }
        }
    }

    private void ChangePage(int Page)
    {
        ImageViewMoment.setImageResource(R.drawable.ic_moment_gray);
        ImageViewInbox.setImageResource(R.drawable.ic_inbox_gray);
        ImageViewNotification.setImageResource(NotificationEnable ? R.drawable.ic_notification_gray2 : R.drawable.ic_notification_gray);
        ImageViewProfile.setImageResource(R.drawable.ic_profile_gray);

        String Tag;
        FragmentBase Fragment;

        switch (Page)
        {
            default:
                Tag = "MomentUI";
                Fragment = new MomentUI();
                ImageViewMoment.setImageResource(R.drawable.ic_moment_blue);
                break;
            case 2:
                Tag = "InboxUI";
                Fragment = new InboxUI();
                ImageViewInbox.setImageResource(R.drawable.ic_inbox_blue);
                break;
            case 4:
                Tag = "NotificationUI";
                Fragment = new NotificationUI();
                NotificationEnable = false;
                ImageViewNotification.setImageResource(R.drawable.ic_notification_blue);
                break;
            case 5:
                Tag = "ProfileUI";
                Fragment = new ProfileUI();
                ImageViewProfile.setImageResource(R.drawable.ic_profile_blue);
                break;
        }

        GetManager().OpenView(Fragment, R.id.SocialActivityContainer, Tag);
    }
}