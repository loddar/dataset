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

package org.failearly.dataz.internal.template.support.test.message.basic;

import org.failearly.dataz.internal.common.message.ClasspathMessageTemplate;
import org.failearly.dataz.internal.common.message.Message;
import org.failearly.dataz.internal.common.message.TemplateParameters;

import static org.failearly.dataz.internal.template.support.test.message.basic.AbstractTemplateObjectMessage.*;

/**
 * MissingTemplateObjectAnnotation is responsible for ...
 */
@ClasspathMessageTemplate("3_MissingTestFixture.txt.vm")
@TemplateParameters({ARG_TEMPLATE_OBJECT_ANNOTATION, ARG_TEMPLATE_OBJECT_FACTORY, ARG_TEMPLATE_OBJECT})
final class MissingTestFixture extends AbstractTemplateObjectMessage<MissingTestFixture> {
    MissingTestFixture() {
        super(MissingTestFixture.class);
    }

    public static Message create(TemplateObjectMessage.Initializer initializer) {
        return new MissingTestFixture().buildLazyMessage(initializer);
    }

}
