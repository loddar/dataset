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

package org.failearly.dataz.internal.template.generator;

import org.failearly.dataz.exception.DataSetException;
import org.failearly.dataz.template.TemplateObjectAnnotationContext;
import org.failearly.dataz.template.generator.RandomRangeGenerator;
import org.failearly.dataz.template.generator.support.GeneratorFactoryBase;
import org.failearly.dataz.template.generator.support.UnlimitedGeneratorBase;
import org.failearly.dataz.template.Scope;
import org.failearly.dataz.template.TemplateObject;

import java.util.Iterator;
import java.util.Random;

/**
 * RandomRangeGeneratorFactory is responsible for creating of implementation instances for {@link org.failearly.dataz.template.generator.RandomRangeGenerator}.
 */
public final class RandomRangeGeneratorFactory extends GeneratorFactoryBase<Integer, RandomRangeGenerator> {

    public RandomRangeGeneratorFactory() {
        super(RandomRangeGenerator.class);
    }

    @Override
    protected TemplateObject doCreate(RandomRangeGenerator annotation, TemplateObjectAnnotationContext context) {
        if (annotation.unique()) {
            return doCreateUniqueGenerator(annotation, context, calculateUniqueLimitValue(annotation));
        }

        return doCreateGenerator(annotation, context, annotation.limit(), calculateLimitValue(annotation));
    }

    private static int calculateUniqueLimitValue(RandomRangeGenerator generatorAnnotation) {
        final int rangeSize = generatorAnnotation.end() - generatorAnnotation.start() + 1;
        final int countValue = generatorAnnotation.count();

        if (countValue > 0) {
            return Math.min(countValue, rangeSize);
        }

        return rangeSize;
    }

    private static int calculateLimitValue(RandomRangeGenerator generatorAnnotation) {
        final int rangeSize = generatorAnnotation.end() - generatorAnnotation.start() + 1;
        final int countValue = generatorAnnotation.count();

        if (countValue > 0) {
            return countValue;
        }

        return rangeSize;
    }

    @Override
    protected UnlimitedGeneratorBase<Integer> doCreateUnlimitedGenerator(RandomRangeGenerator generatorAnnotation, TemplateObjectAnnotationContext context, Integer limitValue) {
        return new RandomRangeGeneratorImpl(generatorAnnotation, context);
    }

    @Override
    protected String doResolveName(RandomRangeGenerator annotation) {
        return annotation.name();
    }

    @Override
    protected String[] doResolveDataSetNames(RandomRangeGenerator annotation) {
        return annotation.datasets();
    }

    @Override
    protected Scope doResolveScope(RandomRangeGenerator annotation) {
        return annotation.scope();
    }

    @SuppressWarnings("WeakerAccess")
    public static final class RandomRangeGeneratorImpl extends UnlimitedGeneratorBase<Integer> {
        private Random random;
        private final int seed;
        private final int start;
        private final int end;
        private final int bound;


        private RandomRangeGeneratorImpl(RandomRangeGenerator annotation, TemplateObjectAnnotationContext context) {
            super(annotation, context);

            this.start = annotation.start();
            this.end = annotation.end();
            this.seed = annotation.seed();
            this.random = random(this.seed);
            this.bound = end - start + 1;
        }

        @Override
        protected void doInit() throws DataSetException {
            super.doInit();
            checkInvariant(start < end, "start < end");
        }

        @Override
        protected void doReset() {
            this.random = random(seed);
        }


        @Override
        public final Iterator<Integer> createIterator() {
            return new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Integer next() {
                    return nextValue();
                }
            };
        }

        private Integer nextValue() {
            final int val = random.nextInt(bound) + start;
            assert val >= start && val <= end : "value (=" + val + ") is not in range [" + start + "," + end + "]";
            return val;
        }
    }
}
