package org.deletethis.exp.db.printer;

import org.deletethis.exp.db.oracle.OraclePrinter;
import java.io.IOException;

/**
 *
 * @author miko
 */
public class Main {
    public static final void main(String [] args) throws IOException {
        OraclePrinter p = new OraclePrinter(new PoliteAppendableImpl(System.out), 20);
        p.printAtoms("intert", "into", "schema", ".", "user");
        p.indent();
        p.newline();
        p.printAtoms("(", "col1", ",", "col2", ")", "values", "(");
        p.printQuoted("hello world");
        p.printQuoted("test string1 test string2 test string3 test string4 test string5");
        p.printAtomsLn(",", "1000", ")", ";");
        p.newline();
    }
}
