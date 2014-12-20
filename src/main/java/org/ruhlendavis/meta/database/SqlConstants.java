package org.ruhlendavis.meta.database;

public interface SqlConstants {
    String DRIVER_CLASS_NAME = "org.h2.Driver";

    String CHECK_CONFIGURATION_TABLE_EXISTENCE = "select count(*) from information_schema.tables where table_name = 'CONFIGURATION_OPTIONS'";
    String SCHEMA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/schema.sql'";
    String DEFAULT_DATA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/defaultData.sql'";
    String QUERY_TEXT = "select text from text where locale = ? and name = ?";
    String TEXT_COLUMN = "text";
}
