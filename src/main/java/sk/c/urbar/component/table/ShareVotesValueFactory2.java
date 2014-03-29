package sk.c.urbar.component.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import sk.c.urbar.data.RateController;
import sk.c.urbar.data.RateUtils;
import sk.c.urbar.data.entity.Share;

/**
 * Created with IntelliJ IDEA.
 * User: coon
 * Date: 11/25/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShareVotesValueFactory2 implements Callback<TableColumn.CellDataFeatures<Object, String>, ObservableValue<String>> {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Object, String> objectStringCellDataFeatures) {


        String retVal = null;

        Object data = objectStringCellDataFeatures.getValue();

        retVal = getValue(data);

        return new ReadOnlyObjectWrapper<String>(retVal);
    }


    protected String getValue(Object data) {
        String retVal = null;

        if (data instanceof Share) {
            Share share = (Share) data;

            int votes = 0;

            if (share.getCustom()) {

                if(share.getVotes() != null) {
                    votes = share.getVotes();
                }

            } else {

                Integer rVotes = RateUtils.getVotes(share.getPart(), RateController.getInstance().getValues());

                if (rVotes != null) {
                    votes = rVotes.intValue();
                }


            }
            retVal = String.valueOf(votes);
        }

        return retVal;
    }

}
