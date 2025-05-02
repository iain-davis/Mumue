package org.mumue.mumue.database;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.mockito.ArgumentMatcher;

public class MyVarargMatcher implements ArgumentMatcher<Object[]> {
    public static MyVarargMatcher myVarargMatcher(Object... expectedValues) {
        return new MyVarargMatcher(expectedValues);
    }

    private final Object[] expectedValues;

    private MyVarargMatcher(Object... expectedValues) {
        this.expectedValues = expectedValues;
    }

    @Override
    public boolean matches(Object... argument) {
        return new EqualsBuilder().append(expectedValues, argument).isEquals();
    }
}
