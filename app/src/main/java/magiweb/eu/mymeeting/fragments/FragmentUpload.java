package magiweb.eu.mymeeting.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import magiweb.eu.mymeeting.R;


/**
 * Created by niallirl on 21/03/15.
 */
public class FragmentUpload extends Fragment implements OnClickListener  {

    public void onClick(View view) {

    }

    Context thiscontext;
    String host="46.7.211.127";
    String uploadLocation = "/home/pi/upload/niall/";
    String commandToRun;
    EditText usr_nm;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Needed as we are using fragements and need to get the context from the main layout
        thiscontext = container.getContext();

        //Getting the telephone number for creating the folder
        try{

            //TODO TO BE REMOVED AT THE LEAVE HERE FOR NOW
            //String android_id = Settings.Secure.getString(thiscontext.getContentResolver(), Settings.Secure.ANDROID_ID);
            //TelephonyManager tMgr = (TelephonyManager)thiscontext.getSystemService(Context.TELEPHONY_SERVICE);
            //String mPhoneNumber = tMgr.getLine1Number();
            //Toast.makeText(thiscontext, "The phone number is: " + mPhoneNumber + ", and the id is: " + android_id, Toast.LENGTH_LONG).show()

            //Creating the unique folder on the server for the device
            //commandToRun = "sudo mkdir /home/pi/upload/" + android_id;
            commandToRun = "sudo mkdir "+ uploadLocation;


        }catch (Exception e){

            e.printStackTrace();
        }

        //Upload File button in the fragement
        Button btnUpLoadFileFrag;
        Button btnListFileFrag;
        Button btnDeleteFileFragement;
        EditText etResult;




        //TODO To be looked at why this happens
        super.onCreate(savedInstanceState);

        //Creating the fragement view
        View view= inflater.inflate(R.layout.fragment_upload,container,false);

        //Setting a reference to the button
        btnUpLoadFileFrag =(Button)view.findViewById(R.id.btnUploadfile);
        btnListFileFrag =(Button)view.findViewById(R.id.btnListfile);
        btnDeleteFileFragement=(Button)view.findViewById(R.id.btnDeletefile);
        usr_nm = (EditText)view.findViewById(R.id.etListFiles);

        //Onclick listener for the Button
        btnUpLoadFileFrag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

             new SSHUploadFile().execute(host);
             Toast.makeText(thiscontext, "Upload File Pressed", Toast.LENGTH_LONG).show();

            }
        });

        btnDeleteFileFragement.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating an array to pass
                commandToRun = "rm -f -r /home/pi/upload/1.jpg";

                new SSHListFiles().execute(commandToRun);
                Toast.makeText(thiscontext, "Delete File Pressed", Toast.LENGTH_LONG).show();

            }
        });

        btnListFileFrag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating an array to pass
                commandToRun = "ls "+ uploadLocation;
                //commandToRun = "ls";
                new SSHListFiles().execute(commandToRun);

                //new SshListFiles().execute();
                //Check to see where I'am in the application
                Toast.makeText(thiscontext, "List File Pressed", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }



    //Inner Class SSHListFiles Start
    class SSHListFiles extends AsyncTask<String, Integer, String> {

        String asd;

        @Override
        protected String doInBackground(String... arg0) {

            JSch jsch = new JSch();

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("compression.s2c", "zlib,none");

            Session session;
            try {
                session = jsch.getSession("pi", host, 22);
                session.setConfig(config);
                session.setPassword("raspberry");
                session.connect();

                //Creating an instance of the ChannelExec. Casting the session.openChannel
                ChannelExec channel = (ChannelExec) session.openChannel("exec");

                //Creating a stream to see the results.
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //Where the output of the connection channel will be held
                channel.setOutputStream(stream);

                //Setting the command that will be executed
                channel.setCommand(commandToRun);

                //Opens the connection with a specified timeout.
                channel.connect(10000);

                // this kludge seemed to be required.
                java.lang.Thread.sleep(500);
                channel.disconnect();

                //Results have to be returned as not in the main UI thread.
                return stream.toString();
            } catch (Exception e) {
                asd = "Not Connected";
                e.printStackTrace();
                return asd;
            }


        }

        //This runs at end and prints the result when command completes
        @Override
        protected void onPostExecute(String abc) {
            usr_nm.setText(abc);

        }

    }

    //Inner Class SSHList Files End

    //Inner Class SSHUpload Start

    public class SSHUploadFile extends AsyncTask<String, Integer, String> {

        private String sourceLocation;
        String asd;

        @Override
        protected String doInBackground(String... arg0) {

            JSch jsch=new JSch();

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("compression.s2c", "zlib,none");

            Session session;
            try {
                session = jsch.getSession("pi", "46.7.211.127",22);
                session.setConfig(config);
                session.setPassword("raspberry");
                session.connect();

                Channel channel = session.openChannel("sftp");
                channel.connect();
                ChannelSftp sftpChannel = (ChannelSftp)channel;

                sourceLocation= Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyMeeting/";
                //Toast.makeText(thiscontext, sourceLocation, Toast.LENGTH_LONG).show();
                File sourceFileLocation = new File(sourceLocation);
                File childfile[] = sourceFileLocation.listFiles();
                for (File file2 : childfile) {
                    //Toast.makeText(thiscontext, file2.toString(), Toast.LENGTH_LONG).show();

                    //putting the file on the server
                    sftpChannel.put(new FileInputStream(file2),uploadLocation + file2.getName());

                }


                //Path to DCIM Folder
                //String filesPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                //Appending the file 1.jpg, this is a test file
                //filesPath = filesPath + "/1.jpg";

                //File f = new File(filesPath);

                //sftpChannel.put(new FileInputStream(filesPath),"/home/pi/upload/1.jpg");

                sftpChannel.exit();

                //Storing the result
                asd = null;
                //Results have to be returned as not in the main UI thread.
                return asd;
            } catch (Exception e) {
                asd = "Not Connected " + e.toString();
                e.printStackTrace();
                return asd;
            }

        }
        //This runs at end and prints the result when command completes
        @Override
        protected void onPostExecute(String abc){


        }

    }

    //Inner Class SSHUpload End

}











