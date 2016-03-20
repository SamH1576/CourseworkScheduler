package a.courseworkscheduler;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //code to run on creation of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); //sets the xml content to be shown
        }

    public void openAddCW(View view) { //handles the click of the Add Coursework button
        Intent intent = new Intent(this, AddCoursework.class); //prepares an intent to send to the Add Coursework activity
        startActivityForResult(intent, 1); //start the new activity, with a caveat to expect a result from the activity (used so that a notification can be displayed afterwards)
    }

    public void onClickEditCW(View view){ //handles the Edit Coursework button click
        Intent intent = new Intent(this, EditCWChooseActivity.class); //prepare an intent to send to EditCWChooseActivity
        startActivity(intent); //start the new activity
    }

    public void onClickViewCW(View view){ //handles the View Coursework button
        Intent intent = new Intent(this, ViewCWListActivity.class); //prepare an intent to send to ViewCWListActivity
        startActivity(intent); //start the new activity
    }

    public void onClickClearFile(View view){ //handles the Clear Coursework button
        clearFile(); //call to the clearFile method which clears the content of the storage file
        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition); //prepare a snackbar (notification)
        Snackbar.make(coordinatorLayoutView, "All Coursework Cleared", Snackbar.LENGTH_LONG) //show a notification that all coursework data has been cleared
                .setAction("Action", null).show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Handles the return of the Add Coursework activity, which returns a result via an intent
        super.onActivityResult(requestCode, resultCode, data); //fetches the result code from the intent

        if (requestCode == 1) { //checks that it is handling the result from the correct Activity
            if (resultCode == RESULT_OK){ //if the Add Coursework activity was completed successfully...
                final View coordinatorLayoutView = findViewById(R.id.snackbarPosition); //display a notification saying that the coursework was added
            Snackbar.make(coordinatorLayoutView, "Coursework Added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        }
    }

    public void clearFile(){ //method to completely clear the storage file, removing all Coursework Data
        BufferedWriter bufferWriter = null; //create a new file writer
        try { //try, catch structure used to handle any errors that may arise
            FileOutputStream fileOutputStream = openFileOutput("CWStore", Context.MODE_PRIVATE); //create an output stream, required for the writer below, point reader at storage file
            bufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream)); //initialise writer, NB MODE_PRIVATE above means new content completely overwrites old content
            bufferWriter.write(""); //write nothing to the file, thereby deleting all content
        } catch (IOException e) { //used to catch any errors for debugging
            e.printStackTrace();
        }finally{ //once the above is complete
            try{
                bufferWriter.close(); //close the file
            } catch (IOException e) { //as before used for error handling
                e.printStackTrace();
            }
        }
    }
}

