package sk.c.urbar.component.table;

import sk.c.urbar.data.RateController;
import sk.c.urbar.data.RateUtils;

/**
 * Created with IntelliJ IDEA.
 * User: coon
 * Date: 11/25/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShareVotesValueFactory extends PropertyValueFactory<Integer> {


    @Override
    protected String getStringValue(Integer value) {
        String retVal = null;
        if (value != null) {
            int votes = 0;

            Integer rVotes = RateUtils.getVotes(value, RateController.getInstance().getValues());

            if (rVotes != null) {
                votes = rVotes.intValue();
            }

            retVal = String.valueOf(votes);
        }

        return retVal;
    }

}
