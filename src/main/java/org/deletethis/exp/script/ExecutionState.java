package org.deletethis.exp.script;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import org.deletethis.exp.ConsoleDebugOut;
import org.deletethis.exp.DebugOut;
import org.deletethis.exp.db.dest.DestinationSql;
import org.deletethis.exp.db.printer.PoliteAppendableBase;
import org.deletethis.exp.db.source.SourceSql;

/**
 *
 * @author miko
 */
public class ExecutionState extends PoliteAppendableBase {
    private final WritableByteChannel out;
    private Charset charset = Charset.forName("UTF-8");
    private SourceSql sourceSql;
    private DestinationSql destinationSql;
    private final DebugOut debugOut = new ConsoleDebugOut();

    public SourceSql getSourceSql() {
        if(sourceSql == null)
            throw new IllegalStateException("not connected to database");
        
        return sourceSql;
    }

    public void setSourceSql(SourceSql sourceSql) {
        this.sourceSql = sourceSql;
    }

    public DestinationSql getDestinationSql() {
        return destinationSql;
    }

    public void setDestinationSql(DestinationSql destinationSql) {
        this.destinationSql = destinationSql;
    }
    
    public void setCharset(String name)
    {
        charset = Charset.forName(name);
    }
    
    public DebugOut getDebug() {
        return debugOut;
    }
    
    public ExecutionState(OutputStream out) {
        this.out = Channels.newChannel(out);
    }

    @Override
    protected void doAppend(char c) {
        char tmp [] = { c };
        ByteBuffer encoded = charset.encode(CharBuffer.wrap(tmp));
        try {
            out.write(encoded);
        } catch(IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    protected void doAppend(CharSequence csq, int start, int end) {
        ByteBuffer encoded = charset.encode(CharBuffer.wrap(csq, start, end));
        try {
            out.write(encoded);
        } catch(IOException ex) {
            throw new IllegalStateException(ex);
        }    }

    @Override
    public ExecutionState newline() {
        doAppend('\n');
        return this;
    }
}
