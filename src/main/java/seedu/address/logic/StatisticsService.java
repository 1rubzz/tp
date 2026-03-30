package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Statistics;
import seedu.address.model.StatisticsMode;
import seedu.address.model.person.Person;

/**
 * Service class that provides statistics about employees.
 * This follows the Service layer pattern to keep business logic separate from UI.
 */
public class StatisticsService {

    public static final int MAX_DEPARTMENT_LIST_SIZE = 30;

    private static final Logger logger = LogsCenter.getLogger(StatisticsService.class);
    private final Logic logic;

    /**
     * Creates a StatisticsService with the given Logic component.
     *
     * @param logic The Logic component used to access employee data
     * @throws NullPointerException if logic is null
     */
    public StatisticsService(Logic logic) {
        requireNonNull(logic);
        this.logic = logic;
        logger.info("StatisticsService initialized");
    }

    /**
     * Returns current statistics based on the filtered person list.
     */
    public Statistics getCurrentStatistics() {
        return getCurrentStatistics(StatisticsMode.DEPARTMENT);
    }

    /**
     * Returns current statistics based on the filtered person list and selected dashboard mode.
     */
    public Statistics getCurrentStatistics(StatisticsMode statisticsMode) {
        requireNonNull(statisticsMode);
        logger.fine("Getting current statistics");
        ObservableList<Person> persons = logic.getFilteredPersonList();
        return new Statistics(persons, statisticsMode);
    }

    /**
     * Returns up to {@value #MAX_DEPARTMENT_LIST_SIZE} unique departments from the full address book.
     */
    public List<String> getCurrentDepartments() {
        logger.fine("Getting current department list for sidebar");
        return logic.getAddressBook().getPersonList().stream()
                .map(person -> person.getDepartment().value)
                .distinct()
                .sorted(Comparator.comparing(name -> name.toLowerCase(Locale.ROOT)))
                .limit(MAX_DEPARTMENT_LIST_SIZE)
                .collect(Collectors.toList());
    }
}
