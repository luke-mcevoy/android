package edu.stevens.cs522.chat.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.preference.PreferenceManager;

import java.util.UUID;

import edu.stevens.cs522.chat.R;

/**
 * Created by dduggan.
 */

public class Settings {

    public static final String SETTINGS = "settings";

    public static final boolean SYNC = false;

    private static String APPID_KEY = "app-id";

    private static String SENDER_ID_KEY = "sender-id";

    private static final String CHAT_SERVER_KEY = "server_uri";

    private static final String CHAT_NAME_KEY = "user-name";

    private static SharedPreferences getPreferences(Context context) {
        // return context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static UUID getAppId(Context context) {
        SharedPreferences prefs = getPreferences(context);
        String appID = prefs.getString(APPID_KEY, null);
        if (appID == null) {
            appID = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(APPID_KEY, appID);
            String chatName = prefs.getString(CHAT_NAME_KEY, null);
            if (chatName == null) {
                editor.putString(CHAT_NAME_KEY, context.getString(R.string.user_name_default));
            }
            editor.commit();
        }
        return UUID.fromString(appID);
    }

    public static long getSenderId(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getLong(SENDER_ID_KEY, -1);
    }

    public static void saveSenderId(Context context, long senderId) {
        SharedPreferences.Editor editor =  getPreferences(context).edit();
        editor.putLong(SENDER_ID_KEY, senderId);
        editor.commit();
    }

    public static String getChatName(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(CHAT_NAME_KEY, null);
    }

    public static void saveChatName(Context context, String chatName) {
        SharedPreferences.Editor editor =  getPreferences(context).edit();
        editor.putString(CHAT_NAME_KEY, chatName);
        editor.commit();
    }

    public static Uri getServerUri(Context context) {
        SharedPreferences prefs = getPreferences(context);
        String serverText = prefs.getString(CHAT_SERVER_KEY, null);
        return (serverText == null) ? null : Uri.parse(serverText);
    }

    public static void saveServerUri(Context context, Uri serverUri) {
        SharedPreferences.Editor editor =  getPreferences(context).edit();
        String serverText = (serverUri == null) ? null : serverUri.toString();
        editor.putString(CHAT_SERVER_KEY, serverText);
        editor.commit();
    }

    public static boolean isRegistered(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getLong(SENDER_ID_KEY, -1) >= 0;
    }

}
