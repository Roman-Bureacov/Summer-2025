/*
    Summer 2025
    testing class for the static class HintGenerator
 */

package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * Testing class for the minesweeper hint generator.
 *
 * @author Roman Bureacov
 * @version July 2025
 */
public final class HintGeneratorTest {
    private static final String PREFIX = "TCSS 360/assignment1Minesweeper/test/minesweeper/";

    String myInputField;
    String myExpectedField;

    /**
     * Tests a simple, hand-generated minefield.
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void testMinefield1() throws IOException {
        test("SimpleField01");
    }

    /**
     * Test two hand-generated minefields.
     * Also tests if the field names are in order.
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void testTwoMinefields() throws IOException {
        test("TwoFields_input");
    }

    /**
     * Tests minefield and densities:
     * <ul>
     *     <li>5x5 0.25</li
     *     <li>7x7 0.35</li>
     *     <li>10x10 0.30</li>
     * </ul>
     * Also tests if the field names are in order.
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void testThreeMinefields() throws IOException {
        test("ThreeFields");
    }

    /**
     * Tests a minefield that is 100x100 and a density of 1
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void test100by100Minefield() throws IOException {
        test("VeryLargeField");
    }

    /**
     * Tests a 1x00 minefield with only 1 mine.
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void testLongMinefield() throws IOException {
        test("LongField");
    }

    /**
     * Tests a 100x1 minefield with only 1 mine
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void testTallMinefield() throws IOException {
        test("TallField");
    }

    /**
     * Tests a 1x1 minefield with only 1 mine
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void testTinyMinefieldWithMine() throws IOException {
        test("TinyFieldWithMine");
    }

    /**
     * Tests a 1x1 minefield with no mine
     * @throws IOException if the test name failed to evaluate
     */
    @Test
    public void testTinyMinefieldWithoutMine() throws IOException {
        test("TinyFieldWithoutMine");
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
