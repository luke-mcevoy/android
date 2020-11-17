package edu.stevens.cs522.chat.rest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.util.Set;
import java.util.UUID;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.contracts.MessageContract;
import edu.stevens.cs522.chat.contracts.PeerContract;
import edu.stevens.cs522.chat.settings.Settings;


/**
 * Created by dduggan.
 */

public class ChatHelper {

    public static final String DEFAULT_CHAT_ROOM = "_default";

    private Context context;

    // Provided by server when we register
    private long senderId;

    // Installation senderId created when the app is installed (and provided with every request for sanity check)
    private UUID appID;

    public ChatHelper(Context context) {
        this.context = context;
        this.appID = Settings.getAppId(context);
    }

    public void register (Uri uri, String chatName, ResultReceiver resultReceiver) {
        if (chatName != null && !chatName.isEmpty()) {
            // TODO save the chat name and add a registration request to the request queue
            // Registraton will be done (immediately) on a background thread by RequestService.
            RegisterRequest registerRequest = new RegisterRequest(uri, chatName, appID);
            if (senderId > 0) { return; }
            Settings.saveChatName(context, chatName);
            addRequest(registerRequest, resultReceiver);
        }
    }

    public void postMessage(String chatRoom, String text, ResultReceiver receiver) {
        if (text != null && !text.isEmpty()) {
            if (chatRoom == null || chatRoom.isEmpty()) {
                chatRoom = DEFAULT_CHAT_ROOM;
            }
            // TODO add a post message request to the request queue (see addRequest)
            // Depending on Settings.SYNC, message will be sent immediately on background
            // thread, or just added locally and eventually synchronized with server database.
            PostMessageRequest postMessageRequest = new PostMessageRequest(
                    Settings.getSenderId(context),
                    appID,
                    chatRoom,
                    text);
            addRequest(postMessageRequest, receiver);
        }
    }

    /**
     * Send request to service that processes the request on a background thread.
     */
    private void addRequest(Request request, ResultReceiver receiver) {
        context.startService(createIntent(context, request, receiver));
    }

    private void addRequest(Request request) {
        addRequest(request, null);
    }

    /**
     * Use an intent to send the request to a background service. The request is included as a Parcelable extra in
     * the intent. The key for the intent extra is in the RequestService class.
     */
    public static Intent createIntent(Context context, Request request, ResultReceiver receiver) {
        Intent requestIntent = new Intent(context, RequestService.class);
        requestIntent.putExtra(RequestService.SERVICE_REQUEST_KEY, request);
        if (receiver != null) {
            requestIntent.putExtra(RequestService.RESULT_RECEIVER_KEY, receiver);
        }
        return requestIntent;
    }

    public static Intent createIntent(Context context, Request request) {
        return createIntent(context, request, null);
    }

}
