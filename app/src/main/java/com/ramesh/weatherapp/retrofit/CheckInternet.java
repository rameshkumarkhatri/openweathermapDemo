package com.ramesh.weatherapp.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */

public class CheckInternet {

	static ConnectivityManager connectivity;
	static NetworkInfo activeNetwork;
	static CheckInternet checkInternet;
	static boolean check = false;

	public static CheckInternet getInstance(){

		if(checkInternet == null){
			checkInternet = new CheckInternet();
		}
		return checkInternet;
	}
	
	
	public static boolean checkInternet(Context context){

		connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		activeNetwork = connectivity.getActiveNetworkInfo();


		if (activeNetwork != null) {

			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
				check = true;
			}else{
				if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
//					Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
					check = true;
				}
			}


			return check;
		}else{
			return false;
		}


		
	}

AsyncTask<Boolean, Boolean, Boolean> task;
	public AsyncTask internetCheckTask(final Context context, final ConnectionCallBackInternet callBackInternet)
	{
		task = new AsyncTask<Boolean, Boolean, Boolean>(){
			boolean isConnected =false;
			@Override
			protected Boolean doInBackground(Boolean[] params) {
				isConnected = isConnectingToInternet(context);
				return null;
			}

			@Override
			protected void onPostExecute(Boolean o) {
				super.onPostExecute(o);
				callBackInternet.intenetConnecte(isConnected);
			}
		};

		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

		return task;
	}
	public static boolean isConnectingToInternet(Context context) {
		boolean connected = false;
		ConnectivityManager CManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NInfo = CManager.getActiveNetworkInfo();
		if (NInfo != null && NInfo.isConnectedOrConnecting()) {
			try {
				if (InetAddress.getByName("digital.cygnismedia.com").isReachable(7000)) {
					// host reachable
					connected = true;
				} else {
					connected = true;
//                    connected = false;
					// host not reachable
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return connected;
	}
	public interface ConnectionCallBackInternet {
		void intenetConnecte(boolean status);
	}
}
