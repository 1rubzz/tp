package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.StatisticsService;
import seedu.address.model.Statistics;
import seedu.address.model.StatisticsMode;
import seedu.address.model.person.Person;

/**
 * Panel that displays statistics about employee records.
 * Only responsible for UI display - statistics calculation is delegated.
 */
public class StatsPanel extends UiPart<Region> {

    private static final String FXML = "StatsPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(StatsPanel.class);
    private static final String ACTIVE_LEGEND_STYLE = "-fx-text-fill: #2e7d32; -fx-font-weight: bold;";
    private static final String INACTIVE_LEGEND_STYLE = "-fx-text-fill: #808080; -fx-font-weight: normal;";
    private static final String DEPARTMENT_LIST_EMPTY_MESSAGE = "No departments available.";

    @FXML
    private Label panelTitleLabel;

    @FXML
    private HBox dashboardLegendRow;

    @FXML
    private Label tagLegendLabel;

    @FXML
    private Label deptLegendLabel;

    @FXML
    private Region topSeparator;

    @FXML
    private VBox statisticsSection;

    @FXML
    private Label uniqueMetricTextLabel;

    @FXML
    private Label mostCommonMetricTextLabel;

    @FXML
    private Label employeesWithMetricTextLabel;

    @FXML
    private Region employeesWithRow;

    @FXML
    private Label employeesWithoutMetricTextLabel;

    @FXML
    private Region employeesWithoutRow;

    @FXML
    private Region bottomSeparator;

    @FXML
    private Label distributionHeaderLabel;

    @FXML
    private Label totalEmployeesLabel;

    @FXML
    private Label uniqueTagsLabel;

    @FXML
    private Label mostCommonTagLabel;

    @FXML
    private Label employeesWithTagsLabel;

    @FXML
    private Label employeesWithoutTagsLabel;

    @FXML
    private Label tagDistributionLabel;

    @FXML
    private VBox departmentListSection;

    @FXML
    private Label departmentListLabel;

    private final StatisticsService statisticsService;
    private StatisticsMode currentMode;
    private boolean showDepartmentListView;

