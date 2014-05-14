package sk.c.urbar.data;

import java.io.File;

/**
 * store manager
 *
 * @author coon
 */
public interface IStoreManager {

    /**
     * save data to file
     *
     * @param file
     * @return
     */
    public boolean save(File file);

    /**
     * load data from file
     *
     * @param file
     * @return
     */
    public boolean load(File file);
}
