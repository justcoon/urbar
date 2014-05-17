package sk.c.urbar.data.ie;

import java.util.List;

/**
 * importer
 * @author coon
 */
public interface IImporter<T> {


    /**
     * set properties names
     * @param properties
     */
    public void setProperties(List<String> properties);

    /**
     * add value
     *
     * @param value
     */
    public void addValue(List<? extends Object> value);

    /**
     * get imported values
     *
     * @return
     * @throws Exception
     */
    public List<T> getValues() throws Exception;
}
