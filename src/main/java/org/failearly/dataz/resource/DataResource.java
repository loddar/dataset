/*
 * dataZ - Test Support For Data Stores.
 *
 * Copyright (C) 2014-2016 'Marko Umek' (http://fail-early.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.failearly.dataz.resource;

import org.failearly.dataz.AdhocDataStore;
import org.failearly.dataz.exception.DataSetException;
import org.failearly.dataz.internal.resource.DataResourceProcessingException;
import org.failearly.dataz.internal.template.TemplateObjects;

import java.io.InputStream;

/**
 * DataSetResource represents either a {@link org.failearly.dataz.DataSet#setup()} or {@link org.failearly.dataz.DataSet#cleanup()}
 * resource.
 * <p>
 * The resource could be checked for existence and opened as {@link java.io.InputStream}.
 */
public interface DataResource {
    /**
     * If the data resource is template based resource, here the target data resource will be generated.
     *
     * @param templateObjects all template objects available.
     * @throws DataResourceProcessingException in case of processing issues while processing the template.
     */
    void generate(TemplateObjects templateObjects) throws DataResourceProcessingException;

    /**
     * @return The data store id.
     * @see org.failearly.dataz.DataSet#datastore()
     * @see AdhocDataStore#id()
     */
    String getDataStoreId();

    /**
     * @return the data set name.
     * @see org.failearly.dataz.DataSet#name()
     * @see org.failearly.dataz.DataStoreSetup#name()
     */
    String getDataSetName();

    /**
     * A single entry of {@link org.failearly.dataz.DataSet#setup()} or {@link org.failearly.dataz.DataSet#cleanup()}.
     *
     * @return the resource name.
     * @see org.failearly.dataz.DataSet#setup()
     * @see org.failearly.dataz.DataSet#cleanup()
     */
    String getResource();

    /**
     * @return {@code true} if the resource should be handled within one transaction.
     * @see org.failearly.dataz.DataSet#transactional()
     */
    boolean isTransactional();

    /**
     * Return an additional value stored under {@code key} or {@code defaultValue}.
     *
     * @param key          the key or name of the additional value
     * @param defaultValue the value to be returned if there is no value stored for given {@code key}
     * @param <T>          the expected type.
     * @return the additional value or {@code defaultValue}.
     */
    <T> T getAdditionalValue(String key, T defaultValue);

    /**
     * Open's the resource as stream.
     *
     * @return the input stream.
     * @throws DataSetException in case of any exception, while open the resource.
     */
    InputStream open() throws DataSetException;

    /**
     * @return {@code true} if the DataStore should fail.
     */
    boolean isFailOnError();
}