package magiweb.eu.mymeeting.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import magiweb.eu.mymeeting.R;

/**
 * Created by niallirl on 21/03/15.
 */
public class FragmentEmail extends Fragment implements OnClickListener {

    private Button btnSend;
    private EditText eMessage;
    private EditText eTo;
    private EditText eSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_mms,container,false);



        //Finding references to the fields
        btnSend =(Button)view.findViewById(R.id.btnSend);
        eMessage =(EditText)view.findViewById(R.id.eTxtMessage);
        eTo =(EditText)view.findViewById(R.id.eTxtTo);
        eSubject =(EditText)view.findViewById(R.id.eTxtSubject);

        btnSend.setOnClickListener(this);


        return view;

    }
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, eSubject.getText());
        intent.putExtra(Intent.EXTRA_TEXT, eMessage.getText());
        intent.setData(Uri.parse("mailto:"+eTo.getText())); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);


    }
}