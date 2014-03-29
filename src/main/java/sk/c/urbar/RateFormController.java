package sk.c.urbar;

import javafx.beans.value.WritableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sk.c.urbar.component.SimpleObjectValue;
import sk.c.urbar.component.validator.IValidationResult;
import sk.c.urbar.component.validator.RegexTypeValidator;
import sk.c.urbar.component.validator.ValidatorState;
import sk.c.urbar.component.validator.ValidatorUtils;
import sk.c.urbar.data.RateController;
import sk.c.urbar.data.entity.Rate;
import sk.c.urbar.scene.ISceneController;
import sk.c.urbar.scene.history.IContentHandler;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;


/**
 * rate form controller
 *
 * @author coon
 */
public class RateFormController implements Initializable, ISceneController<Rate> {
    /**
     * logger
     */
    private Log log = LogFactory.getLog(RateFormController.class);
    private ResourceBundle resourceBundle;
    private IContentHandler handler;
    @FXML
    private javafx.scene.control.TableView tableView;
    @FXML
    private TextField part;
    @FXML
    private TextField votes;
    /**
     * rate data
     */
    private WritableValue<Rate> rate;
    /**
     * edit or add ?
     */
    private boolean edit = false;
    private boolean okClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;

        setValue(new SimpleObjectValue<Rate>(new Rate()), false);
    }

    private Collection<IValidationResult> validate() {
        Collection<IValidationResult> retVal = new HashSet<IValidationResult>();

        IValidationResult r = new RegexTypeValidator<String>("part", true, "[0-9]*", resourceBundle).validate(part.getText());

        if (r != null) {
            retVal.add(r);
        }

        r = new RegexTypeValidator<String>("votes", true, "[0-9]*", resourceBundle).validate(votes.getText());

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

                RateController.getInstance().getValues().add(rate.getValue());
            }
            try {
                Main.getInstance().getContentHandler().change("ratelist.fxml", null);

            } catch (Exception e) {
                log.error("handleOk - change content error:", e);
            }

//            try {
//                handler.back();
//
//            } catch (Exception e) {
//               log.error("handleOk - change content error:", e);
//            }
        } else {

            String m = ValidatorUtils.getMessage(validationResults, "\n", ValidatorState.ERROR, ValidatorState.WARNING);
            if (m != null) {
//                Dialogs.create().masthead(m).actions(Dialog.Actions.OK).showWarning();

                log.debug("handleOk - validation message " + m);
                javafx.scene.control.Dialogs.showErrorDialog(new Dialog().getStage(), m, resourceBundle.getString("warning"), resourceBundle.getString("warning"));
            }
        }
    }

    /**
     * set value from components
     */
    protected void setValueFromComponents() {
        rate.getValue().setPart(Integer.valueOf(part.getText()));
        rate.getValue().setVotes(Integer.valueOf(votes.getText()));

    }

    /**
     * set value to components
     *
     * @param rate
     */
    protected void setValueToComponents(Rate rate) {

        part.setText(rate != null && rate.getPart() != null ? rate.getPart().toString() : "");
        votes.setText(rate != null && rate.getVotes() != null ? rate.getVotes().toString() : "");

    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        try {
            Main.getInstance().getContentHandler().change("ratelist.fxml", null);

        } catch (Exception e) {
            log.error("handleCancel - change content error:", e);
        }
//        try {
//
//            handler.back();
//
//        } catch (Exception e) {
//          log.error("handleCancel - change content error:", e);
//        }
    }

    @Override
    public WritableValue<Rate> getValue() {
        return rate;
    }

    @Override
    public void setValue(WritableValue<Rate> value) {

        setValue(value, true);
    }

    protected void setValue(WritableValue<Rate> value, boolean edit) {

        if (value == null) {
            throw new IllegalArgumentException("Missing required value");
        }

        this.rate = value;
        this.edit = edit;
        setValueToComponents(value.getValue());
    }

    @Override
    public boolean isChanged() {
        return okClicked;
    }

    @Override
    public void setContentHandler(IContentHandler handler) {
        this.handler = handler;
    }

    @Override
    public String getName() {
        return resourceBundle.getString("rate");
    }
}
