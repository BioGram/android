package co.biogram.main.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import co.biogram.main.R;
import co.biogram.main.activity.ActivityMain;
import co.biogram.main.handler.MiscHandler;
import co.biogram.main.handler.SharedHandler;
import co.biogram.main.handler.URLHandler;

public class NotificationService extends Service
{
    private Runnable runnable;
    private Handler handler;

    @Override
    public void onCreate()
    {
        MiscHandler.Debug("NotificationService Started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        final Context context = NotificationService.this;

        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                AndroidNetworking.post(URLHandler.GetURL("NotificationService"))
                .addHeaders("TOKEN", SharedHandler.GetString(context, "TOKEN"))
                .build()
                .getAsString(new StringRequestListener()
                {
                    @Override
                    public void onResponse(String Response)
                    {MiscHandler.Debug(Response);
                        try
                        {
                            JSONObject Result = new JSONObject(Response);

                            if (Result.getInt("Message") == 1000 && !Result.getString("Result").equals(""))
                            {
                                JSONArray ResultList = new JSONArray(Result.getString("Result"));

                                for (int I = 0; I < ResultList.length(); I++)
                                {
                                    JSONObject Notification = ResultList.getJSONObject(I);

                                    String Message = Notification.getString("Username") + " ";

                                    switch (Notification.getInt("Type"))
                                    {
                                        case 1: Message += context.getString(R.string.NotificationFragmentPostTag);     break;
                                        case 2: Message += context.getString(R.string.NotificationFragmentPostLike);    break;
                                        case 3: Message += context.getString(R.string.NotificationFragmentFollow);      break;
                                        case 4: Message += context.getString(R.string.NotificationFragmentCommentLike); break;
                                        case 5: Message += context.getString(R.string.NotificationFragmentComment);     break;
                                        case 6: Message += context.getString(R.string.NotificationFragmentCommentTag);  break;
                                    }

                                    CreateNotification(context, Message);
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            MiscHandler.Debug("Notifications - L75 - " + e.toString());
                        }

                        handler.postDelayed(runnable, 5000);
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        handler.postDelayed(runnable, 5000);
                    }
                });
            }
        };

        handler.postDelayed(runnable, 5000);

        return START_STICKY;
    }

    private void CreateNotification(Context context, String Message)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_back_white)
        .setContentTitle("BioGram")
        .setContentText(Message);

        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, ActivityMain.class), PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pi);
        builder.setAutoCancel(true);

        NotificationManager Manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Manager.notify(MiscHandler.GenerateViewID(), builder.build());
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onDestroy()
    {
        MiscHandler.Debug("NotificationService Destroyed");
    }
}