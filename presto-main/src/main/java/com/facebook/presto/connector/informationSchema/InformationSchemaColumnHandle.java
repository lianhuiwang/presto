/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.connector.informationSchema;

import com.facebook.presto.spi.ColumnHandle;
import com.facebook.presto.spi.ColumnMetadata;
import com.facebook.presto.spi.TableMetadata;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class InformationSchemaColumnHandle
        implements ColumnHandle
{
    private final String columnName;

    @JsonCreator
    public InformationSchemaColumnHandle(@JsonProperty("columnName") String columnName)
    {
        this.columnName = columnName;
    }

    @JsonProperty
    public String getColumnName()
    {
        return columnName;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(columnName);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final InformationSchemaColumnHandle other = (InformationSchemaColumnHandle) obj;
        return Objects.equal(this.columnName, other.columnName);
    }

    @Override
    public String toString()
    {
        return "information_schema:" + columnName;
    }

    public static Map<String, ColumnHandle> toInformationSchemaColumnHandles(TableMetadata tableMetadata)
    {
        ImmutableMap.Builder<String, ColumnHandle> columnHandles = ImmutableMap.builder();
        for (ColumnMetadata columnMetadata : tableMetadata.getColumns()) {
            columnHandles.put(columnMetadata.getName(), new InformationSchemaColumnHandle(columnMetadata.getName()));
        }

        return columnHandles.build();
    }
}
