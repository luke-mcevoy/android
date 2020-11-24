package edu.stevens.cs522.chat.rest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ResultReceiver;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.chat.contracts.MessageContract;
import edu.stevens.cs522.chat.contracts.PeerContract;
import edu.stevens.cs522.chat.entities.ChatMessage;
import edu.stevens.cs522.chat.entities.Peer;
import edu.stevens.cs522.chat.managers.MessageManager;
import edu.stevens.cs522.chat.managers.PeerManager;
import edu.stevens.cs522.chat.managers.RequestManager;
import edu.stevens.cs522.chat.managers.TypedCursor;
import edu.stevens.cs522.chat.settings.Settings;
import edu.stevens.cs522.base.StringUtils;

/**
 * Created by dduggan.
 */

public class RequestProcessor {

    private Context context;

    private RestMethod restMethod;

    private RequestManager requestManager;

    public RequestProcessor(Context context) {
        this.context = context;
        this.restMethod = new RestMethod(context);
        // Used for managing messages in the database
        this.requestManager = new RequestManager(context);
    }

    public Response process(Request request) {
        return request.process(this);
    }

    public Response perform(RegisterRequest request) {
        Response response = restMethod.perform(request);
        if (response instanceof RegisterResponse) {
            /*
             * Add a record for this peer to the local database.  The PK is the sender ID
             * returned from registration on the server.
             */
            RegisterResponse registration = (RegisterResponse) response;
            Peer myself = new Peer();
            myself.id = registration.getSenderId();
            myself.name = request.chatname;
            myself.timestamp = DateUtils.now();
            myself.longitude = 40.7440;
            myself.latitude = -74.0324;
            new PeerManager(context).persist(myself);

            // TODO update the server URI, user name and sender id in settings
            Settings.saveServerUri(context, request.chatServer);
            Settings.saveChatName(context, myself.name);
            Settings.saveSenderId(context, myself.id);

        }
        return response;
    }

    public Response perform(PostMessageRequest request) {
        if (!Settings.SYNC) {
            // TODO insert the message into the local database
            ContentResolver contentResolver = context.getContentResolver();
            ChatMessage chatMessage = new ChatMessage();

//            chatMessage.seqNum = request.senderId;
            chatMessage.chatRoom = request.chatRoom;
            chatMessage.timestamp = request.timestamp;
            chatMessage.latitude = request.latitude;
            chatMessage.longitude = request.longitude;
            chatMessage.sender = Settings.getChatName(context);
            chatMessage.senderId = request.senderId;
            chatMessage.messageText = request.message;
            requestManager.persist(chatMessage);


            Response response = restMethod.perform(request);
            if (response instanceof PostMessageResponse) {
                // TODO update the message in the database with the sequence number
                chatMessage.seqNum = ((PostMessageResponse) response).getMessageId();
//                requestManager.updateSeqNum(request.senderId, requestManager.getLastSequenceNumber());
            }
            return response;
        } else {
            /*
             * We will just insert the message into the database, and rely on background
             * sync to upload.
             */
            ChatMessage chatMessage = new ChatMessage();
            // TODO fill the fields with values from the request message
            chatMessage.messageText = request.message;
            chatMessage.longitude = request.longitude;
            chatMessage.latitude = request.latitude;
            chatMessage.timestamp = request.timestamp;
            chatMessage.sender = Settings.getChatName(context);
            chatMessage.senderId = request.senderId;

            requestManager.persist(chatMessage);
            return request.getDummyResponse();
        }
    }

    /**
     * For SYNC: perform a sync using a request manager.  These requests are
     * generated from an alarm that is scheduled at periodic intervals.
     */
    public Response perform(SynchronizeRequest request) {
        RestMethod.StreamingResponse response = null;
        final TypedCursor<ChatMessage> messages = requestManager.getUnsentMessages();
        try {
            /*
             * This is the callback from streaming new local messages to the server.
             */
            RestMethod.StreamingOutput out = new RestMethod.StreamingOutput() {
                @Override
                public void write(final OutputStream os) throws IOException {
                    try {
                        JsonWriter wr = new JsonWriter(new OutputStreamWriter(new BufferedOutputStream(os)));
                        wr.beginArray();

                        if (messages.moveToFirst()) {
                            do {
                                /*
                                 * TODO stream unread messages to the server:
                                 * {
                                 *   chatroom : ...,
                                 *   timestamp : ...,
                                 *   latitude : ...,
                                 *   longitude : ....,
                                 *   text : ...
                                 * }
                                 */
                                ChatMessage message = messages.getEntity();
                                wr.beginObject();
                                wr.name("chatroom").value(message.chatRoom);
                                wr.name("timestamp").value(message.timestamp.getDate());
                                wr.name("latitude").value(message.latitude);
                                wr.name("longitude").value(message.longitude);
                                wr.name("text").value(message.messageText);
                                wr.endObject();
                            } while (messages.moveToNext());
                        }

                        wr.endArray();
                        wr.flush();
                    } finally {
                        messages.close();
                    }
                }
            };
            /*
             * Connect to the server and upload messages not yet shared.
             */
            response = restMethod.perform(request, out);

            /*
             * Stream downloaded peer and message information, and update the database.
             * The connection is closed in the finally block below.
             */
            JsonReader rd = new JsonReader(new InputStreamReader(new BufferedInputStream(response.getInputStream()), StringUtils.CHARSET));
            // TODO parse data from server (messages and peers) and update database
            // See RequestManager for operations to help with this.
//            requestManager.persist();



            /*
             *
             */
            return response.getResponse();

        } catch (IOException e) {
            return new ErrorResponse(0, ErrorResponse.Status.SERVER_ERROR, e.getMessage());

        } finally {
            if (response != null) {
                response.disconnect();
            }
        }
    }


}
