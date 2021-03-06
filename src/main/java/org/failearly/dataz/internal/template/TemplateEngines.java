/*
 * dataZ - Test Support For Data Stores.
 *
 * Copyright 2014-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.failearly.dataz.internal.template;

import org.failearly.dataz.config.DataSetProperties;
import org.failearly.dataz.internal.common.classutils.ObjectCreatorUtil;
import org.failearly.dataz.template.engine.TemplateEngine;
import org.failearly.dataz.template.engine.TemplateEngineFactory;

/**
 * TemplateEngines provides a single factory method for TemplateEngines.
 */
public final class TemplateEngines {

    private TemplateEngines() {
    }

    /**
     * Creates a template engine using {@link DataSetProperties#getTemplateEngineFactoryClass()}.
     * @return a template engine.
     */
    public static TemplateEngine createTemplateEngine() {
        final TemplateEngineFactory factory=resolveTemplateEngineFactory();
        return factory.createTemplateEngine();
    }

    private static TemplateEngineFactory resolveTemplateEngineFactory() {
        final String factoryClass= DataSetProperties.getTemplateEngineFactoryClass();
        return ObjectCreatorUtil.createInstance(TemplateEngineFactory.class, factoryClass);
    }
}
