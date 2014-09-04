package org.deletethis.exp.db.printer;

/**
 *
 * @author miko
 */
public abstract class PoliteAppendableBase implements PoliteAppendable {
    abstract protected void doAppend(char c);

    abstract protected void doAppend(CharSequence seq, int start, int end);

    private void validateCharacter(char c) {
        if (c < 32 && c != '\t') {
            throw new IllegalArgumentException("control character may not be appended");
        }
    }

    @Override
    final public PoliteAppendable append(char c) {
        validateCharacter(c);
        doAppend(c);
        return this;
    }

    @Override
    final public PoliteAppendable append(CharSequence csq) {
        int len = csq.length();
        for (int i = 0; i < len; ++i) {
            validateCharacter(csq.charAt(i));
        }
        doAppend(csq, 0, len);
        return this;
    }
    
    @Override
    final public PoliteAppendable appendln(CharSequence csq) {
        PoliteAppendable res = append(csq);
        newline();
        return res;
    }

    @Override
    final public PoliteAppendable append(CharSequence csq, int start, int end) {
        for (int i = start; i < end; ++i) {
            validateCharacter(csq.charAt(i));
        }
        doAppend(csq, start, end);
        return this;
    }
}
