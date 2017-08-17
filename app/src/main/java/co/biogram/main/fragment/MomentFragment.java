package co.biogram.main.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.biogram.main.R;
import co.biogram.main.handler.MiscHandler;
import co.biogram.main.handler.SharedHandler;
import co.biogram.main.misc.AdapterPost;
import co.biogram.main.misc.LoadingView;
import co.biogram.main.misc.RecyclerViewScroll;

public class MomentFragment extends Fragment
{
    private final List<AdapterPost.Struct> PostList = new ArrayList<>();
    private RecyclerViewScroll RecyclerViewScrollMain;
    private AdapterPost Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final Context context = getActivity();
        final ImageView ImageViewWrite = new ImageView(context);

        RelativeLayout RelativeLayoutMain = new RelativeLayout(context);
        RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        RelativeLayoutMain.setBackgroundResource(R.color.White);
        RelativeLayoutMain.setClickable(true);

        RelativeLayout RelativeLayoutHeader = new RelativeLayout(context);
        RelativeLayoutHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 56)));
        RelativeLayoutHeader.setBackgroundResource(R.color.White5);
        RelativeLayoutHeader.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(RelativeLayoutHeader);

        RelativeLayout.LayoutParams TextViewTitleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTitleParam.addRule(RelativeLayout.CENTER_VERTICAL);
        TextViewTitleParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        TextViewTitleParam.setMargins(MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15), MiscHandler.ToDimension(context, 15));

        TextView TextViewTitle = new TextView(context);
        TextViewTitle.setLayoutParams(TextViewTitleParam);
        TextViewTitle.setText(getString(R.string.MomentFragment));
        TextViewTitle.setTextColor(ContextCompat.getColor(context, R.color.Black));
        TextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TextViewTitle.setTypeface(null, Typeface.BOLD);

        RelativeLayoutHeader.addView(TextViewTitle);

        RelativeLayout.LayoutParams ImageViewBookbarkParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 56), RelativeLayout.LayoutParams.MATCH_PARENT);
        ImageViewBookbarkParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        ImageView ImageViewBookbark = new ImageView(context);
        ImageViewBookbark.setLayoutParams(ImageViewBookbarkParam);
        ImageViewBookbark.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewBookbark.setPadding(MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16));
        ImageViewBookbark.setImageResource(R.drawable.ic_bookmark_blue);
        ImageViewBookbark.setId(MiscHandler.GenerateViewID());
        ImageViewBookbark.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.MainActivityFullContainer, new BookmarkFragment()).addToBackStack("BookmarkFragment").commit();
            }
        });

        RelativeLayoutHeader.addView(ImageViewBookbark);

        RelativeLayout.LayoutParams ImageViewSearchParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 56), RelativeLayout.LayoutParams.MATCH_PARENT);
        ImageViewSearchParam.addRule(RelativeLayout.LEFT_OF, ImageViewBookbark.getId());

        ImageView ImageViewSearch = new ImageView(context);
        ImageViewSearch.setLayoutParams(ImageViewSearchParam);
        ImageViewSearch.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewSearch.setPadding(MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16), MiscHandler.ToDimension(context, 16));
        ImageViewSearch.setImageResource(R.drawable.ic_search_blue);
        ImageViewSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.MainActivityFullContainer, new SearchFragment(), "SearchFragment").addToBackStack("SearchFragment").commit();
            }
        });

        RelativeLayoutHeader.addView(ImageViewSearch);

        RelativeLayout.LayoutParams ViewLineParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 1));
        ViewLineParam.addRule(RelativeLayout.BELOW, RelativeLayoutHeader.getId());

        View ViewLine = new View(context);
        ViewLine.setLayoutParams(ViewLineParam);
        ViewLine.setBackgroundResource(R.color.Gray2);
        ViewLine.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(ViewLine);

        RelativeLayout.LayoutParams SwipeRefreshLayoutMainParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        SwipeRefreshLayoutMainParam.addRule(RelativeLayout.BELOW, ViewLine.getId());

        final SwipeRefreshLayout SwipeRefreshLayoutMain = new SwipeRefreshLayout(context);
        SwipeRefreshLayoutMain.setLayoutParams(SwipeRefreshLayoutMainParam);
        SwipeRefreshLayoutMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            private boolean LoadingTop = false;

            @Override
            public void onRefresh()
            {
                if (LoadingTop)
                    return;

                LoadingTop = true;
                SwipeRefreshLayoutMain.setRefreshing(false);
                SwipeRefreshLayoutMain.setEnabled(false);

                if (PostList.size() == 0)
                {
                    LoadingTop = false;
                    SwipeRefreshLayoutMain.setEnabled(true);
                    return;
                }

                AndroidNetworking.post(MiscHandler.GetRandomServer("PostList"))
                .addBodyParameter("Time", String.valueOf(PostList.get(0).Time))
                .addHeaders("TOKEN", SharedHandler.GetString(context, "TOKEN"))
                .setTag("MomentFragment")
                .build()
                .getAsString(new StringRequestListener()
                {
                    @Override
                    public void onResponse(String Response)
                    {
                        LoadingTop = false;
                        SwipeRefreshLayoutMain.setEnabled(true);

                        try
                        {
                            JSONObject Result = new JSONObject(Response);

                            if (Result.getInt("Message") == 1000 && !Result.getString("Result").equals(""))
                            {
                                JSONArray ResultList = new JSONArray(Result.getString("Result"));

                                for (int K = 0; K < ResultList.length(); K++)
                                {
                                    JSONObject Post = ResultList.getJSONObject(K);

                                    AdapterPost.Struct PostStruct = new AdapterPost.Struct();
                                    PostStruct.PostID = Post.getString("PostID");
                                    PostStruct.OwnerID = Post.getString("OwnerID");
                                    PostStruct.Type = Post.getInt("Type");
                                    PostStruct.Category = Post.getInt("Category");
                                    PostStruct.Time = Post.getInt("Time");
                                    PostStruct.Comment = Post.getBoolean("Comment");
                                    PostStruct.Message = Post.getString("Message");
                                    PostStruct.Data = Post.getString("Data");
                                    PostStruct.Username = Post.getString("Username");
                                    PostStruct.Avatar = Post.getString("Avatar");
                                    PostStruct.Like = Post.getBoolean("Like");
                                    PostStruct.LikeCount = Post.getInt("LikeCount");
                                    PostStruct.CommentCount = Post.getInt("CommentCount");
                                    PostStruct.BookMark = Post.getBoolean("BookMark");
                                    PostStruct.Follow = Post.getBoolean("Follow");

                                    PostList.add(0, PostStruct);
                                }

                                Adapter.notifyDataSetChanged();
                            }
                        }
                        catch (Exception e)
                        {
                            MiscHandler.Debug("FragmentMoment-RequestNew: " + e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        LoadingTop = false;
                        SwipeRefreshLayoutMain.setEnabled(true);
                    }
                });
            }
        });

        RelativeLayoutMain.addView(SwipeRefreshLayoutMain);

        LinearLayoutManager LinearLayoutManagerMain = new LinearLayoutManager(context);
        RecyclerViewScrollMain = new RecyclerViewScroll(LinearLayoutManagerMain)
        {
            @Override
            public void OnLoadMore()
            {
                PostList.add(null);
                Adapter.notifyItemInserted(PostList.size());

                AndroidNetworking.post(MiscHandler.GetRandomServer("PostList"))
                .addBodyParameter("Skip", String.valueOf(PostList.size() - 1))
                .addHeaders("TOKEN", SharedHandler.GetString(context, "TOKEN"))
                .setTag("MomentFragment")
                .build()
                .getAsString(new StringRequestListener()
                {
                    @Override
                    public void onResponse(String Response)
                    {
                        PostList.remove(PostList.size() - 1);
                        Adapter.notifyItemRemoved(PostList.size());

                        try
                        {
                            JSONObject Result = new JSONObject(Response);

                            if (Result.getInt("Message") == 1000 && !Result.getString("Result").equals(""))
                            {
                                JSONArray ResultList = new JSONArray(Result.getString("Result"));

                                for (int K = 0; K < ResultList.length(); K++)
                                {
                                    JSONObject Post = ResultList.getJSONObject(K);

                                    AdapterPost.Struct PostStruct = new AdapterPost.Struct();
                                    PostStruct.PostID = Post.getString("PostID");
                                    PostStruct.OwnerID = Post.getString("OwnerID");
                                    PostStruct.Type = Post.getInt("Type");
                                    PostStruct.Category = Post.getInt("Category");
                                    PostStruct.Time = Post.getInt("Time");
                                    PostStruct.Comment = Post.getBoolean("Comment");
                                    PostStruct.Message = Post.getString("Message");
                                    PostStruct.Data = Post.getString("Data");
                                    PostStruct.Username = Post.getString("Username");
                                    PostStruct.Avatar = Post.getString("Avatar");
                                    PostStruct.Like = Post.getBoolean("Like");
                                    PostStruct.LikeCount = Post.getInt("LikeCount");
                                    PostStruct.CommentCount = Post.getInt("CommentCount");
                                    PostStruct.BookMark = Post.getBoolean("BookMark");
                                    PostStruct.Follow = Post.getBoolean("Follow");

                                    PostList.add(PostStruct);
                                }

                                Adapter.notifyDataSetChanged();
                            }
                        }
                        catch (Exception e)
                        {
                            MiscHandler.Debug("MomentFragment-RequestBottom: " + e.toString());
                            RecyclerViewScrollMain.ResetLoading(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        PostList.remove(PostList.size() - 1);
                        Adapter.notifyItemRemoved(PostList.size());
                        RecyclerViewScrollMain.ResetLoading(false);
                    }
                });
            }
        };

        RecyclerView RecyclerViewMain = new RecyclerView(context);
        RecyclerViewMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        RecyclerViewMain.setLayoutManager(LinearLayoutManagerMain);
        RecyclerViewMain.setAdapter(Adapter = new AdapterPost(getActivity(), PostList, "MomentFragment"));
        RecyclerViewMain.addOnScrollListener(RecyclerViewScrollMain);
        RecyclerViewMain.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            private boolean IsRunning = false;

            @Override
            public void onScrolled(RecyclerView View, int DX, int DY)
            {
                if (!IsRunning)
                {
                    IsRunning = true;

                    if (DY > 0)
                        ImageViewWrite.animate().setDuration(400).translationY(ImageViewWrite.getHeight() + 150).withEndAction(new Runnable() { @Override public void run() { IsRunning = false; } }).setInterpolator(new AccelerateInterpolator(2)).start();
                    else
                        ImageViewWrite.animate().setDuration(400).translationY(0).withEndAction(new Runnable() { @Override public void run() { IsRunning = false; } }).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }
        });

        SwipeRefreshLayoutMain.addView(RecyclerViewMain);

        RelativeLayout.LayoutParams ImageViewWriteParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 60), MiscHandler.ToDimension(context, 60));
        ImageViewWriteParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ImageViewWriteParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ImageViewWriteParam.setMargins(MiscHandler.ToDimension(context, 20), MiscHandler.ToDimension(context, 20), MiscHandler.ToDimension(context, 20), MiscHandler.ToDimension(context, 20));

        GradientDrawable ShapeBorder = new GradientDrawable();
        ShapeBorder.setShape(GradientDrawable.OVAL);
        ShapeBorder.setColor(Color.parseColor("#1f000000"));

        GradientDrawable ShapeContent = new GradientDrawable();
        ShapeContent.setShape(GradientDrawable.OVAL);
        ShapeContent.setColor(ContextCompat.getColor(context, R.color.BlueLight));
        ShapeContent.setStroke(MiscHandler.ToDimension(context, 4), Color.TRANSPARENT);

        ImageViewWrite.setLayoutParams(ImageViewWriteParam);
        ImageViewWrite.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageViewWrite.setBackground(new LayerDrawable(new Drawable[] { ShapeBorder, ShapeContent }));
        ImageViewWrite.setImageResource(R.drawable.ic_write);
        ImageViewWrite.setPadding(MiscHandler.ToDimension(context, 18), MiscHandler.ToDimension(context, 18), MiscHandler.ToDimension(context, 18), MiscHandler.ToDimension(context, 18));
        ImageViewWrite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.MainActivityFullContainer, new WriteFragment()).addToBackStack("WriteFragment").commit();
            }
        });

        RelativeLayoutMain.addView(ImageViewWrite);

        RelativeLayout.LayoutParams RelativeLayoutLoadingParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayoutLoadingParam.addRule(RelativeLayout.BELOW, ViewLine.getId());

        final RelativeLayout RelativeLayoutLoading = new RelativeLayout(context);
        RelativeLayoutLoading.setLayoutParams(RelativeLayoutLoadingParam);
        RelativeLayoutLoading.setBackgroundResource(R.color.White);
        RelativeLayoutLoading.setClickable(true);

        RelativeLayoutMain.addView(RelativeLayoutLoading);

        RelativeLayout.LayoutParams LoadingViewMainParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 56));
        LoadingViewMainParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        final LoadingView LoadingViewMain = new LoadingView(context);
        LoadingViewMain.setLayoutParams(LoadingViewMainParam);

        RelativeLayoutLoading.addView(LoadingViewMain);

        RelativeLayout.LayoutParams TextViewTryParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTryParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        final TextView TextViewTryAgain = new TextView(context);
        TextViewTryAgain.setLayoutParams(TextViewTryParam);
        TextViewTryAgain.setText(getString(R.string.TryAgain));
        TextViewTryAgain.setTextColor(ContextCompat.getColor(context, R.color.BlueGray2));
        TextViewTryAgain.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TextViewTryAgain.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { RetrieveDataFromServer(context, RelativeLayoutLoading, LoadingViewMain, TextViewTryAgain); } });

        RelativeLayoutLoading.addView(TextViewTryAgain);

        RetrieveDataFromServer(context, RelativeLayoutLoading, LoadingViewMain, TextViewTryAgain);

        return RelativeLayoutMain;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        AndroidNetworking.forceCancel("MomentFragment");
    }

    private void RetrieveDataFromServer(final Context context, final RelativeLayout RelativeLayoutLoading, final LoadingView LoadingViewMain, final TextView TextViewTryAgain)
    {
        TextViewTryAgain.setVisibility(View.GONE);
        LoadingViewMain.Start();

        AndroidNetworking.post(MiscHandler.GetRandomServer("PostList"))
        .addHeaders("TOKEN", SharedHandler.GetString(context, "TOKEN"))
        .setTag("MomentFragment")
        .build()
        .getAsString(new StringRequestListener()
        {
            @Override
            public void onResponse(String Response)
            {
                try
                {
                    JSONObject Result = new JSONObject(Response);

                    if (Result.getInt("Message") == 1000 && !Result.getString("Result").equals(""))
                    {
                        JSONArray ResultList = new JSONArray(Result.getString("Result"));

                        for (int K = 0; K < ResultList.length(); K++)
                        {
                            JSONObject Post = ResultList.getJSONObject(K);

                            AdapterPost.Struct PostStruct = new AdapterPost.Struct();
                            PostStruct.PostID = Post.getString("PostID");
                            PostStruct.OwnerID = Post.getString("OwnerID");
                            PostStruct.Type = Post.getInt("Type");
                            PostStruct.Category = Post.getInt("Category");
                            PostStruct.Time = Post.getInt("Time");
                            PostStruct.Comment = Post.getBoolean("Comment");
                            PostStruct.Message = Post.getString("Message");
                            PostStruct.Data = Post.getString("Data");
                            PostStruct.Username = Post.getString("Username");
                            PostStruct.Avatar = Post.getString("Avatar");
                            PostStruct.Like = Post.getBoolean("Like");
                            PostStruct.LikeCount = Post.getInt("LikeCount");
                            PostStruct.CommentCount = Post.getInt("CommentCount");
                            PostStruct.BookMark = Post.getBoolean("BookMark");
                            PostStruct.Follow = Post.getBoolean("Follow");

                            PostList.add(PostStruct);
                        }

                        Adapter.notifyDataSetChanged();
                    }
                }
                catch (Exception e)
                {
                    MiscHandler.Debug("MomentFragment-RequestFirst: " + e.toString());
                }

                LoadingViewMain.Stop();
                TextViewTryAgain.setVisibility(View.GONE);
                RelativeLayoutLoading.setVisibility(View.GONE);
            }

            @Override
            public void onError(ANError anError)
            {
                LoadingViewMain.Stop();
                TextViewTryAgain.setVisibility(View.VISIBLE);
            }
        });
    }
}
