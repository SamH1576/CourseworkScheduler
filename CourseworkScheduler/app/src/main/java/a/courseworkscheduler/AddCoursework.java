package a.courseworkscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Sam on 16/03/2016.
 */
public class AddCoursework extends AppCompatActivity {
    public String oldDataString;
    public String origCWName = null;
    public boolean isUpdate = false;
    public int context = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coursework);
        Intent intent = getIntent();
        String message = intent.getStringExtra(EditCWChooseActivity.EXTRA_MESSAGE);
        if (message != null) {
            prepForm(message);
            origCWName = message;
            isUpdate = true;
            oldDataString = prepRemoveOldData(message);
        }
    }

    public void onAddCWClick(View view) {
        String verifymessage = null;
        EditText CWName = (EditText) findViewById(R.id.CWTitle); //fetch the title of the coursework from the form
        String CWName_Text = CWName.getText().toString();
        if (!Objects.equals(origCWName, CWName_Text)) { //Identifies whether this is an update or not, as OrigCWName will be null unless initialised when intent is received
            if (returnCWData().contains(CWName_Text)) { //if the file already contains a piece of CW with the same name...
                Toast.makeText(this, "Already a coursework with that name!", //display a notification
                        Toast.LENGTH_LONG).show();
                return; //Exit Method without adding data to file
            }
        }
        verifymessage = VerifyInputs("CWName", CWName_Text);
        if(verifymessage!=null){
            Toast.makeText(this, verifymessage, //display a notification
                    Toast.LENGTH_LONG).show();
            return; //Exit Method without adding data to file
        }
        verifymessage = null;
        EditText DueDate = (EditText) findViewById(R.id.DueDate);
        String DueDate_Text = DueDate.getText().toString();
        verifymessage = VerifyInputs("DueDate", DueDate_Text);
        if(verifymessage!=null){
            Toast.makeText(this, verifymessage, //display a notification
                    Toast.LENGTH_LONG).show();
            return; //Exit Method without adding data to file
        }
        verifymessage = null;
        EditText Weighting = (EditText) findViewById(R.id.Weighting);
        String Weighting_Text = Weighting.getText().toString();
        verifymessage = VerifyInputs("Weighting", Weighting_Text);
        if(verifymessage!=null){
            Toast.makeText(this, verifymessage, //display a notification
                    Toast.LENGTH_LONG).show();
            return; //Exit Method without adding data to file
        }


        //Send calls to add data to file
        if (isUpdate) {
            String datatostore = oldDataString + CWName_Text + "|" + DueDate_Text + "|" + Weighting_Text + ",";
            addToFile(datatostore);
        } else {
            String datatostore = CWName_Text + "|" + DueDate_Text + "|" + Weighting_Text + ",";
            addToFile(datatostore);
        }


        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        isUpdate = false; //reset isUpate
        finish(); //exit activity

    }

    public void prepForm(String message) {
        int arraylength = stringtoarray.finalmatrix.length;
        for (int i = 0; i < arraylength; i++) {
            String tempdata = (stringtoarray.finalmatrix[i][0]);
            if (tempdata != null) {
                if (Objects.equals(tempdata, message)) {
                    EditText CWTitle = (EditText) findViewById(R.id.CWTitle);
                    EditText DueDate = (EditText) findViewById(R.id.DueDate);
                    EditText Weighting = (EditText) findViewById(R.id.Weighting);
                    CWTitle.setText((stringtoarray.finalmatrix[i][0]));
                    DueDate.setText((stringtoarray.finalmatrix[i][1]));
                    Weighting.setText((stringtoarray.finalmatrix[i][2]));
                }
            }
        }

    }

    public void addToFile(String data) {
        BufferedWriter bufferWriter = null;
        try {
            if (isUpdate) {
                context = Context.MODE_PRIVATE;
            } else {
                context = Context.MODE_APPEND;
            }
            FileOutputStream fileOutputStream = openFileOutput("CWStore", context);
            bufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferWriter != null;
                bufferWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String prepRemoveOldData(String message) {
        int j = 150; //Large number so that if something goes wrong, no string data is lost by accident
        String OC = returnCWData();
        String substrtoDel = null;
        int arraylength = stringtoarray.finalmatrix.length;
        for (int i = 0; i < arraylength; i++) {
            String tempdata = (stringtoarray.finalmatrix[i][0]);
            if (Objects.equals(tempdata, message)) {
                j = i;
            }
        }
        substrtoDel = (stringtoarray.finalmatrix[j][0]) + "|" + (stringtoarray.finalmatrix[j][1]) + "|" + (stringtoarray.finalmatrix[j][2]) + ",";
        return OC.replace(substrtoDel, "");
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferReader != null;
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf(result);
    } //Function added as cannot be referenced from another class (openInputStream cannot be static)

    public String VerifyInputs(String elementToCheck, String input){
        String notification = null;
        switch(elementToCheck){
            case "CWName":
                if(Objects.equals(input, "")){
                    notification = "Please enter a coursework title!";
                }else if(input.length()>25){
                    notification = "Coursework title is too long!";
                }
                break;
            case "DueDate":
                if(!isValidDate(input)  || input.length()>8) {
                    notification = "Date is invalid (DD/MM/YY)";
                }
                break;
            case "Weighting":
                if(!isNumeric(input)){
                    notification = "Weighting must be a number!";
                }else if(Double.valueOf(input)>100 || Double.valueOf(input)<0){
                    notification = "Weighting must be between 0 and 100!";
                }
                break;
        }

        return notification;
    }

    public boolean isValidDate(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        try {
            df.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static boolean isNumeric(String str){
        try{
            double d = Double.parseDouble(str);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
}
