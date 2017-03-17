/*
 * dataZ - Test Support For Data Stores.
 *
 * Copyright 2014-2017 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.failearly.dataz.internal.template.support.test.message.generator;

import org.failearly.common.message.ClasspathMessageTemplate;
import org.failearly.common.message.TemplateParameters;
import org.failearly.dataz.internal.template.support.test.message.basic.AbstractTemplateObjectMessage;

/**
 * MissingTemplateObjectAnnotation is responsible for ...
 */
@ClasspathMessageTemplate("1_MissingGeneratorFactory.txt.vm")
@TemplateParameters({AbstractTemplateObjectMessage.ARG_TEMPLATE_OBJECT_ANNOTATION})
final class MissingGeneratorFactory extends AbstractTemplateObjectMessage<MissingGeneratorFactory> {
    MissingGeneratorFactory() {
        super(MissingGeneratorFactory.class);
    }
}
