import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author KaylinKerrick
 *
 */
public class GlossaryTest {
    /**
     * Creates a file used to test each method.
     *
     * @param text
     *            (terms and definitions) to include in the file
     * @param fileName
     *            of the new file
     * @requires the text string is properly formatted, fileName ends with txt
     * @ensures the test files are created with the desired text and file name
     */
    private void testFileCreator(String text, String fileName) {
        SimpleWriter out = new SimpleWriter1L(fileName);
        out.print(text);
        out.close();
    }

    /**
     * Test for the parseFile method. Only one term and definition.
     */
    @Test
    public void testParseFile_1() {
        String fileName = "glossaryTestFile1.txt";
        String text = "Term 1\nDefinition 1";

        int expectedGlossarySize = 1;
        String expectedDefinition = "Definition 1";

        this.testFileCreator(text, fileName);

        Map<String, String> glossary = Glossary.parseFile(fileName);

        int actualGlossarySize = glossary.size();
        String actualDefinition = glossary.get("Term 1");

        assertEquals(expectedGlossarySize, actualGlossarySize);
        assertEquals(expectedDefinition, actualDefinition);
    }

    /**
     * Test for the parseFile method. Two terms with definitions.
     */
    @Test
    public void testParseFile_2() {
        String fileName = "glossaryTestFile2.txt";
        String text = "Term 1\nDefinition 1\n\nTerm 2\nDefinition 2";

        int expectedGlossarySize = 2;
        String expectedDefinition1 = "Definition 1";
        String expectedDefinition2 = "Definition 2";

        this.testFileCreator(text, fileName);

        Map<String, String> glossary = Glossary.parseFile(fileName);

        int actualGlossarySize = glossary.size();
        String actualDefinition1 = glossary.get("Term 1");
        String actualDefinition2 = glossary.get("Term 2");

        assertEquals(expectedGlossarySize, actualGlossarySize);
        assertEquals(expectedDefinition1, actualDefinition1);
        assertEquals(expectedDefinition2, actualDefinition2);
    }

    /**
     * Test for the parseFile method. Only one term and a blank definition.
     */
    @Test
    public void testParseFile_0() {
        String fileName = "glossaryTestFile0.txt";
        String text = "Term 1\n"; //There is no definition (blank line)

        int expectedGlossarySize = 1;
        String expectedDefinition = "";

        this.testFileCreator(text, fileName);

        Map<String, String> glossary = Glossary.parseFile(fileName);

        int actualGlossarySize = glossary.size();
        String actualDefinition = glossary.get("Term 1");

        assertEquals(expectedGlossarySize, actualGlossarySize);
        assertEquals(expectedDefinition, actualDefinition);
    }

}
