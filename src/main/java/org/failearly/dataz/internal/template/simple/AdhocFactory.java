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

package org.failearly.dataz.internal.template.simple;

import org.failearly.dataz.internal.common.classutils.ObjectCreatorUtil;
import org.failearly.dataz.template.Scope;
import org.failearly.dataz.template.TemplateObject;
import org.failearly.dataz.template.TemplateObjectAnnotationContext;
import org.failearly.dataz.template.TemplateObjectFactoryBase;
import org.failearly.dataz.template.simple.Adhoc;

/**
 * AdhocFactory creates {@link org.failearly.dataz.template.simple.Adhoc.AdhocTemplateObject} from {@link Adhoc} template object impl.
 */
public final class AdhocFactory extends TemplateObjectFactoryBase<Adhoc> {
    public AdhocFactory() {
        super(Adhoc.class);
    }

    @Override
    protected TemplateObject doCreate(Adhoc annotation, TemplateObjectAnnotationContext context) {
        final Adhoc.AdhocTemplateObject templateObjectPrototype = ObjectCreatorUtil.createInstance(annotation.value());
        return templateObjectPrototype.create(annotation, context);
    }

    @Override
    protected String[] doResolveDataSetNames(Adhoc annotation) {
        return annotation.datasets();
    }

    @Override
    protected Scope doResolveScope(Adhoc annotation) {
        return annotation.scope();
    }

    @Override
    protected String doResolveName(Adhoc annotation) {
        return annotation.name();
    }
}

