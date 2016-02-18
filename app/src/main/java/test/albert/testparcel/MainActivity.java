package test.albert.testparcel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FilenameResultListener {
    Handler handler;
    FilenameResultReceiver receiver;
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv01);
        startLongTask();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String message = bundle.getString("message");
                Log.d("ddd", "message: " + message);
            }
        };

        if (savedInstanceState == null) {
            Log.d("MainAct.onCreate 1", "savedinstancestate null, create receiver with handler");
            receiver = new FilenameResultReceiver(handler);
        } else {
            Log.d("MainAct.onCreate 1", "savedinstancestate NOT null, DEPARCEL receiver without handler");
            receiver = savedInstanceState.getParcelable("parcel");
        }
        receiver.setListener(this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("message", "something from the click");
                receiver.onReceiveResult(0, bundle);
            }
        });
    }

    void startLongTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {

                }
                return "done after 10 seconds!";
            }
        }.execute("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("parcel", receiver);
    }

    @Override
    public void onFilenameReceived(String filename) {
        tv.setText("MA.onFilenameReceived: " + filename);
    }
}