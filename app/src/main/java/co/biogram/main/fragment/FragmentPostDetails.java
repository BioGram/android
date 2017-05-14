package co.biogram.main.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Bidi;

import co.biogram.main.R;
import co.biogram.main.handler.MiscHandler;
import co.biogram.main.handler.RequestHandler;
import co.biogram.main.handler.SharedHandler;
import co.biogram.main.handler.TagHandler;
import co.biogram.main.handler.URLHandler;
import co.biogram.main.misc.LoadingView;
import co.biogram.main.misc.ImageViewCircle;
import co.biogram.main.misc.TextCrawler;

public class FragmentPostDetails extends Fragment
{
    private RelativeLayout RelativeLayoutLoading;
    private LoadingView LoadingViewData;
    private TextView TextViewTry;

    private ImageViewCircle ImageViewCircleProfile;
    private TextView TextViewUsername;
    private TextView TextViewTime;
    private TextView TextViewMessage;
    private LinearLayout LinearLayoutContentSingle;
    private ImageView ImageViewSingle;
    private LinearLayout LinearLayoutContentDouble;
    private ImageView ImageViewDouble1;
    private ImageView ImageViewDouble2;
    private LinearLayout LinearLayoutContentTriple;
    private ImageView ImageViewTriple1;
    private ImageView ImageViewTriple2;
    private ImageView ImageViewTriple3;
    private RelativeLayout RelativeLayoutContentLink;
    private LoadingView LoadingViewLink;
    private TextView TextViewTryLink;
    private TextView TextViewWebsiteLink;
    private TextView TextViewDescriptionLink;
    private ImageView ImageViewFavLink;
    private TextView TextViewLikeCount;
    private TextView TextViewCommentCount;

    private ImageView ImageViewOption;
    private ImageView ImageViewBookMark;
    private ImageView ImageViewIconLike;

    private String PostID = "";
    private String OwnerID = "";
    private boolean IsLike = false;
    private boolean IsComment = false;
    private boolean IsBookMark = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final Context context = getActivity();

        RelativeLayout Root = new RelativeLayout(context);
        Root.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        Root.setBackgroundColor(ContextCompat.getColor(context, R.color.White));
        Root.setClickable(true);

