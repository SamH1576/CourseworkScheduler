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
    public final static String EXTRA_MESSAGE = "a.courseworkscheduler.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cwlist);
    }

    public void onClickOrderName(View view){
        Intent intent = new Intent(this, ViewOrderedCWListActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Name");
        startActivity(intent);

    }

    public void onClickOrderDate(View view){
        Intent intent = new Intent(this, ViewOrderedCWListActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Date");
        startActivity(intent);

    }

    public void onClickOrderWeight(View view){
        Intent intent = new Intent(this, ViewOrderedCWListActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Weight");
        startActivity(intent);

    }
}
