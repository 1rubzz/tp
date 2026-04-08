package seedu.address.model.statistics;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;

/**
 * Calculates statistics using person tags.
 */
public class TagStatisticsCalculator extends AbstractValueStatisticsCalculator {

    @Override
    protected Set<String> extractValues(Person person) {
        return person.getTags().stream()
                .map(tag -> {
                    assert tag != null : "Tag cannot be null";
                    assert tag.getTagName() != null : "Tag name cannot be null";
                    return tag.getTagName();
                })
                .collect(Collectors.toSet());
    }

    @Override
    protected String getEmptyDistributionMessage() {
        return "No tags yet";
    }
}

