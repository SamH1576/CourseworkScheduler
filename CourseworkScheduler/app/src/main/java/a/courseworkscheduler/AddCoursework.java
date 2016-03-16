package a.courseworkscheduler;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Sam on 16/03/2016.
 */
public class AddCoursework extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coursework);
    }

    public void onAddCWClick(View view){
        EditText CWName = (EditText)findViewById(R.id.CWTitle);
        String CWName_Text = CWName.getText().toString();

        EditText DueDate = (EditText)findViewById(R.id.DueDate);
        String DueDate_Text = DueDate.getText().toString();

        EditText Weighting = (EditText)findViewById(R.id.Weighting);
        String Weighting_Text = Weighting.getText().toString();

        //TODO add verification to the form entries


        //Add data to file
        //TODO rewrite this as a callable function, so that initially file can be wiped, then MODE_APPEND used to add to it
        String datatostore = CWName_Text + "," + DueDate_Text + "," + Weighting_Text;
        BufferedWriter bufferWriter = null;
        try {
            FileOutputStream fileOutputStream = openFileOutput("CWStore", Context.MODE_PRIVATE);
            bufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferWriter.write(datatostore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                bufferWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        finish(); //exit activity

    }
}


