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
    private String add_coursework_inputs;
    public static String[][] finalmatrix;

    public stringtoarray(String rawinputs) {
        add_coursework_inputs = rawinputs;
    }

//    public void arraymaker() {
//        String[] array = add_coursework_inputs.split(",");
//        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i]);
//        }
//    }

    public void MatrixMaker(String inputs) {
        String[] rows = inputs.split(",");

        String[][] matrix = new String[rows.length][];
        int r = 0;
        for (String row : rows) {
            matrix[r++] = row.split("\\|");
            finalmatrix = matrix;
        }

    }

    public void MatrixSorterbyName() {
        Arrays.sort(finalmatrix, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                return entry1[0].compareTo(entry2[0]);
            }
        });
    }

    public void MatrixSorterbyDate() {
        Arrays.sort(finalmatrix, new Comparator<String[]>() {
            @Override
            public int compare(String[] entry1, String[] entry2) {

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                Date date1 = null;
                Date date2 = null;
                try {

                    date1 = format.parse(entry1[1].split(" ")[0]);
                    date2 = format.parse(entry2[1].split(" ")[0]);

                    return date1.compareTo(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(stringtoarray.class.getName()).log(Level.SEVERE, null, ex);
                }
                return date1.compareTo(date2);
            }
        });

    }

    public void MatrixSorterbyWeight() {
        Arrays.sort(finalmatrix, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                return Double.valueOf(entry2[2]).compareTo(Double.valueOf(entry1[2]));
            }
        });
    }

}

