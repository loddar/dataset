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

package org.failearly.dataz.resource;

import org.failearly.dataz.common.test.annotations.Subject;
import org.failearly.dataz.NamedDataStore;
import org.failearly.dataz.config.Constants;
import org.failearly.dataz.config.DataSetProperties;
import org.failearly.dataz.internal.template.TemplateObjects;
import org.failearly.dataz.test.DataResourceMatcherBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.failearly.dataz.common.test.ExceptionVerifier.on;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * DataResourceBuilderTest contains tests for {@link DataResourceBuilder} .
 */
@Subject({DataResource.class, DataResourceBuilder.class})
public class DataResourceBuilderTest {

    private static final String ANY_DATA_SET_NAME = "DS";
    private static final String EXISTING_RESOURCE = "/any-existing-resource.setup";
    private static final String NON_EXISTING_RESOURCE = "/non-existing-resource.setup";
    private static final String EXISTING_TEMPLATE_RESOURCE = "/any-existing-resource.setup.vm";
    private static final String NON_EXISTING_TEMPLATE_RESOURCE = "/non-existing-resource.setup.vm";

    private static final TemplateObjects NO_TEMPLATE_OBJECTS = TemplateObjects.noTemplateObjects();

    @Subject
    private final DataResourceBuilder dataResourceBuilder = DataResourceBuilder.createBuilder(ATestClass.class) //
        .withTemplateObjects(NO_TEMPLATE_OBJECTS)     //
        .withDataSetName(ANY_DATA_SET_NAME);

    @BeforeClass
    public static void setDefaultDataStore() throws Exception {
        System.setProperty(Constants.DATAZ_PROPERTY_DEFAULT_DATA_STORE, DefaultDataStore.class.getName());
        DataSetProperties.reload();
    }

    @AfterClass
    public static void resetDefaultDataStore() throws Exception {
        System.clearProperty(Constants.DATAZ_PROPERTY_DEFAULT_DATA_STORE);
        DataSetProperties.reload();
    }

    @Test
    public void how_many_data_resources_will_be_created_for_any_existing_resource() throws Exception {
        // act / when
        final List<DataResource> dataResources = optionalDataResourceBuilder(EXISTING_RESOURCE).build();

        // assert / then
        assertThat("#data resources?", dataResources, is(hasSize(1)));
    }

    private DataResourceBuilder optionalDataResourceBuilder(String resourceName) {
        return dataResourceBuilder               //
                .withResourceName(resourceName)                   //
                .optional();                                       //
    }

    @Test
    public void what_kind_of_DataResource_will_be_created_for_any_existing_non_template_resource() throws Exception {
        // act / when
        final List<DataResource> dataResources = optionalDataResourceBuilder(EXISTING_RESOURCE).build();

        // assert / then
        final DataResource dataResource = dataResources.get(0);
        assertThat("returns Standard DataResource?", dataResource.getClass().getName(), is("org.failearly.dataz.internal.resource.StandardDataResource"));
        assertDataResource(dataResource, EXISTING_RESOURCE);
    }

    private void assertDataResource(DataResource dataResource, String resource) {
        assertThat("DataResource?", dataResource,
            DataResourceMatcherBuilder.createWithDefaults()
                .withDataSetName(ANY_DATA_SET_NAME)
                .withResource(resource)
                .build()
        );
    }


    @Test
    public void how_many_data_resources_will_be_created_for_non_existing_resource() throws Exception {
        // act / when
        final List<DataResource> dataResources = optionalDataResourceBuilder(NON_EXISTING_RESOURCE).build();

        // assert / then
        assertThat("#data resources?", dataResources, is(hasSize(1)));
    }

    @Test
    public void what_kind_of_DataResource_will_be_created_for_non_existing_resource__if_the_resource_is_optional() throws Exception {
        // act / when
        final List<DataResource> dataResources = optionalDataResourceBuilder(NON_EXISTING_RESOURCE).build();

        // assert / then
        assertDataResourceType(dataResources.get(0), "IgnoringDataResource");
    }

