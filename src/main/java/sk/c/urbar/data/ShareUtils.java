package sk.c.urbar.data;

import sk.c.urbar.data.entity.Rate;
import sk.c.urbar.data.entity.Share;

import java.util.Collection;

/**
 * {@link sk.c.urbar.data.entity.Share} utils
 *
 * @author coon
 */
public class ShareUtils {

    private ShareUtils() {
    }

    /**
     * get votes sum for {@link sk.c.urbar.data.entity.Share} collection
     *
     * @param shares
     * @param rates
     * @return
     * @see sk.c.urbar.data.RateUtils#getVotes(Integer, java.util.Collection)
     */
    public static Integer getVotesSum(Collection<Share> shares, Collection<Rate> rates) {
        Integer retVal = null;

        if (shares != null && rates != null) {

            int votesSum = 0;

            Integer v;
            for (Share s : shares) {

                if (s.getCustom()) {
                    v = s.getVotes();
                } else {
                    v = RateUtils.getVotes(s.getPart(), rates);
                }
                if (v != null) {
                    votesSum += v.intValue();
                }
            }
            retVal = votesSum;
        }

        return retVal;
    }
}
