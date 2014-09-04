package org.deletethis.exp.db.oracle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import junit.framework.Assert;
import org.deletethis.exp.db.printer.PoliteAppendableImpl;
import org.junit.Test;

/**
 *
 * @author miko
 */
public class OracleListeralPrinterTest {
    private void testEscape(String raw, String escaped) throws UnsupportedEncodingException, IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(buffer, true, "UTF-8");
        
        OracleLiteralPrinter olp = new OracleLiteralPrinter();
        OraclePrinter op = new OraclePrinter(new PoliteAppendableImpl(ps), 1000);
        
        olp.printEscapedString(op, raw);
        Assert.assertEquals(escaped, buffer.toString("UTF-8"));
        
    }
    
    @Test
    public void aaa() throws UnsupportedEncodingException, IOException {
        testEscape("hello", "'hello'");
        testEscape("hello&", "'hello'||chr(38)");
        testEscape("&hello", "chr(38)||'hello'");
        testEscape("he&ll", "'he'||chr(38)||'ll'");
        testEscape("he&&ll", "'he'||chr(38)||chr(38)||'ll'");
        testEscape("&&l", "chr(38)||chr(38)||'l'");
    }
}
