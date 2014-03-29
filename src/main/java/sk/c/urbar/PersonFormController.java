package sk.c.urbar;

import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sk.c.urbar.component.SimpleObjectValue;
import sk.c.urbar.component.input.RestrictiveTextField;
import sk.c.urbar.component.table.EditingCheckBoxCell;
import sk.c.urbar.component.table.EditingTextFieldCell;
import sk.c.urbar.component.validator.*;
import sk.c.urbar.data.PersonController;
import sk.c.urbar.data.RateController;
import sk.c.urbar.data.ShareUtils;
import sk.c.urbar.data.entity.Person;
import sk.c.urbar.data.entity.Share;
import sk.c.urbar.scene.ISceneController;
import sk.c.urbar.scene.history.IContentHandler;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * person form controller
 *
 * @author coon
 */
public class PersonFormController implements Initializable, ISceneController<Person> {
    /**
     * logger
     */
    private Log log = LogFactory.getLog(PersonFormController.class);
    private ResourceBundle resourceBundle;
    private SimpleDateFormat dateFormat;
    @FXML
    private javafx.scene.control.TableView sharesTableView;
    @FXML
    private TableColumn sharePart;
    @FXML
    private TableColumn shareVotes;
    @FXML
    private TableColumn shareCustom;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField surNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField identificationField;
    @FXML
    private TextField bankAccountField;
    @FXML
    private TextArea documentField;
    @FXML
    private TextArea noticeField;
    @FXML
    private RestrictiveTextField birthDateField;
    @FXML
    private RestrictiveTextField registeredFromField;
    @FXML
    private RestrictiveTextField registeredToField;
    //    @FXML
//    private DatePicker birthDateField;
//    @FXML
//    private DatePicker registeredFromField;
//    @FXML
//    private DatePicker registeredToField;
    @FXML
    private Label votesSum;
    @FXML
    private CheckBox activeField;
    /**
     * person data
     */
    private WritableValue<Person> person;
    /**
     * edit or add ?
     */
    private boolean edit = false;
    private boolean okClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        dateFormat = new SimpleDateFormat(resourceBundle.getString("dateFormat"));
        sharesTableView.setItems(FXCollections.observableArrayList());
        sharesTableView.setEditable(true);
        sharePart.setEditable(true);
        javafx.util.Callback<TableColumn, TableCell> cellFactory =
                new javafx.util.Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingTextFieldCell(Integer.class);
                    }
                };

        sharePart.setCellFactory(cellFactory);
        sharePart.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Share, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Share, Integer> t) {
                ((Share) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPart(t.getNewValue());

                //refresh vote columns
                t.getTableView().getColumns().get(1).setVisible(false);
                t.getTableView().getColumns().get(1).setVisible(true);


                fireSharesChanged();
            }
        });

        cellFactory =
                new javafx.util.Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingTextFieldCell(Integer.class);
                    }
                };

        shareVotes.setCellFactory(cellFactory);
        shareVotes.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Share, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Share, Integer> t) {
                ((Share) t.getTableView().getItems().get(t.getTablePosition().getRow())).setVotes(t.getNewValue());

                fireSharesChanged();
            }
        });

        cellFactory =
                new javafx.util.Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCheckBoxCell(Boolean.class);
                    }
                };
        shareCustom.setCellFactory(cellFactory);
        shareCustom.setEditable(true);
        shareCustom.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Share, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Share, Boolean> t) {
                ((Share) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustom(t.getNewValue());

                //refresh vote columns
                t.getTableView().getColumns().get(1).setVisible(false);
                t.getTableView().getColumns().get(1).setVisible(true);


                fireSharesChanged();
            }
        });


        Person p = new Person();
        Share s = new Share();
        s.setPart(0);
        p.getShares().add(s);
        setValue(new SimpleObjectValue<Person>(p), false);
    }

    private Collection<IValidationResult> validate() {
        Collection<IValidationResult> retVal = new HashSet<IValidationResult>();

        IValidationResult r;

        r = new MinMaxLengthTypeValidator<String>("firstName", 1, null, resourceBundle).validate(firstNameField.getText());

        if (r != null) {
            retVal.add(r);
        }

        r = new MinMaxLengthTypeValidator<String>("surName", 1, null, resourceBundle).validate(surNameField.getText());

        if (r != null) {
            retVal.add(r);
        }

//        r = new RegexTypeValidator<String>("birthDate", false, resourceBundle.getString("dateFormatRegex"), resourceBundle).validate(birthDateField.getText());
        r = new DateFormatValidator<String>("birthDate", false, dateFormat, resourceBundle).validate(birthDateField.getText());

        if (r != null) {
            retVal.add(r);
        }

//        r = new RegexTypeValidator<String>("registeredFrom", true, resourceBundle.getString("dateFormatRegex"), resourceBundle).validate(registeredFromField.getText());
        r = new DateFormatValidator<String>("registeredFrom", true, dateFormat, resourceBundle).validate(registeredFromField.getText());

        if (r != null) {
            retVal.add(r);
        }

//        r = new RegexTypeValidator<String>("registeredTo", false, resourceBundle.getString("dateFormatRegex"), resourceBundle).validate(registeredToField.getText());

        r = new DateFormatValidator<String>("registeredTo", false, dateFormat, resourceBundle).validate(registeredToField.getText());

        if (r != null) {
            retVal.add(r);
        }

        r = new RegexTypeValidator<String>("email", false, resourceBundle.getString("emailFormatRegex"), resourceBundle).validate(emailField.getText());

        if (r != null) {
            retVal.add(r);
        }


        return retVal;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        Collection<IValidationResult> validationResults = validate();

        if (!ValidatorUtils.containsStates(validationResults, ValidatorState.ERROR, ValidatorState.WARNING)) {

            setValueFromComponents();

            okClicked = true;

            if (!edit) {

                PersonController.getInstance().getValues().add(person.getValue());
            }
            try {
                Main.getInstance().getContentHandler().change("personlist.fxml", null);

            } catch (Exception e) {
                log.error("handleOk - change content error:", e);
            }

        } else {

            String m = ValidatorUtils.getMessage(validationResults, "\n", ValidatorState.ERROR, ValidatorState.WARNING);
            if (m != null) {

                log.debug("handleOk - validation message " + m);
//                Dialogs.create().masthead(m).actions(Dialog.Actions.OK).showWarning();

                javafx.scene.control.Dialogs.showErrorDialog(new Dialog().getStage(), m, resourceBundle.getString("warning"), resourceBundle.getString("warning"));
            }

        }
    }

    /**
     * set value from components
     */
    protected void setValueFromComponents() {
        person.getValue().setFirstName(firstNameField.getText());
        person.getValue().setSurName(surNameField.getText());
        person.getValue().setAddress(addressField.getText());
        person.getValue().setEmail(emailField.getText());
        person.getValue().setBankAccount(bankAccountField.getText());

        person.getValue().setIdentification(identificationField.getText());
        person.getValue().setBirthDate(Utils.getDate(birthDateField.getText(), dateFormat));
        person.getValue().setRegisteredFrom(Utils.getDate(registeredFromField.getText(), dateFormat));
        person.getValue().setRegisteredTo(Utils.getDate(registeredToField.getText(), dateFormat));

//        ComponentUtils.setDateToValue(birthDateField, person.getValue(), "birthDate");
//        ComponentUtils.setDateToValue(registeredFromField, person.getValue(), "registeredFrom");
//        ComponentUtils.setDateToValue(registeredToField, person.getValue(), "registeredTo");

        person.getValue().setDocument(documentField.getText());
        person.getValue().setNotice(noticeField.getText());
        person.getValue().setActive(activeField.isSelected());

        person.getValue().getShares().clear();

        person.getValue().getShares().addAll(sharesTableView.getItems());

    }

    /**
     * set value to components
     *
     * @param person
     */
    protected void setValueToComponents(Person person) {

        firstNameField.setText(person.getFirstName());
        surNameField.setText(person.getSurName());
        addressField.setText(person.getAddress());
        bankAccountField.setText(person.getBankAccount());
        emailField.setText(person.getEmail());

        identificationField.setText(person.getIdentification());
        birthDateField.setText(Utils.getDateString(person.getBirthDate(), dateFormat));
        registeredFromField.setText(Utils.getDateString(person.getRegisteredFrom(), dateFormat));
        registeredToField.setText(Utils.getDateString(person.getRegisteredTo(), dateFormat));
//        ComponentUtils.setDateToPicker(birthDateField, person, "birthDate");
//        ComponentUtils.setDateToPicker(registeredFromField, person, "registeredFrom");
//        ComponentUtils.setDateToPicker(registeredToField, person, "registeredTo");

        documentField.setText(person.getDocument());
        noticeField.setText(person.getNotice());
        activeField.setSelected(person.getActive());

        sharesTableView.getItems().clear();
        if (person.getShares() != null) {
            sharesTableView.getItems().addAll(person.getShares());
        }

        fireSharesChanged();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {

        try {
            Main.getInstance().getContentHandler().change("personlist.fxml", null);

        } catch (Exception e) {
            log.error("handleCancel - change content error:", e);
        }
    }

    @FXML
    protected void addShare(ActionEvent event) {
        try {

            Share s = new Share();
            s.setPart(0);
            sharesTableView.getItems().add(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void deleteShare(ActionEvent event) {
        int selected = sharesTableView.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            try {
//                Action response = response = Dialogs.create().masthead(resourceBundle.getString("itemDeleteConfirmation")).actions(Dialog.Actions.YES, Dialog.Actions.NO).showWarning();
//
//                if (response == Dialog.Actions.YES) {
                Dialogs.DialogResponse dr = javafx.scene.control.Dialogs.showConfirmDialog(new Dialog().getStage(), resourceBundle.getString("itemDeleteConfirmation"), resourceBundle.getString("confirmation"), resourceBundle.getString("confirmation"), Dialogs.DialogOptions.OK_CANCEL);

                if (dr == javafx.scene.control.Dialogs.DialogResponse.OK) {
                    sharesTableView.getItems().remove(selected);

                    fireSharesChanged();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            Dialogs.create().masthead(resourceBundle.getString("itemNotSelected")).showWarning();

            javafx.scene.control.Dialogs.showWarningDialog(new Dialog().getStage(), resourceBundle.getString("itemNotSelected"), resourceBundle.getString("warning"), resourceBundle.getString("warning"));
        }
    }

    @Override
    public WritableValue<Person> getValue() {
        return person;
    }

    @Override
    public void setValue(WritableValue<Person> value) {

        setValue(value, true);
    }

    protected void setValue(WritableValue<Person> value, boolean edit) {

        if (value == null) {
            throw new IllegalArgumentException("Missing required value");
        }
        this.person = value;
        this.edit = edit;
        setValueToComponents(value.getValue());
    }

    @Override
    public boolean isChanged() {
        return okClicked;
    }

    @Override
    public void setContentHandler(IContentHandler handler) {

    }

    @Override
    public String getName() {
        return resourceBundle.getString("person");
    }

    protected void fireSharesChanged() {

        int votes = 0;

        Integer vs = ShareUtils.getVotesSum(((Collection<Share>) sharesTableView.getItems()), RateController.getInstance().getValues());

        if (vs != null) {
            votes = vs.intValue();
        }

        votesSum.setText(String.valueOf(votes));
    }

}
