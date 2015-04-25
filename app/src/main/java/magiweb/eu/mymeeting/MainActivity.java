package magiweb.eu.mymeeting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import magiweb.eu.mymeeting.fragments.FragmentEmail;
import magiweb.eu.mymeeting.fragments.FragmentImage;
import magiweb.eu.mymeeting.fragments.FragmentSound;
import magiweb.eu.mymeeting.fragments.FragmentUpload;

public class MainActivity extends Activity {

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    private String stringImageLocation;
    //Database variable
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        //TODO change this to enable or disable the buttons.
        Toast.makeText(this, "Media Stroage Available: " + mExternalStorageAvailable + "\nMedia Stroage Writeable: " + mExternalStorageWriteable, Toast.LENGTH_LONG).show();

        // Creating database and table
        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");

        //Check if the folder exists or make it.
        File mFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyMeeting/");
        Toast.makeText(this, "CP: " + mFolder, Toast.LENGTH_LONG).show();
        if (!mFolder.exists()) {
            mFolder.mkdir();
            Toast.makeText(this, "Folder:" + mFolder + "\nwas created", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Folder:" + mFolder + "\nexists", Toast.LENGTH_LONG).show();
        }


        //Starting a default fragment
        fr = new FragmentStartup();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();


        setContentView(R.layout.activity_main);
        Button buttonPicture = (Button) findViewById(R.id.btnTakePicture);
        Button buttonSound = (Button) findViewById(R.id.btnRecordSound);
        Button buttonEmail = (Button) findViewById(R.id.btnSendEmail);
        Button buttonUpload = (Button) findViewById(R.id.btnUpload);


        buttonPicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                fr = new FragmentImage();

                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fr);
                fragmentTransaction.commit();

            }
        });

        buttonSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                fr = new FragmentSound();

                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fr);
                fragmentTransaction.commit();

            }
        });

        buttonEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                fr = new FragmentEmail();

                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fr);
                fragmentTransaction.commit();

            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                fr = new FragmentUpload();

                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fr);
                fragmentTransaction.commit();

            }
        });


    }


}
