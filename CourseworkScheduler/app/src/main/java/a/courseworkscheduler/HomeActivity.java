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
        startActivityForResult(intent, 0xe110);
    }

    public void onClickViewCW(View view){
        Intent intent = new Intent(this, ViewCWListActivity.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0xe110) {
            final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
            Snackbar.make(coordinatorLayoutView, "Coursework Added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}

