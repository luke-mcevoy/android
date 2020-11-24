package edu.stevens.cs522.chat.rest;

import android.net.Uri;
import android.os.Parcel;
import android.util.JsonReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dduggan.
 */

public class RegisterRequest extends Request {

    public Uri chatServer;

    public String chatname;

    public RegisterRequest(Uri chatServer, String chatname, UUID clientID) {
        super(0, clientID);
        this.chatServer = chatServer;
        this.chatname = chatname;
    }


    public static String CHAT_SERVER = "X-Chat-Server";

    public static String CHAT_NAME = "X-Chat-Name";

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> headers = super.getRequestHeaders();
        // TODO add headers
//        headers.put(CHAT_SERVER, chatServer.toString());
//        headers.put(CHAT_NAME, chatname);
        return headers;
    }

    @Override
    public String getRequestEntity() throws IOException {
        return null;
    }

    @Override
    public Response getResponse(HttpURLConnection connection, JsonReader rd) throws IOException{
        return new RegisterResponse(connection);
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
        super.writeToParcel(dest, flags);
        dest.writeString(chatServer.toString());
        dest.writeString(chatname);
    }

    public RegisterRequest(Parcel in) {
        super(in);
        this.chatServer = Uri.parse(in.readString());
        this.chatname = in.readString();
    }

    public static Creator<RegisterRequest> CREATOR = new Creator<RegisterRequest>() {
        @Override
        public RegisterRequest createFromParcel(Parcel source) {
            return new RegisterRequest(source);
        }

        @Override
        public RegisterRequest[] newArray(int size) {
            return new RegisterRequest[size];
        }
    };

}
