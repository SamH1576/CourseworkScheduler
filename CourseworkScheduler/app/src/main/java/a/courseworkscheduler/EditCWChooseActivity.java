package a.courseworkscheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EditCWChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cwchoose);
        ListView lv = (ListView) findViewById(R.id.listView);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> your_array_list = new ArrayList<String>();
        String data = returnCWData();
        your_array_list.add(data);
        your_array_list.add("bar");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);
    }

    public String returnCWData(){
        BufferedReader bufferReader = null;
        StringBuilder result = new StringBuilder();
        try{
            FileInputStream fileInputStream = openFileInput("CWStore");
            bufferReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = bufferReader.readLine()) !=null){
                result.append(line);
                            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                bufferReader.close();
                            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return String.valueOf(result);
    }
}
