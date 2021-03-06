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

package org.failearly.dataz.template.generator;


/**
 * GeneratorConstants contains basic generator constants.
 */
public interface GeneratorConstants {
    /**
     * The default seed value.
     *
     * @see org.failearly.dataz.template.generator.RandomRangeGenerator#seed()
     * @see org.failearly.dataz.template.generator.RandomBooleanGenerator#seed()
     */
    int DEFAULT_SEED = 0;

    /**
     * No or invalid count.
     */
    int INVALID_COUNT=-1;
}
