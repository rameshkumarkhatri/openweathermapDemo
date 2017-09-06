package com.ramesh.weatherapp.retrofit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */

public class InternetConnectReceiver extends BroadcastReceiver {
    public static boolean isConnected = true;

    @Override
    public void onReceive(final Context context, Intent intent) {
        new AsyncTask<Object, Object, Object>() {


            @Override
            protected Object doInBackground(Object[] params) {
                CheckInternet.getInstance().internetCheckTask(context, new CheckInternet.ConnectionCallBackInternet() {
                    @Override
                    public void intenetConnecte(boolean status) {

                        if (status) {
                            isConnected = true;
                        } else {
                            isConnected = false;
                        }

                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (isConnected) {

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
