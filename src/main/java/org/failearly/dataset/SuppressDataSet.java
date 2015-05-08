/*
 * dataSet - Test Support For Datastores.
 *
 * Copyright (C) 2014-2014 Marko Umek (http://fail-early.com/contact)
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
package org.failearly.dataset;

import java.lang.annotation.*;

/**
 * Use this for suppressing DataSet functionality on a test method. This could be useful, if all test share the same {@link DataSet}, by annotating
 * the test class, but you have also some test methods which does not need any DataSet functionality.
 * <br><br>
 * Remark: In case you use it for example with {@link DataSet} on the same
 * test method, {@code SuppressDataSet} will supersede {@code DataSet}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SuppressDataSet {
}
