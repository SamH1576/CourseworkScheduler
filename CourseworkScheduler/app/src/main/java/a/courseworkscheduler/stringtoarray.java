package a.courseworkscheduler;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Huan Min Gan
 */
public class stringtoarray {
    public static String[][] finalmatrix;//Initialise a public string of 2 dimensions named

    public void MatrixMaker(String inputs) {//Method to create a 2 dimensional matrix of 3xn size from a string of inputs
        String[] rows = inputs.split(",");//Defines a string array 'rows'. The individual elements of 'rows' are the strings in 'inputs' are delimited by ,'s
        String[][] matrix = new String[rows.length][];//Defines a 2 dimensional array of row length = number of elements in the 'rows' array
        int r = 0;// Initialise integer r = 0
        for (String row : rows) {//For loop to split the strings in 'rows' into different elements delimited by |'s and assign them to the subsequent column
            matrix[r++] = row.split("\\|");
            finalmatrix = matrix; //Equates the public 2 dimensional array finalmatrix = matrix. finalmatrix data is used in subsequent parts of the code
        }
    }

    public void MatrixSorterbyName() {//Method to sort finalmatrix according to column[0] i.e the first column. Since it is a strings, it will sort it alphabetically
        Arrays.sort(finalmatrix, new Comparator<String[]>() {//Sorts the array using a comparator
            @Override
            public int compare(final String[] entry1, final String[] entry2) {//Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
                return entry1[0].compareTo(entry2[0]);//returns an integer which indicates which is the greater value
            }
        });
    }

    public void MatrixSorterbyDate() {//Method to sort finalmatrix according to column[1] i.e the second column.
        Arrays.sort(finalmatrix, new Comparator<String[]>() {//Sorts the array using a comparator
            @Override
            public int compare(String[] entry1, String[] entry2) {//Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");//Initialise a date formatter which can be used to turn a string into a date
                Date date1 = null;
                Date date2 = null;
                try {//try,catch structure is used to catch any errors arising from parsing the strings below

                    date1 = format.parse(entry1[1].split(" ")[0]);//Converts the element into a date
                    date2 = format.parse(entry2[1].split(" ")[0]);//Converts the element into a date

                    return date1.compareTo(date2);//returns an integer which indicates which is the greater value
                } catch (ParseException ex) {//returns any errors to the log file
                    Logger.getLogger(stringtoarray.class.getName()).log(Level.SEVERE, null, ex);
                }
                return date1.compareTo(date2);//returns an integer which indicates which is the greater value (redundant code required by Android Studio in case try-catch fails)
            }
        });

    }

    public void MatrixSorterbyWeight() {//Method to sort finalmatrix according to column[2] i.e the third column.
        Arrays.sort(finalmatrix, new Comparator<String[]>() {//Sorts the array using a comparator
            @Override
            public int compare(final String[] entry1, final String[] entry2) {//Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
                return Double.valueOf(entry2[2]).compareTo(Double.valueOf(entry1[2]));//Compares two entries in primitive type form double to account for value of the number displayed by the string
            }
        });
    }

}

