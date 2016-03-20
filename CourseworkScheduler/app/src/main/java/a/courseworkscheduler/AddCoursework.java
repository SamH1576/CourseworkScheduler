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

public class AddCoursework extends AppCompatActivity { //code for the AddCoursework activity
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
            Button AddButton = (Button)findViewById(R.id.AddCWBtn);
            AddButton.setText("Update");
            oldDataString = prepRemoveOldData(message); //call the prepRemoveOldData method, which returns a string of all data minus the data for the coursework which is being edited
        }
    }

    public void onAddCWClick(View view) { //handles the click of the Add Coursework button
        String verifymessage; //initialise verifymessage variable
        EditText CWName = (EditText) findViewById(R.id.CWTitle); //identify the CWName field
        String CWName_Text = CWName.getText().toString(); //get the text from the CWName field
        if (!Objects.equals(origCWName, CWName_Text)) { //Identifies whether this is an update or not, as OrigCWName will be null unless initialised when intent is received
            if (returnCWData().contains(CWName_Text + "|")) { //if the file already contains a piece of CW with the same name...
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
        if(isUpdate){
            Toast.makeText(this, "Coursework Updated!", //display a notification
                    Toast.LENGTH_LONG).show();
        }
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
        for (int i = 0; i < arraylength; i++) { //Loop over array length to grab the data from each row of the matrix
            String tempdata = (stringtoarray.finalmatrix[i][0]); //first check there is infact data in that row
            if (tempdata != null) { //if there isn't anything in the row, don't bother
                if (Objects.equals(tempdata, message)) { //if the first element in the row (CWtitle) matches the CW being edited, fill the form with the data from this row
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

    public void addToFile(String data) { //add the passed string to the storage file
        BufferedWriter bufferWriter = null; //initialise a new BufferWriter, which actually handles the file input
        try { //the try catch structure is used to catch any errors when opening and writing the file, which would otherwise cause the app to close
            if (isUpdate) {
                context = Context.MODE_PRIVATE; //Private mode clears the entire file and adds the new string in its place
            } else {
                context = Context.MODE_APPEND; //Append mode adds the new string to the end of the file
            }
            FileOutputStream fileOutputStream = openFileOutput("CWStore", context); //Initialise an output reader and target file with the write type specified above, required for the buffer writer
            bufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream)); //Point the file writer at the target file
            bufferWriter.write(data); //write the data to the file
        } catch (IOException e) { //handle any errors
            e.printStackTrace(); //print the error to logcat
        } finally { //after the try, catch structure, the file is closed using another try catch to handle errors if closing
            try {
                assert bufferWriter != null; //checking that the bufferWriter isn't null, ie no errors above
                bufferWriter.close(); //close the file
            } catch (IOException e) { //handle any errors
                e.printStackTrace(); //print error to logcat
            }
        }
    }

    public String prepRemoveOldData(String message) { //method to return a string of the stored data, without the piece of coursework which is being edited
        int j = 150; //Large number so that if something goes wrong, no string data is lost by accident
        String OC = returnCWData(); //fetch the file data
        int arraylength = stringtoarray.finalmatrix.length; //fetch the length of the matrix (no of rows)
        for (int i = 0; i < arraylength; i++) { //for each row of the matrix ...
            String tempdata = (stringtoarray.finalmatrix[i][0]); //check the first element ...
            if (Objects.equals(tempdata, message)) { //if the first element (CWName) is the same as the coursework being edited (message) ...
                j = i; //record the row number for use below
            }
        }
        String substrtoDel = (stringtoarray.finalmatrix[j][0]) + "|" + (stringtoarray.finalmatrix[j][1]) + "|" + (stringtoarray.finalmatrix[j][2]) + ","; //prepares a string of the data for the coursework being edited...
        return OC.replace(substrtoDel, ""); //replaces that string with nothing (ie deletes it), and returns this final string
    }

    public String returnCWData() { //Returns a string of the data which is currently stored NB Added to each class as cannot be static due to openFileStream
        BufferedReader bufferReader = null; //Initialise a buffer reader, which will read the file
        StringBuilder result = new StringBuilder(); //Initialise a stringbuilder, which puts together strings
        try { //try, catch structure used to handle errors
            FileInputStream fileInputStream = openFileInput("CWStore"); //Initialise an input stream and target file, required for the reader below
            bufferReader = new BufferedReader(new InputStreamReader(fileInputStream)); //Point the reader at the target file
            String line;
            while ((line = bufferReader.readLine()) != null) { //Whilst there are sill lines to be read...
                result.append(line); //add that line to the string
            }
        } catch (IOException e) { //error handling
            e.printStackTrace();
        } finally { //assuming there are no errors in the try catch above...
            try {
                assert bufferReader != null; //checking the file isn't null, ie not opened
                bufferReader.close(); //close the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf(result); //return the file data as a string
    }

    public String VerifyInputs(String elementToCheck, String input){ //Method to verify inputs of each field on the AddCW activity
        String notification = null; //declare a String variable and initialise it
        switch(elementToCheck){
            case "CWName": //handles the CWName field
                if(Objects.equals(input, "")){ //no title given
                    notification = "Please enter a coursework title!";
                }else if(input.length()>50){ //title is too long
                    notification = "Coursework title is too long!";
                }
                break;
            case "DueDate": //handles the DueDate field
                if(!isValidDate(input)  || input.length()!= 8) { //ensures the date is valid and in the correct format
                    notification = "Date is invalid (DD/MM/YY)";
                }
                break;
            case "Weighting": //handles the weighting field
                if(!isNumeric(input)){ //checks that the input is actually numeric
                    notification = "Weighting must be a number!";
                }else if(Double.valueOf(input)>100 || Double.valueOf(input)<0){ //input must be between 0 and 100
                    notification = "Weighting must be between 0 and 100!";
                }
                break;
        }

        return notification; //returns the appropriate notification declared in the switch structure if there is a problem with the input, null if no problems
    }

    public boolean isValidDate(String dateString) { //Method which checks a string to confirm it is a valid date
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH); //Initialises a date formater and tells it to check dates of the form dd/MM/yy
        try {
            df.setLenient(false); //Checks that the date is not only in the correct form, but a possible date as well ie not 99/99/99
            df.parse(dateString); //parses the string which was sent to the method, if there is no error it simply returns true
            return true;
        } catch (ParseException e) { //if an error is raised in the try brace then the string is not a valid date, so false is returned
            return false;
        }
    }
    public static boolean isNumeric(String str){ //Checks that a string contains a number
        try{
            Double.parseDouble(str); //parses the string to a double format
        }catch(NumberFormatException nfe){ //if an error is raised in the try brace, the string was non-numeric, so the method returns false
            return false;
        }
        return true; //no errors means the string is a number, so true is returned
    }
}
