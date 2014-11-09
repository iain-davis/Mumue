package org.ruhlendavis.meta.datastore;

public interface SqlConstants {
    public static final String CHECK_CONFIGURATION_TABLE_EXISTENCE = "select count(*) from information_schema.tables where table_name = 'CONFIGURATION_OPTIONS'";
    public static final String SCHEMA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/schema.sql'";
    public static final String DEFAULT_DATA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/defaultData.sql'";
}
