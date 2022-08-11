import java.util.Scanner;
import java.lang.StringBuilder;

public class Cipher {

    // Swap given columns in a matrix.
    public static char[][] swapColumns(char[][] matrix, int[] colPermArray) {
        // Make a duplicate matrix to copy from.
        char[][]tempMatrix = matrix.clone();
        // Replace columns of old matrix with order of new ones.
        for (int col = 0; col < matrix.length; col++) {
            matrix[col] = tempMatrix[colPermArray[col]];
        }
        return matrix;
    }

    public static char[][] decryptColumns(char[][] matrix, int[] colPermArray) {
        // Make a duplicate matrix to copy from.
        char[][]tempMatrix = matrix.clone();
        // Replace columns of old matrix with order of new ones.
        for (int col = 0; col < matrix.length; col++) {
            matrix[colPermArray[col]] = tempMatrix[col];
        }
        return matrix;
    }

    // Read out rows according to the row permutation order.
    public static String swapRows(char[][] matrix, int[] rowPermArray) {
        StringBuilder sOutput = new StringBuilder();
        // Read each row out according to the row permutation
        for (int row = 0; row < rowPermArray.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                sOutput.append(matrix[col][rowPermArray[row]]);
            }
        }
        return sOutput.toString();
    }

    // Read out rows according to the row permutation order.
    public static String matrixToString(char[][] matrix) {
        StringBuilder sOutput = new StringBuilder();
        // Read each row out according to the row permutation
        for (int row = 0; row < matrix[0].length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                sOutput.append(matrix[col][row]);
            }
        }
        return sOutput.toString();
    }

    // Print out the contents of the matrix.
    public static void printMatrix(char[][] matrix) {
        int iRowSize = matrix[0].length;
        int iKeySize = matrix.length;
        for (int row = 0; row < iRowSize; row++) {
            for (int col = 0; col < iKeySize; col++) {
                System.out.print(matrix[col][row] + ", ");
            }
            System.out.println();
        }
    }

    // Main.
    public static void main(String[] args) {

        /* Getting input */

        Scanner sInput = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            try {
                // Determine if encrypting or decrypting.
                System.out.print("Type \"Encrypt\" to Encrypt, \"Decrypt\" to Decrypt, or \"Exit\" to exit: ");
                String sFunction = sInput.nextLine().toLowerCase();
                if (sFunction.equals("exit")) { System.exit(0); } // Check for exit flag

                while (!sFunction.equals("encrypt") && !sFunction.equals("decrypt")) {
                    System.out.println("Invalid input.");
                    System.out.print("Type \"Encrypt\" to Encrypt or \"Decrypt\" to Decrypt: ");
                    sFunction = sInput.nextLine().toLowerCase();
                }

                // Get message.
                // Empty elements of the array are replaced by x's, similar to the example given.
                System.out.print("Enter the message to send: ");
                String sMessage = sInput.nextLine();

                // Get key length.
                System.out.print("Enter the key length: ");
                int iKeySize = Integer.parseInt(sInput.nextLine());
                while (iKeySize < 1) {
                    System.out.print("Invalid key length.  Please Enter the key length: ");
                    iKeySize = Integer.parseInt(sInput.nextLine());
                }

                // determine row length.
                int iRowSize = (int) Math.ceil((double) sMessage.length() / iKeySize);
                System.out.println("Number of rows: " + iRowSize);

                int colPermArray[] = new int[iKeySize];
                int rowPermArray[] = new int[iRowSize];



                // Get column permutation.
                {
                    System.out.print("Enter the column permutation, separated by spaces. Example: ");
                    for (int i = 1; i <= iKeySize; i++) { System.out.print(i + " "); }
                    System.out.println();

                    String sColPerm = sInput.nextLine();
                    String[] sSplitArr = sColPerm.split(" ");

                    for (int i = 0; i < iKeySize; i++) {
                        colPermArray[i] = Integer.parseInt(sSplitArr[i]) - 1;
                    }
                }

                // Get row permutation.
                if (iRowSize > 1) {
                    System.out.print("Enter the row permutation, separated by spaces. Example: ");
                    for (int i = 1; i <= iRowSize; i++) { System.out.print(i + " "); }
                    System.out.println();

                    String sRowPerm = sInput.nextLine();
                    String[] sSplitArr = sRowPerm.split(" ");

                    for (int i = 0; i < iRowSize; i++) {
                        rowPermArray[i] = Integer.parseInt(sSplitArr[i]) - 1;
                    }
                }

                /* Populating matrix */

                char[][] matrix = new char[iKeySize][iRowSize]; // Col first, row second.

                // If encrypting, populate matrix in numerical order.
                if (sFunction.equals("encrypt")) {

                    sMessage.replace(' ', 'x'); // Replace spaces with x; this is what the example given does.

                    int pos = 0;
                    for (int row = 0; row < iRowSize; row++) {
                        for (int col = 0; col < iKeySize; col++) {
                            if (pos < sMessage.length()) {
                                matrix[col][row] = sMessage.charAt(pos);
                                pos++;
                            }
                            else {
                                matrix[col][row] = 'x'; // Fill empty spaces with x.
                            }
                        }
                    }
                    // If decrypting, populate matrix in order of row permutations.
                } else if (sFunction.equals("decrypt")) {
                    // Populate array in order of row permutation.
                    int pos = 0;
                    for (int row = 0; row < iRowSize; row++) {
                        for (int col = 0; col < iKeySize; col++) {
                            if (pos < sMessage.length()) {
                                matrix[col][rowPermArray[row]] = sMessage.charAt(pos);
                                pos++;
                            }
                        }
                    }
                }

                /* Reading from matrix */

                // Swap the columns of the matrix according to the column permutation.
                if (sFunction.equals("encrypt")) {
                    matrix = swapColumns(matrix, colPermArray);
                } else if (sFunction.equals("decrypt")) {
                    matrix = decryptColumns(matrix, colPermArray);
                }

                // Read the matrix out to a string
                String sOutput = "";
                if (sFunction.equals("encrypt")) { // Read by row permutations.
                    sOutput = swapRows(matrix, rowPermArray);
                } else if (sFunction.equals("decrypt")) { // Read in order.
                    sOutput = matrixToString(matrix);
                }

                System.out.println("Result: " + sOutput); // Display result.

            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        sInput.close();
    }
}