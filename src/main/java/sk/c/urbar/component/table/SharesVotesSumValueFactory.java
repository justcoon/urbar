package sk.c.urbar.component.table;

import sk.c.urbar.data.RateController;
import sk.c.urbar.data.ShareUtils;
import sk.c.urbar.data.entity.Share;

import java.util.Collection;

/**
 * shares vote sum {@link sk.c.urbar.component.table.PropertyValueFactory}
 *
 * @author coon
 *
 * @see sk.c.urbar.data.ShareUtils#getVotesSum(java.util.Collection, java.util.Collection)
 */
public class SharesVotesSumValueFactory extends PropertyValueFactory<Collection<Share>> {
    @Override
    protected String getStringValue(Collection<Share> value) {
        String retVal = null;
        if (value != null) {
            int votes = 0;


            Integer votesSum = ShareUtils.getVotesSum(value, RateController.getInstance().getValues());

            if (votesSum != null) {
                votes = votesSum.intValue();
            }
            retVal = String.valueOf(votes);
        }

        return retVal;
    }

}
