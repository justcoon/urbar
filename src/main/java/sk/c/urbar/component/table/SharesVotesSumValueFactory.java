package sk.c.urbar.component.table;

import sk.c.urbar.data.RateController;
import sk.c.urbar.data.ShareUtils;
import sk.c.urbar.data.entity.Share;

import java.util.Collection;

/**
 * @author coon
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

//                for (Share s : v) {
//                    if (s.getVotes() != null) {
//                        votes += s.getVotes();
//                    }
//                }

            retVal = String.valueOf(votes);
        }

        return retVal;
    }

}
