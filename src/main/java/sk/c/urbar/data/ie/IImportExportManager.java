package sk.c.urbar.data.ie;

import java.io.File;

/**
 * import/export manager
 *
 * @author coon
 */
public interface IImportExportManager {


    /**
     * save data to file
     *
     * @param file
     * @return
     */
    public boolean save(File file, IExporter<?> exporter);

    /**
     * load data from file
     *
     * @param file
     * @return
     */
    public boolean load(File file, IImporter<?> importer);
}
