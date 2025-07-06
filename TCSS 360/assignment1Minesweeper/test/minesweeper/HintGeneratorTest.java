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
    private static final String PREFIX = "TCSS 360/assignment1Minesweeper/test/minesweeper/cases";

    String myInputField;
    String myExpectedField;

    /**
     * Tests all the cases provided in the cases folder. Test cases are of the format
     * <ul>
     *     <li> testName_input.txt for the input</li>
     *     <li> testName_output.txt for the expected output</li>
     * </ul>
     */
    @Test
    public void testCases() {
        final Set<String> testCaseNames = new HashSet<>();

        // get the test case names
        final File[] testCases = (new File(PREFIX)).listFiles();
        assert testCases != null;
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
            }
        }
    }

    /* --- HELPERS ---*/

    /**
     * Tests a field by its test name and converting it into file names.
     * @param theFieldName
     * @throws IOException
     */
    private void test(final String theFieldName) throws IOException {
        test(theFieldName + "_input.txt", theFieldName + "_output.txt");
    }

    /**
     * Tests a minefield by two file names: the input and the expected output.
     * @param theInputField the file name for the input minefield
     * @param theOutputField the file name for the expected output
     * @throws IOException if the file names failed to resolve
     */
    private void test(final String theInputField, final String theOutputField)
        throws IOException {

        // assemble strings
        myInputField = Files.readString(Path.of(PREFIX, theInputField));
        myExpectedField = Files.readString(Path.of(PREFIX, theOutputField));

        test();
    }

    /**
     * Tests the minefields stored in the instance variables. Redirects system input and output
     * streams to objects that can be compared for equality.
     */
    private void test() {
        // redirect input and output
        final ByteArrayInputStream input = new ByteArrayInputStream(myInputField.getBytes());
        System.setIn(input);
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // get output
        HintGenerator.main();

        // normalize newlines, one or both may have the carriage return
        final String expectedOut = output.toString().replaceAll("\r", "");
        myExpectedField = myExpectedField.replaceAll("\r", "");

        assertEquals(
                myExpectedField,
                expectedOut,
                "Unequal minefields"
        );
    }
}
