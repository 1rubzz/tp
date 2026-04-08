package seedu.address.model;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.statistics.DepartmentStatisticsCalculator;
import seedu.address.model.statistics.RoleStatisticsCalculator;
import seedu.address.model.statistics.StatisticsCalculator;
import seedu.address.model.statistics.StatisticsComputation;
import seedu.address.model.statistics.TagStatisticsCalculator;

/**
 * Represents statistics calculated from employee records.
 * This is a data container with no UI logic.
 *
 * <p>Statistics are computed based on a selected mode (TAG, DEPARTMENT, or ROLE)
 * and include metrics such as total employees, unique values, most common value,
 * and value distribution.
 */
public class Statistics {

    private static final Logger logger = LogsCenter.getLogger(Statistics.class);

    private final StatisticsMode statisticsMode;
    private final int totalEmployees;
    private final int uniqueValueCount;
    private final String mostCommonValue;
    private final int employeesWithValue;
    private final int employeesWithoutValue;
    private final String valueDistribution;

    /**
     * Creates a Statistics object by calculating from a list of persons in tag mode.
     *
     * @param persons The list of persons to calculate statistics from
     */
    public Statistics(List<Person> persons) {
        this(persons, StatisticsMode.TAG);
    }

    /**
     * Creates a Statistics object by calculating from a list of persons with the selected mode.
     *
     * @param persons The list of persons to calculate statistics from
     * @param statisticsMode The mode to calculate statistics for
     */
    public Statistics(List<Person> persons, StatisticsMode statisticsMode) {
        assert persons != null : "Person list cannot be null";
        assert statisticsMode != null : "Statistics mode cannot be null";

        this.statisticsMode = statisticsMode;
        this.totalEmployees = persons.size();
        logger.fine("Calculating " + statisticsMode.getFullName() + " statistics for "
                + totalEmployees + " employees");

        StatisticsComputation computation = getCalculatorForMode(statisticsMode).compute(persons);
        this.employeesWithValue = computation.getEmployeesWithValue();
        this.employeesWithoutValue = computation.getEmployeesWithoutValue();
        this.uniqueValueCount = computation.getUniqueValueCount();
        this.mostCommonValue = computation.getMostCommonValue();
        this.valueDistribution = computation.getDistribution();

        logger.fine("Statistics calculated in " + statisticsMode.getFullName() + " mode: "
                + uniqueValueCount + " unique values, " + employeesWithValue + " employees with values");
    }

    private StatisticsCalculator getCalculatorForMode(StatisticsMode mode) {
        Objects.requireNonNull(mode);
        return switch (mode) {
        case TAG -> new TagStatisticsCalculator();
        case DEPARTMENT -> new DepartmentStatisticsCalculator();
        case ROLE -> new RoleStatisticsCalculator();
        default -> throw new IllegalArgumentException("Unsupported mode: " + mode);
        };
    }

    /**
     * Returns the statistics mode used for calculation.
     *
     * @return The current {@link StatisticsMode} (TAG, DEPARTMENT, or ROLE).
     */
    public StatisticsMode getStatisticsMode() {
        return statisticsMode;
    }

    /**
     * Returns the total number of employees in the dataset.
     *
     * @return The total employee count.
     */
    public int getTotalEmployees() {
        return totalEmployees;
    }

    /**
     * Returns the number of unique values in the dataset for the current mode.
     *
     * <p>For example:
     * <ul>
     *   <li>In TAG mode: number of unique tags across all employees</li>
     *   <li>In DEPARTMENT mode: number of unique departments</li>
     *   <li>In ROLE mode: number of unique roles</li>
     * </ul>
     *
     * @return The count of unique values.
     */
    public int getUniqueValueCount() {
        return uniqueValueCount;
    }

    /**
     * Returns the most frequently occurring value in the dataset for the current mode.
     *
     * <p>If multiple values have the same highest frequency, the one ordered first
     * alphabetically (case-insensitive) is returned.
     *
     * @return The most common tag, department, or role as a string.
     */
    public String getMostCommonValue() {
        return mostCommonValue;
    }

    /**
     * Returns the number of employees that have at least one value for the current mode.
     *
     * <p>For example:
     * <ul>
     *   <li>In TAG mode: employees with at least one tag</li>
     *   <li>In DEPARTMENT mode: all employees (always equals totalEmployees)</li>
     *   <li>In ROLE mode: all employees (always equals totalEmployees)</li>
     * </ul>
     *
     * @return The count of employees with at least one value.
     */
    public int getEmployeesWithValue() {
        return employeesWithValue;
    }

    /**
     * Returns the number of employees that have no value for the current mode.
     *
     * <p>For example:
     * <ul>
     *   <li>In TAG mode: employees with no tags</li>
     *   <li>In DEPARTMENT mode: always 0 (department is required)</li>
     *   <li>In ROLE mode: always 0 (role is required)</li>
     * </ul>
     *
     * @return The count of employees with no value.
     */
    public int getEmployeesWithoutValue() {
        return employeesWithoutValue;
    }

    /**
     * Returns a formatted string representing the distribution of values for the current mode.
     *
     * <p>The distribution is formatted with each value on a new line, followed by its count
     * and percentage. Values are sorted by count (highest first), then alphabetically for ties.
     *
     * @return A formatted distribution string.
     */
    public String getValueDistribution() {
        return valueDistribution;
    }
}