    private void assertDataResourceType(DataResource dataResource, String resourceTypeAsString) {
        assertThat("Data Resource Type?", dataResource.getClass().getName(), is("org.failearly.dataz.internal.resource." + resourceTypeAsString));
    }

    @Test
    public void what_kind_of_DataResource_will_be_created_for_non_existing_resource__if_the_resource_is_mandatory() throws Exception {
        // act / when
        final List<DataResource> dataResources = mandatoryDataResourceBuilder(NON_EXISTING_RESOURCE).build();

        // assert / then
        assertDataResourceType(dataResources.get(0), "MissingDataResource");
    }

    private DataResourceBuilder mandatoryDataResourceBuilder(String resourceName) {
        return dataResourceBuilder           //
                .withResourceName(resourceName)               //
                .mandatory();                                  //
    }

    @Test
    public void what_kind_of_DataResource_will_be_created_for_any_existing_template_resource() throws Exception {
        // act / when
        final List<DataResource> dataResources = optionalDataResourceBuilder(EXISTING_TEMPLATE_RESOURCE).build();

        // assert / then
        assertDataResourceType(dataResources.get(0), "TemplateDataResource");
        assertDataResource(dataResources.get(0), EXISTING_TEMPLATE_RESOURCE);
    }

    @Test
    public void what_kind_of_DataResource_will_be_created_for_non_existing_template_resource__if_resource_is_optional() throws Exception {
        // act / when
        final List<DataResource> dataResources = optionalDataResourceBuilder(NON_EXISTING_TEMPLATE_RESOURCE).build();

        // assert / then
        assertDataResourceType(dataResources.get(0), "IgnoringDataResource");
    }

    @Test
    public void what_kind_of_DataResource_will_be_created_for_non_existing_template_resource__if_resource_is_mandatory() throws Exception {
        // act / when
        final List<DataResource> dataResources = mandatoryDataResourceBuilder(NON_EXISTING_TEMPLATE_RESOURCE).build();

        // assert / then
        assertDataResourceType(dataResources.get(0), "MissingDataResource");
    }

    @Test
    public void what_happens_setting_NO_NamedDataStore() throws Exception {
        // act / when
        final List<DataResource> dataResources =  optionalDataResourceBuilder(EXISTING_RESOURCE)
            .withNamedDataStore(/* no datastores */)
            .build();


        // assert / then
        assertDataResource(dataResources.get(0), DefaultDataStore.class, EXISTING_RESOURCE);
    }

    private void assertDataResource(DataResource dataResource, Class<? extends NamedDataStore> expectedNamedDataStore, String expectedResource) {
        assertThat("DataResource?", dataResource,
            DataResourceMatcherBuilder.createWithDefaults()
                .withDataSetName(ANY_DATA_SET_NAME)
                .withResource(expectedResource)
                .withNamedDataStore(expectedNamedDataStore)
                .build()
        );
    }

    @Test
    public void what_happens_if_set_one_NamedDataStore() throws Exception {
        // act / when
        final List<DataResource> dataResources =  optionalDataResourceBuilder(EXISTING_RESOURCE)
            .withNamedDataStore(OtherDataStore.class)
            .build();


        // assert / then
        assertThat("#data resources?", dataResources, is(hasSize(1)));
        assertDataResource(dataResources.get(0), OtherDataStore.class, EXISTING_RESOURCE);
    }

    @Test
    public void what_happens_if_set_multiple_NamedDataStore() throws Exception {
        // act / when
        final List<DataResource> dataResources =  optionalDataResourceBuilder(EXISTING_RESOURCE)
            .withNamedDataStore(OtherDataStore.class, AnotherDataStore.class, DefaultDataStore.class)
            .build();


        // assert / then
        assertThat("#data resources?", dataResources, is(hasSize(3)));
        assertDataResource(dataResources.get(0), OtherDataStore.class, EXISTING_RESOURCE);
        assertDataResource(dataResources.get(1), AnotherDataStore.class, EXISTING_RESOURCE);
        assertDataResource(dataResources.get(2), DefaultDataStore.class, EXISTING_RESOURCE);
    }

