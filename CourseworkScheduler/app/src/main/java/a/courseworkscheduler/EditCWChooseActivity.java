package a.courseworkscheduler;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static a.courseworkscheduler.stringtoarray.*;
public class EditCWChooseActivity extends AppCompatActivity { //Code for the EditCWChoose activity
    public final static String EXTRA_MESSAGE = "a.courseworkscheduler.MESSAGE"; //initialise a string variable which is used to send messages with intents to other activies
    @Override
    protected void onCreate(Bundle savedInstanceState) { //method executed on the activity being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cwchoose); //sets the activity layout to be the Edit_CWchoose xml
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() { //handles an item in the array list being clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //when an item is clicked...
                TextView item =(TextView) view; //identify the item clicked ...
                String message = item.getText().toString(); //fetch its text
                sendtoAddCW(message); //call to method sendtoAddCW with the CWName that was clicked
            }
        };
        ListView lv = (ListView) findViewById(R.id.listView); //declare and initialise a listView object
        lv.setOnItemClickListener(itemClickListener); //activates the OnItemClickListener prepared above

        List<String> your_array_list = new ArrayList<String>(); //declare a new arraylist of strings
        String data = returnCWData(); //fetch the CW Data from the storage file
        stringtoarray s2a = new stringtoarray(); //declare a new instance of stringtoarray
        s2a.MatrixMaker(data); //calls the MatrixMaker method of stringtoarray, which uses the CW data pulled from the storage file and converts it to a matrix
        int arraylength = stringtoarray.finalmatrix.length; //determine the length (no of rows) of the matrix
        for (int i = 0; i < arraylength; i++) { //for each row...
            String tempdata = (finalmatrix[i][0]); //grab the first element...
            if (tempdata != null) { //check it contains data..
                your_array_list.add(tempdata); //if it does, add it to the array list being built
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( //Using the arraylist of string, initialise an arrayAdapter which can be used to move the data into a ListView
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter); //finally, fill the listView (list of items on display) with the data from arrayAdapter
        Log.d("Log", "Complete"); //for debugging purposes only

        if(!arrayAdapter.getItem(0).isEmpty()){ //check the first element of the array is not empty
            Log.d("Log", "Nothing to show"); //(for debugging)
            return; //if not empty, then there is data to display, simply return to exit the onCreate method
        }
        Toast.makeText(this, "Nothing to show!", //if there is no data in the ListView, display a notification
                Toast.LENGTH_LONG).show();

    }

    public String returnCWData() { //Returns a string of the data which is currently stored NB Added to each class as cannot be static due to openFileStream
        BufferedReader bufferReader = null; //Initialise a buffer reader, which will read the file
        StringBuilder result = new StringBuilder(); //Initialise a stringbuilder, which puts together strings
        try { //try, catch structure used to handle errors
            FileInputStream fileInputStream = openFileInput("CWStore"); //Initialise an input stream and target file, required for the reader below
            bufferReader = new BufferedReader(new InputStreamReader(fileInputStream)); //Point the reader at the target file
            String line;
            while ((line = bufferReader.readLine()) != null) { //Whilst there are sill lines to be read...
                result.append(line); //add that line to the string
            }
        } catch (IOException e) { //error handling
            e.printStackTrace();
        } finally { //assuming there are no errors in the try catch above...
            try {
                assert bufferReader != null; //checking the file isn't null, ie not opened
                bufferReader.close(); //close the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf(result); //return the file data as a string
    }

    public void sendtoAddCW(String message){ //method to start AddCoursework activity, passing an intent with CWName as the message
        Intent intent = new Intent(this, AddCoursework.class); //create a new intent and point it to AddCoursework
        intent.putExtra(EXTRA_MESSAGE, message); //add the message to the intent
        startActivity(intent); //start the AddCoursework activity
        finish(); //Ensures on completion of AddCoursework the app does not return to the edit choose CW activity
    }


}
