package a.courseworkscheduler;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Huan Min Gan
 */
public class stringtoarray {
    private String add_coursework_inputs;
    public static String[][] finalmatrix;

    public stringtoarray(String rawinputs) {
        add_coursework_inputs = rawinputs;
    }

    public void arraymaker() {
        String[] array = add_coursework_inputs.split(",");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public void MatrixMaker(String inputs) {
        String[] rows = inputs.split(",");

        String[][] matrix = new String[rows.length][];
        int r = 0;
        for (String row : rows) {
            matrix[r++] = row.split("\\|");
            finalmatrix = matrix;
        }
        //matrix[1][1] row column
        //stringtoarray s2a = new stringtoarray(inputs);
        //s2a.MatrixMaker(inputs);
    }
}
