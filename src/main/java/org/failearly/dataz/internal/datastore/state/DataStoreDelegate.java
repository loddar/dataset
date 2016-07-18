/*
 * Copyright (c) 2009.
 *
 * Date: 16.05.16
 * 
 */
package org.failearly.dataz.internal.datastore.state;

import org.failearly.common.proputils.PropertiesAccessor;
import org.failearly.dataz.datastore.DataStore;
import org.failearly.dataz.datastore.DataStoreException;
import org.failearly.dataz.NamedDataStore;
import org.failearly.dataz.resource.DataResource;

import java.util.Objects;

/**
 * DataStoreDelegate is responsible for ...
 */
abstract class DataStoreDelegate implements DataStore {
    private DataStore origin;

    DataStoreDelegate(DataStore dataStore) {
        Objects.requireNonNull(dataStore, "DataStore must not be null.");
        if (dataStore instanceof DataStoreDelegate) {
            this.origin = ((DataStoreDelegate) dataStore).originDataStore();
        }
        else {
            this.origin = dataStore;
        }
        Objects.requireNonNull(this.origin, "DataStore must not be null.");
    }

    protected final DataStore originDataStore() {
        return origin;
    }

    @Override
    public final Class<? extends NamedDataStore> getNamedDataStore() {
        return origin.getNamedDataStore();
    }

    @Override
    public String getId() {
        return origin.getId();
    }

    @Override
    public String getName() {
        return origin.getName();
    }

    @Override
    public String getConfigFile() {
        return origin.getConfigFile();
    }

    @Override
    public void initialize() throws DataStoreException {
        origin.initialize();
    }

    @Override
    public PropertiesAccessor getProperties() {
        return origin.getProperties();
    }

    @Override
    public boolean hasTransactionalSupport() {
        return origin.hasTransactionalSupport();
    }

    @Override
    public void applyDataResource(DataResource dataResource) {
        origin.applyDataResource(dataResource);
    }

    @Override
    public void dispose() {
        doDispose();
    }

    protected final void doDispose() {
        if (origin == null) {
            throw new IllegalStateException("Called dispose twice!");
        }
        origin.dispose();
        origin = null;
    }
}
