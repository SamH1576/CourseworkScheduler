package a.courseworkscheduler;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AddCoursework extends AppCompatActivity {

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

        setContentView(R.layout.activity_home);
       Snackbar.make(view, "Coursework Added", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}