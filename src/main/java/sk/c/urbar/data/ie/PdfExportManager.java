package sk.c.urbar.data.ie;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sk.c.urbar.data.PersonController;
import sk.c.urbar.data.entity.Person;
import sk.c.urbar.settings.SettingProperty;
import sk.c.urbar.settings.SettingsManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author coon
 */
public class PdfExportManager implements IImportExportManager {

    /**
     * singelton instance
     */
    private static final PdfExportManager INSTANCE = new PdfExportManager();
    /**
     * logger
     */
    private Log log = LogFactory.getLog(PdfExportManager.class);

    private PdfExportManager() {


    }

    /**
     * get {@link sk.c.urbar.data.IStoreManager} instance
     *
     * @return
     */
    public static IImportExportManager getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean save(File file, IExporter<?> exporter) {

        boolean retVal = false;

        if (file != null) {
            log.debug("save export file " + file.getName());

            FileOutputStream fw = null;

            try {
                fw = new FileOutputStream(file);

                setValues(exporter, fw);

                retVal = true;

            } catch (Exception e) {
                log.error("save export file " + file.getName() + " error:", e);
            }


            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                log.error("fw close error:", e);
            }

        }
        return retVal;
    }

    /**
     * get values from controllers
     *
     * @return
     */
    private List<Person> getControllerValues() {

        return PersonController.getInstance().getValues();
    }

    /**
     * load controllers
     *
     * @param values value for controllers
     * @return
     */
    private boolean loadController(List<Person> values) {

        boolean retVal = false;

        if (values != null) {

            PersonController.getInstance().getValues().addAll(values);

            retVal = true;

        }

        return retVal;
    }

    @Override
    public boolean load(File file, IImporter<?> importer) {

        throw new UnsupportedOperationException("Not supported");
    }

    /**
     * set values to writer
     *
     * @param writer
     * @throws Exception
     */
    protected void setValues(IExporter<?> exporter, FileOutputStream writer) throws Exception {

        if (exporter != null && writer != null) {

            String encoding = SettingsManager.getInstance().getValue(SettingProperty.ENCODING);
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, encoding, BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont);

            List<String> properties = exporter.getProperties();
            Document document = new Document(PageSize.A4);

            // step 2
            PdfWriter.getInstance(document, writer);
            // step 3
            document.open();
            PdfPTable table = new PdfPTable(properties.size());

            table.setFooterRows(1);
            table.setWidthPercentage(100f);

            table.getDefaultCell().setColspan(1);


            table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

            for (String p : properties) {

                table.addCell(new Phrase(p, font));

            }

//            table.setHeaderRows(1);
            table.getDefaultCell().setBackgroundColor(null);

            List<List<String>> values = ((IExporter<String>) exporter).getValues();

            String pValue;

            for (List<String> value : values) {

                for (String pv : value) {

                    pValue = pv;
                   
                    if (pValue == null) {
                        pValue = "";
                    }
                    table.addCell(new Phrase(pValue, font));
                }
            }

            document.add(table);
            document.close();

        }
    }

}
