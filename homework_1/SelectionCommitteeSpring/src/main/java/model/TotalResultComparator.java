package model;


import java.util.Comparator;

/**
 * Comparator for comparing by total result.
 * From bigger to lower value.
 *
 * @author Vladislav Prokopenko
 */
public class TotalResultComparator implements Comparator<UserFinalStatementResult> {
    @Override
    public int compare(UserFinalStatementResult o1, UserFinalStatementResult o2) {
        return -o1.getTotalResult() + o2.getTotalResult();
    }
}
