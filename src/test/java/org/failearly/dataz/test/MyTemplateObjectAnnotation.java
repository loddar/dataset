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

import org.failearly.dataz.config.Constants;
import org.failearly.dataz.template.Scope;
import org.failearly.dataz.template.TemplateObjectFactory;

import java.lang.annotation.*;

/**
 * MyTemplateObjectAnnotation is a template object annotation as replacement for real ones, which has only the minimal set of annotation's attributes.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TemplateObjectFactory.Definition(MyTemplateObjectFactory.class)
@Repeatable(MyTemplateObjectAnnotation.List.class)
public @interface MyTemplateObjectAnnotation {

    /**
     * @return The name of the generator. Could be used in Velocity templates by {@code $<name>}.
     */
    String name();

    /**
     * @return The name of the associated dataz.
     *
     * @see org.failearly.dataz.DataSet#name()
     */
    String dataset() default Constants.DATASET_DEFAULT_NAME;

    /**
     * @return The scope of the template object (either {@link Scope#LOCAL} or {@link Scope#GLOBAL}.
     */
    Scope scope() default Scope.DEFAULT;

    /**
     * The description.
     */
    String description() default "<no description>";

    /**
     * Containing Annotation Type.
     *
     * Remark: This will be used by Java8 compiler.
     *
     * @see java.lang.annotation.Repeatable
     */
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        MyTemplateObjectAnnotation[] value();
    }
}