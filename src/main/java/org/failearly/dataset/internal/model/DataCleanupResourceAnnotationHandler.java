/*
 * dataSet - Test Support For Data Stores.
 *
 * Copyright (C) 2014-2015 Marko Umek (http://fail-early.com/contact)
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

package org.failearly.dataset.internal.model;

import org.failearly.dataset.annotations.DataCleanupResourceFactoryDefinition;
import org.failearly.dataset.internal.template.TemplateObjects;
import org.failearly.dataset.resource.DataResource;
import org.failearly.dataset.resource.DataResourcesFactory;
import org.failearly.dataset.util.ObjectCreator;

import java.util.List;

/**
 * DataSetupResourceAnnotationHandler creates an DataResourceFactory from meta annotation
 * {@link DataCleanupResourceFactoryDefinition}.
 */
    final class DataCleanupResourceAnnotationHandler extends DataResourceAnnotationHandlerBase<DataCleanupResourceFactoryDefinition> {

    DataCleanupResourceAnnotationHandler(List<DataResource> dataResourceList, TemplateObjects generatorCreators) {
        super(DataCleanupResourceFactoryDefinition.class, generatorCreators, dataResourceList);
    }

    @Override
    protected DataResourcesFactory createDataResourceFactory(DataCleanupResourceFactoryDefinition metaAnnotation) {
        return ObjectCreator.createInstance(metaAnnotation.factory());
    }
}
