package co.biogram.main.activity;

import android.content.Intent;
import android.os.Bundle;

import co.biogram.main.fragment.FragmentActivity;

public class WelcomeActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, MessengerActivity.class));

        finish();

        /*if (Misc.GetBoolean("IsLogin"))
        {
            if (Misc.GetString("Activity").equals("Chat"))
                startActivity(new Intent(this, Chat_List_UI.class));
            else
                startActivity(new Intent(this, SocialActivity.class));

            finish();
            return;
        }

        if (Build.VERSION.SDK_INT > 20)
            getWindow().setStatusBarColor(Misc.Color(R.color.Primary));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        RelativeLayout RelativeLayoutMain = new RelativeLayout(this);
        RelativeLayoutMain.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        RelativeLayoutMain.setId(R.id.ContainerFull);

        setContentView(RelativeLayoutMain);

        GetManager().OpenView(new WelcomeUI(), R.id.ContainerFull, "WelcomeUI");*/
    }
}
