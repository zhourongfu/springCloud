package com.weilus.commons.logging.resolver;

import ch.qos.logback.classic.db.names.DBNameResolver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by liutq on 2019/3/18.
 */
public class DateSuffixDBNameResolver implements DBNameResolver {

    private String tableNamePrefix = "";

    private String tableNameSuffix = LocalDate.now().format(DateTimeFormatter.ofPattern("_yyyyMMdd"));

    private String columnNamePrefix = "";

    private String columnNameSuffix = "";

    public <N extends Enum<?>> String getTableName(N tableName) {
        return tableNamePrefix + tableName.name().toLowerCase() + tableNameSuffix;
    }

    public <N extends Enum<?>> String getColumnName(N columnName) {
        return columnNamePrefix + columnName.name().toLowerCase() + columnNameSuffix;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix != null ? tableNamePrefix : "";
    }

    public void setTableNameSuffix(String tableNameSuffix) {
        this.tableNameSuffix = tableNameSuffix != null ? tableNameSuffix : "";
    }

    public void setColumnNamePrefix(String columnNamePrefix) {
        this.columnNamePrefix = columnNamePrefix != null ? columnNamePrefix : "";
    }

    public void setColumnNameSuffix(String columnNameSuffix) {
        this.columnNameSuffix = columnNameSuffix != null ? columnNameSuffix : "";
    }
}
