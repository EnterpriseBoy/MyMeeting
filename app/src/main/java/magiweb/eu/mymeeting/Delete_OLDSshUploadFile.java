package magiweb.eu.mymeeting;

import android.os.AsyncTask;
import android.os.Environment;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by niallirl on 06/04/15.
 */
public class Delete_OLDSshUploadFile extends AsyncTask<String, Integer, String> {


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

            //Path to DCIM Folder
            String filesPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            //Appending the file 1.jpg, this is a test file
            filesPath = filesPath + "/1.jpg";

            File f = new File(filesPath);


            sftpChannel.put(new FileInputStream(filesPath),"/home/pi/upload/1.jpg");

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