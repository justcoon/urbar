package sk.c.urbar.component.table;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author coon
 */
public class FormatedPropertyDateValueFactory extends PropertyValueFactory<java.util.Date> {

    String format;
    SimpleDateFormat dateFormat;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    protected String getStringValue(Date value) {
        String retVal = null;
        if (value != null) {
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat(format);

            }

            retVal = dateFormat.format(value);
        }

        return retVal;
    }
}
