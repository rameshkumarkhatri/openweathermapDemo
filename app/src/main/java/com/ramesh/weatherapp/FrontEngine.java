package com.ramesh.weatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Ramesh Kumar on 8/31/17.
 */

public class FrontEngine extends Application{

    public static User user=null;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static double LATITUDE,LONGITUDE;


    /* alert Dialogue for validation */
    public static void AlertDialogueGPS(String content, final Context context, String title) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(content)
                .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context
                                .startActivity(intent);
                    }
                }).show();

    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {

            // getting application package name, as defined in manifest
            String packageName = context.getApplicationContext()
                    .getPackageName();

            // Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext()
                    .getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    /* alert Dialogue for validation */
    public static void AlertDialogue(String content, Context context, String title) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(content)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                }).show();

    }


}
