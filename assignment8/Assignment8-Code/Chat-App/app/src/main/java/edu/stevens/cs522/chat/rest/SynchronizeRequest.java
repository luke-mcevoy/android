package edu.stevens.cs522.chat.rest;

import android.os.Parcel;
import android.util.JsonReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.UUID;

import edu.stevens.cs522.chat.entities.ChatMessage;

/**
 * Created by dduggan.
 */

public class SynchronizeRequest extends Request {

    // Added by request processor
    public long lastSequenceNumber;

    public static String LAST_SEQUENCE_NUMBER = "X-Last_Sequence_Number";

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> headers = super.getRequestHeaders();
        // TODO add headers
//        headers.put(LAST_SEQUENCE_NUMBER, String.valueOf(lastSequenceNumber));
        return headers;
    }

    @Override
    public String getRequestEntity() throws IOException {
        // We stream output for SYNC, so this always returns null
        return null;
    }

    @Override
    public Response getResponse(HttpURLConnection connection, JsonReader rd) throws IOException{
        assert rd == null;
        return new SynchronizeResponse(connection);
    }

    @Override
    public Response process(RequestProcessor processor) {
        return processor.perform(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(senderId);
        dest.writeString(appID.toString());
    }

    public SynchronizeRequest(long senderId, UUID clientID) {
        super(senderId, clientID);
    }
    public SynchronizeRequest(long senderId, UUID clientID, ChatMessage message) {
        super(senderId, clientID);
//        this.message = message;
    }

    public SynchronizeRequest(Parcel in) {
        super(in);
        // TODO
        senderId = in.readLong();
        appID = UUID.fromString(in.readString());
    }

    public static Creator<SynchronizeRequest> CREATOR = new Creator<SynchronizeRequest>() {
        @Override
        public SynchronizeRequest createFromParcel(Parcel source) {
            return new SynchronizeRequest(source);
        }

        @Override
        public SynchronizeRequest[] newArray(int size) {
            return new SynchronizeRequest[size];
        }
    };

}
