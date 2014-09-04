package org.deletethis.exp.db.oracle;

import org.deletethis.exp.db.printer.PoliteAppendableImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author miko
 */
public class OraclePrinterTest {
    @Test
    public void test1() {
        StringBuilder sb = new StringBuilder();
        OraclePrinter p = new OraclePrinter(new PoliteAppendableImpl(sb), 20);
        p.printAtoms("insert", "into", "schema", ".", "user");
        p.indent();
        p.newline();
        p.printAtoms("(", "col1", ",", "col2", ")", "values", "(");
        p.printQuoted("hello world");
        p.printQuoted("test string1 test string2 test string3 test string4 test string5");
        p.printAtomsLn(",", "1000", ")", ";");
        p.newline();

        System.out.println(sb);
        
        Assert.assertEquals(
                "insert into schema.\n"+
                "user\n"+
                "  (col1, col2)values\n"+
                "  ('hello world''te'\n"+
                "  ||'st string1 tes'\n"+
                "  ||'t string2 test'\n"+
                "  ||' string3 test '\n"+
                "  ||'string4 test s'\n"+
                "  ||'tring5', 1000);\n\n",
                sb.toString());
    }
}
