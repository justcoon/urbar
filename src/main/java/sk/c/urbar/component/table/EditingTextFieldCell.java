package sk.c.urbar.component.table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
 */
public class EditingTextFieldCell<S, T> extends TableCell<S, T> {

    protected Class<T> type;
    private TextField textField;

    public EditingTextFieldCell(Class<T> type) {
        this.type = type;
    }

    protected Class<T> getType() {
        return type;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getStringValue(getItem()));
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean arg1, Boolean arg2) {
                if (!arg2) {
                    commitEdit(getTypeValue(textField.getText()));
                }
            }
        });
    }

    /**
     * get string value
     *
     * @param value
     * @return
     */
    protected String getStringValue(T value) {
        return value == null ? "" : value.toString();
    }

    /**
     * get type value
     *
     * @param value
     * @return
     * @see #getType()
     */
    protected T getTypeValue(String value) {
        return (T) ConvertUtils.convert(value, getType());
    }

    /**
     * get string value
     *
     * @return
     * @see #getStringValue(Object)
     */
    private String getString() {
        return getStringValue(getItem());
    }
}
