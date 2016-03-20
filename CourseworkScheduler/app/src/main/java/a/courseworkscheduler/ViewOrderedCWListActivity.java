package a.courseworkscheduler;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static a.courseworkscheduler.stringtoarray.finalmatrix;

public class ViewOrderedCWListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //code to execute on creation of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ordered_cwlist); //sets the xml content to be shown
        Intent intent = getIntent(); //catches the intent sent from the previous activity
        String message = intent.getStringExtra(EditCWChooseActivity.EXTRA_MESSAGE); //extracts the message included in the intent which was sent

        ListView lv = (ListView) findViewById(R.id.listView); //declare a new ListView and point it to the ListView element in the xml file
        List<String> your_array_list = new ArrayList<String>(); //declare a new arrayList
        String data = returnCWData(); //fetch the CW data from the storage file
        stringtoarray s2a = new stringtoarray(); //creates a new instance of the stringtoarray class
        s2a.MatrixMaker(data); //takes the data fetched from the storage file and converts it to a matrix
        if (finalmatrix[0][0] != null) { //checks that the first element of the matrix is not null
            switch (message) { //switch used to differentiate between calls (eg what to order the matrix by, fetched from the calling intent)
                case "Name":
                    s2a.MatrixSorterbyName(); //sort the matrix alphabetically by name (a-z)
                    break;
                case "Date":
                    s2a.MatrixSorterbyDate(); //sort the matrix by date, earliest first
                    break;
                case "Weight": //sort the matrix by weight
                    s2a.MatrixSorterbyWeight();
                    break;
            }
        }
        int arraylength = stringtoarray.finalmatrix.length; //determine the length (no of rows) of the matrix
        if (!finalmatrix[0][0].isEmpty()) { //check the first element isn't empty
            for (int i = 0; i < arraylength; i++) { //for each row, fetch each column of data
                String tempdata = (finalmatrix[i][0]);
                String tempdata2 = (finalmatrix[i][1]);
                String tempdata3 = (finalmatrix[i][2]);//add the data / weighting to the CW info (only fetched if needed)
                your_array_list.add(tempdata + "  " + tempdata2 + "  " + tempdata3); //create a string of the data to display, and add it to the arrayList
            }

        } else { //if the matrix is empty...
            Toast.makeText(this, "Nothing to show!", //display Toast
                    Toast.LENGTH_LONG).show();
            return; //exit the method
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( //the matrix must not be empty, therefore fill an arrayAdapter with the data from the matrix
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter); //use the arrayAdapter to fill the onscreen ListView with data
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
}
