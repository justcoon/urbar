package sk.c.urbar.data.ie;

import java.util.List;

/**
 * importer
 */
public interface IImporter<T> {


    public void setProperties(List<String> properties);

    public void addValue(List<? extends Object> value);

    public List<T> getValues() throws Exception;
}
