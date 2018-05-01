package co.biogram.main.ui.social;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import co.biogram.main.R;
import co.biogram.main.fragment.FragmentView;
import co.biogram.main.handler.Misc;
import co.biogram.main.ui.general.CameraViewUI;
import co.biogram.main.ui.general.CropViewUI;
import co.biogram.main.ui.general.GalleryViewUI;
import co.biogram.main.ui.view.PermissionDialog;
import co.biogram.main.ui.view.StatefulLayout;

public class Profile_UI extends FragmentView
{
    @Override
    public void OnCreate()
    {
        View view = View.inflate(Activity, R.layout.social_profile, null);

        view.findViewById(R.id.ImageViewSetting).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Open Setting Page
            }
        });

        view.findViewById(R.id.ImageViewProfile).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Open Profile Page
            }
        });

        final CircleImageView CircleImageViewProfile = view.findViewById(R.id.CircleImageViewProfile);
        CircleImageViewProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_avatar, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                TextView TextViewCamera = dialogView.findViewById(R.id.TextViewCamera);
                TextViewCamera.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        PermissionDialog permissionDialog = new PermissionDialog(Activity);
                        permissionDialog.SetContentView(R.drawable.___general_permission_camera, R.string.SocialProfileUIAvatarCameraMessage, Manifest.permission.CAMERA, new PermissionDialog.OnChoiceListener()
                        {
                            @Override
                            public void OnChoice(boolean Result)
                            {
                                if (!Result)
                                {
                                    Misc.ToastOld(R.string.SocialProfileUIAvatarCameraMessage);
                                    return;
                                }

                                Activity.GetManager().OpenView(new CameraViewUI(Misc.ToDP(250), Misc.ToDP(250), true, new CameraViewUI.OnCaptureListener()
                                {
                                    @Override
                                    public void OnCapture(Bitmap bitmap)
                                    {
                                        // TODO Update Profile
                                    }
                                }), "CameraViewUI", true);
                            }
                        });
                    }
                });

                TextView TextViewGallery = dialogView.findViewById(R.id.TextViewGallery);
                TextViewGallery.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        PermissionDialog permissionDialog = new PermissionDialog(Activity);
                        permissionDialog.SetContentView(R.drawable.___general_permission_storage, R.string.SocialProfileUIAvatarGalleryMessage, Manifest.permission.READ_EXTERNAL_STORAGE, new PermissionDialog.OnChoiceListener()
                        {
                            @Override
                            public void OnChoice(boolean Result)
                            {
                                if (!Result)
                                {
                                    Misc.ToastOld(R.string.SocialProfileUIAvatarGalleryMessage);
                                    return;
                                }

                                Activity.GetManager().OpenView(new GalleryViewUI(1, GalleryViewUI.TYPE_IMAGE, new GalleryViewUI.GalleryListener()
                                {
                                    private String Path = "";

                                    @Override
                                    public void OnSelection(String path)
                                    {
                                        Path = path;
                                    }

                                    @Override
                                    public void OnRemove(String path)
                                    {
                                        Path = path;
                                    }

                                    @Override
                                    public void OnSave()
                                    {
                                        Activity.GetManager().OpenView(new CropViewUI(Path, true, new CropViewUI.OnCropListener()
                                        {
                                            @Override
                                            public void OnCrop(Bitmap bitmap)
                                            {
                                                Activity.onBackPressed();
                                                // TODO Send avaar
                                            }
                                        }), "CropViewUI", true);
                                    }
                                }), "CameraViewUI", true);
                            }
                        });
                    }
                });

                TextView TextViewDelete = dialogView.findViewById(R.id.TextViewDelete);
                TextViewDelete.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                        CircleImageViewProfile.setImageResource(R.drawable.z_social_profile_avatar);

                        // TODO Request Delete Profile Image
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final TextView TextViewName = view.findViewById(R.id.TextViewName);
        TextViewName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_name, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                EditText EditTextName = dialogView.findViewById(R.id.EditTextName);
                EditTextName.setText(TextViewName.getText());
                EditTextName.setSelection(TextViewName.length());

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                TextView TextViewSubmit = dialogView.findViewById(R.id.TextViewSubmit);
                TextViewSubmit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        // TODO Update Name
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final TextView TextViewUsername = view.findViewById(R.id.TextViewUsername);
        TextViewUsername.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_username, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                final EditText EditTextUsername = dialogView.findViewById(R.id.EditTextUsername);
                EditTextUsername.setText(TextViewUsername.getText());
                EditTextUsername.setSelection(TextViewUsername.length());

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                TextView TextViewSubmit = dialogView.findViewById(R.id.TextViewSubmit);
                TextViewSubmit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        // TODO Update Username
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        view.findViewById(R.id.LinearLayoutBookmark).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Open Saved Page
            }
        });

        final TextView TextViewFollowing = view.findViewById(R.id.TextViewFollowing);
        TextViewFollowing.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Open Following Page
            }
        });

        final TextView TextViewFollower = view.findViewById(R.id.TextViewFollower);
        TextViewFollower.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Open Follower Page
            }
        });

        final TextView TextViewProfileView = view.findViewById(R.id.TextViewProfileView);
        TextViewProfileView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Open ProfileView Page
            }
        });

        final TextView TextViewRating = view.findViewById(R.id.TextViewRating);
        final TextView TextViewRate = view.findViewById(R.id.TextViewRate);

        view.findViewById(R.id.LinearLayoutRating).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Open Rating Page
            }
        });

        view.findViewById(R.id.LinearLayoutSpecial).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity.GetManager().OpenView(new Profile_SpecialCenterUI(), "Profile_SpecialCenterUI", true);
            }
        });

        final TextView TextViewPhoneNumber = view.findViewById(R.id.TextViewPhoneNumber);

        view.findViewById(R.id.LinearLayoutPhoneNumber).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_phone, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                final EditText EditTextPhone = dialogView.findViewById(R.id.EditTextPhone);
                EditTextPhone.setText(TextViewPhoneNumber.getText());
                EditTextPhone.setSelection(TextViewPhoneNumber.length());

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                TextView TextViewSubmit = dialogView.findViewById(R.id.TextViewSubmit);
                TextViewSubmit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        // TODO Update Phone
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final TextView TextViewEmailAddress = view.findViewById(R.id.TextViewEmailAddress);

        view.findViewById(R.id.LinearLayoutEmail).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_email, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                final EditText EditTextEmail = dialogView.findViewById(R.id.EditTextEmail);
                EditTextEmail.setText(TextViewEmailAddress.getText());
                EditTextEmail.setSelection(TextViewEmailAddress.length());

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                TextView TextViewSubmit = dialogView.findViewById(R.id.TextViewSubmit);
                TextViewSubmit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        // TODO Update Email
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final TextView TextViewAboutMe = view.findViewById(R.id.TextViewAboutMe);

        view.findViewById(R.id.LinearLayoutAboutMe).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_about, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                final EditText EditTextAbout = dialogView.findViewById(R.id.EditTextAbout);
                EditTextAbout.setText(TextViewAboutMe.getText());
                EditTextAbout.setSelection(TextViewAboutMe.length());

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                TextView TextViewSubmit = dialogView.findViewById(R.id.TextViewSubmit);
                TextViewSubmit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        // TODO Update AboutMe
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final TextView TextViewWebsite = view.findViewById(R.id.TextViewWebsite);

        view.findViewById(R.id.LinearLayoutWebsite).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_website, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                final EditText EditTextWebsite = dialogView.findViewById(R.id.EditTextWebsite);
                EditTextWebsite.setText(TextViewWebsite.getText());
                EditTextWebsite.setSelection(TextViewWebsite.length());

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                TextView TextViewSubmit = dialogView.findViewById(R.id.TextViewSubmit);
                TextViewSubmit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        // TODO Update Website
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final TextView TextViewLocation = view.findViewById(R.id.TextViewLocation);

        view.findViewById(R.id.LinearLayoutLocation).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_location, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                final ListView ListViewSearch = dialogView.findViewById(R.id.ListViewSearch);
                final EditText EditTextLocation = dialogView.findViewById(R.id.EditTextLocation);

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                ImageView ImageViewSearch = dialogView.findViewById(R.id.ImageViewSearch);
                ImageViewSearch.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String[] values = new String[] { "Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android", "iPhone", "WindowsMobile" };


                        ListViewSearch.setAdapter(new ArrayAdapter<>(Activity, android.R.layout.simple_list_item_1, values));
                    }
                });

                ListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                    {

                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final TextView TextViewFeature1 = view.findViewById(R.id.TextViewTag1);
        final TextView TextViewFeature2 = view.findViewById(R.id.TextViewTag2);
        final TextView TextViewFeature3 = view.findViewById(R.id.TextViewTag3);
        final TextView TextViewFeature4 = view.findViewById(R.id.TextViewTag4);
        final TextView TextViewFeature5 = view.findViewById(R.id.TextViewTag5);
        final TextView TextViewFeature6 = view.findViewById(R.id.TextViewTag6);
        final TextView TextViewFeature7 = view.findViewById(R.id.TextViewTag7);

        view.findViewById(R.id.LinearLayoutFeature).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(Activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);

                View dialogView = View.inflate(Activity, R.layout.social_profile_dialog_feature, null);

                ImageView ImageViewClose = dialogView.findViewById(R.id.ImageViewClose);
                ImageViewClose.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dialog.dismiss(); } });

                if (dialog.getWindow() != null)
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                TextView TextViewSubmit = dialogView.findViewById(R.id.TextViewSubmit);
                TextViewSubmit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();

                        // TODO Update AboutMe
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });

        final StatefulLayout StatefulLayoutMain = view.findViewById(R.id.StatefulLayoutMain);

        switch (new Random().nextInt(3))
        {
            case 0:
                StatefulLayoutMain.Connection(Misc.ToDP(120));
                break;
            case 1:
                StatefulLayoutMain.Loading();
                break;
            case 2:
                StatefulLayoutMain.Content(Misc.ToDP(120));
                break;
        }

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                StatefulLayoutMain.Hide();
            }
        }, 5000);

        // TODO Implement Cache
        // TODO Update From Server

        TextViewUsername.setText("@alikhazaee");
        TextViewName.setText("Ali Khazaee");
        TextViewFollowing.setText("12.6K");
        TextViewFollower.setText("1892");
        TextViewProfileView.setText("102K");
        TextViewRating.setText("4.6");
        TextViewRate.setText("1023");
        TextViewPhoneNumber.setText("09385454764");
        TextViewEmailAddress.setText("dev.khazaee@gmail.com");
        TextViewAboutMe.setText("I am e Sosis");
        TextViewWebsite.setText("https://google.com");
        TextViewLocation.setText("Karaj - MehrShahr");
        TextViewFeature1.setText("Developer");
        TextViewFeature2.setText("PHP");
        TextViewFeature3.setText("Node JS");
        TextViewFeature4.setText("Sexy Lady");
        TextViewFeature5.setText("Hava Garm e Haa");
        TextViewFeature6.setText("Tanbe 1000");
        TextViewFeature7.setText("9103");

        ViewMain = view;
    }
}