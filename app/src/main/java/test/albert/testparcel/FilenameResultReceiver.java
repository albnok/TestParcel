package test.albert.testparcel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

public class FilenameResultReceiver extends ResultReceiver {
    FilenameResultListener listener;

    public void setListener(FilenameResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        Log.d("FRR.onReceiveResult", "listener null? " + (listener==null));
        if (listener!=null) {
            listener.onFilenameReceived("HEY WE GOT THIS!!! " + resultData.getString("message"));
        }
    }

    @Override
    public void send(int resultCode, Bundle resultData) {
        super.send(resultCode, resultData);
    }

    public FilenameResultReceiver(Handler handler) {
        super(handler);
        Log.d("FRR constructor", "we've built with a handler.");
    }

    public static Creator<FilenameResultReceiver> CREATOR = new Creator<FilenameResultReceiver>() {
        @Override
        public FilenameResultReceiver createFromParcel(Parcel source) {
            FilenameResultReceiver receiver = new FilenameResultReceiver(null);
            Log.d("FRR.createFromParcel", "we've rebuilt the FilenameResultReceiver from a parcel. dunno how its callbacks will survive");
            return receiver;
        }

        public FilenameResultReceiver[] newArray(int size) {
            return new FilenameResultReceiver[size];
        }
    };
}
