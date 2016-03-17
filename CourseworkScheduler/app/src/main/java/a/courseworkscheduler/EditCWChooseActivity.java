package a.courseworkscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static a.courseworkscheduler.stringtoarray.*;

public class EditCWChooseActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "a.courseworkscheduler.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cwchoose);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item =(TextView) view;
                String message = item.getText().toString();
                sendtoAddCW(message);
            }
        };
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(itemClickListener);

        List<String> your_array_list = new ArrayList<String>();
        String data = returnCWData();
        stringtoarray s2a = new stringtoarray(data);
        s2a.MatrixMaker(data);
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
        //TODO Add Snackbar saying nothing to display if no CWs
        lv.setAdapter(arrayAdapter);
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
    }
    public void sendtoAddCW(String message){
        Intent intent = new Intent(this, AddCoursework.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();
    }
}
