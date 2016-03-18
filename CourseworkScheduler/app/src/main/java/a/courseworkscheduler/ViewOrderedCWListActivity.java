package a.courseworkscheduler;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


        ListView lv = (ListView) findViewById(R.id.listView);
        List<String> your_array_list = new ArrayList<String>();
        String data = returnCWData();
        stringtoarray s2a = new stringtoarray(data);
        s2a.MatrixMaker(data);
        //TODO add matrix ordering method here!//////////////////////////////
        int arraylength = stringtoarray.finalmatrix.length;
        for (int i = 0; i < arraylength; i++) {
            String tempdata = (finalmatrix[i][0]);
            if (tempdata != null) {
                your_array_list.add(tempdata);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);
        if(arrayAdapter.isEmpty()){
            final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
            Snackbar.make(coordinatorLayoutView, "Nothing to show", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
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
