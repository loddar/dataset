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

package org.failearly.dataset.template;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * TemplateObjectFactoryBase is the base class for implementing {@link TemplateObjectFactory}. It also cast to the
 * actually expected annotation and provides type safe methods.
 *
 * @see #doCreate(Annotation)
 * @see #doResolveDataSetName(Annotation)
 */
public abstract class TemplateObjectFactoryBase<TOA extends Annotation> implements TemplateObjectFactory {
    private final Class<TOA> annotationClass;

    protected TemplateObjectFactoryBase(Class<TOA> annotationClass) {
        Objects.requireNonNull(annotationClass,"Missing annotation class.");
        this.annotationClass = annotationClass;
    }

    @Override
    public final TemplateObject create(Annotation annotation) {
        Objects.requireNonNull(annotation,"Missing annotation of type " + annotationClass.getName());
        return doCreate(annotationClass.cast(annotation));
    }

    /**
     * Type safe alternative for {@link #create(Annotation)}.
     * @param annotation the annotation.
     * @return the created template object.
     */
    protected abstract TemplateObject doCreate(TOA annotation);


    @Override
    public final String resolveDataSetName(Annotation annotation) {
        return doResolveDataSetName(annotationClass.cast(annotation));
    }

    /**
     * Type safe alternative for {@link #resolveDataSetName(Annotation)}.
     * @param annotation the annotation.
     * @return the data set name.
     */
    protected abstract String doResolveDataSetName(TOA annotation);


    @Override
    public final Scope resolveScope(Annotation annotation) {
        final Scope scope = doResolveScope(annotationClass.cast(annotation));
        return scope.getScopeValue();
    }

    /**
     * Type safe alternative for {@link #resolveScope(Annotation)}.
     * @param annotation the annotation.
     * @return the scope.
     */
    protected abstract Scope doResolveScope(TOA annotation);

    @Override
    public final void __extend_TemplateObjectFactoryBase__instead_of_implementing_TemplateObjectFactory() {
        throw new UnsupportedOperationException("__extend_TemplateObjectFactoryBase__instead_of_implementing_TemplateObjectFactory should not be called");
    }
}