        RelativeLayout Header = new RelativeLayout(context);
        Header.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 56)));
        Header.setBackgroundColor(ContextCompat.getColor(context, R.color.White5));
        Header.setId(MiscHandler.GenerateViewID());

        Root.addView(Header);

        ImageView ImageViewBack = new ImageView(context);
        ImageViewBack.setPadding(MiscHandler.ToDimension(context, 12), MiscHandler.ToDimension(context, 12), MiscHandler.ToDimension(context, 12), MiscHandler.ToDimension(context, 12));
        ImageViewBack.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewBack.setLayoutParams(new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 56), MiscHandler.ToDimension(context, 56)));
        ImageViewBack.setImageResource(R.drawable.ic_back_blue);
        ImageViewBack.setId(MiscHandler.GenerateViewID());
        ImageViewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getActivity().onBackPressed();
            }
        });

        Header.addView(ImageViewBack);

        RelativeLayout.LayoutParams TextViewTitleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTitleParam.addRule(RelativeLayout.RIGHT_OF, ImageViewBack.getId());
        TextViewTitleParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        TextView TextViewTitle = new TextView(context);
        TextViewTitle.setLayoutParams(TextViewTitleParam);
        TextViewTitle.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewTitle.setText(getString(R.string.FragmentPostDetailsTitle));
        TextViewTitle.setTypeface(null, Typeface.BOLD);
        TextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        Header.addView(TextViewTitle);

        RelativeLayout.LayoutParams ImageViewOptionParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 56), MiscHandler.ToDimension(context, 56));
        ImageViewOptionParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        ImageViewOption = new ImageView(context);
        ImageViewOption.setPadding(MiscHandler.ToDimension(context, 14), MiscHandler.ToDimension(context, 14), MiscHandler.ToDimension(context, 14), MiscHandler.ToDimension(context, 14));
        ImageViewOption.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewOption.setLayoutParams(ImageViewOptionParam);
        ImageViewOption.setImageResource(R.drawable.ic_option_black);
        ImageViewOption.setVisibility(View.GONE);
        ImageViewOption.setId(MiscHandler.GenerateViewID());
        ImageViewOption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Dialog DialogOption = new Dialog(getActivity());
                DialogOption.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DialogOption.setCancelable(true);

                LinearLayout DialogOptionRoot = new LinearLayout(context);
                DialogOptionRoot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                DialogOptionRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.White));
                DialogOptionRoot.setOrientation(LinearLayout.VERTICAL);

                TextView TextViewFollow = new TextView(context);
                TextViewFollow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextViewFollow.setTextColor(ContextCompat.getColor(context, R.color.Black));
                TextViewFollow.setText(getString(R.string.FragmentPostDetailsFollow));
                TextViewFollow.setPadding(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));
                TextViewFollow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                TextViewFollow.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        DialogOption.dismiss();
                        MiscHandler.Toast(context, getString(R.string.Soon));
                    }
                });

                DialogOptionRoot.addView(TextViewFollow);

                View ViewFollowLine = new View(context);
                ViewFollowLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1)));
                ViewFollowLine.setBackgroundResource(R.color.Gray1);

                DialogOptionRoot.addView(ViewFollowLine);

                final TextView TextViewTurn = new TextView(context);
                TextViewTurn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextViewTurn.setTextColor(ContextCompat.getColor(context, R.color.Black));
                TextViewTurn.setVisibility(View.GONE);
                TextViewTurn.setPadding(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));
                TextViewTurn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                TextViewTurn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        RequestHandler.Core().Method("POST")
                        .Address(URLHandler.GetURL(URLHandler.URL.POST_TURN_COMMENT))
                        .Param("PostID", PostID)
                        .Header("TOKEN", SharedHandler.GetString(context, "TOKEN"))
                        .Tag("FragmentPostDetails")
                        .Build(new RequestHandler.OnCompleteCallBack()
                        {
                            @Override
                            public void OnFinish(String Response, int Status)
                            {
                                if (Status != 200)
                                    return;

                                try
                                {
                                    if (new JSONObject(Response).getInt("Message") == 1000)
                                        IsComment = !IsComment;
                                }
                                catch (Exception e)
                                {
                                    // Leave Me Alone
                                }
                            }
                        });

                        DialogOption.dismiss();
                    }
                });

                if (IsComment)
                    TextViewTurn.setText(getString(R.string.FragmentPostDetailsTurnOn));
                else
                    TextViewTurn.setText(getString(R.string.FragmentPostDetailsTurnOff));

                DialogOptionRoot.addView(TextViewTurn);

                View ViewTurnLine = new View(context);
                ViewTurnLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1)));
                ViewTurnLine.setBackgroundResource(R.color.Gray1);
                ViewTurnLine.setVisibility(View.GONE);

                DialogOptionRoot.addView(ViewTurnLine);

                TextView TextViewCopy = new TextView(context);
                TextViewCopy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextViewCopy.setTextColor(ContextCompat.getColor(context, R.color.Black));
                TextViewCopy.setText(getString(R.string.FragmentPostDetailsCopy));
                TextViewCopy.setPadding(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));
                TextViewCopy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                TextViewCopy.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        ClipboardManager ClipBoard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData Clip = ClipData.newPlainText(PostID, TextViewMessage.getText().toString());
                        ClipBoard.setPrimaryClip(Clip);

                        MiscHandler.Toast(context, getString(R.string.FragmentPostDetailsClipboard));
                        DialogOption.dismiss();
                    }
                });

                DialogOptionRoot.addView(TextViewCopy);

                View ViewCopyLine = new View(context);
                ViewCopyLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1)));
                ViewCopyLine.setBackgroundResource(R.color.Gray1);

                DialogOptionRoot.addView(ViewCopyLine);

                TextView TextViewBlock = new TextView(context);
                TextViewBlock.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextViewBlock.setTextColor(ContextCompat.getColor(context, R.color.Black));
                TextViewBlock.setText(getString(R.string.FragmentPostDetailsBlock));
                TextViewBlock.setPadding(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));
                TextViewBlock.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                TextViewBlock.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        DialogOption.dismiss();
                        MiscHandler.Toast(context, getString(R.string.Soon));
                    }
                });

                DialogOptionRoot.addView(TextViewBlock);

                View ViewBlockLine = new View(context);
                ViewBlockLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1)));
                ViewBlockLine.setBackgroundResource(R.color.Gray1);

                DialogOptionRoot.addView(ViewBlockLine);

                TextView TextViewDelete = new TextView(context);
                TextViewDelete.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextViewDelete.setTextColor(ContextCompat.getColor(context, R.color.Black));
                TextViewDelete.setText(getString(R.string.FragmentPostDetailsDelete));
                TextViewDelete.setVisibility(View.GONE);
                TextViewDelete.setPadding(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));
                TextViewDelete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                TextViewDelete.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        RequestHandler.Core().Method("POST")
                        .Address(URLHandler.GetURL(URLHandler.URL.POST_DELETE))
                        .Header("TOKEN", SharedHandler.GetString(context, "TOKEN"))
                        .Param("PostID", PostID)
                        .Tag("FragmentPostDetails")
                        .Build(new RequestHandler.OnCompleteCallBack()
                        {
                            @Override
                            public void OnFinish(String Response, int Status)
                            {
                                if (Status != 200)
                                    return;

                                try
                                {
                                    if (new JSONObject(Response).getInt("Message") == 1000)
                                        getActivity().onBackPressed();
                                }
                                catch (Exception e)
                                {
                                    // Leave Me Alone
                                }
                            }
                        });

                        DialogOption.dismiss();
                    }
                });

                DialogOptionRoot.addView(TextViewDelete);

                View ViewDeleteLine = new View(context);
                ViewDeleteLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1)));
                ViewDeleteLine.setBackgroundResource(R.color.Gray1);
                ViewDeleteLine.setVisibility(View.GONE);

                DialogOptionRoot.addView(ViewDeleteLine);

                TextView TextViewReport = new TextView(context);
                TextViewReport.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextViewReport.setTextColor(ContextCompat.getColor(context, R.color.Black));
                TextViewReport.setText(getString(R.string.FragmentPostDetailsReport));
                TextViewReport.setPadding(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));
                TextViewReport.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                TextViewReport.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        DialogOption.dismiss();
                        MiscHandler.Toast(context, getString(R.string.Soon));
                    }
                });

                DialogOptionRoot.addView(TextViewReport);

                if (OwnerID.equals(SharedHandler.GetString(context, "ID")))
                {
                    TextViewFollow.setVisibility(View.GONE);
                    ViewFollowLine.setVisibility(View.GONE);

                    TextViewTurn.setVisibility(View.VISIBLE);
                    ViewTurnLine.setVisibility(View.VISIBLE);

                    TextViewBlock.setVisibility(View.GONE);
                    ViewBlockLine.setVisibility(View.GONE);

                    TextViewDelete.setVisibility(View.VISIBLE);
                    ViewDeleteLine.setVisibility(View.VISIBLE);

                    TextViewReport.setVisibility(View.GONE);
                }

                DialogOption.setContentView(DialogOptionRoot);
                DialogOption.show();
            }
        });

        Header.addView(ImageViewOption);

        RelativeLayout.LayoutParams BookMarkParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 56), MiscHandler.ToDimension(context, 56));
        BookMarkParam.addRule(RelativeLayout.LEFT_OF, ImageViewOption.getId());

        ImageViewBookMark = new ImageView(context);
        ImageViewBookMark.setPadding(MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16));
        ImageViewBookMark.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewBookMark.setLayoutParams(BookMarkParam);
        ImageViewBookMark.setImageResource(R.drawable.ic_bookmark_black);
        ImageViewBookMark.setVisibility(View.GONE);
        ImageViewBookMark.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RequestHandler.Core().Method("POST")
                .Address(URLHandler.GetURL(URLHandler.URL.POST_BOOKMARK))
                .Header("TOKEN", SharedHandler.GetString(context, "TOKEN"))
                .Param("PostID", PostID)
                .Tag("FragmentPostDetails")
                .Build(new RequestHandler.OnCompleteCallBack()
                {
                    @Override
                    public void OnFinish(String Response, int Status)
                    {
                        if (Status != 200)
                            return;

                        try
                        {
                            if (new JSONObject(Response).getInt("Message") == 1000)
                            {
                                IsBookMark = !IsBookMark;

                                if (IsBookMark)
                                    ImageViewBookMark.setImageResource(R.drawable.ic_bookmark_black2);
                                else
                                    ImageViewBookMark.setImageResource(R.drawable.ic_bookmark_black);
                            }
                        }
                        catch (Exception e)
                        {
                            // Leave Me Alone
                        }
                    }
                });
            }
        });

        Header.addView(ImageViewBookMark);

        RelativeLayout.LayoutParams LineParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1));
        LineParam.addRule(RelativeLayout.BELOW, Header.getId());

        View ViewLine = new View(context);
        ViewLine.setLayoutParams(LineParam);
        ViewLine.setBackgroundResource(R.color.Gray2);
        ViewLine.setId(MiscHandler.GenerateViewID());

        Root.addView(ViewLine);

        RelativeLayout.LayoutParams ScrollParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ScrollParam.addRule(RelativeLayout.BELOW, ViewLine.getId());

        ScrollView ScrollViewMain = new ScrollView(context);
        ScrollViewMain.setLayoutParams(ScrollParam);
        ScrollViewMain.setFillViewport(true);
        ScrollViewMain.setVerticalScrollBarEnabled(false);
        ScrollViewMain.setHorizontalScrollBarEnabled(false);

        Root.addView(ScrollViewMain);

        RelativeLayout RelativeLayoutMain = new RelativeLayout(context);
        RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        ScrollViewMain.addView(RelativeLayoutMain);

        RelativeLayout.LayoutParams ImageViewCircleProfileParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 55), MiscHandler.ToDimension(context, 55));
        ImageViewCircleProfileParam.setMargins(MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 10));

        ImageViewCircleProfile = new ImageViewCircle(context);
        ImageViewCircleProfile.setLayoutParams(ImageViewCircleProfileParam);
        ImageViewCircleProfile.setImageResource(R.color.BlueGray);
        ImageViewCircleProfile.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(ImageViewCircleProfile);

        RelativeLayout.LayoutParams TextViewUsernameParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewUsernameParam.addRule(RelativeLayout.RIGHT_OF, ImageViewCircleProfile.getId());
        TextViewUsernameParam.setMargins(0, MiscHandler.ToDimension(context, 14), 0, 0);

        TextViewUsername = new TextView(context);
        TextViewUsername.setLayoutParams(TextViewUsernameParam);
        TextViewUsername.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewUsername.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        TextViewUsername.setId(MiscHandler.GenerateViewID());
        TextViewUsername.setTypeface(null, Typeface.BOLD);

        RelativeLayoutMain.addView(TextViewUsername);

        RelativeLayout.LayoutParams TextViewTimeParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTimeParam.addRule(RelativeLayout.BELOW, TextViewUsername.getId());
        TextViewTimeParam.addRule(RelativeLayout.RIGHT_OF, ImageViewCircleProfile.getId());

        TextViewTime = new TextView(context);
        TextViewTime.setLayoutParams(TextViewTimeParam);
        TextViewTime.setTextColor(ContextCompat.getColor(context, R.color.Gray4));
        TextViewTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        RelativeLayoutMain.addView(TextViewTime);

        RelativeLayout.LayoutParams TextViewMessageParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewMessageParam.addRule(RelativeLayout.BELOW, ImageViewCircleProfile.getId());
        TextViewMessageParam.setMargins(MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 5), MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 5));

        TextViewMessage = new TextView(context);
        TextViewMessage.setLayoutParams(TextViewMessageParam);
        TextViewMessage.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TextViewMessage.setId(MiscHandler.GenerateViewID());
        TextViewMessage.setLineSpacing(1f, 1.25f);
        TextViewMessage.setVisibility(View.GONE);

        RelativeLayoutMain.addView(TextViewMessage);

        RelativeLayout.LayoutParams RelativeLayoutContentParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayoutContentParam.setMargins(MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 10), 0);
        RelativeLayoutContentParam.addRule(RelativeLayout.BELOW, TextViewMessage.getId());

        RelativeLayout RelativeLayoutContent = new RelativeLayout(context);
        RelativeLayoutContent.setLayoutParams(RelativeLayoutContentParam);
        RelativeLayoutContent.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(RelativeLayoutContent);

        LinearLayoutContentSingle = new LinearLayout(context);
        LinearLayoutContentSingle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayoutContentSingle.setVisibility(View.GONE);

        RelativeLayoutContent.addView(LinearLayoutContentSingle);

        ImageViewSingle = new ImageView(context);
        ImageViewSingle.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 200)));
        ImageViewSingle.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageViewSingle.setBackgroundResource(R.color.BlueGray);

        LinearLayoutContentSingle.addView(ImageViewSingle);

        LinearLayoutContentDouble = new LinearLayout(context);
        LinearLayoutContentDouble.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayoutContentDouble.setVisibility(View.GONE);

        RelativeLayoutContent.addView(LinearLayoutContentDouble);

        ImageViewDouble1 = new ImageView(context);
        ImageViewDouble1.setLayoutParams(new LinearLayout.LayoutParams(0, MiscHandler.ToDimension(context, 200), 1f));
        ImageViewDouble1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageViewDouble1.setBackgroundResource(R.color.BlueGray);

        LinearLayoutContentDouble.addView(ImageViewDouble1);

        View ViewLineDouble = new View(context);
        ViewLineDouble.setLayoutParams(new LinearLayout.LayoutParams(0, RelativeLayout.LayoutParams.MATCH_PARENT, 0.05f));

        LinearLayoutContentDouble.addView(ViewLineDouble);

        ImageViewDouble2 = new ImageView(context);
        ImageViewDouble2.setLayoutParams(new LinearLayout.LayoutParams(0, MiscHandler.ToDimension(context, 200), 1f));
        ImageViewDouble2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageViewDouble2.setBackgroundResource(R.color.BlueGray);

        LinearLayoutContentDouble.addView(ImageViewDouble2);

        LinearLayoutContentTriple = new LinearLayout(context);
        LinearLayoutContentTriple.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayoutContentTriple.setVisibility(View.GONE);

        RelativeLayoutContent.addView(LinearLayoutContentTriple);

        ImageViewTriple1 = new ImageView(context);
        ImageViewTriple1.setLayoutParams(new LinearLayout.LayoutParams(0, MiscHandler.ToDimension(context, 200), 1f));
        ImageViewTriple1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageViewTriple1.setBackgroundResource(R.color.BlueGray);

        LinearLayoutContentTriple.addView(ImageViewTriple1);

        View ViewLineTriple = new View(context);
        ViewLineTriple.setLayoutParams(new LinearLayout.LayoutParams(0, RelativeLayout.LayoutParams.MATCH_PARENT, 0.05f));

        LinearLayoutContentTriple.addView(ViewLineTriple);

        RelativeLayout RelativeLayoutTripleLayout = new RelativeLayout(context);
        RelativeLayoutTripleLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MiscHandler.ToDimension(context, 200), 1f));

        LinearLayoutContentTriple.addView(RelativeLayoutTripleLayout);

        ImageViewTriple2 = new ImageView(context);
        ImageViewTriple2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 97)));
        ImageViewTriple2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageViewTriple2.setBackgroundResource(R.color.BlueGray);
        ImageViewTriple2.setId(MiscHandler.GenerateViewID());

        RelativeLayoutTripleLayout.addView(ImageViewTriple2);

        RelativeLayout.LayoutParams ViewLineTriple2Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 6));
        ViewLineTriple2Param.addRule(RelativeLayout.BELOW, ImageViewTriple2.getId());

        View ViewLineTriple2 = new View(context);
        ViewLineTriple2.setLayoutParams(ViewLineTriple2Param);
        ViewLineTriple2.setId(MiscHandler.GenerateViewID());

        RelativeLayoutTripleLayout.addView(ViewLineTriple2);

        RelativeLayout.LayoutParams ImageViewTriple3Triple3Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 97));
        ImageViewTriple3Triple3Param.addRule(RelativeLayout.BELOW, ViewLineTriple2.getId());

        ImageViewTriple3 = new ImageView(context);
        ImageViewTriple3.setLayoutParams(ImageViewTriple3Triple3Param);
        ImageViewTriple3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageViewTriple3.setBackgroundResource(R.color.BlueGray);

        RelativeLayoutTripleLayout.addView(ImageViewTriple3);

        GradientDrawable ShapeLink = new GradientDrawable();
        ShapeLink.setStroke(MiscHandler.ToDimension(context, 1), ContextCompat.getColor(context, R.color.BlueGray));

        RelativeLayoutContentLink = new RelativeLayout(context);
        RelativeLayoutContentLink.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        RelativeLayoutContentLink.setPadding(MiscHandler.ToDimension(context, 1), MiscHandler.ToDimension(context, 1), MiscHandler.ToDimension(context, 1), MiscHandler.ToDimension(context, 1));
        RelativeLayoutContentLink.setBackground(ShapeLink);
        RelativeLayoutContentLink.setMinimumHeight(MiscHandler.ToDimension(context, 56));
        RelativeLayoutContentLink.setVisibility(View.GONE);

        RelativeLayoutContent.addView(RelativeLayoutContentLink);

        RelativeLayout.LayoutParams LoadingViewLinkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        LoadingViewLinkParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        LoadingViewLinkParam.addRule(RelativeLayout.CENTER_VERTICAL);

        LoadingViewLink = new LoadingView(context);
        LoadingViewLink.setLayoutParams(LoadingViewLinkParam);

        RelativeLayoutContentLink.addView(LoadingViewLink);

        RelativeLayout.LayoutParams TextViewTryLinkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTryLinkParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        TextViewTryLinkParam.addRule(RelativeLayout.CENTER_VERTICAL);

        TextViewTryLink = new TextView(context);
        TextViewTryLink.setLayoutParams(TextViewTryLinkParam);
        TextViewTryLink.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewTryLink.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        TextViewTryLink.setTypeface(null, Typeface.BOLD);
        TextViewTryLink.setText(getString(R.string.TryAgain));
        TextViewTryLink.setVisibility(View.GONE);

        RelativeLayoutContentLink.addView(TextViewTryLink);

        RelativeLayout.LayoutParams TextViewWebsiteLinkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewWebsiteLinkParam.setMargins(MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 5), MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 5));

        TextViewWebsiteLink = new TextView(context);
        TextViewWebsiteLink.setLayoutParams(TextViewWebsiteLinkParam);
        TextViewWebsiteLink.setTextColor(ContextCompat.getColor(context, R.color.BlueLight));
        TextViewWebsiteLink.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TextViewWebsiteLink.setId(MiscHandler.GenerateViewID());

        RelativeLayoutContentLink.addView(TextViewWebsiteLink);

        RelativeLayout.LayoutParams TextViewDescriptionLinkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewDescriptionLinkParam.setMargins(MiscHandler.ToDimension(context, 10), 0, MiscHandler.ToDimension(context, 10), MiscHandler.ToDimension(context, 5));
        TextViewDescriptionLinkParam.addRule(RelativeLayout.BELOW, TextViewWebsiteLink.getId());

        TextViewDescriptionLink = new TextView(context);
        TextViewDescriptionLink.setLayoutParams(TextViewDescriptionLinkParam);
        TextViewDescriptionLink.setTextColor(ContextCompat.getColor(context, R.color.Gray3));
        TextViewDescriptionLink.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        TextViewDescriptionLink.setId(MiscHandler.GenerateViewID());

        RelativeLayoutContentLink.addView(TextViewDescriptionLink);

        RelativeLayout.LayoutParams ImageViewFavLinkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ImageViewFavLinkParam.addRule(RelativeLayout.BELOW, TextViewDescriptionLink.getId());

        ImageViewFavLink = new ImageView(context);
        ImageViewFavLink.setLayoutParams(ImageViewFavLinkParam);
        ImageViewFavLink.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageViewFavLink.setMaxHeight(MiscHandler.ToDimension(context, 300));
        ImageViewFavLink.setAdjustViewBounds(true);

        RelativeLayoutContentLink.addView(ImageViewFavLink);

        RelativeLayout.LayoutParams LinearLayoutInfoParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        LinearLayoutInfoParam.addRule(RelativeLayout.BELOW, RelativeLayoutContent.getId());

        LinearLayout LinearLayoutInfo = new LinearLayout(context);
        LinearLayoutInfo.setLayoutParams(LinearLayoutInfoParam);
        LinearLayoutInfo.setId(MiscHandler.GenerateViewID());
        LinearLayoutInfo.setPadding(0, MiscHandler.ToDimension(context, 15), 0, MiscHandler.ToDimension(context, 15));

        RelativeLayoutMain.addView(LinearLayoutInfo);

        TextViewLikeCount = new TextView(context);
        TextViewLikeCount.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        TextViewLikeCount.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewLikeCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        TextViewLikeCount.setTypeface(null, Typeface.BOLD);
        TextViewLikeCount.setPadding(MiscHandler.ToDimension(context, 15), 0, 0, 0);
        TextViewLikeCount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle bundle = new Bundle();
                bundle.putString("PostID", PostID);

                Fragment fragment = new FragmentLike();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ActivityMainFullContainer, fragment).addToBackStack("FragmentLike").commit();
            }
        });

        LinearLayoutInfo.addView(TextViewLikeCount);

        TextView TextViewLike = new TextView(context);
        TextViewLike.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        TextViewLike.setTextColor(ContextCompat.getColor(context, R.color.BlueGray2));
        TextViewLike.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        TextViewLike.setTypeface(null, Typeface.BOLD);
        TextViewLike.setText(getString(R.string.FragmentPostDetailsLike));
        TextViewLike.setPadding(MiscHandler.ToDimension(context, 5), 0, 0, 0);
        TextViewLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                bundle.putString("PostID", PostID);

                Fragment fragment = new FragmentLike();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ActivityMainFullContainer, fragment).addToBackStack("FragmentLike").commit();
            }
        });

        LinearLayoutInfo.addView(TextViewLike);

        TextViewCommentCount = new TextView(context);
        TextViewCommentCount.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        TextViewCommentCount.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewCommentCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        TextViewCommentCount.setTypeface(null, Typeface.BOLD);
        TextViewCommentCount.setPadding(MiscHandler.ToDimension(context, 15), 0, 0, 0);
        TextViewCommentCount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (IsComment)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("PostID", PostID);
                    bundle.putString("OwnerID", OwnerID);

                    Fragment fragment = new FragmentComment();
                    fragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ActivityMainFullContainer, fragment).addToBackStack("FragmentComment").commit();
                    return;
                }

                MiscHandler.Toast(context, getString(R.string.FragmentPostDetailsCommentDisable));
            }
        });

        LinearLayoutInfo.addView(TextViewCommentCount);

        TextView TextViewComment = new TextView(context);
        TextViewComment.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        TextViewComment.setTextColor(ContextCompat.getColor(context, R.color.BlueGray2));
        TextViewComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        TextViewComment.setTypeface(null, Typeface.BOLD);
        TextViewComment.setText(getString(R.string.FragmentPostDetailsComment));
        TextViewComment.setPadding(MiscHandler.ToDimension(context, 5), 0, 0, 0);
        TextViewComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (IsComment)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("PostID", PostID);
                    bundle.putString("OwnerID", OwnerID);

                    Fragment fragment = new FragmentComment();
                    fragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ActivityMainFullContainer, fragment).addToBackStack("FragmentComment").commit();
                    return;
                }

                MiscHandler.Toast(context, getString(R.string.FragmentPostDetailsCommentDisable));
            }
        });

        LinearLayoutInfo.addView(TextViewComment);

        RelativeLayout.LayoutParams ViewLine2Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1));
        ViewLine2Param.addRule(RelativeLayout.BELOW, LinearLayoutInfo.getId());

        View ViewLine2 = new View(context);
        ViewLine2.setLayoutParams(ViewLine2Param);
        ViewLine2.setBackgroundResource(R.color.Gray);
        ViewLine2.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(ViewLine2);

        RelativeLayout.LayoutParams LinearLayoutToolParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        LinearLayoutToolParam.addRule(RelativeLayout.BELOW, ViewLine2.getId());

        LinearLayout LinearLayoutTool = new LinearLayout(context);
        LinearLayoutTool.setLayoutParams(LinearLayoutToolParam);
        LinearLayoutTool.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(LinearLayoutTool);

        ImageViewIconLike = new ImageView(context);
        ImageViewIconLike.setPadding(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));
        ImageViewIconLike.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewIconLike.setLayoutParams(new LinearLayout.LayoutParams(MiscHandler.ToDimension(context, 56), MiscHandler.ToDimension(context, 56), 1f));
        ImageViewIconLike.setImageResource(R.drawable.ic_like);
        ImageViewIconLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (IsLike)
                {
                    ImageViewIconLike.setImageResource(R.drawable.ic_like);

                    ObjectAnimator Fade = ObjectAnimator.ofFloat(ImageViewIconLike, "alpha",  0.1f, 1f);
                    Fade.setDuration(400);

                    AnimatorSet AnimationSet = new AnimatorSet();
                    AnimationSet.play(Fade);
                    AnimationSet.start();

                    IsLike = false;
                    TextViewLikeCount.setText(String.valueOf(Integer.parseInt(TextViewLikeCount.getText().toString()) - 1));

                    AndroidNetworking.post(URLHandler.GetURL(URLHandler.URL.POST_LIKE))
                    .addBodyParameter("PostID", PostID)
                    .addHeaders("TOKEN", SharedHandler.GetString("TOKEN"))
                    .setTag("FragmentPostDetails").build().getAsString(null);
                }
                else
                {
                    ImageViewIconLike.setImageResource(R.drawable.ic_like_red);

                    ObjectAnimator SizeX = ObjectAnimator.ofFloat(ImageViewIconLike, "scaleX", 1.5f);
                    SizeX.setDuration(200);

                    ObjectAnimator SizeY = ObjectAnimator.ofFloat(ImageViewIconLike, "scaleY", 1.5f);
                    SizeY.setDuration(200);

                    ObjectAnimator Fade = ObjectAnimator.ofFloat(ImageViewIconLike, "alpha",  0.1f, 1f);
                    Fade.setDuration(400);

                    ObjectAnimator SizeX2 = ObjectAnimator.ofFloat(ImageViewIconLike, "scaleX", 1f);
                    SizeX2.setDuration(200);
                    SizeX2.setStartDelay(200);

                    ObjectAnimator SizeY2 = ObjectAnimator.ofFloat(ImageViewIconLike, "scaleY", 1f);
                    SizeY2.setDuration(200);
                    SizeY2.setStartDelay(200);

                    AnimatorSet AnimationSet = new AnimatorSet();
                    AnimationSet.playTogether(SizeX, SizeY, Fade, SizeX2, SizeY2);
                    AnimationSet.start();

                    IsLike = true;
                    TextViewLikeCount.setText(String.valueOf(Integer.parseInt(TextViewLikeCount.getText().toString()) + 1));

                    AndroidNetworking.post(URLHandler.GetURL(URLHandler.URL.POST_LIKE))
                    .addBodyParameter("PostID", PostID)
                    .addHeaders("TOKEN", SharedHandler.GetString("TOKEN"))
                    .setTag("FragmentPostDetails").build().getAsString(null);
                }
            }
        });

        LinearLayoutTool.addView(IconLike);

        ImageView IconComment = new ImageView(context);
        IconComment.setPadding(MiscHandler.ToDimension(14), MiscHandler.ToDimension(14), MiscHandler.ToDimension(14), MiscHandler.ToDimension(14));
        IconComment.setScaleType(ImageView.ScaleType.FIT_CENTER);
        IconComment.setLayoutParams(new LinearLayout.LayoutParams(MiscHandler.ToDimension(56), MiscHandler.ToDimension(56), 1f));
        IconComment.setImageResource(R.drawable.ic_comment);
        IconComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (IsComment)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("PostID", PostID);
                    bundle.putString("OwnerID", OwnerID);

                    Fragment fragment = new FragmentComment();
                    fragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ActivityMainFullContainer, fragment).addToBackStack("FragmentComment").commit();
                    return;
                }

                MiscHandler.Toast(getActivity(), getString(R.string.AdapterPostComment));
            }
        });

        LinearLayoutTool.addView(IconComment);

        ImageView IconShare = new ImageView(context);
        IconShare.setPadding(MiscHandler.ToDimension(14), MiscHandler.ToDimension(14), MiscHandler.ToDimension(14), MiscHandler.ToDimension(14));
        IconShare.setScaleType(ImageView.ScaleType.FIT_CENTER);
        IconShare.setLayoutParams(new LinearLayout.LayoutParams(MiscHandler.ToDimension(56), MiscHandler.ToDimension(56), 1f));
        IconShare.setImageResource(R.drawable.ic_share);
        IconShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent SendIntent = new Intent();
                SendIntent.setAction(Intent.ACTION_SEND);
                SendIntent.putExtra(Intent.EXTRA_TEXT, TextViewMessage + "\n http://BioGram.Co/");
                SendIntent.setType("text/plain");
                getActivity().startActivity(Intent.createChooser(SendIntent, getString(R.string.AdapterPostChoice)));
            }
        });

        LinearLayoutTool.addView(IconShare);

        RelativeLayout.LayoutParams Line3Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(1));
        Line3Param.addRule(RelativeLayout.BELOW, LinearLayoutTool.getId());
        Line3Param.setMargins(0, 0, 0, MiscHandler.ToDimension(25));

        View Line3 = new View(context);
        Line3.setLayoutParams(Line3Param);
        Line3.setBackgroundColor(ContextCompat.getColor(context, R.color.Gray));
        Line3.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(Line3);

        RelativeLayout.LayoutParams LoadParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        LoadParam.addRule(RelativeLayout.BELOW, ViewLine.getId());

        RelativeLayoutLoading = new RelativeLayout(context);
        RelativeLayoutLoading.setLayoutParams(LoadParam);
        RelativeLayoutLoading.setBackgroundColor(ContextCompat.getColor(context, R.color.White));
        RelativeLayoutLoading.setVisibility(View.VISIBLE);

        Root.addView(RelativeLayoutLoading);

        RelativeLayout.LayoutParams LoadingPageParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        LoadingPageParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        LoadingPageParam.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        LoadingViewData = new LoadingView(context);
        LoadingViewData.setLayoutParams(LoadingPageParam);


        Root.addView(LoadingViewData);

        RelativeLayout.LayoutParams TryPageParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TryPageParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        TryPageParam.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        TextViewTry = new TextView(context);
        TextViewTry.setLayoutParams(TryPageParam);
        TextViewTry.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewTry.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        TextViewTry.setTypeface(null, Typeface.BOLD);
        TextViewTry.setText(getString(R.string.GeneralTryAgain));
        TextViewTry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RetrieveDataFromServer();
            }
        });

        Root.addView(TextViewTry);

        RetrieveDataFromServer();

        return Root;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        RequestHandler.Core().Cancel("FragmentPostDetails");
    }

    private void RetrieveDataFromServer()
    {
        LoadingViewData.Start();
        TextViewTry.setVisibility(View.GONE);

        AndroidNetworking.post(URLHandler.GetURL(URLHandler.URL.POST_DETAILS))
        .addBodyParameter("PostID", ((getArguments() == null) ? "" : getArguments().getString("PostID", "")))
        .addHeaders("TOKEN", SharedHandler.GetString("TOKEN"))
        .setTag("FragmentPostDetails").build().getAsString(new StringRequestListener()
        {
            @Override
            public void onResponse(String Response)
            {
                try
                {
                    JSONObject Result = new JSONObject(Response);

                    if (Result.getInt("Message") == 1000)
                    {
                        Result = new JSONObject(Result.getString("Result"));

                        if (!Result.getString("Avatar").equals(""))
                            RequestHandler.Core().LoadImage(ImageViewCircleProfile, Result.getString("Avatar"), "FragmentPostDetails", MiscHandler.ToDimension(55), MiscHandler.ToDimension(55), true);

                        TextViewUsername.setText(Result.getString("Username"));
                        TextViewTime.setText(MiscHandler.GetTimeName(Result.getLong("Time")));

                        if (!Result.getString("Message").equals(""))
                        {
                            if (new Bidi(Result.getString("Message"), Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT).getBaseLevel() == 0)
                            {
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) TextViewMessage.getLayoutParams();
                                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                                TextViewMessage.setLayoutParams(params);
                            }
                            else
                            {
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) TextViewMessage.getLayoutParams();
                                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                TextViewMessage.setLayoutParams(params);
                            }

                            TextViewMessage.setVisibility(View.VISIBLE);
                            TextViewMessage.setText(Result.getString("Message"));

                            new TagHandler(TextViewMessage, new TagHandler.OnTagClickListener()
                            {
                                @Override
                                public void OnTagClicked(String Tag, int Type)
                                {
                                    MiscHandler.Toast(getActivity(), Tag);
                                }
                            });
                        }

                        if (Result.getInt("Type") == 1)
                        {
                            try
                            {
                                final JSONArray URL = new JSONArray(Result.getString("Data"));

                                switch (URL.length())
                                {
                                    case 1:
                                        LinearLayoutContentSingle.setVisibility(View.VISIBLE);
                                        ImageViewSingle.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { try { OpenPreviewImage(URL.get(0).toString(), null, null); } catch (Exception e) { /* Leave Me Alone */ } } });
                                        RequestHandler.Core().LoadImage(ImageViewSingle, URL.get(0).toString(), "FragmentPostDetails", true);
                                        break;
                                    case 2:
                                        LinearLayoutContentDouble.setVisibility(View.VISIBLE);
                                        ImageViewDouble1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { try { OpenPreviewImage(URL.get(0).toString(), URL.get(1).toString(), null); } catch (Exception e) { /* Leave Me Alone */ } } });
                                        ImageViewDouble2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { try { OpenPreviewImage(URL.get(1).toString(), URL.get(0).toString(), null); } catch (Exception e) { /* Leave Me Alone */ } } });
                                        RequestHandler.Core().LoadImage(ImageViewDouble1, URL.get(0).toString(), "FragmentPostDetails", true);
                                        RequestHandler.Core().LoadImage(ImageViewDouble2, URL.get(1).toString(), "FragmentPostDetails", true);
                                        break;
                                    case 3:
                                        LinearLayoutContentTriple.setVisibility(View.VISIBLE);
                                        ImageViewTriple1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { try { OpenPreviewImage(URL.get(0).toString(), URL.get(1).toString(), URL.get(2).toString()); } catch (Exception e) { /* Leave Me Alone */ } } });
                                        ImageViewTriple2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { try { OpenPreviewImage(URL.get(1).toString(), URL.get(2).toString(), URL.get(0).toString()); } catch (Exception e) { /* Leave Me Alone */ } } });
                                        ImageViewTriple3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { try { OpenPreviewImage(URL.get(2).toString(), URL.get(0).toString(), URL.get(1).toString()); } catch (Exception e) { /* Leave Me Alone */ } } });
                                        RequestHandler.Core().LoadImage(ImageViewTriple1, URL.get(0).toString(), "FragmentPostDetails", true);
                                        RequestHandler.Core().LoadImage(ImageViewTriple2, URL.get(1).toString(), "FragmentPostDetails", true);
                                        RequestHandler.Core().LoadImage(ImageViewTriple3, URL.get(2).toString(), "FragmentPostDetails", true);
                                        break;
                                }
                            }
                            catch (Exception e)
                            {
                                // Leave Me Alone
                            }
                        }
                        else if (Result.getInt("Type") == 2)
                        {
                            // Fill Me Later
                            Result.getInt("Type");
                        }
                        else if (Result.getInt("Type") == 3)
                        {
                            RelativeLayoutContentLink.setVisibility(View.VISIBLE);
                            Loading.Start();

                            try
                            {
                                JSONArray URL = new JSONArray(Result.getString("Data"));

                                final TextCrawler Request = new TextCrawler(URL.get(0).toString(), "FragmentPostDetails", new TextCrawler.TextCrawlerCallBack()
                                {
                                    @Override
                                    public void OnCompleted(TextCrawler.URLContent Content)
                                    {
                                        if (new Bidi(Content.Title, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT).getBaseLevel() == 0)
                                        {
                                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) Website.getLayoutParams();
                                            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                                            Website.setLayoutParams(params);
                                        }
                                        else
                                        {
                                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) Website.getLayoutParams();
                                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                            Website.setLayoutParams(params);
                                        }

                                        if (new Bidi(Content.Title, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT).getBaseLevel() == 0)
                                        {
                                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) Description.getLayoutParams();
                                            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                                            Description.setLayoutParams(params);
                                        }
                                        else
                                        {
                                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) Description.getLayoutParams();
                                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                            Description.setLayoutParams(params);
                                        }

                                        Website.setText(Content.Title);
                                        Description.setText(Content.Description);
                                        Loading.Stop();
                                        Fav.setVisibility(View.VISIBLE);

                                        RequestHandler.Core().LoadImage(Fav, Content.Image, "FragmentPostDetails", true);
                                    }

                                    @Override
                                    public void OnFailed()
                                    {
                                        Loading.Stop();
                                        Try.setVisibility(View.VISIBLE);
                                    }
                                });

                                Try.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        Request.Start();
                                        Loading.Start();
                                    }
                                });

                                Request.Start();
                            }
                            catch (Exception e)
                            {
                                // Leave Me Alone
                            }
                        }

                        LikeCount.setText(String.valueOf(Result.getInt("LikeCount")));
                        CommentCount.setText(String.valueOf(Result.getInt("CommentCount")));

                        PostID = Result.getString("PostID");
                        OwnerID = Result.getString("OwnerID");
                        IsLike = Result.getBoolean("Like");
                        IsComment = Result.getBoolean("Comment");
                        IsBookMark = Result.getBoolean("BookMark");

                        if (IsLike)
                            IconLike.setImageResource(R.drawable.ic_like_red);

                        if (IsBookMark)
                            BookMark.setImageResource(R.drawable.ic_bookmark_black2);

                        ImageViewOption.setVisibility(View.VISIBLE);
                        BookMark.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception e)
                {
                    // Leave Me Alone
                }

                LoadingViewData.Stop();
                TextViewTry.setVisibility(View.GONE);
                RelativeLayoutLoading.setVisibility(View.GONE);
            }
        });
    }

    private void OpenPreviewImage(String URL, String URL2, String URL3)
    {
        Bundle bundle = new Bundle();
        bundle.putString("URL", URL);

        if (URL2 != null && !URL2.equals(""))
            bundle.putString("URL2", URL2);

        if (URL3 != null && !URL3.equals(""))
            bundle.putString("URL3", URL3);

        Fragment fragment = new FragmentImagePreview();
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ActivityMainFullContainer, fragment).addToBackStack("FragmentImagePreview").commit();
    }
}