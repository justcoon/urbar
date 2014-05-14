package sk.c.urbar;

import com.google.common.eventbus.Subscribe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sk.c.urbar.component.SimpleObjectValue;
import sk.c.urbar.data.RateController;
import sk.c.urbar.data.entity.Rate;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Rate list controller
 *
 * @author coon
 */
public class RateListController implements Initializable {
    ResourceBundle resourceBundle;
    @FXML
    javafx.scene.control.TableView tableView;
    //    @FXML
//    TextField searchField;
    ObservableList<Rate> data;
    /**
     * logger
     */
    private Log log = LogFactory.getLog(RateListController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        data = FXCollections.observableArrayList();
        data.addAll(RateController.getInstance().getValues());
        tableView.setItems(data);

//        searchField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable,
//                                String oldValue, String newValue) {
//
//                updateFilteredData();
//            }
//        });
    }

    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    private void updateFilteredData() {

        List<Rate> values = new ArrayList<Rate>();
        values.addAll(RateController.getInstance().getValues());
//        CollectionUtils.filter(values, new RateSearchPredicate(searchField.getText()));

        data.clear();
        data.addAll(values);

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private void reapplyTableSortOrder() {
        List<TableColumn<Rate, ?>> sortOrder = new ArrayList<TableColumn<Rate, ?>>(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
    }

    @FXML
    protected void addRate(ActionEvent event) {
        try {

            Main.getInstance().getContentHandler().change("rateform.fxml", null);
        } catch (Exception e) {
            log.error("addRate - change content error:", e);
        }

    }

    @FXML
    protected void editRate(ActionEvent event) {
        int selected = tableView.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            try {
                Rate Rate = (Rate) tableView.getItems().get(selected);

                Main.getInstance().getContentHandler().change("rateform.fxml", new SimpleObjectValue<Rate>(Rate));


            } catch (Exception e) {
                log.error("editRate - change content error:", e);
            }
        } else {
//            Dialogs.create().masthead(resourceBundle.getString("itemNotSelected")).showWarning();
            javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), resourceBundle.getString("itemNotSelected"), resourceBundle.getString("warning"), resourceBundle.getString("warning"));
        }
    }

    @FXML
    protected void deleteRate(ActionEvent event) {
        int selected = tableView.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            try {
//                Action response = response = Dialogs.create().masthead(resourceBundle.getString("itemDeleteConfirmation")).actions(Dialog.Actions.YES, Dialog.Actions.NO).showWarning();
//
//                if (response == Dialog.Actions.YES) {


                Dialogs.DialogResponse dr = javafx.scene.control.Dialogs.showConfirmDialog(new Dialog().getStage(), resourceBundle.getString("itemDeleteConfirmation"), resourceBundle.getString("confirmation"), resourceBundle.getString("confirmation"), Dialogs.DialogOptions.OK_CANCEL);

                if (dr == Dialogs.DialogResponse.OK) {

                    Rate p = (Rate) tableView.getItems().get(selected);
                    RateController.getInstance().getValues().remove(p);

                    tableView.getItems().remove(selected);

                }

            } catch (Exception e) {
                log.error("deleteRate error:", e);
            }
        } else {
//            Dialogs.create().masthead(resourceBundle.getString("itemNotSelected")).showWarning();

            javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), resourceBundle.getString("itemNotSelected"), resourceBundle.getString("warning"), resourceBundle.getString("warning"));
        }
    }

    @Subscribe
    public void ratesChanged(ChangeEvent event) {


        if (event != null && event.getSource().equals(RateController.class)) {
            System.out.println("changed " + event.getSource());

            data.clear();
            data.addAll(RateController.getInstance().getValues());
        }
    }

}
