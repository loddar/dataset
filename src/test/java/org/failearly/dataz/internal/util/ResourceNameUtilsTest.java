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

package org.failearly.dataz.internal.util;

import org.failearly.dataz.config.DataSetProperties;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResourceNameUtilsTest {
    public ResourceNameUtilsTest() {
    }

    @Test
    public void getResourceNameWithoutPathAndSuffix() throws Exception {
        assertThat("With double suffix?", ResourceNameUtils.getResourceNameWithoutPathAndSuffix("/this/is/a/path/to/resource-file.txt.vm"), is("resource-file"));
        assertThat("With single suffix?", ResourceNameUtils.getResourceNameWithoutPathAndSuffix("/this/is/a/path/to/resource-file.txt"), is("resource-file"));
        assertThat("Without suffix?", ResourceNameUtils.getResourceNameWithoutPathAndSuffix("/this/is/a/path/to/resource-file"), is("resource-file"));
        assertThat("Without path and suffix?", ResourceNameUtils.getResourceNameWithoutPathAndSuffix("resource-file"), is("resource-file"));
        assertThat("Empty?", ResourceNameUtils.getResourceNameWithoutPathAndSuffix(""), is(""));
        assertThat("Null?", ResourceNameUtils.getResourceNameWithoutPathAndSuffix(null), is(""));
    }

    @Test
    public void getResourceSuffix() throws Exception {
        assertThat("With double suffix?", ResourceNameUtils.getResourceSuffix("/this/is/a/path/to/resource-file.setup.xml"), is(".setup.xml"));
        assertThat("With  suffix + template suffix?",
                ResourceNameUtils.getResourceSuffix("/this/is/a/path/to/resource-file.setup" + DataSetProperties.getTemplateSuffix()),
                is(".setup"));
        assertThat("With single suffix?", ResourceNameUtils.getResourceSuffix("/resource-file.txt"), is(".txt"));
        assertThat("Without suffix?", ResourceNameUtils.getResourceSuffix("/this/is/a/path/to/resource-file"), is(""));
        assertThat("Without path?", ResourceNameUtils.getResourceSuffix("resource-file.txt"), is(".txt"));
        assertThat("Empty?", ResourceNameUtils.getResourceSuffix(""), is(""));
        assertThat("Null?", ResourceNameUtils.getResourceSuffix(null), is(""));
    }

    @Test
    public void getResourcePath() throws Exception {
        assertThat("Absolute path?", ResourceNameUtils.getResourcePath("/this/is/a/path/to/resource-file.setup.xml"), is("/this/is/a/path/to"));
        assertThat("Absolute path?", ResourceNameUtils.getResourcePath("/resource-file.setup.xml"), is("/"));
        assertThat("Relative path?", ResourceNameUtils.getResourcePath("rel/path/to/resource-file.setup.xml"), is("rel/path/to"));
        assertThat("No path?", ResourceNameUtils.getResourcePath("resource-file.setup.xml"), is(""));
        assertThat("Empty?", ResourceNameUtils.getResourcePath(""), is(""));
        assertThat("Null?", ResourceNameUtils.getResourcePath(null), is(""));
    }
}