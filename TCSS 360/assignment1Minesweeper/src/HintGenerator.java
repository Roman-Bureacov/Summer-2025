import java.util.Scanner;

/**
 * A static class that generates hints for a minefield input.
 *
 * @author Roman Bureacov
 * @version July 2025
 */
public final class HintGenerator {
    /** the character that represents a mine. */
    private static final char MINE = '*';

    private HintGenerator() {
        super();
    }

    /**
     * Entry point into the program to read in a minefield and output
     * a field with the corresponding hints.
     * <br>
     * The input is a minefield to generate hints for,
     * with row number and column number preceding
     * the field made with periods and asterisks denoting
     * the mine locations. Input is terminated on 0 rows
     * and 0 columns.
     *
     * @param theArgs unused
     */
    public static void main(final String... theArgs) {
        final Scanner reader = new Scanner(System.in);
        int minefieldCount = 0;

        while (reader.hasNext()) {
            // get the row and column count
            final int rowCount = reader.nextInt();
            final int colCount = reader.nextInt();
            reader.nextLine(); // eat the newline character (delicious)

            // break on zero arguments
            if (rowCount <= 0 || colCount <= 0) {
                break;
            }

            // generate a matrix with mines and zeroes
            final char[][] minefield = new char[rowCount][];

            for (int row = 0; row < rowCount; row++) {
                final String minefieldRow = reader.nextLine().replaceAll("\\.", "0");
                minefield[row] = minefieldRow.toCharArray();
            }

            // generate the hints
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    if (minefield[row][col] == MINE) {
                        addHints(minefield, row, col);
                    }
                }
            }

            // print out the minefield
            minefieldCount++;
            printField(minefield, minefieldCount);
        }
    }

    /**
     * increments the adjacent hints for the mine at the position specified.
     * @param theMinefield the minefield to assess
     * @param theMineRow the row position of the mine
     * @param theMineCol the col position of the mine
     */
    private static void addHints(final char[][] theMinefield,
                                 final int theMineRow, final int theMineCol) {
        final byte maxCount = 2;
        final int rowCount = theMinefield.length;
        final int colCount = theMinefield[0].length;

        // current row/col plus -1, then 0, then 1
        for (byte row = -1; row < maxCount; row++) {
            // if the row is in range...
            final int newRow = theMineRow + row;
            if (0 <= newRow && newRow < rowCount) {
                for (byte col = -1; col < maxCount; col++) {
                    // if the col is in range...
                    final int newCol = theMineCol + col;
                    if (0 <= newCol && newCol < colCount) {
                        // if this isn't the mine we're talking about...
                        if (theMinefield[newRow][newCol] != MINE) {
                            // increment the character at position
                            theMinefield[newRow][newCol]++;
                        }
                    }
                }
            }
        }
    }

    /**
     * prints out the minefield.
     * @param theMinefield the minefield to print
     * @param theMinefieldIndex the minefield number
     */
    private static void printField(final char[][] theMinefield, final int theMinefieldIndex) {
        System.out.format("Field #%d:\n", theMinefieldIndex);

        for (final char[] row : theMinefield) {
            // because each row is a character array...
            System.out.println(row);
        }

        System.out.println();
    }
}
