package org.deletethis.exp.db.oracle;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.deletethis.exp.Slice;
import org.deletethis.exp.db.dest.DestinationSql;
import org.deletethis.exp.db.generic.DestinationSqlUtil;
import org.deletethis.exp.db.printer.PoliteAppendable;
import org.deletethis.exp.db.printer.Printer;
import org.deletethis.exp.db.printer.RecordingPrinter;
import org.deletethis.exp.db.source.ColumnSchema;
import org.deletethis.exp.db.source.SourceCursor;

/**
 *
 * @author miko
 */
public class OracleDestination implements DestinationSql {
    private String blobVar(String blob) { return (blob.toLowerCase() + "_blob").toUpperCase(); }

    private void printBlobInsert(Printer out, RecordingPrinter ins, OracleLiteralPrinter lp) {
        out.printAtomsLn("declare");
        out.indent();
        for(String key: lp.getBlobs().keySet()) {
            out.printAtomsLn(blobVar(key), "blob", ";");
        }
        out.unindent();

        out.printAtomsLn("begin");
        out.indent();
        ins.print(out);
        out.printAtoms("returning");

        boolean first = true;

        for(String key: lp.getBlobs().keySet()) {
            if(first) {
                first = false;
            } else {
                out.printAtoms(",");
            }
            out.printAtoms(key);
        }
        out.printAtoms("into");
        first = true;
        for(String key: lp.getBlobs().keySet()) {
            if(first) {
                first = false;
            } else {
                out.printAtoms(",");
            }
            out.printAtoms(blobVar(key));
        }
        out.printAtomsLn(";");

        for(Map.Entry<String, byte[]> e: lp.getBlobs().entrySet()) {
            byte [] val = e.getValue();
            for(Slice s: Slice.create(val.length, 1900)) {
                out.printAtoms("dbms_lob", ".", "append", "(");
                out.printAtoms(blobVar(e.getKey()));
                out.printAtoms(",", "hextoraw", "(");
                out.printQuoted(DatatypeConverter.printHexBinary(Arrays.copyOfRange(val, s.start, s.start+s.length)));
                out.printAtomsLn(")", ")", ";");
            }
        }
        out.unindent();
        out.printAtomsLn("end", ";");
        out.printAtomsLn("/");
    }

    private Printer createPrinter(PoliteAppendable out) {
        return new OraclePrinter(out, 126);
    }

    @Override
    public void printInsert(PoliteAppendable out, String table, List<ColumnSchema> columns, SourceCursor row) {
        RecordingPrinter ins = new RecordingPrinter();
        OracleLiteralPrinter lp = new OracleLiteralPrinter();
        lp.startRow();

        DestinationSqlUtil.printInsert(ins, lp, table, columns, row);

        Printer p = createPrinter(out);

        if(!lp.hasBlobs()) {
            ins.print(p);
            p.printAtoms(";");
            p.newline();
        } else {
            printBlobInsert(p, ins, lp);
        }
    }

    @Override
    public void setSequenceValue(PoliteAppendable out, String seqenceName, BigInteger value) {
        Printer p = createPrinter(out);

        p.printAtomsLn("declare");
        p.indent();
        p.printAtomsLn("diff", "number", ";");
        p.printAtomsLn("inc", "number", ";");
        p.printAtomsLn("dummy", "number", ";");
        p.unindent();
        p.printAtomsLn("begin");
        p.indent();
        p.printAtoms("select", value.toString(), "-", "last_number", ",", "increment_by", "into", "diff", ",", "inc",
                "from", "user_sequences", "where", "sequence_name" ,"=");
        p.printQuoted(seqenceName);
        p.printAtomsLn(";");
        p.printAtomsLn("if", "diff", "!=" ,"0", "then");
        p.indent();
        p.printAtoms("execute", "immediate");
        p.printQuoted("alter sequence " + seqenceName + " increment by ");
        p.printAtomsLn("||", "diff", ";");
        p.printAtomsLn("select", seqenceName, ".", "nextval", "into", "dummy", "from", "dual", ";");
        p.printAtoms("execute", "immediate");
        p.printQuoted("alter sequence " + seqenceName + " increment by ");
        p.printAtomsLn("||", "inc", ";");
        p.unindent();
        p.printAtomsLn("end", "if", ";");
        p.unindent();
        p.printAtomsLn("end", ";");
        p.printAtomsLn("/");

        /*
        out.appendln("declare");
        out.appendln("  diff number; inc number; dummy number;");
        out.appendln("begin");
        out.appendln("  select "+value+"-last_number, increment_by into diff, inc from user_sequences where sequence_name='"+seqenceName+"';");
        out.appendln("  if diff != 0 then");
        out.appendln("    execute immediate 'alter sequence "+seqenceName+" increment by ' || diff;");
        out.appendln("    select "+seqenceName+".nextval into dummy from dual;");
        out.appendln("    execute immediate 'alter sequence "+seqenceName+" increment by ' || inc;");
        out.appendln("  end if;");
        out.appendln("end;");
        out.appendln("/");*/
    }
}
