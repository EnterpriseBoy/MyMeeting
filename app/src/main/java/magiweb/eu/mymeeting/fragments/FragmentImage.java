package magiweb.eu.mymeeting.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import magiweb.eu.mymeeting.R;


public class FragmentImage extends Fragment {

    //Reference to buttons
    public static Button buttonDeleteImage;
    public static Button buttonSaveImage;


    //Location of where the will will be saved
    private Uri outputFileUri;

    //Image view for showing the image.
    int IMAGE_CAPTURE_REQUEST_CODE = 0;
    ImageView iv1;
    TextView txt1;

    Context thiscontext;






    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Checking the context of the view from the ViewGroup
        thiscontext = container.getContext();

       //Expand (Inflate) the Activity
        View view=inflater.inflate(R.layout.fragment_image, container, false);

        buttonDeleteImage = (Button) view.findViewById(R.id.btnDeleteImage);
        buttonSaveImage = (Button) view.findViewById(R.id.btnSaveImage);

        //Creating a time stamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        //Setting up the path for Full file to be capture
        File file = new File (Environment.getExternalStorageDirectory().getAbsolutePath(),"/MyMeeting/QR" + timeStamp + ".jpg");

        //Creating the file
        try {
            file.createNewFile();
        } catch (IOException e) {

        }

        //Converting the file path to a URI
        Uri outputFileUri = Uri.fromFile(file);

        // Creating an intent i
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Adding the saving file location
        i.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);

        //Starting the intent
        startActivityForResult(i, IMAGE_CAPTURE_REQUEST_CODE);

        //These and may be removed Don't do anything yet
        buttonDeleteImage.setOnClickListener(
                new OnClickListener(){
                    public void onClick(View v){
                        buttonDeleteImageClicked(v);
                    }

                }
        );
        //These Don't do anything yet and may be removed
        buttonSaveImage.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        buttonSaveImageClicked(v);
                    }

                }
        );

        return view;
    }

    public void buttonDeleteImageClicked(View view){

    }

    public void buttonSaveImageClicked(View view){

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        //Finding a reference to the image view to show the image
        iv1=(ImageView)getView().findViewById(R.id.imageViewImage);

        if(requestCode==IMAGE_CAPTURE_REQUEST_CODE){

            if(data != null){
                if(data.hasExtra("data")){
                    Bitmap thumbnail = data.getParcelableExtra("data");

                    iv1.setImageBitmap(thumbnail);

                }

            }else{


                if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                    Log.d("CameraDemo", "Pic saved");

                    File file = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Test.jpg");

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
                    int outWidth = options.outWidth;
                    int outHeight = options.outHeight;

                    int width = iv1.getWidth();
                    int height = iv1.getHeight();


                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(outWidth/width,outHeight/height);

                    // Decode the image file into a Bitmap sized to fill the View
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = scaleFactor;

                    iv1.setImageBitmap(bitmap);

                }else{
                    Log.d("CameraDemo", "Pic Not Saved");
                }

          }
        }

    }


}
