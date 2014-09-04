package org.deletethis.exp.db.printer;

/**
 *
 * @author miko
 */
public interface PoliteAppendable extends Appendable {

    @Override
    PoliteAppendable append(char c);

    @Override
    PoliteAppendable append(CharSequence csq);
    
    PoliteAppendable appendln(CharSequence csq);

    @Override
    PoliteAppendable append(CharSequence csq, int start, int end);
    
    PoliteAppendable newline();
}
