package magiweb.eu.mymeeting.fragments;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import magiweb.eu.mymeeting.R;


/**
 * Created by niallirl on 21/03/15.
 */
public class FragmentSound extends Fragment implements OnClickListener  {

    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private String outputLocation =null;
    private String newFile = null;
    private Button save,stop,play,record;
    private EditText saveFileText;
    private TextView saveFileLabel;
    Context thiscontext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    //Needed as we are using fragements and need to get the context from the main layout
    thiscontext = container.getContext();

    super.onCreate(savedInstanceState);

    View view= inflater.inflate(R.layout.fragment_sound,container,false);

        //References
        save =(Button)view.findViewById(R.id.btnSave);
        stop =(Button)view.findViewById(R.id.btnStop);
        play =(Button)view.findViewById(R.id.btnPlay);
        record=(Button)view.findViewById(R.id.btnRecord);
        saveFileLabel=(TextView)view.findViewById(R.id.textviewSoundSaveFileLabel);
        saveFileText=(EditText)view.findViewById(R.id.editTextFileNameSave);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        save.setOnClickListener(this);
        record.setOnClickListener(this);

        save.setEnabled(false);
        play.setEnabled(false);
        stop.setEnabled(false);
        saveFileLabel.setVisibility(View.INVISIBLE);
        saveFileText.setVisibility(View.INVISIBLE);



    return view;

    }

    @Override
    public void onClick(View view) {


        //Checks to see which button is pressed
        boolean PlayPressed;
        boolean StopPressed;
        boolean RecordPressed;
        boolean SavePressed;

        if(RecordPressed= view.toString().contains("btnRecord")){

            try {
                outputLocation= Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyMeeting/";
                outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyMeeting/myrecording.3gp";
                //Toast.makeText(thiscontext, Environment.getExternalStorageDirectory().getAbsolutePath().toString(),Toast.LENGTH_LONG).show();
                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);
                myAudioRecorder.prepare();
                myAudioRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            save.setEnabled(false);
            stop.setEnabled(true);
            play.setEnabled(false);


            Toast.makeText(thiscontext, "Record Pressed",Toast.LENGTH_LONG).show();
        }

        if(StopPressed= view.toString().contains("btnStop")){
            try{
                myAudioRecorder.stop();
                //Below two lines are causing this to crash.
                myAudioRecorder.release();
                myAudioRecorder  = null;
                stop.setEnabled(false);
                play.setEnabled(true);
                save.setEnabled(true);
                saveFileLabel.setEnabled(true);
                saveFileText.setEnabled(true);
                saveFileLabel.setVisibility(View.VISIBLE);
                saveFileText.setVisibility(view.VISIBLE);

            }catch (Exception e){
                e.printStackTrace();
            }

            Toast.makeText(thiscontext, "Stop Pressed",Toast.LENGTH_LONG).show();
        }

        if(PlayPressed= view.toString().contains("btnPlay")){
            try{
                MediaPlayer m = new MediaPlayer();
                m.setDataSource(outputFile);
                m.prepare();
                m.start();
            }catch(IOException e){
                e.printStackTrace();
            }
            Toast.makeText(thiscontext, "Play Pressed",Toast.LENGTH_LONG).show();
        }

        if(SavePressed= view.toString().contains("btnSave")){

            //File name to be saved
            Toast.makeText(thiscontext, "Save Pressed",Toast.LENGTH_LONG).show();
            newFile=outputLocation + saveFileText.getText().toString() + ".3gp";

            File sourceFile = new File(outputFile);
            File destinationFile = new File(newFile);

            try{
                //Apache file utils for copying files
                FileUtils.copyFile(sourceFile,destinationFile);
                //Deleting the original file
                FileUtils.forceDelete(sourceFile);
            }catch (IOException e){
                e.printStackTrace();
            }

        }



    }






}
