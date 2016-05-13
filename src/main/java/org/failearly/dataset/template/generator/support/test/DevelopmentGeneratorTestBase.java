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

package org.failearly.dataset.template.generator.support.test;

import org.failearly.dataset.template.TemplateObjectFactory;
import org.failearly.dataset.template.generator.Generator;
import org.failearly.dataset.template.generator.support.GeneratorFactoryBase;
import org.failearly.dataset.template.support.test.DevelopmentTemplateObjectTestBase;

import java.lang.annotation.Annotation;

/**
 * DevelopmentGeneratorTestBase is the base test support class for all {@link Generator} and
 * {@link GeneratorFactoryBase} implementations. After finishing the development, you should
 * replace {@code DevelopmentGeneratorTestBase} with {@link GeneratorTestBase}.
 * <p>
 * <br><br>
 * Start with something like this:
 * <br><br>
 * <pre>
 *      public class MyGeneratorTest extends {@link DevelopmentGeneratorTestBase}{@literal
 * <Object,NullTemplateObjectAnnotation,NullGeneratorFactory>} {
 *          public MyGeneratorTest () {
 *              super(..
 *          }
 *      }
 * </pre>
 */
@SuppressWarnings("unused")
public abstract class DevelopmentGeneratorTestBase<T, TOA extends Annotation, TOF extends TemplateObjectFactory, TO extends Generator<T>>
    extends DevelopmentTemplateObjectTestBase<TOA, TOF, TO> implements GeneratorTemplates {

    protected DevelopmentGeneratorTestBase() {
    }

    protected DevelopmentGeneratorTestBase(
            Class<TOA> templateObjectAnnotationClass,
            Class<TOF> templateObjectFactoryClass,
            Class<TO> templateObjectClass,
            Class<?> testFixtureClass
    ) {
        super(templateObjectAnnotationClass, templateObjectFactoryClass, templateObjectClass, testFixtureClass);
    }
}