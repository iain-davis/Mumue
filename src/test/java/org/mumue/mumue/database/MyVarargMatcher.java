package org.mumue.mumue.database;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.mockito.ArgumentMatcher;
import org.mockito.internal.matchers.VarargMatcher;

public class MyVarargMatcher extends ArgumentMatcher<Object[]> implements VarargMatcher {
    public static MyVarargMatcher myVarargMatcher(Object... expectedValues) {
        return new MyVarargMatcher(expectedValues);
    }

    private Object[] expectedValues;

    private MyVarargMatcher(Object... expectedValues) {
        this.expectedValues = expectedValues;
    }

    @Override
    public boolean matches(Object varargArgument) {
        return new EqualsBuilder().append(expectedValues, varargArgument).isEquals();
    }
}
