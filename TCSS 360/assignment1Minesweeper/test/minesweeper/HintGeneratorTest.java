/*
    Summer 2025
    testing class for the static class HintGenerator
 */

package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

/**
 * Testing class for the minesweeper hint generator.
 *
 * @author Roman Bureacov
 * @version July 2025
 */
public final class HintGeneratorTest {
    /** directory of all test cases. */
    private static final String PREFIX =
            Path.of("TCSS 360", "assignment1Minesweeper", "test", "minesweeper", "cases").toString();

    /**
     * Tests all the cases provided in the cases folder. Test cases are of the format:
     * <ul>
     *     <li> testName_input.txt for the input</li>
     *     <li> testName_output.txt for the expected output</li>
     * </ul>
     * Where testName may consist of only letters, numbers, and periods.
     * @throws IOException No testing directory found or missing test pairings
     */
    @Test
    public void testCases() throws IOException {
        final Set<String> testCaseNames = new HashSet<>();

        // get the test case names
        final File testDir = new File(PREFIX);
        final File[] testCases = testDir.listFiles();

        // if the there's something wrong with IO and finding the test cases
        if (!testDir.exists()) {
            throw new IOException(
                    """
                    No testing directory found.
                    Expected local directory %s
                    No such absolute directory found: %s
                    """.formatted(
                            PREFIX,
                            System.getProperty("user.dir") + PREFIX
                    )
            );
        } else if (testCases == null) {
            throw new IllegalStateException(
                    """
                    No test cases found in directory:
                    Local: %s
                    Absolute: %s
                    """.formatted(
                            PREFIX,
                            System.getProperty("user.dir") + PREFIX
                    ));
        }

        for (final File name : testCases) {
            // add only the test case name to the set
            testCaseNames.add(name.getName().split("_")[0]);
        }

        // test the individual cases by name
        for (final String testName : testCaseNames) {
            try {
                test(testName);
            } catch (final Exception theExc) {
                Logger.getAnonymousLogger().log(
                        Level.WARNING,
                        "Test failed at: " + testName,
                        theExc
                );
                throw theExc;
            }
        }
    }

    /* --- HELPERS ---*/

    /**
     * Tests a field by its test name and converting it into file names.
     * @param theTestCaseName the name of the test case
     * @throws IOException if there is no test pair or an IO error
     */
    private void test(final String theTestCaseName) throws IOException {
        test(theTestCaseName + "_input.txt", theTestCaseName + "_output.txt");
    }

    /**
     * Tests the minefields stored in the instance variables. Redirects system input and output
     * streams to objects that can be compared for equality.
     * @param theInputField the name of the text file that is the input minefield
     * @param theOutputField the name of the text file that is the output minefield
     */
    private void test(final String theInputField, final String theOutputField) throws IOException {

        // assemble strings
        final String inputField = Files.readString(Path.of(PREFIX, theInputField));
        final String expectedField = Files.readString(Path.of(PREFIX, theOutputField));

        // redirect input and output
        final ByteArrayInputStream input = new ByteArrayInputStream(inputField.getBytes());
        System.setIn(input);
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // get output
        HintGenerator.main();

        // normalize newlines, one or both may have the carriage return
        final String actualOut = output.toString().replaceAll("\r", "");
        final String expectedOut = expectedField.replaceAll("\r", "");

        assertEquals(
                expectedOut,
                actualOut,
                """
                Unequal minefields at
                %s
                %s
                """.formatted(theInputField, theOutputField)
        );
    }
}
