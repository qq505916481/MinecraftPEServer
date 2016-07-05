package net.fengberd.minecraftpe_server;

import android.os.*;
import android.app.*;
import android.content.*;

public class ServerService extends Service
{
	private boolean isRunning = false;

	@Override
	public int onStartCommand(Intent intent,int flags,int startId)
	{
		run();
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy()
	{
		stop();
	}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	private void run()
	{
		if(!isRunning)
		{
			isRunning=true;
			Context context = getApplicationContext();
			Intent i = new Intent(context,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
			Notification note = new Notification.Builder(context)
				.setContentIntent(pi)
				.setContentTitle((MainActivity.nukkitMode?"Nukkit":"PocketMine")+" is running")
				.setContentText(MainActivity.instance.getString(R.string.message_tap_open))
				.setSmallIcon(R.drawable.ic_launcher)
				.setWhen(System.currentTimeMillis())
				.setOngoing(true)
				.build();
			
			note.flags|=Notification.FLAG_NO_CLEAR;
			startForeground(1337,note);
		}
	}

	private void stop()
	{
		if(isRunning)
		{
			isRunning=false;
			stopForeground(true);
		}
	}
}