    /**
     * Creates a StatsPanel.
     *
     * @param logic The Logic component to get employee data from
     */
    public StatsPanel(Logic logic) {
        super(FXML);
        requireNonNull(logic);

        this.statisticsService = new StatisticsService(logic);
        this.currentMode = StatisticsMode.DEPARTMENT;
        this.showDepartmentListView = false;

        // Listen for changes to the person list.
        logic.getFilteredPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                logger.fine("Person list changed, refreshing statistics");
                refresh();
            }
        });

        logic.getAddressBook().getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                logger.fine("Address book changed, refreshing statistics");
                refresh();
            }
        });

        refresh();
        logger.info("StatsPanel initialized successfully");
    }

    /**
     * Sets the current dashboard mode and refreshes the panel.
     */
    public void setStatisticsMode(StatisticsMode statisticsMode) {
        requireNonNull(statisticsMode);
        if (currentMode == statisticsMode && !showDepartmentListView) {
            return;
        }

        logger.fine("Switching statistics dashboard mode to: " + statisticsMode.getFullName());
        showDepartmentListView = false;
        currentMode = statisticsMode;
        refresh();
    }

    /**
     * Shows the department-list override in the right panel.
     */
    public void showDepartmentList() {
        if (showDepartmentListView) {
            return;
        }

        showDepartmentListView = true;
        refresh();
    }

    /**
     * Shows the statistics dashboard view in the right panel.
     */
    public void showStatistics() {
        if (!showDepartmentListView) {
            return;
        }

        showDepartmentListView = false;
        refresh();
    }

    /**
     * Refreshes all statistics in the panel.
     */
    private void refresh() {
        logger.finer("Refreshing statistics panel");
        if (showDepartmentListView) {
            updateDepartmentListUi(statisticsService.getCurrentDepartments());
        } else {
            Statistics stats = statisticsService.getCurrentStatistics(currentMode);
            updateStatisticsUi(stats);
        }
        logger.finer("Statistics panel refresh completed");
    }

    /**
     * Updates UI with the given statistics.
     * This method only handles UI updates, no calculations.
     */
    private void updateStatisticsUi(Statistics stats) {
        requireNonNull(stats);

        showStatisticsLayout();
        updateModeLabels();

        totalEmployeesLabel.setText(String.valueOf(stats.getTotalEmployees()));
        uniqueTagsLabel.setText(String.valueOf(stats.getUniqueValueCount()));
        mostCommonTagLabel.setText(stats.getMostCommonValue());
        employeesWithTagsLabel.setText(String.valueOf(stats.getEmployeesWithValue()));
        employeesWithoutTagsLabel.setText(String.valueOf(stats.getEmployeesWithoutValue()));
        tagDistributionLabel.setText(stats.getValueDistribution());

        logger.finer("UI updated with: " + stats.getTotalEmployees() + " employees, "
                + stats.getUniqueValueCount() + " unique values in " + currentMode.getFullName() + " mode");
    }

    private void updateDepartmentListUi(List<String> departments) {
        requireNonNull(departments);
        showDepartmentListLayout();
        panelTitleLabel.setText("📋 Departments");
        departmentListLabel.setText(departments.isEmpty()
                ? DEPARTMENT_LIST_EMPTY_MESSAGE
                : String.join("\n", departments));
        logger.finer("Department list UI updated with " + departments.size() + " department(s)");
    }

    private void showStatisticsLayout() {
        panelTitleLabel.setText("📊 HR Statistics");
        dashboardLegendRow.setManaged(true);
        dashboardLegendRow.setVisible(true);
        topSeparator.setManaged(true);
        topSeparator.setVisible(true);
        statisticsSection.setManaged(true);
        statisticsSection.setVisible(true);
        bottomSeparator.setManaged(true);
        bottomSeparator.setVisible(true);
        distributionHeaderLabel.setManaged(true);
        distributionHeaderLabel.setVisible(true);
        tagDistributionLabel.setManaged(true);
        tagDistributionLabel.setVisible(true);
        departmentListSection.setManaged(false);
        departmentListSection.setVisible(false);
    }

    private void showDepartmentListLayout() {
        dashboardLegendRow.setManaged(false);
        dashboardLegendRow.setVisible(false);
        topSeparator.setManaged(false);
        topSeparator.setVisible(false);
        statisticsSection.setManaged(false);
        statisticsSection.setVisible(false);
        bottomSeparator.setManaged(false);
        bottomSeparator.setVisible(false);
        distributionHeaderLabel.setManaged(false);
        distributionHeaderLabel.setVisible(false);
        tagDistributionLabel.setManaged(false);
        tagDistributionLabel.setVisible(false);
        departmentListSection.setManaged(true);
        departmentListSection.setVisible(true);
    }

    private void updateModeLabels() {
        if (currentMode == StatisticsMode.TAG) {
            uniqueMetricTextLabel.setText("🏷️ Unique tags:");
            mostCommonMetricTextLabel.setText("📈 Most common tag:");
            employeesWithMetricTextLabel.setText("✅ Employees with tags:");
            employeesWithoutMetricTextLabel.setText("❌ Without tags:");
            distributionHeaderLabel.setText("📋 Tag Distribution (Top 5)");
            employeesWithRow.setManaged(true);
            employeesWithRow.setVisible(true);
            employeesWithoutRow.setManaged(true);
            employeesWithoutRow.setVisible(true);
            tagLegendLabel.setStyle(ACTIVE_LEGEND_STYLE);
            deptLegendLabel.setStyle(INACTIVE_LEGEND_STYLE);
        } else {
            uniqueMetricTextLabel.setText("🏢 Unique dept:");
            mostCommonMetricTextLabel.setText("📈 Most common dept:");
            distributionHeaderLabel.setText("📋 Dept Distribution (Top 5)");
            employeesWithRow.setManaged(false);
            employeesWithRow.setVisible(false);
            employeesWithoutRow.setManaged(false);
            employeesWithoutRow.setVisible(false);
            tagLegendLabel.setStyle(INACTIVE_LEGEND_STYLE);
            deptLegendLabel.setStyle(ACTIVE_LEGEND_STYLE);
        }
    }
}
