package a.courseworkscheduler;

//import additional code
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class AddCoursework extends AppCompatActivity {
    public String oldDataString; //Initialise class wide variables
    public String origCWName = null;
    public boolean isUpdate = false;
    public int context = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //code to run when activity is created
        super.onCreate(savedInstanceState); //checks if a saved version of the activity is being resent
        setContentView(R.layout.activity_add_coursework); //calls layout file to use
        Intent intent = getIntent(); //create intent
        String message = intent.getStringExtra(EditCWChooseActivity.EXTRA_MESSAGE); //grabs any messages from calling intent
        if (message != null) { //if there is something in that message
            prepForm(message); //calls the prepform method to input data in fields which user will edit
            origCWName = message; //catch the coursework title which is being editted
            isUpdate = true; //set class wide variable isUpate to be true, used later on
            Button DeleteButton = (Button)findViewById(R.id.DelCWBtn); //find and reference the DelCWBtn
            DeleteButton.setVisibility(View.VISIBLE); //make the DelCWBtn visible
            oldDataString = prepRemoveOldData(message); //call the prepRemoveOldData method, which returns a string of all data minus the data for the coursework which is being edited
        }
    }

    public void onAddCWClick(View view) { //handles the click of the Add Coursework button
        String verifymessage; //initialise verifymessage variable
        EditText CWName = (EditText) findViewById(R.id.CWTitle); //identify the CWName field
        String CWName_Text = CWName.getText().toString(); //get the text from the CWName field
        if (!Objects.equals(origCWName, CWName_Text)) { //Identifies whether this is an update or not, as OrigCWName will be null unless initialised when intent is received
            if (returnCWData().contains(CWName_Text)) { //if the file already contains a piece of CW with the same name...
                Toast.makeText(this, "Already a coursework with that name!", //display a notification
                        Toast.LENGTH_LONG).show();
                return; //Exit Method without adding data to file
            }
        }
        verifymessage = VerifyInputs("CWName", CWName_Text); //calls the VerifyInputs method and sends the CWName tag and the text from the CWName field
        if(verifymessage!=null){ //if the verify message method returns a message
            Toast.makeText(this, verifymessage, //display a notification
                    Toast.LENGTH_LONG).show();
            return; //Exit Method without adding data to file
        }
        verifymessage = null; //renull the verifymessage variable to ensure it does not get incorrectly
        EditText DueDate = (EditText) findViewById(R.id.DueDate); //identify the DueDate field
        String DueDate_Text = DueDate.getText().toString(); //get the text from the DueDate field
        verifymessage = VerifyInputs("DueDate", DueDate_Text); //Verify the input as above
        if(verifymessage!=null){ //if the verification returns a message
            Toast.makeText(this, verifymessage, //display a notification
                    Toast.LENGTH_LONG).show();
            return; //Exit Method without adding data to file
        }
        verifymessage = null; //renull the verifymessage variable to ensure it does not get incorrectly
        EditText Weighting = (EditText) findViewById(R.id.Weighting); //identify the Weighting field
        String Weighting_Text = Weighting.getText().toString(); //get the text from the DueDate field
        verifymessage = VerifyInputs("Weighting", Weighting_Text); //Verify the input from above
        if(verifymessage!=null){ //if the verification returns a message
            Toast.makeText(this, verifymessage, //display a notification
                    Toast.LENGTH_LONG).show();
            return; //Exit Method without adding data to file
        }


        //Send calls to add data to file
        if (isUpdate) { //if the isUpdate has been set true above
            String datatostore = oldDataString + CWName_Text + "|" + DueDate_Text + "|" + Weighting_Text + ","; //takes the oldDataString initialised above and adds the updated data
            addToFile(datatostore); //calls the addToFile method to save the data
        } else {
            String datatostore = CWName_Text + "|" + DueDate_Text + "|" + Weighting_Text + ","; //constructs the new data string
            addToFile(datatostore); //saves the new data string
        }


        Intent returnIntent = new Intent(); //initialise the intent to return
        setResult(Activity.RESULT_OK, returnIntent); //set the result to return
        isUpdate = false; //reset isUpate //set isUpdate false for safety
        finish(); //exit activity

    }

    public void onDelCWClick(View view){ //handles the delete coursework button
        addToFile(oldDataString); //takes the oldDataString created in the onCreate method and adds that to file, effectively removing the coursework being edited
        isUpdate = false; //reset isUpdate
        Toast.makeText(this, "Coursework Deleted", //display a notification
                Toast.LENGTH_LONG).show();
        finish(); //exit activity
    }

    public void prepForm(String message) { //method to prepare the form is coursework is being edited
        int arraylength = stringtoarray.finalmatrix.length; //find the finalmatrix length
        for (int i = 0; i < arraylength; i++) { //
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
        String substrtoDel;
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
    }

    public String VerifyInputs(String elementToCheck, String input){
        String notification = null;
        switch(elementToCheck){
            case "CWName":
                if(Objects.equals(input, "")){
                    notification = "Please enter a coursework title!";
                }else if(input.length()>50){
                    notification = "Coursework title is too long!";
                }
                break;
            case "DueDate":
                if(!isValidDate(input)  || input.length()!= 8) {
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
            df.setLenient(false);
            df.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static boolean isNumeric(String str){
        try{
            Double.parseDouble(str);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
}
