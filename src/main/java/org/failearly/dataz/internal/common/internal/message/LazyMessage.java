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

package org.failearly.dataz.internal.common.internal.message;

import org.failearly.dataz.internal.common.message.MessageBuilder;
import org.failearly.dataz.internal.common.message.MessageBuilderBase;

/**
 * LazyMessage is responsible for ...
 */
final class LazyMessage<T extends MessageBuilderBase> extends AbstractMessage {
    private final MessageBuilder.Initializer<T> initializer;
    private final T messageBuilder;

    LazyMessage(T messageBuilder, MessageBuilder.Initializer<T> initializer) {
        this.initializer=initializer;
        this.messageBuilder=messageBuilder;
    }

    @Override
    protected String doGenerate() {
        return initializer.applyOn(messageBuilder).build().generate();
    }
}
