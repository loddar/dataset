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
package org.failearly.dataset.internal.template;

import org.failearly.dataset.template.TemplateObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * TemplateObjects holds all resolved (see {@link TemplateObjectsResolver}) from a test method or test class.
 */
public final class TemplateObjects {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateObjects.class);

    private final List<TemplateObjectCreator> templateObjectCreators;

    TemplateObjects() {
        this(new LinkedList<>());
    }

    private TemplateObjects(List<TemplateObjectCreator> templateObjectCreators) {
        this.templateObjectCreators=templateObjectCreators;
    }

    /**
     * Add a template object creator instance. Called by {@link TemplateObjectsResolver}.
     *
     * @param templateObjectCreator a new instance.
     */
    void add(TemplateObjectCreator templateObjectCreator) {
        this.templateObjectCreators.add(templateObjectCreator);
    }

    /**
     * Collects the annotation instances.
     *
     * @return associated annotations
     */
    public List<Annotation> collectAnnotations() {
        return templateObjectCreators.stream().map(TemplateObjectCreator::getAnnotation).collect(Collectors.toList());
    }

    /**
     * Filter the template objects which belongs to given data set.
     * @param dataSetName the name of the data set.
     * @return filtered instance of template objects.
     */
    public TemplateObjects filterByDataSet(String dataSetName) {
        return new TemplateObjects(doFilterByDataSetName(dataSetName));
    }

    /**
     * Applies all (newly created) template objects and send them to {@code templateObjectConsumer}. If two template objects have the same {@code name},
     * the second will be ignored.
     *
     * @param templateObjectConsumer a consumer function
     *
     * @see TemplateObject#name()
     */
    public void apply(Consumer<TemplateObject> templateObjectConsumer) {
        final Set<String> templateObjectNames=new HashSet<>();
        for (TemplateObjectCreator templateObjectCreator : templateObjectCreators) {
            final TemplateObject templateObject = templateObjectCreator.createTemplateObjectInstance();
            if( templateObjectNames.add(templateObject.name()) ) {
                LOGGER.debug("Use template object '{}' (annotation={})", templateObject.name(), templateObjectCreator.getAnnotation());
                templateObjectConsumer.accept(templateObject);
            }
            else {
                LOGGER.warn("Template object '{}' already defined (annotation={}). Ignored!", templateObject.name(), templateObjectCreator.getAnnotation());
            }
        }
    }


    private List<TemplateObjectCreator> doFilterByDataSetName(String dataSetName) {
        return templateObjectCreators.stream()  //
                    .filter((templateObjectCreator -> dataSetName.equals(templateObjectCreator.getDataSetName()))) //
                    .collect(Collectors.toList());
    }
}