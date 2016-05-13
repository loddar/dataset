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

package org.failearly.dataz.template.generator;

import org.failearly.dataz.config.Constants;
import org.failearly.dataz.template.Scope;
import org.failearly.dataz.template.TemplateObjectFactory;
import org.failearly.dataz.internal.template.generator.ListGeneratorFactory;

import java.lang.annotation.*;

/**
 * ListGenerator provides a list of constant {@link #values()}.
 * <br>Usage Example:<br><br>
 * <pre>
 *   {@literal @Test}
 *   {@literal @}ListGenerator(name="names", values={"Marko", "Adam", "Brian"})
 *    public void my_test() {
 *        // The 'names' generator generates:
 *        // Marko, Adam, Brian
 *    }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ListGenerator.List.class)
@TemplateObjectFactory.Definition(ListGeneratorFactory.class)
public @interface ListGenerator {
    /**
     * @return The name of the template object. Could be used in Velocity templates by {@code $<name>}.
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
     * @return Limit type.
     *
     * @see org.failearly.dataz.template.generator.Limit#LIMITED
     * @see org.failearly.dataz.template.generator.Limit#UNLIMITED
     */
    Limit limit() default Limit.LIMITED;

    /**
     * The values to be used by current. The values are strings, but could be used as int values as well.
     * @return the values.
     */
    String[] values();

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
        ListGenerator[] value();
    }
}