/*
    Summer 2025
    testing class for the static class HintGenerator
 */

package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Testing class for the minesweeper hint generator.
 *
 * @author Roman Bureacov
 * @version July 2025
 */
public final class HintGeneratorTest {
    String mySampleField;
    String myExpectedField;

    @Test
    public void testMinefield1() {
        mySampleField =
                """
                4 5
                .....
                .....
                *....
                ...*.
                0 0
                """;
        myExpectedField =
                """
                Field #1:
                00000
                11000
                *1111
                111*1
                
                """;

        test();
    }

    private void test() {
        // redirect input and output
        final ByteArrayInputStream input = new ByteArrayInputStream(mySampleField.getBytes());
        System.setIn(input);
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // get output
        HintGenerator.main();

        // normalize newlines
        final String expectedOut = output.toString().replaceAll("\r", "");
        myExpectedField = myExpectedField.replaceAll("\r", "");

        assertEquals(
                myExpectedField,
                expectedOut,
                "Unequal minefields"
        );
    }
}
