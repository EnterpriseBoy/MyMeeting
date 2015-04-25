package magiweb.eu.mymeeting.fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import magiweb.eu.mymeeting.MainActivity;
import magiweb.eu.mymeeting.R;

/**
 * Created by niallirl on 25/04/15.
 */
public class FragmentStartup extends Fragment implements View.OnClickListener {

    Context thiscontext;
    private Button buttonMaps;
    private Button buttonSavePhoneNumber;
    SQLiteDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Needed as we are using fragements and need to get the context from the main layout
        thiscontext = container.getContext();

        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_startup, container, false);

        buttonMaps =(Button)view.findViewById(R.id.buttonGetLocation);
        buttonSavePhoneNumber =(Button)view.findViewById(R.id.buttonSaveDetailsDatabase);

        buttonMaps.setOnClickListener(this);
        buttonSavePhoneNumber.setOnClickListener(this);



        return view;


    }


    public void onClick(View view){

        //Checks to see which button is pressed
        boolean buttonMapPressed;
        boolean buttonSaveDetailsPressed;

        if(buttonMapPressed= view.toString().contains("buttonGetLocation")){

            Toast.makeText(thiscontext, "Maps Pressed", Toast.LENGTH_LONG).show();
        }

        if(buttonSaveDetailsPressed= view.toString().contains("buttonSaveDetailsDatabase")){
            MySQLiteHelper db = new MySQLiteHelper(MainActivity.this);
            db.addStudent(new Student("Student One", "+1111111", "Module 1", "Course 1"));
            Toast.makeText(thiscontext, "Save Details pressed", Toast.LENGTH_LONG).show();
        }

    }
}
