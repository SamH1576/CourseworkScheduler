package a.courseworkscheduler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

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
                return entry1[1].compareTo(entry2[1]);
            }
        });

    }
    //TODO INVERT THIS to make it descending
    public void MatrixSorterbyWeight() {
        Arrays.sort(finalmatrix, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                return Double.valueOf(entry1[2]).compareTo(Double.valueOf(entry2[2]));

            }
        });
    }
}

