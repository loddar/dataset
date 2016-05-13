/*
 * dataZ - Test Support For Data Stores.
 *
 * Copyright (C) 2014-2016 'Marko Umek' (http://fail-early.com)
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

package org.failearly.dataz.test;

import org.failearly.dataz.internal.util.BuilderBase;

import java.lang.annotation.Annotation;

/**
 * AnnotationResolver is responsible for ...
 */
public final class AnnotationInstanceResolver<T extends Annotation> extends BuilderBase<T> {

    private final Class<T> annotationClass;
    private Class<?> testClass;
    private String methodName;

    private AnnotationInstanceResolver(Class<T> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public static <T extends Annotation> AnnotationInstanceResolver<T> annotationResolver(Class<T> annotationClass) {
        return new AnnotationInstanceResolver<>(annotationClass);
    }

    public AnnotationInstanceResolver<T> fromClass(Class<?> testClass) {
        this.testClass = testClass;
        return this;
    }

    public AnnotationInstanceResolver<T> fromMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    @Override
    protected T doBuild() throws RuntimeException {
        T annotation;
        try {
            if (methodName == null) {
                annotation = testClass.getAnnotation(annotationClass);
            } else {
                annotation = testClass.getMethod(methodName).getAnnotation(annotationClass);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unknown method '" + methodName + "' on " + testClass);
        }

        if (annotation == null) {
            throw new RuntimeException("Can't find annotation of " + annotationClass);
        }

        return annotation;
    }

    @Override
    protected void checkMandatoryFields() {
        checkMandatoryField(annotationClass, "annotationClass");
        checkMandatoryField(testClass, "testClass");
    }
}