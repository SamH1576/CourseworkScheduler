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

import static a.courseworkscheduler.stringtoarray.finalmatrix;

public class ViewOrderedCWListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ordered_cwlist);
        Intent intent = getIntent();
        String message = intent.getStringExtra(EditCWChooseActivity.EXTRA_MESSAGE);
        int extrainfo = 0;

        ListView lv = (ListView) findViewById(R.id.listView);
        List<String> your_array_list = new ArrayList<String>();
        String data = returnCWData();
        stringtoarray s2a = new stringtoarray(data);
        s2a.MatrixMaker(data);
        switch (message) {
            case "Name":
                s2a.MatrixSorterbyName();
                break;
            case "Date":
                s2a.MatrixSorterbyDate();
                extrainfo = 1;
                break;
            case "Weight":
                s2a.MatrixSorterbyWeight();
                extrainfo = 2;
                break;
        }
        int arraylength = stringtoarray.finalmatrix.length;
        for (int i = 0; i < arraylength; i++) {
            String tempdata = (finalmatrix[i][0]);
            String tempdata2 = (finalmatrix[i][extrainfo]); //add the data / weighting to the CW info
            if (tempdata != null) {
                if (extrainfo != 0) {
                    your_array_list.add(tempdata + " " + tempdata2);
                } else {
                    your_array_list.add(tempdata);
                }
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);
        if (!arrayAdapter.getItem(0).isEmpty()) { //If the first item is empty the whole array is empty - if this isn't the case, exit the method
            Log.d("Log", "Nothing to show");

            return;
        }
        Toast.makeText(this, "Nothing to show!", //If the code reaches this point the array must be empty - so display Toast
                Toast.LENGTH_LONG).show();
    }


    public String returnCWData() {
        BufferedReader bufferReader = null;
        StringBuilder result = new StringBuilder();
        try {
            FileInputStream fileInputStream = openFileInput("CWStore");
            bufferReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                result.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return String.valueOf(result);
    } //Added to each class as cannot be static due to openFileStream
}
