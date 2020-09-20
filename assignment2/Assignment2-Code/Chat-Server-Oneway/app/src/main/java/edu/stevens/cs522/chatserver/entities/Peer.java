package edu.stevens.cs522.chatserver.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by dduggan.
 */

public class Peer implements Parcelable {

    // Will be database key
    public long id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp;

    // Where we heard from them
    public InetAddress address;

    public int port;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO
        out.writeLong(id);
        out.writeString(name);
        out.writeLong(timestamp.getTime());
        out.writeSerializable(address);

        byte[] addressBytes = address.getAddress();
        int addressBytesLength = addressBytes.length;


//        out.writeSerializable(timestamp);
//        out.writeLong(address.getAddress());
//        new byte[] address
//        out.writeLong(timestamp.getTime());
    }

    public Peer(Parcel in) {
        // TODO
        id = in.readLong();
        name = in.readString();
        timestamp = new Date(in.readLong());
        address = (InetAddress) in.readSerializable();

//        timestamp = (Date) in.readSerializable();
//        address = InetAddress.getByAddress();
    }

    public Peer(int port, String name, Timestamp timestamp) {
        this.port = port;
        this.name = name;
        this.timestamp = timestamp;
    }

    public static final Creator<Peer> CREATOR = new Creator<Peer>() {

        @Override
        public Peer createFromParcel(Parcel source) {
            // TODO
            return new Peer(source);
        }

        @Override
        public Peer[] newArray(int size) {
            // TODO
            return new Peer[size];
        }

    };
}

