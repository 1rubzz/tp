package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Statistics;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class StatisticsServiceIntegrationTest {

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Storage storage;
    private Logic logic;
    private StatisticsService statisticsService;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
        statisticsService = new StatisticsService(logic);
    }

    @Test
    public void getCurrentStatistics_returnsValidStats() {
        Statistics stats = statisticsService.getCurrentStatistics();

        assertNotNull(stats);
        assertEquals(model.getFilteredPersonList().size(), stats.getTotalEmployees());
    }

    @Test
    public void getCurrentStatistics_afterAddingEmployee_updatesStats() throws Exception {
        // Get initial count
        int initialCount = statisticsService.getCurrentStatistics().getTotalEmployees();

        // Add an employee
        String addCommand = "add n/IntegrationTest p/12345678 e/integration@example.com r/Employee t/TestTag";
        logic.execute(addCommand);

        // Verify stats updated
        Statistics updatedStats = statisticsService.getCurrentStatistics();
        assertEquals(initialCount + 1, updatedStats.getTotalEmployees());
    }

    @Test
    public void getCurrentStatistics_afterDeletingEmployee_updatesStats() throws Exception {
        // Add an employee first
        String addCommand = "add n/ToDeleteIntegration p/87654321 e/todelete@example.com r/Employee t/DeleteTag";
        logic.execute(addCommand);

        // Get count after add
        int countAfterAdd = statisticsService.getCurrentStatistics().getTotalEmployees();

        // Delete the employee (last one)
        String deleteCommand = "delete " + countAfterAdd;
        logic.execute(deleteCommand);

        // Verify stats updated
        Statistics statsAfterDelete = statisticsService.getCurrentStatistics();
        assertEquals(countAfterAdd - 1, statsAfterDelete.getTotalEmployees());
    }
}