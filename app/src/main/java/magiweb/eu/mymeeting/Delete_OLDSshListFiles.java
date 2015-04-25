package magiweb.eu.mymeeting;

import android.os.AsyncTask;
import android.view.View;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * Created by niallirl on 07/04/15.
 */
public class Delete_OLDSshListFiles extends AsyncTask<Void, Integer, String> {



    //public SshListFiles(AsyncResponse listener){
    //    this.listener=listener;
    //}




    private View view = null;

    String asd;

    @Override
    protected String doInBackground(Void... arg0) {

        System.out.println("doInBackground..!");
        JSch jsch = new JSch();

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        config.put("compression.s2c", "zlib,none");

        Session session;
        try {
            session = jsch.getSession("pi", "46.7.211.127", 22);
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
            channel.setCommand("ls /home/pi/upload/");

            //Opens the connection with a specified timeout.
            channel.connect(1000);

            // this kludge seemed to be required.
            java.lang.Thread.sleep(500);
            channel.disconnect();

            //Storing the result
            asd = stream.toString();
            //Results have to be returned as not in the main UI thread.
            return "Test";
        } catch (Exception e) {
            asd = "Not Connected";
            e.printStackTrace();
            return asd;
        }


    }

    //This runs at end and prints the result when command completes
    @Override
    protected void onPostExecute(String abc) {



    }


}