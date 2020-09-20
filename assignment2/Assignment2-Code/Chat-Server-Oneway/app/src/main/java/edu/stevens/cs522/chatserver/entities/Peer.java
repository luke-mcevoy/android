package edu.stevens.cs522.chatserver.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
        out.writeString(name);
        out.writeLong(timestamp.getTime());
        out.writeInt(address.getAddress().length);
        out.writeByteArray(address.getAddress());
        out.writeInt(port);
    }

    public Peer(Parcel in) throws UnknownHostException {
        // TODO
        name = in.readString();
        timestamp = new Date(in.readLong());
        byte[] addy = new byte[in.readInt()];
        in.readByteArray(addy);
        address = InetAddress.getByAddress(addy);
        port = in.readInt();
    }

    public Peer(int port, String name, Date timestamp, InetAddress address) {
        this.port = port;
        this.name = name;
        this.timestamp = timestamp;
        this.address = address;
    }

    public static final Creator<Peer> CREATOR = new Creator<Peer>() {

        @Override
        public Peer createFromParcel(Parcel source) {
            // TODO
            try {
                return new Peer(source);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Peer[] newArray(int size) {
            // TODO
            return new Peer[size];
        }
    };
}

