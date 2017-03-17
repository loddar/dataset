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

package org.failearly.dataz.template;

import java.lang.annotation.*;
import java.util.Set;

/**
 * TemplateObjectFactory creates from a Template Object Annotation a {@link TemplateObject}. The class object of any implementation must be assigned
 * to the associated annotation by using the meta annotation {@link Definition#value()}.
 * <br><br>
 * Remark: Please extend  {@link TemplateObjectFactoryBase} instead of implementing this interface!!
 * <br><br>
 * The Template Object Annotation:<br><br>
 * <pre>
 *    {@literal @}Target({ElementType.METHOD, ElementType.TYPE})
 *    {@literal @}Retention(RetentionPolicy.RUNTIME)
 *    {@literal @}Definition(factory = <b>MyTemplateObjectFactory.class</b>) // Assign the factory
 *     public @interface MyTemplateObjectAnnotation {
 *        // Mandatory attributes
 *        String name();
 *        String dataz();
 *
 *        // Other attributes
 *        // ...
 *     }
 * </pre>
 * <br><br>
 * The appropriate Template Object Factory implementation:<br><br>
 * <pre>
 *      public final class MyTemplateObjectFactory extends TemplateObjectFactoryBase{@literal <}MyTemplateObjectAnnotation{@literal >} {
 *           public MyTemplateObjectFactory() {
 *               super(MyTemplateObjectAnnotation.class);
 *           }
 *
 *          {@literal @}Override
 *           protected TemplateObject doCreate(MyTemplateObjectAnnotation annotation) {
 *              // Creates <i>MyTemplateObject</i> from the template object annotation!!!
 *              return new MyTemplateObject(annotation);
 *           }
 *
 *          {@literal @}Override
 *           protected String doResolveDataSetNames(MyTemplateObjectAnnotation annotation) {
 *              return annotation.dataz();
 *           }
 *
 *          // The implementation of {@link TemplateObject}!!!
 *          public final class MyTemplateObject extends TemplateObjectBase {
 *              public MyTemplateObject(MyTemplateObjectAnnotation annotation) {
 *                  super(annotation, annotation.dataz(), annotation.name() );
 *              }
 *          }
 *      }
 * </pre>
 *
 * @see Definition#value()
 * @see TemplateObject
 */
public interface TemplateObjectFactory {
    /**
     * Create an instance of the template object using the annotation.
     *
     *
     * @param context          the template object annotation's context
     * @param annotation       the annotation instance.
     *
     * @return a new instance of template object.
     */
    TemplateObject create(TemplateObjectAnnotationContext context, Annotation annotation);

    /**
     * Resolves the name of the template object annotation.
     *
     * @param annotation the annotation
     *
     * @return the name of the template object annotation.
     */
    String resolveName(Annotation annotation);

    /**
     * Resolves the associated datasets of the template object annotation.
     *
     * @param annotation the annotation
     *
     * @return the data set name of the template object annotation.
     */
    Set<String> resolveDataSetNames(Annotation annotation);

    /**
     * Resolves the scope from the template object annotation.
     *
     * @param annotation the annotation
     *
     * @return the {@link Scope} of the template object.
     */
    Scope resolveScope(Annotation annotation);

    @SuppressWarnings("unused")
    void __extend_TemplateObjectFactoryBase__instead_of_implementing_TemplateObjectFactory();


    /**
     * Definition is a meta annotation used for {@link TemplateObject} annotations (TOA).
     * <br><br>
     * Example:<br><br>
     * <pre>
     *     {@literal @Target}({ElementType.METHOD, ElementType.TYPE})
     *     {@literal @Retention}(RetentionPolicy.RUNTIME)
     *     <b>{@literal @Definition}(factory = MyGeneratorFactory.class)</b>
     *     {@literal @}{@link java.lang.annotation.Repeatable}(MyTemplateObjectAnnotation.MyTemplateObjectAnnotations.class)
     *     public {@literal @interface} MyTemplateObjectAnnotation {
     *         // mandatory attribute
     *         String name();
     *
     *         // mandatory attribute
     *         String dataz();
     *
     *         // additional elements omitted for brevity
     *         ...
     *
     *         // Necessary for Repeatable
     *         {@literal @Target}({ElementType.METHOD, ElementType.TYPE})
     *         {@literal @Retention}(RetentionPolicy.RUNTIME)
     *         {@literal @interface} List {
     *             MyTemplateObjectAnnotation[] value();
     *         }
     *     }
     * </pre>
     *
     * @see TemplateObjectFactory
     * @see TemplateObjectFactoryBase
     * @see TemplateObject
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface Definition {
        /**
         * The responsible generator factory class.
         *
         * @return a {@link TemplateObjectFactory} class which is associated to the template object annotation.
         */
        Class<? extends TemplateObjectFactory> value();
    }
}
