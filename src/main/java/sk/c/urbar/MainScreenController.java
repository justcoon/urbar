package sk.c.urbar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sk.c.urbar.data.PersonController;
import sk.c.urbar.data.StoreManager;
import sk.c.urbar.data.entity.Person;
import sk.c.urbar.data.entity.PersonExporter;
import sk.c.urbar.data.entity.PersonImporter;
import sk.c.urbar.data.ie.CsvImportExportManager;
import sk.c.urbar.data.ie.PdfExportManager;
import sk.c.urbar.settings.SettingProperty;
import sk.c.urbar.settings.SettingsManager;

import javax.swing.event.ChangeEvent;
import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * main screen controller
 *
 * @author coon
 */
public class MainScreenController implements Initializable {
    ResourceBundle resourceBundle;
    @FXML
    MenuItem save;
    @FXML
    MenuItem info;
    @FXML
    BorderPane root;
    /**
     * logger
     */
    private Log log = LogFactory.getLog(MainScreenController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.resourceBundle = resourceBundle;
        resetSave();
    }

    public void onOpen(ActionEvent event) {

        try {

            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(resourceBundle.getString("dataFileDescription"), resourceBundle.getString("dataFileExtension"));
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(Main.getInstance().getStage());


            if (file != null) {

                if (StoreManager.getInstance().load(file)) {
                    fireLoaded();
                }

            }
        } catch (Exception e) {
            log.error("onOpen error:", e);
        }
    }

    public void onSave(ActionEvent event) {

        try {


            String fileName = SettingsManager.getInstance().getValue(SettingProperty.DATA_FILE_NAME);

            File file = fileName != null ? new File(fileName) : null;

            if (file != null && file.exists()) {


                if (StoreManager.getInstance().save(file)) {
                    fireSaved();
                } else {
                    String m = MessageFormat.format(resourceBundle.getString("saveError"), fileName);

                    javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), m, resourceBundle.getString("error"), resourceBundle.getString("error"));
                }
            }


        } catch (Exception e) {
            log.error("onSave error:", e);
        }
    }

    public void onSaveAs(ActionEvent event) {

        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(resourceBundle.getString("dataFileDescription"), resourceBundle.getString("dataFileExtension"));
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(Main.getInstance().getStage());

            if (file != null) {


                if (StoreManager.getInstance().save(file)) {
                    fireSaved();
                } else {
                    String m = MessageFormat.format(resourceBundle.getString("saveError"), file.getPath());

                    javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), m, resourceBundle.getString("error"), resourceBundle.getString("error"));
                }
            }


        } catch (Exception e) {
            log.error("onSaveAs error:", e);
        }
    }

    public void onExit(ActionEvent event) {
        try {

            // save  data before exit
            String fileName = SettingsManager.getInstance().getValue(SettingProperty.DATA_FILE_NAME);

            File file = fileName != null ? new File(fileName) : null;

            if (file != null && file.exists()) {
                String m = MessageFormat.format(resourceBundle.getString("saveQuestion"), fileName);
                Dialogs.DialogResponse dr = javafx.scene.control.Dialogs.showConfirmDialog(new Dialog().getStage(), m, resourceBundle.getString("confirmation"), resourceBundle.getString("confirmation"), Dialogs.DialogOptions.OK_CANCEL);

                if (dr == javafx.scene.control.Dialogs.DialogResponse.OK) {


                    if (!StoreManager.getInstance().save(file)) {
                        m = MessageFormat.format(resourceBundle.getString("saveError"), fileName);

                        javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), m, resourceBundle.getString("error"), resourceBundle.getString("error"));
                    }

                }
            }

            //save settings
            SettingsManager.getInstance().saveSettings();

            Main.getInstance().getStage().close();
            Main.getInstance().stop();
        } catch (Exception e) {
            log.error("onExit error:", e);
        }
    }

    protected void fireLoaded() {
        resetSave();
    }

    protected void fireSaved() {
        resetSave();
        resetInfo();
    }

    protected boolean existDataFile() {
        String fileName = SettingsManager.getInstance().getValue(SettingProperty.DATA_FILE_NAME);

        File file = fileName != null ? new File(fileName) : null;

        return file != null && file.exists();
    }

    protected void resetSave() {

        save.setDisable(!existDataFile());
    }

    protected void resetInfo() {
        save.setDisable(!existDataFile());
    }

    public void onPersons(ActionEvent event) {

        try {


            Main.getInstance().getContentHandler().change("personlist.fxml", null);

        } catch (Exception e) {
            log.error("onPersons error:", e);
        }
    }

    public void onRates(ActionEvent event) {

        try {


            Main.getInstance().getContentHandler().change("ratelist.fxml", null);

        } catch (Exception e) {
            log.error("onRates error:", e);
        }
    }

    public void onExportCSV(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(resourceBundle.getString("importExportFileDescription"), resourceBundle.getString("importExportFileExtension"));
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(Main.getInstance().getStage());

            if (file != null) {


                CsvImportExportManager.getInstance().save(file, new PersonExporter(PersonController.getInstance().getValues()));


            }


        } catch (Exception e) {
            log.error("onExportCSV error:", e);
        }
    }

    public void onExportPDF(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(resourceBundle.getString("importExportPDFFileDescription"), resourceBundle.getString("importExportPDFFileExtension"));
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(Main.getInstance().getStage());

            if (file != null) {


                PdfExportManager.getInstance().save(file, new PersonExporter(Arrays.asList("firstName", "surName", "address","identification", "birthDate", "part", "votes"), PersonController.getInstance().getValues()) {
                    @Override
                    public List<String> getProperties() {

                        List<String> retVal = null;
                        List<String> properties = getObjectProperties();

                        if (properties != null) {
                            retVal = new ArrayList<>(properties.size());
                            for (String p : properties) {
                                retVal.add(resourceBundle.getString(p));
                            }
                        }

                        return retVal;
                    }
                });


            }


        } catch (Exception e) {
            log.error("onExportPDF error:", e);
        }
    }

    public void onImportCSV(ActionEvent event) {
        try {

            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(resourceBundle.getString("importExportFileDescription"), resourceBundle.getString("importExportFileExtension"));
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(Main.getInstance().getStage());


            if (file != null) {

                PersonImporter importer = new PersonImporter();

                if (CsvImportExportManager.getInstance().load(file, importer)) {

                    List<Person> values = importer.getValues();

                    PersonController.getInstance().getValues().addAll(values);
                    Main.getInstance().getEventBus().post(new ChangeEvent(PersonController.class));
                }


            }
        } catch (Exception e) {
            log.error("onImportCSV error:", e);
        }
    }

    public void onAbout(ActionEvent event) {
        try {

            String about = MessageFormat.format(resourceBundle.getString("aboutApp"), Main.VERSION);

            javafx.scene.control.Dialogs.showInformationDialog(new Dialog().getStage(), about, resourceBundle.getString("about"), resourceBundle.getString("about"));


        } catch (Exception e) {
            log.error("onAbout error:", e);
        }
    }

    public void onInfo(ActionEvent event) {
        try {

            String fn = SettingsManager.getInstance().getValue(SettingProperty.DATA_FILE_NAME);

            if (fn == null) {
                fn = "";
            }

            javafx.scene.control.Dialogs.showInformationDialog(new Dialog().getStage(), fn, resourceBundle.getString("info"), resourceBundle.getString("info"));


        } catch (Exception e) {
            log.error("onInfo error:", e);
        }
    }
}
