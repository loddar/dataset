/*
 * dataSet - Test Support For Data Stores.
 *
 * Copyright (C) 2014-2015 Marko Umek (http://fail-early.com/contact)
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

package org.failearly.dataset.internal.resource.factory.use;

import org.failearly.dataset.DataSet;
import org.failearly.dataset.ReusableDataSet;
import org.failearly.dataset.Use;
import org.failearly.dataset.internal.resource.factory.DataResourcesFactoryTestBase;
import org.failearly.dataset.resource.DataResource;
import org.failearly.dataset.test.MyTemplateObjectAnnotation;
import org.junit.Test;

import java.util.List;

import static org.failearly.dataset.test.DataResourceMatchers.isDefaultDataResource;

/**
 * UseSetupResourcesFactoryTest contains tests for {@link UseSetupResourcesFactory} and it's associated annotation {@link Use}.
 */
@SuppressWarnings("unchecked")
public final class UseSetupResourcesFactoryTest extends DataResourcesFactoryTestBase<Use, UseSetupResourcesFactory> {

    private static final String TEMPLATE_OBJECT_CONTENT = "Uses template objects!";
    private static final String EXPECTED_TEMPLATE_OBJECT_CONTENT = "\n" + TEMPLATE_OBJECT_CONTENT + "\n";

    public UseSetupResourcesFactoryTest() {
        super(Use.class, new UseSetupResourcesFactory(), AnyClass.class);
    }

    /**
     * Basic test for @Use with static datasets. A (test) method uses @Use with at least two interfaces, which uses @DataSet one with default
     * settings ({@link UseDefaultDataSet}) and one with explicit resource ({@link UsesExplicitResource}) should resolve the specified resources.
     */
    @Test
    public void reusable_dataset_interfaces_with_static_data_sets__should_resolve_the_resources_in_correct_order() throws Exception {
        // act / when
        final List<DataResource> dataResources = createDataResourcesFromMethod("staticDataSets");

        // assert / then
        assertResolvedDataResources(dataResources,                                                                      //
                isDefaultDataResource("/org/failearly/dataset/internal/resource/factory/use/UseDefaultDataSet.setup"),  //
                isDefaultDataResource("/any-existing-resource.setup")                                                   //
        );
    }


    /**
     * Test for {@link ReusableDataSet}s which uses {@link Use} itself.
     */
    @Test
    public void reusable_dataset_interface_with_double_use__should_resolve_the_resources_in_correct_order() throws Exception {
        // act / when
        final List<DataResource> dataResources = createDataResourcesFromMethod("doubleUse");

        // assert / then
        assertResolvedDataResources(dataResources,                                                                     //
                isDefaultDataResource("/org/failearly/dataset/internal/resource/factory/use/UseDefaultDataSet.setup"),  //
                isDefaultDataResource("/any-existing-resource.setup")                                                   //
        );
    }

    @Test
    public void reusable_dataset_class__should_resolve_base_class_resources_first_and_then_climb_up_the_class_hierarchy() throws Exception {
        // act / when
        final List<DataResource> dataResources = createDataResourcesFromMethod("reusableDatasetClassHierarchy");

        // assert / then
        assertResolvedDataResources(dataResources,                                                                     //
                isDefaultDataResource("/org/failearly/dataset/internal/resource/factory/use/ReusableBaseClass.setup"), //
                isDefaultDataResource("/org/failearly/dataset/internal/resource/factory/use/ReusableClass.setup")  //
        );
    }

    @Test
    public void reusable_dataset_with_template__should_use_template_objects() throws Exception {
        // act / when
        final List<DataResource> dataResources = createDataResourcesFromMethod("reuseWithTemplateObjects");

        // assert / then
        assertDataResourcesContent(dataResources.get(0), EXPECTED_TEMPLATE_OBJECT_CONTENT);
    }

    private static class AnyClass {
        @Use({UseDefaultDataSet.class, UsesExplicitResource.class})
        public void staticDataSets() {
        }

        @Use(ReusableDataSetUsingUse.class)
        public void doubleUse() {
        }

        @Use(ReusableClass.class)
        public void reusableDatasetClassHierarchy() {
        }

        @Use(ReusableTemplateDataSet.class)
        public void reuseWithTemplateObjects() {
        }

    }

    @DataSet
    private interface UseDefaultDataSet extends ReusableDataSet {
    }

    @DataSet(setup = "/any-existing-resource.setup")
    private interface UsesExplicitResource extends ReusableDataSet {
    }

    @Use({UseDefaultDataSet.class,UsesExplicitResource.class})
    private interface ReusableDataSetUsingUse extends ReusableDataSet {
    }


    @DataSet
    private static class ReusableBaseClass implements ReusableDataSet {
    }

    @DataSet
    private static class ReusableClass extends ReusableBaseClass {
    }

    @DataSet(setup = "template.vm")
    @MyTemplateObjectAnnotation(name = "to", description = TEMPLATE_OBJECT_CONTENT)
    private interface ReusableTemplateDataSet extends ReusableDataSet {
    }
}