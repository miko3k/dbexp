package org.deletethis.exp.db.source;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author miko
 */
public interface SourceValue {
    boolean isNull();
    String getString();
    Date getDate();
    BigDecimal getNumber();
    byte [] getBlob();
}
