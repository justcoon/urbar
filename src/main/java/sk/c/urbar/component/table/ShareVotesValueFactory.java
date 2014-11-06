package sk.c.urbar.component.table;

import sk.c.urbar.data.RateController;
import sk.c.urbar.data.RateUtils;

/**
 * @author coon
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
