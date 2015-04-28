package com.example.henri_000.homecontroll;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.content.SharedPreferences;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.os.StrictMode;
import android.widget.EditText;
import android.content.DialogInterface;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends ActionBarActivity {
    private Networktask networktask;
    private Checksocket checksocket;
    private Dialog dialog = new Dialog();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        networktask = new Networktask();

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);

        final Switch switch1 = (Switch) findViewById(R.id.switch1);
        Switch switch2 = (Switch) findViewById(R.id.switch2);
        Switch switch3 = (Switch) findViewById(R.id.switch3);
        Switch switch4 = (Switch) findViewById(R.id.switch4);

        //Listeners for home screen buttons
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });
        button3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });
        button4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    try {
                        networktask = new Networktask();
                        networktask.execute();
                        Thread.sleep(50, 0);
                        networktask.toggleON();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try {
                        networktask = new Networktask();
                        networktask.execute();
                        Thread.sleep(50, 0);
                        networktask.toggleOFF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    } //OnCreate() end


    private class Checksocket extends AsyncTask<Void, Void, Void> {
        private Socket socket;

        @Override
        protected Void doInBackground(Void... params) {
            SocketAddress sockadr = new InetSocketAddress(dialog.getIp(), 9090);
            socket = new Socket();
            try {
                socket.connect(sockadr, 5000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class Networktask extends AsyncTask<Void, Void, Void> {
        private Socket socket;
        private PrintWriter printwriter;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                SocketAddress sockadr = new InetSocketAddress(dialog.getIp(), 9090);
                socket = new Socket();
                socket.connect(sockadr, 5000);
                if (socket.isConnected()) {
                    printwriter = new PrintWriter(socket.getOutputStream(), true);
                    //alertConnected();
                }
                else alertConnectFail();
            } catch (UnknownHostException e) {
                alertIP();
                e.printStackTrace();
            } catch (IOException e) {
                alertConnectFail();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void input) {
        }
        public void toggleON() throws IOException {
            printwriter.println("TOGGLE\n ON unit 1");
        }
        public void toggleOFF() throws IOException {
            printwriter.println("TOGGLE\n OFF unit 1");
        }
    }


    public void alertConnectFail() {
        AlertDialog.Builder myalert = new AlertDialog.Builder(this);
        myalert.setMessage(R.string.connectFail).create();
        myalert.setTitle(R.string.connectFailTitle).create();
        myalert.show();
    }
    public void alertConnected() {
        AlertDialog.Builder myalert = new AlertDialog.Builder(this);
        myalert.setMessage(R.string.connectSuccess).create();
        myalert.setTitle(R.string.connectSuccessTitle).create();
        myalert.show();
    }
    public void alertIP() {
        AlertDialog.Builder myalert = new AlertDialog.Builder(this);
        myalert.setMessage(R.string.ipError).create();
        myalert.setTitle(R.string.connectFailTitle).create();
        myalert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            dialog.showInputDialog(MainActivity.this);
        }

        return super.onOptionsItemSelected(item);
    }
}