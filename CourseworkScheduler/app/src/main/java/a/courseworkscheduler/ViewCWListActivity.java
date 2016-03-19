package a.courseworkscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewCWListActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "a.courseworkscheduler.MESSAGE"; //declares a variable which can be used to transfer messages using intents
    @Override
    protected void onCreate(Bundle savedInstanceState) { //code executed on creation of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cwlist); //sets the xml content to display
    }

    public void onClickOrderName(View view){ //handles the Order by Name button
        Intent intent = new Intent(this, ViewOrderedCWListActivity.class); //create a new intent and point it at the ViewOrderedCWList activity
        intent.putExtra(EXTRA_MESSAGE, "Name"); //add the "Name" string as a message in the intent, used to differentiate the calls to ViewOrderedCWList
        startActivity(intent); //start the new activity

    }

    public void onClickOrderDate(View view){ //handles the Order by Date button
        Intent intent = new Intent(this, ViewOrderedCWListActivity.class); //create a new intent and point it at the ViewOrderedCWList activity
        intent.putExtra(EXTRA_MESSAGE, "Date"); //add the "Date" string as a message in the intent, used to differentiate the calls to ViewOrderedCWList
        startActivity(intent); //start the new activity

    }

    public void onClickOrderWeight(View view){ //handles the Order by Weighting button
        Intent intent = new Intent(this, ViewOrderedCWListActivity.class); //create a new intent and point it at the ViewOrderedCWList activity
        intent.putExtra(EXTRA_MESSAGE, "Weight"); //add the "Weight" string as a message in the intent, used to differentiate the calls to ViewOrderedCWList
        startActivity(intent); //start the new activity

    }
}
