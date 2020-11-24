package edu.stevens.cs522.chat.rest;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

import edu.stevens.cs522.chat.activities.RegisterActivity;
import edu.stevens.cs522.chat.settings.Settings;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class RequestService extends IntentService {

    private static final String TAG = RequestService.class.getCanonicalName();

    public static final String SERVICE_REQUEST_KEY = "edu.stevens.cs522.chat.rest.extra.REQUEST";

    public static final String RESULT_RECEIVER_KEY = "edu.stevens.cs522.chat.rest.extra.RECEIVER";

    private RequestProcessor processor;

    public RequestService() {
        super("RequestService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        processor = new RequestProcessor(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Request request = intent.getParcelableExtra(SERVICE_REQUEST_KEY);
        if (request == null) {
            // The intent was generated from a periodic alarm.
            long senderId = Settings.getSenderId(this);
            UUID appId = Settings.getAppId(this);
            request = new SynchronizeRequest(senderId, appId);
        }
        ResultReceiver receiver = intent.getParcelableExtra(RESULT_RECEIVER_KEY);

        Response response = processor.process(request);

        if (receiver != null) {
            if (response instanceof ErrorResponse) {
                // TODO let activity know request failed
                receiver.send(RegisterActivity.RESULT_CANCELED, null);
            } else {
                // TODO let activity know request succeeded
                receiver.send(RegisterActivity.RESULT_OK, null);
            }


        } else {
            Log.i(TAG, "Missing receiver");
        }
    }

}