    @Test
    public void what_happens_if_set_multiple_NamedDataStore_but_in_different_order() throws Exception {
        // act / when
        final List<DataResource> dataResources =  optionalDataResourceBuilder(EXISTING_RESOURCE)
            .withNamedDataStore(AnotherDataStore.class, DefaultDataStore.class, OtherDataStore.class)
            .build();


        // assert / then
        assertThat("#data resources?", dataResources, is(hasSize(3)));
        assertDataResource(dataResources.get(0), AnotherDataStore.class, EXISTING_RESOURCE);
        assertDataResource(dataResources.get(1), DefaultDataStore.class, EXISTING_RESOURCE);
        assertDataResource(dataResources.get(2), OtherDataStore.class, EXISTING_RESOURCE);
    }

    @Test
    public void what_happens_if_set_multiple_NamedDataStore_but_repeating() throws Exception {
        // act / when
        final List<DataResource> dataResources =  optionalDataResourceBuilder(EXISTING_RESOURCE)
            .withNamedDataStore(OtherDataStore.class, AnotherDataStore.class, DefaultDataStore.class, DefaultDataStore.class, AnotherDataStore.class)
            .build();


        // assert / then
        assertThat("Use only distinct data stores?", dataResources, is(hasSize(3)));
        assertDataResource(dataResources.get(0), OtherDataStore.class, EXISTING_RESOURCE);
        assertDataResource(dataResources.get(1), AnotherDataStore.class, EXISTING_RESOURCE);
        assertDataResource(dataResources.get(2), DefaultDataStore.class, EXISTING_RESOURCE);
    }

    @Test
    public void what_happens_on_missing_expected_builder_property() throws Exception {
        // assert / then
        on(() -> DataResourceBuilder.createBuilder(ATestClass.class).build())                                      //
                .expect(IllegalStateException.class)                                                               //
                .expect("DataResourceBuilder: Mandatory field 'mandatory/optional' missing (must not be null)!")   //
                .verify();

        on(() -> DataResourceBuilder.createBuilder(ATestClass.class).optional().build())                           //
                .expect(IllegalStateException.class)                                                               //
                .expect("DataResourceBuilder: Mandatory field 'templateObjects' missing (must not be null)!")      //
                .verify();

        on(() -> DataResourceBuilder.createBuilder(ATestClass.class).mandatory().build())
                .expect(IllegalStateException.class)
                .expect("DataResourceBuilder: Mandatory field 'templateObjects' missing (must not be null)!")
                .verify();

        on(() -> DataResourceBuilder.createBuilder(ATestClass.class).optional().withTemplateObjects(NO_TEMPLATE_OBJECTS).build()) //
                .expect(IllegalStateException.class)                                                                              //
                .expect("Builder: Mandatory field 'dataSetName' missing (must not be null)!")                                     //
                .verify();

        on(() -> DataResourceBuilder.createBuilder(ATestClass.class)                      //
                                    .optional()                                           //
                                    .withTemplateObjects(NO_TEMPLATE_OBJECTS)             //
                                    .withDataSetName("DataSetName")                       //
                                    .build())                                                   //
                .expect(IllegalStateException.class)                                            //
                .expect("Builder: Mandatory field 'resourceName' missing (must not be null)!")  //
                .verify();
    }

    // Just for satisfy the interfaces
    public static class ATestClass {
        @Test
        public void anyTestMethod() {
        }
    }

    private static class DefaultDataStore extends NamedDataStore {}
    private static class OtherDataStore extends NamedDataStore {}
    private static class AnotherDataStore extends NamedDataStore {}

}