package sk.c.urbar;

import com.google.common.eventbus.Subscribe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sk.c.urbar.component.SimpleObjectValue;
import sk.c.urbar.data.PersonController;
import sk.c.urbar.data.entity.Person;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * person list controller
 *
 * @author coon
 */
public class PersonListController implements Initializable {

    ResourceBundle resourceBundle;
    @FXML
    javafx.scene.control.TableView tableView;
    @FXML
    TextField searchField;
//    @FXML
//    TableColumn activeColumn;
    @FXML
    CheckBox activeField;
    ObservableList<Person> data;
    SimpleDateFormat dateFormat;
    /**
     * logger
     */
    private Log log = LogFactory.getLog(PersonListController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        dateFormat = new SimpleDateFormat(resourceBundle.getString("dateFormat"));
        data = FXCollections.observableArrayList();
//        data.addAll(PersonController.getInstance().getValues());
        tableView.setItems(data);
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                updateFilteredData();
            }
        });
        activeField.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                updateFilteredData();
            }
        });


//        javafx.util.Callback<TableColumn, TableCell> cellFactory =
//                new
//
//                        javafx.util.Callback<TableColumn, TableCell>() {
//                            public TableCell call(TableColumn p) {
//                                return new CheckBoxTableCell();
//                            }
//                        };
//
//        activeColumn.setCellFactory(cellFactory);
        updateFilteredData();

    }

    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    private void updateFilteredData() {

        List<Person> values = new ArrayList<Person>();
        values.addAll(PersonController.getInstance().getValues());

//        Predicate p = new PersonSearchPredicate(searchField.getText());
        Predicate p = PredicateUtils.andPredicate(new PersonSearchPredicate(searchField.getText()), new PersonActivePredicate(activeField.isSelected()));
        CollectionUtils.filter(values, p);
        data.clear();
        data.addAll(values);

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private void reapplyTableSortOrder() {
        List<TableColumn<Person, ?>> sortOrder = new ArrayList<javafx.scene.control.TableColumn<Person, ?>>(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
    }

    @FXML
    protected void addPerson(ActionEvent event) {
        try {

            Main.getInstance().getContentHandler().change("personform.fxml", null);
        } catch (Exception e) {
            log.error("addPerson - change content error:", e);
        }

    }

    @FXML
    protected void editPerson(ActionEvent event) {
        int selected = tableView.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            try {
                Person person = (Person) tableView.getItems().get(selected);

                Main.getInstance().getContentHandler().change("personform.fxml", new SimpleObjectValue<Person>(person));


            } catch (Exception e) {
                log.error("editPerson - change content error:", e);
            }
        } else {
//            Dialogs.create().masthead(resourceBundle.getString("itemNotSelected")).showWarning();

            javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), resourceBundle.getString("itemNotSelected"), resourceBundle.getString("warning"), resourceBundle.getString("warning"));
        }
    }

    @FXML
    protected void deletePerson(ActionEvent event) {
        int selected = tableView.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            try {
//                Action response = response = Dialogs.create().masthead(resourceBundle.getString("itemDeleteConfirmation")).actions(Dialog.Actions.YES, Dialog.Actions.NO).showWarning();
//
//                if (response == Dialog.Actions.YES) {

                Dialogs.DialogResponse dr = javafx.scene.control.Dialogs.showConfirmDialog(new Dialog().getStage(), resourceBundle.getString("itemDeleteConfirmation"), resourceBundle.getString("confirmation"), resourceBundle.getString("confirmation"), Dialogs.DialogOptions.OK_CANCEL);

                if (dr == javafx.scene.control.Dialogs.DialogResponse.OK) {

                    Person p = (Person) tableView.getItems().get(selected);
                    PersonController.getInstance().getValues().remove(p);

                    tableView.getItems().remove(selected);

                }

            } catch (Exception e) {
                log.error("deletePerson error:", e);
            }
        } else {
//            Dialogs.create().masthead(resourceBundle.getString("itemNotSelected")).showWarning();

            javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), resourceBundle.getString("itemNotSelected"), resourceBundle.getString("warning"), resourceBundle.getString("warning"));
        }
    }

    @Subscribe
    public void personsChanged(ChangeEvent event) {

        if (event != null && event.getSource().equals(PersonController.class)) {
            System.out.println("changed " + event.getSource());

            updateFilteredData();
        }
    }

    /**
     * search {@link org.apache.commons.collections.Predicate} for {@link Person}
     */
    protected static class PersonSearchPredicate implements Predicate {
        String searchExpression;
        boolean ignoreCase = true;
        boolean ignoreNull = true;

        PersonSearchPredicate(String searchExpression) {
            this.searchExpression = searchExpression;

        }

        @Override
        public boolean evaluate(Object o) {
            boolean retVal = false;
            if (o instanceof Person) {
                retVal = true;

                if (searchExpression != null) {
                    Person p = (Person) o;
                    if (!matches(searchExpression, p.getFirstName(), ignoreCase,
                            ignoreNull) && !matches(searchExpression, p.getSurName(), ignoreCase,
                            ignoreNull) && !matches(searchExpression, p.getAddress(), ignoreCase,
                            ignoreNull) && !matches(searchExpression, p.getIdentification(), ignoreCase,
                            ignoreNull)) {
                        retVal = false;
                    }
                }
            }
            return retVal;
        }

        protected boolean matches(String expression, Object value, boolean ignoreCase, boolean ignoreNull) {
            boolean retVal = false;
            if (value == null && ignoreNull) {
                retVal = true;
            } else {
                String v = value.toString();
                if (ignoreCase) {
                    retVal = org.apache.commons.lang.StringUtils.containsIgnoreCase(v, expression);
                } else {
                    retVal = org.apache.commons.lang.StringUtils.contains(v, expression);
                }
            }
            return retVal;
        }
    }

    /**
     * active {@link org.apache.commons.collections.Predicate} for {@link Person}
     */
    protected static class PersonActivePredicate implements Predicate {
        boolean activeOnly;

        PersonActivePredicate(boolean activeOnly) {
            this.activeOnly = activeOnly;
        }

        @Override
        public boolean evaluate(Object o) {
            boolean retVal = false;
            if (o instanceof Person) {
                boolean a = ((Person) o).getActive();

                if (!activeOnly || (activeOnly && a)) {
                    retVal = true;
                }

            }

            return retVal;
        }
    }
}
