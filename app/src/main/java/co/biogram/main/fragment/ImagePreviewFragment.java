package co.biogram.main.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import co.biogram.main.R;
import co.biogram.main.handler.MiscHandler;
import co.biogram.main.ui.LoadingView;
import co.biogram.main.misc.TouchImageView;

public class ImagePreviewFragment extends Fragment
{
    private final List<String> ListMain = new ArrayList<>();

    private RelativeLayout RelativeLayoutHeader;
    private Bitmap ImageCache = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (getArguments() != null)
        {
            if (!getArguments().getString("URL", "").equals(""))
                ListMain.add(getArguments().getString("URL"));

            if (!getArguments().getString("URL2", "").equals(""))
                ListMain.add(getArguments().getString("URL2"));

            if (!getArguments().getString("URL3", "").equals(""))
                ListMain.add(getArguments().getString("URL3"));
        }

        Context context = getActivity();

        RelativeLayout RelativeLayoutMain = new RelativeLayout(context);
        RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        RelativeLayoutMain.setBackgroundResource(R.color.Black);
        RelativeLayoutMain.setClickable(true);

        RelativeLayoutHeader = new RelativeLayout(context);
        RelativeLayoutHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 56)));
        RelativeLayoutHeader.setBackgroundColor(Color.parseColor("#3f000000"));

        RelativeLayoutMain.addView(RelativeLayoutHeader);

        ImageView ImageViewBack = new ImageView(context);
        ImageViewBack.setPadding(MiscHandler.ToDimension(context, 12), MiscHandler.ToDimension(context, 12), MiscHandler.ToDimension(context, 12), MiscHandler.ToDimension(context, 12));
        ImageViewBack.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageViewBack.setLayoutParams(new RelativeLayout.LayoutParams(MiscHandler.ToDimension(context, 56), MiscHandler.ToDimension(context, 56)));
        ImageViewBack.setImageResource(R.drawable.ic_back_white);
        ImageViewBack.setId(MiscHandler.GenerateViewID());
        ImageViewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getActivity().onBackPressed();
            }
        });

        RelativeLayoutHeader.addView(ImageViewBack);

        RelativeLayout.LayoutParams TextViewTitleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTitleParam.addRule(RelativeLayout.RIGHT_OF, ImageViewBack.getId());
        TextViewTitleParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        TextView TextViewTitle = new TextView(context);
        TextViewTitle.setLayoutParams(TextViewTitleParam);
        TextViewTitle.setTextColor(ContextCompat.getColor(context, R.color.White));
        TextViewTitle.setText(getString(R.string.ImagePreviewFragment));
        TextViewTitle.setTypeface(null, Typeface.BOLD);
        TextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        RelativeLayoutHeader.addView(TextViewTitle);

        if (ImageCache != null)
        {
            TouchImageView TouchImageViewMain = new TouchImageView(context);
            TouchImageViewMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            TouchImageViewMain.setImageBitmap(ImageCache);
            TouchImageViewMain.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (RelativeLayoutHeader.getVisibility() == View.GONE)
                        RelativeLayoutHeader.setVisibility(View.VISIBLE);
                    else
                        RelativeLayoutHeader.setVisibility(View.GONE);
                }
            });

            RelativeLayoutMain.addView(TouchImageViewMain);
        }
        else
        {
            ViewPager ViewPagerMain = new ViewPager(context);
            ViewPagerMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ViewPagerMain.setAdapter(new PreviewAdapter(context));

            RelativeLayoutMain.addView(ViewPagerMain);
        }

        RelativeLayoutHeader.bringToFront();

        return RelativeLayoutMain;
    }

    public void SetBitmap(Bitmap bitmap)
    {
        ImageCache = bitmap;
    }

    private class PreviewAdapter extends PagerAdapter
    {
        private final Context context;

        PreviewAdapter(Context c)
        {
            context = c;
        }

        @Override
        public Object instantiateItem(ViewGroup Container, int Position)
        {
            RelativeLayout RelativeLayoutMain = new RelativeLayout(context);
            RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

            TouchImageView TouchImageViewMain = new TouchImageView(context);
            TouchImageViewMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            TouchImageViewMain.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (RelativeLayoutHeader.getVisibility() == View.GONE)
                        RelativeLayoutHeader.setVisibility(View.VISIBLE);
                    else
                        RelativeLayoutHeader.setVisibility(View.GONE);
                }
            });

            RelativeLayoutMain.addView(TouchImageViewMain);

            RelativeLayout.LayoutParams LoadingViewMainParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(context, 56));
            LoadingViewMainParam.addRule(RelativeLayout.CENTER_IN_PARENT);

            final LoadingView LoadingViewMain = new LoadingView(context);
            LoadingViewMain.setLayoutParams(LoadingViewMainParam);
            LoadingViewMain.SetColor(R.color.White);
            LoadingViewMain.Start();

            RelativeLayoutMain.addView(LoadingViewMain);

            /*Glide.with(context)
            .load(ListMain.get(Position))
            .listener(new RequestListener<String, GlideDrawable>()
            {
                @Override
                public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b)
                {
                    LoadingViewMain.Stop();
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1)
                {
                    LoadingViewMain.Stop();
                    return false;
                }
            })
            .into(TouchImageViewMain);*/

            Container.addView(RelativeLayoutMain);

            return RelativeLayoutMain;
        }

        @Override
        public void destroyItem(ViewGroup Container, int position, Object object)
        {
            Container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public int getCount()
        {
            if (ImageCache != null)
                return 1;

            return ListMain.size();
        }
    }
}
