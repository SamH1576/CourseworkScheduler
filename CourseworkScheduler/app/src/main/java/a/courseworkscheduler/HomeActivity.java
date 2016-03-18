package a.courseworkscheduler;

import android.content.Context;
import android.content.Intent;
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

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openAddCW(View view) {
        Intent intent = new Intent(this, AddCoursework.class);
        startActivityForResult(intent, 1);
    }

    public void onClickEditCW(View view){
        Intent intent = new Intent(this, EditCWChooseActivity.class);
        startActivity(intent);
    }

    public void onClickViewCW(View view){
        Intent intent = new Intent(this, ViewCWListActivity.class);
        startActivity(intent);
    }

    public void onClickClearFile(View view){
        clearFile();
        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        Snackbar.make(coordinatorLayoutView, "All Coursework Cleared", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK){
                final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
            Snackbar.make(coordinatorLayoutView, "Coursework Added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        }
    }

    public void clearFile(){
        BufferedWriter bufferWriter = null;
        try {
            FileOutputStream fileOutputStream = openFileOutput("CWStore", Context.MODE_PRIVATE);
            bufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                bufferWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

