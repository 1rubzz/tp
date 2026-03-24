package seedu.address.logic;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Statistics;
import seedu.address.model.person.Person;

/**
 * Service class that provides statistics about employees.
 * This follows the Service layer pattern to keep business logic separate from UI.
 */
public class StatisticsService {

    private static final Logger logger = LogsCenter.getLogger(StatisticsService.class);
    private final Logic logic;

    public StatisticsService(Logic logic) {
        assert logic != null : "Logic cannot be null";
        this.logic = logic;
        logger.info("StatisticsService initialized");
    }

    /**
     * Returns current statistics based on the filtered person list.
     */
    public Statistics getCurrentStatistics() {
        assert logic != null : "Logic is null in getCurrentStatistics";

        ObservableList<Person> observableList = logic.getFilteredPersonList();
        assert observableList != null : "Filtered person list cannot be null";

        logger.fine("Retrieving statistics for " + observableList.size() + " employees");

        // ObservableList is a List, so this works fine
        List<Person> personList = observableList;
        Statistics stats = new Statistics(personList);

        assert stats != null : "Statistics object should not be null";
        assert stats.getTotalEmployees() == personList.size() : "Total employees count mismatch";

        logger.info("Statistics calculated: " + stats.getTotalEmployees() + " employees, "
                + stats.getUniqueTagCount() + " unique tags");

        return stats;
    }
}
