package org.deletethis.exp.db.printer;

import java.io.IOException;

/**
 *
 * @author miko
 */
public class PoliteAppendableImpl extends PoliteAppendableBase {
    private final Appendable out;
    
    public PoliteAppendableImpl(Appendable out) {
        this.out = out;
    }

    @Override
    public PoliteAppendable newline() {
        try {
            out.append("\n");
            return this;
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    protected void doAppend(char c) {
        try {
            if(c == '\r')
                return;
            
            if(c == '\n')
                newline();
            
            out.append(c);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }    
        }

    @Override
    protected void doAppend(CharSequence seq, int start, int end) {
        for (int i = start; i < end; ++i) {
            doAppend(seq.charAt(i));
        }
    }
}
