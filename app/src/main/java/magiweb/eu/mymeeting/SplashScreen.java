package magiweb.eu.mymeeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by niallirl on 02/04/15.
 */
public class SplashScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        Thread startTimer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                    Intent i= new Intent("com.magiweb.MyMeeting.Main");
                    startActivity(i);


                }catch(InterruptedException e){

                    e.printStackTrace();

                }
                finally{
                    finish();
                }

            }
        };
        startTimer.start();

    }
}


