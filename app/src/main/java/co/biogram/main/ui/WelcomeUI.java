package co.biogram.main.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import co.biogram.fragment.FragmentBase;
import co.biogram.main.R;
import co.biogram.main.activity.MainActivity;
import co.biogram.main.handler.MiscHandler;
import co.biogram.main.handler.SharedHandler;
import co.biogram.main.view.Button;
import co.biogram.main.view.TextView;

public class WelcomeUI extends FragmentBase
{
    private ScrollView ScrollViewMain;
    private GoogleApiClient googleApiClient;
    private boolean IsGoogleAvailable = false;

    @Override
    public void OnCreate()
    {
        ScrollViewMain = new ScrollView(GetActivity());
        ScrollViewMain.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));
        ScrollViewMain.setBackgroundResource(R.color.White);
        ScrollViewMain.setFillViewport(true);

        RelativeLayout RelativeLayoutMain = new RelativeLayout(GetActivity());
        RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        ScrollViewMain.addView(RelativeLayoutMain);

        LinearLayout LinearLayoutHeader = new LinearLayout(GetActivity());
        LinearLayoutHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(GetActivity(), 200)));
        LinearLayoutHeader.setBackgroundResource(R.color.BlueLight);
        LinearLayoutHeader.setOrientation(LinearLayout.VERTICAL);
        LinearLayoutHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayoutHeader.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(LinearLayoutHeader);

        LinearLayout LinearLayoutLanguage = new LinearLayout(GetActivity());
        LinearLayoutLanguage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(GetActivity(), 35)));
        LinearLayoutLanguage.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayoutLanguage.setGravity(Gravity.CENTER);

        LinearLayoutHeader.addView(LinearLayoutLanguage);

        TextView TextViewLanguage = new TextView(GetActivity(), 16, false);
        TextViewLanguage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextViewLanguage.setText(GetActivity().getString(R.string.WelcomeLanguage));
        TextViewLanguage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog DialogLanguage = new Dialog(GetActivity());
                DialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DialogLanguage.setCancelable(false);

                RelativeLayout RelativeLayoutLanguage = new RelativeLayout(GetActivity());
                RelativeLayoutLanguage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

                RelativeLayout.LayoutParams TextViewTitleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, D56);
                TextViewTitleParam.addRule(MiscHandler.Align("R"));

                TextView TextViewTitle = new TextView(GetActivity(), 16, false);
                TextViewTitle.setLayoutParams(TextViewTitleParam);
                TextViewTitle.setTextColor(ContextCompat.getColor(GetActivity(), R.color.Black));
                TextViewTitle.setText(GetActivity().getString(R.string.WelcomeLanguageSelect));
                TextViewTitle.setPadding(MiscHandler.ToDimension(GetActivity(), 10), 0, MiscHandler.ToDimension(GetActivity(), 10), 0);
                TextViewTitle.setGravity(Gravity.CENTER_VERTICAL);

                RelativeLayoutLanguage.addView(TextViewTitle);

                RelativeLayout.LayoutParams ImageViewCloseParam = new RelativeLayout.LayoutParams(D56, D56);
                ImageViewCloseParam.addRule(MiscHandler.Align("L"));

                ImageView ImageViewClose = new ImageView(GetActivity());
                ImageViewClose.setLayoutParams(ImageViewCloseParam);
                ImageViewClose.setImageResource(R.drawable.ic_close_blue);
                ImageViewClose.setPadding(MiscHandler.ToDimension(GetActivity(), 15), MiscHandler.ToDimension(GetActivity(), 15), MiscHandler.ToDimension(GetActivity(), 15), MiscHandler.ToDimension(GetActivity(), 15));
                ImageViewClose.setId(MiscHandler.GenerateViewID());
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { DialogLanguage.dismiss(); } });

                RelativeLayoutLanguage.addView(ImageViewClose);

                RelativeLayout.LayoutParams ViewLineParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(GetActivity(), 1));
                ViewLineParam.addRule(RelativeLayout.BELOW, ImageViewClose.getId());

                View ViewLine = new View(GetActivity());
                ViewLine.setLayoutParams(ViewLineParam);
                ViewLine.setId(MiscHandler.GenerateViewID());
                ViewLine.setBackgroundResource(R.color.Gray2);

                RelativeLayoutLanguage.addView(ViewLine);

                RelativeLayout.LayoutParams TextViewEnglishParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, D56);
                TextViewEnglishParam.addRule(RelativeLayout.BELOW, ViewLine.getId());
                TextViewEnglishParam.addRule(MiscHandler.Align("R"));

                TextView TextViewEnglish = new TextView(GetActivity(), 16, false);
                TextViewEnglish.setLayoutParams(TextViewEnglishParam);
                TextViewEnglish.setTextColor(ContextCompat.getColor(GetActivity(), R.color.Black));
                TextViewEnglish.setText(GetActivity().getString(R.string.WelcomeLanguageEnglish));
                TextViewEnglish.setPadding(MiscHandler.ToDimension(GetActivity(), 10), 0, MiscHandler.ToDimension(GetActivity(), 10), 0);
                TextViewEnglish.setId(MiscHandler.GenerateViewID());
                TextViewEnglish.setGravity(MiscHandler.Gravity("L") | Gravity.CENTER_VERTICAL);
                TextViewEnglish.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { DialogLanguage.dismiss(); MiscHandler.ChangeLanguage(GetActivity(), "en"); } });

                RelativeLayoutLanguage.addView(TextViewEnglish);

                RelativeLayout.LayoutParams ViewLine2Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(GetActivity(), 1));
                ViewLine2Param.addRule(RelativeLayout.BELOW, TextViewEnglish.getId());

                View ViewLine2 = new View(GetActivity());
                ViewLine2.setLayoutParams(ViewLine2Param);
                ViewLine2.setId(MiscHandler.GenerateViewID());
                ViewLine2.setBackgroundResource(R.color.Gray);

                RelativeLayoutLanguage.addView(ViewLine2);

                RelativeLayout.LayoutParams TextViewPersianParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, D56);
                TextViewPersianParam.addRule(RelativeLayout.BELOW, ViewLine2.getId());
                TextViewPersianParam.addRule(MiscHandler.Align("R"));

                TextView TextViewPersian = new TextView(GetActivity(), 16, false);
                TextViewPersian.setLayoutParams(TextViewPersianParam);
                TextViewPersian.setTextColor(ContextCompat.getColor(GetActivity(), R.color.Black));
                TextViewPersian.setText(GetActivity().getString(R.string.WelcomeLanguagePersian));
                TextViewPersian.setPadding(MiscHandler.ToDimension(GetActivity(), 10), 0, MiscHandler.ToDimension(GetActivity(), 10), 0);
                TextViewPersian.setGravity(MiscHandler.Gravity("R") | Gravity.CENTER_VERTICAL);
                TextViewPersian.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { DialogLanguage.dismiss(); MiscHandler.ChangeLanguage(GetActivity(), "fa"); } });

                RelativeLayoutLanguage.addView(TextViewPersian);

                DialogLanguage.setContentView(RelativeLayoutLanguage);
                DialogLanguage.show();
            }
        });

        ImageView ImageViewLanguage = new ImageView(GetActivity());
        ImageViewLanguage.setLayoutParams(new RelativeLayout.LayoutParams(MiscHandler.ToDimension(GetActivity(), 30), MiscHandler.ToDimension(GetActivity(), 30)));
        ImageViewLanguage.setImageResource(R.drawable.ic_option_white);
        ImageViewLanguage.setPadding(MiscHandler.ToDimension(GetActivity(), 7), MiscHandler.ToDimension(GetActivity(), 7), MiscHandler.ToDimension(GetActivity(), 7), MiscHandler.ToDimension(GetActivity(), 7));

        if (MiscHandler.IsRTL())
        {
            LinearLayoutLanguage.addView(ImageViewLanguage);
            LinearLayoutLanguage.addView(TextViewLanguage);
        }
        else
        {
            LinearLayoutLanguage.addView(TextViewLanguage);
            LinearLayoutLanguage.addView(ImageViewLanguage);
        }

        LinearLayout.LayoutParams ImageViewHeaderParam = new LinearLayout.LayoutParams(MiscHandler.ToDimension(GetActivity(), 150), MiscHandler.ToDimension(GetActivity(), 65));
        ImageViewHeaderParam.setMargins(0, MiscHandler.ToDimension(GetActivity(), 15), 0, 0);

        ImageView ImageViewHeader = new ImageView(GetActivity());
        ImageViewHeader.setLayoutParams(ImageViewHeaderParam);
        ImageViewHeader.setImageResource(MiscHandler.IsFa() ? R.drawable.ic_logo_fa : R.drawable.ic_logo);

        LinearLayoutHeader.addView(ImageViewHeader);

        LinearLayout.LayoutParams TextViewHeaderParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextViewHeaderParam.setMargins(0, MiscHandler.ToDimension(GetActivity(), 10), 0, 0);

        TextView TextViewHeader = new TextView(GetActivity(), 14, false);
        TextViewHeader.setLayoutParams(TextViewHeaderParam);
        TextViewHeader.setText(GetActivity().getString(R.string.WelcomeHeader));
        TextViewHeader.setTextColor(ContextCompat.getColor(GetActivity(), R.color.White));

        LinearLayoutHeader.addView(TextViewHeader);

        TextView TextViewHeader2 = new TextView(GetActivity(), 14, false);
        TextViewHeader2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextViewHeader2.setText(GetActivity().getString(R.string.WelcomeHeader2));
        TextViewHeader2.setTextColor(ContextCompat.getColor(GetActivity(), R.color.White));

        LinearLayoutHeader.addView(TextViewHeader2);

        RelativeLayout.LayoutParams RelativeLayoutSignUpParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(GetActivity(), 270), MiscHandler.ToDimension(GetActivity(), 45));
        RelativeLayoutSignUpParam.setMargins(0, MiscHandler.ToDimension(GetActivity(), 30), 0, 0);
        RelativeLayoutSignUpParam.addRule(RelativeLayout.BELOW, LinearLayoutHeader.getId());
        RelativeLayoutSignUpParam.addRule(RelativeLayout.CENTER_HORIZONTAL);

        GradientDrawable DrawableSignUp = new GradientDrawable();
        DrawableSignUp.setColor(ContextCompat.getColor(GetActivity(), R.color.BlueLight));
        DrawableSignUp.setCornerRadius(MiscHandler.ToDimension(GetActivity(), 7));

        Button ButtonSignUp = new Button(GetActivity(), 18, false);
        ButtonSignUp.setLayoutParams(RelativeLayoutSignUpParam);
        ButtonSignUp.setText(GetActivity().getString(R.string.WelcomeSignUp));
        ButtonSignUp.setId(MiscHandler.GenerateViewID());
        ButtonSignUp.setBackground(DrawableSignUp);
        ButtonSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TranslateAnimation Anim = MiscHandler.IsRTL() ? new TranslateAnimation(0f, -1000f, 0f, 0f) : new TranslateAnimation(0f, 1000f, 0f, 0f);
                Anim.setDuration(200);

                ScrollViewMain.setAnimation(Anim);

                GetActivity().GetManager().OpenView(new SignUpPhoneUI(true), R.id.WelcomeActivityContainer, "SignUpPhoneUI");
            }
        });

        if (MiscHandler.IsFa())
            ButtonSignUp.setPadding(0, -MiscHandler.ToDimension(GetActivity(), 1), 0, 0);

        RelativeLayoutMain.addView(ButtonSignUp);

        RelativeLayout.LayoutParams RelativeLayoutORParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(GetActivity(), 45));
        RelativeLayoutORParam.setMargins(0, MiscHandler.ToDimension(GetActivity(), 20), 0, MiscHandler.ToDimension(GetActivity(), 5));
        RelativeLayoutORParam.addRule(RelativeLayout.BELOW, ButtonSignUp.getId());

        RelativeLayout RelativeLayoutOR = new RelativeLayout(GetActivity());
        RelativeLayoutOR.setLayoutParams(RelativeLayoutORParam);
        RelativeLayoutOR.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(RelativeLayoutOR);

        RelativeLayout.LayoutParams TextViewORParam = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(GetActivity(), 40), RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewORParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        TextView TextViewOR = new TextView(GetActivity(), 14, true);
        TextViewOR.setLayoutParams(TextViewORParam);
        TextViewOR.setText(GetActivity().getString(R.string.WelcomeOR));
        TextViewOR.setId(MiscHandler.GenerateViewID());
        TextViewOR.setGravity(Gravity.CENTER);
        TextViewOR.setTextColor(ContextCompat.getColor(GetActivity(), R.color.BlueGray));

        RelativeLayoutOR.addView(TextViewOR);

        RelativeLayout.LayoutParams ViewOrLine1Param = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(GetActivity(), 115), MiscHandler.ToDimension(GetActivity(), 1));
        ViewOrLine1Param.addRule(RelativeLayout.RIGHT_OF, TextViewOR.getId());
        ViewOrLine1Param.addRule(RelativeLayout.CENTER_VERTICAL);

        View ViewOrLine1 = new View(GetActivity());
        ViewOrLine1.setLayoutParams(ViewOrLine1Param);
        ViewOrLine1.setBackgroundResource(R.color.BlueGray);

        RelativeLayoutOR.addView(ViewOrLine1);

        RelativeLayout.LayoutParams ViewOrLine2Param = new RelativeLayout.LayoutParams(MiscHandler.ToDimension(GetActivity(), 115), MiscHandler.ToDimension(GetActivity(), 1));
        ViewOrLine2Param.addRule(RelativeLayout.LEFT_OF, TextViewOR.getId());
        ViewOrLine2Param.addRule(RelativeLayout.CENTER_VERTICAL);

        View ViewOrLine2 = new View(GetActivity());
        ViewOrLine2.setLayoutParams(ViewOrLine2Param);
        ViewOrLine2.setBackgroundResource(R.color.BlueGray);

        RelativeLayoutOR.addView(ViewOrLine2);

        RelativeLayout.LayoutParams LinearLayoutGoogleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        LinearLayoutGoogleParam.addRule(RelativeLayout.BELOW, RelativeLayoutOR.getId());
        LinearLayoutGoogleParam.addRule(RelativeLayout.CENTER_HORIZONTAL);

        LinearLayout LinearLayoutGoogle = new LinearLayout(GetActivity());
        LinearLayoutGoogle.setLayoutParams(LinearLayoutGoogleParam);
        LinearLayoutGoogle.setGravity(Gravity.CENTER);
        LinearLayoutGoogle.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayoutGoogle.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { GetActivity().startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), 100); } });

        RelativeLayoutMain.addView(LinearLayoutGoogle);

        LinearLayout.LayoutParams ImageViewGoogleParam = new LinearLayout.LayoutParams(MiscHandler.ToDimension(GetActivity(), 30), MiscHandler.ToDimension(GetActivity(), 30));
        ImageViewGoogleParam.setMargins(MiscHandler.ToDimension(GetActivity(), 5), 0, MiscHandler.ToDimension(GetActivity(), 5), 0);

        ImageView ImageViewGoogle = new ImageView(GetActivity());
        ImageViewGoogle.setLayoutParams(ImageViewGoogleParam);
        ImageViewGoogle.setBackgroundResource(R.drawable.ic_google);

        LinearLayoutGoogle.addView(ImageViewGoogle);

        TextView TextViewGoogle = new TextView(GetActivity(), 16, true);
        TextViewGoogle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextViewGoogle.setTextColor(ContextCompat.getColor(GetActivity(), R.color.BlueLight));
        TextViewGoogle.setText(GetActivity().getString(R.string.WelcomeSignInGoogle));

        LinearLayoutGoogle.addView(TextViewGoogle);

        RelativeLayout.LayoutParams RelativeLayoutSignInParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, D56);
        RelativeLayoutSignInParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout RelativeLayoutSignIn = new RelativeLayout(GetActivity());
        RelativeLayoutSignIn.setLayoutParams(RelativeLayoutSignInParam);
        RelativeLayoutSignIn.setBackgroundResource(R.color.White5);
        RelativeLayoutSignIn.setGravity(Gravity.CENTER);
        RelativeLayoutSignIn.setId(MiscHandler.GenerateViewID());
        RelativeLayoutSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TranslateAnimation Anim = MiscHandler.IsRTL() ? new TranslateAnimation(0f, -1000f, 0f, 0f) : new TranslateAnimation(0f, 1000f, 0f, 0f);
                Anim.setDuration(200);

                ScrollViewMain.setAnimation(Anim);

                GetActivity().GetManager().OpenView(new SignUpPhoneUI(false), R.id.WelcomeActivityContainer, "SignUpPhoneUI");
            }
        });

        RelativeLayoutMain.addView(RelativeLayoutSignIn);

        TextView TextViewSignIn = new TextView(GetActivity(), 16, false);
        TextViewSignIn.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        TextViewSignIn.setTextColor(ContextCompat.getColor(GetActivity(), R.color.Gray4));
        TextViewSignIn.setText(GetActivity().getString(R.string.WelcomeSignIn));
        TextViewSignIn.setId(MiscHandler.GenerateViewID());

        RelativeLayoutSignIn.addView(TextViewSignIn);

        RelativeLayout.LayoutParams TextViewSignIn2Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewSignIn2Param.setMargins(MiscHandler.ToDimension(GetActivity(), 5), 0, MiscHandler.ToDimension(GetActivity(), 5), 0);
        TextViewSignIn2Param.addRule(MiscHandler.AlignTo("R"), TextViewSignIn.getId());

        TextView TextViewSignIn2 = new TextView(GetActivity(), 16, false);
        TextViewSignIn2.setLayoutParams(TextViewSignIn2Param);
        TextViewSignIn2.setTextColor(ContextCompat.getColor(GetActivity(), R.color.BlueLight));
        TextViewSignIn2.setText(GetActivity().getString(R.string.WelcomeSignIn2));

        RelativeLayoutSignIn.addView(TextViewSignIn2);

        RelativeLayout.LayoutParams ViewLineParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, MiscHandler.ToDimension(GetActivity(), 1));
        ViewLineParam.addRule(RelativeLayout.ABOVE, RelativeLayoutSignIn.getId());

        View ViewLine = new View(GetActivity());
        ViewLine.setLayoutParams(ViewLineParam);
        ViewLine.setBackgroundResource(R.color.Gray2);
        ViewLine.setId(MiscHandler.GenerateViewID());

        RelativeLayoutMain.addView(ViewLine);

        RelativeLayout.LayoutParams TextViewTerm2Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTerm2Param.addRule(RelativeLayout.ABOVE, ViewLine.getId());
        TextViewTerm2Param.addRule(RelativeLayout.CENTER_HORIZONTAL);
        TextViewTerm2Param.setMargins(0, 0, 0, MiscHandler.ToDimension(GetActivity(), 20));

        TextView TextViewTerm2 = new TextView(GetActivity(), 16, true);
        TextViewTerm2.setLayoutParams(TextViewTerm2Param);
        TextViewTerm2.setTextColor(ContextCompat.getColor(GetActivity(), R.color.BlueGray));
        TextViewTerm2.setText(GetActivity().getString(R.string.WelcomeTerm2));
        TextViewTerm2.setId(MiscHandler.GenerateViewID());
        TextViewTerm2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { GetActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://biogram.co"))); } });

        RelativeLayoutMain.addView(TextViewTerm2);

        RelativeLayout.LayoutParams TextViewTermParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextViewTermParam.addRule(RelativeLayout.ABOVE, TextViewTerm2.getId());
        TextViewTermParam.addRule(RelativeLayout.CENTER_HORIZONTAL);

        TextView TextViewTerm = new TextView(GetActivity(), 16, false);
        TextViewTerm.setLayoutParams(TextViewTermParam);
        TextViewTerm.setTextColor(ContextCompat.getColor(GetActivity(), R.color.BlueGray));
        TextViewTerm.setText(GetActivity().getString(R.string.WelcomeTerm));

        RelativeLayoutMain.addView(TextViewTerm);

        ViewMain = ScrollViewMain;

        IsGoogleAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(GetActivity()) == ConnectionResult.SUCCESS;

        if (IsGoogleAvailable)
        {
            googleApiClient = new GoogleApiClient.Builder(GetActivity().getApplicationContext())
            .addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("590625045379-pnhlgdqpr5i8ma705ej7akcggsr08vdf.apps.googleusercontent.com")
            .build())
            .build();
        }
    }

    @Override
    public void OnResume()
    {
        if (IsGoogleAvailable)
            googleApiClient.connect();
    }

    @Override
    public void OnPause()
    {
        AndroidNetworking.forceCancel("WelcomeUI");

        if (IsGoogleAvailable)
        {
            if (googleApiClient.isConnected())
                Auth.GoogleSignInApi.signOut(googleApiClient);

            googleApiClient.disconnect();
        }
    }

    @Override
    public void OnActivityResult(int RequestCode, int ResultCode, Intent intent)
    {
        if (RequestCode == 100)
        {
            GoogleSignInResult Result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);

            if (Result.isSuccess())
            {
                final GoogleSignInAccount Result2 = Result.getSignInAccount();

                if (Result2 != null)
                {
                    AndroidNetworking.post(MiscHandler.GetRandomServer("SignInGoogle"))
                    .addBodyParameter("Token", Result2.getIdToken())
                    .addBodyParameter("Session", MiscHandler.GenerateSession())
                    .setTag("WelcomeUI")
                    .build()
                    .getAsString(new StringRequestListener()
                    {
                        @Override
                        public void onResponse(String Response)
                        {MiscHandler.Debug("A: " + Response);
                            try
                            {
                                JSONObject Result3 = new JSONObject(Response);

                                switch (Result3.getInt("Message"))
                                {
                                    case -2:
                                        MiscHandler.Toast(GetActivity(), GetActivity().getString(R.string.GeneralError2));
                                        break;
                                    case -1:
                                        MiscHandler.Toast(GetActivity(), GetActivity().getString(R.string.GeneralError1));
                                        break;
                                    case 0:
                                        if (Result3.getBoolean("Registered"))
                                        {
                                            SharedHandler.SetBoolean(GetActivity(), "IsLogin", true);
                                            SharedHandler.SetBoolean(GetActivity(), "IsGoogle", true);
                                            SharedHandler.SetString(GetActivity(), "Token", Result3.getString("Token"));
                                            SharedHandler.SetString(GetActivity(), "ID", Result3.getString("ID"));
                                            SharedHandler.SetString(GetActivity(), "Username", Result3.getString("Username"));
                                            SharedHandler.SetString(GetActivity(), "Avatar", Result3.getString("Avatar"));

                                            GetActivity().startActivity(new Intent(GetActivity(), MainActivity.class));
                                            GetActivity().finish();
                                            return;
                                        }

                                        TranslateAnimation Anim = MiscHandler.IsRTL() ? new TranslateAnimation(0f, -1000f, 0f, 0f) : new TranslateAnimation(0f, 1000f, 0f, 0f);
                                        Anim.setDuration(200);

                                        ScrollViewMain.setAnimation(Anim);

                                        GetActivity().GetManager().OpenView(new SignUpUsernameUI(Result2.getIdToken(), 0), R.id.WelcomeActivityContainer, "SignUpUsernameUI");
                                        break;
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                        MiscHandler.Toast(GetActivity(), GetActivity().getString(R.string.WelcomeGoogleError));
                                        break;
                                }
                            }
                            catch (Exception e)
                            {
                                MiscHandler.Debug("WelcomeUI-SignInGoogle: " + e.toString());
                            }
                        }

                        @Override
                        public void onError(ANError e)
                        {
                            MiscHandler.Toast(GetActivity(), GetActivity().getString(R.string.GeneralNoInternet));
                        }
                    });
                }
            }
        }
    }
}
