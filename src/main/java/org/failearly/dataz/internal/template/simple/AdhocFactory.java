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

package org.failearly.dataz.internal.template.simple;

import org.failearly.dataz.template.Scope;
import org.failearly.dataz.template.TemplateObject;
import org.failearly.dataz.template.TemplateObjectFactoryBase;
import org.failearly.dataz.template.simple.Adhoc;
import org.failearly.common.test.ObjectCreator;

/**
 * AdhocFactory creates {@link org.failearly.dataz.template.simple.Adhoc.AdhocTemplateObject} from {@link Adhoc} template object annotation.
 */
public final class AdhocFactory extends TemplateObjectFactoryBase<Adhoc> {
    public AdhocFactory() {
        super(Adhoc.class);
    }

    @Override
    protected TemplateObject doCreate(Adhoc annotation) {
        final Adhoc.AdhocTemplateObject templateObjectPrototype = ObjectCreator.createInstance(annotation.value());
        return templateObjectPrototype.create(annotation);
    }

    @Override
    protected String doResolveDataSetName(Adhoc annotation) {
        return annotation.dataset();
    }

    @Override
    protected Scope doResolveScope(Adhoc annotation) {
        return annotation.scope();
    }

}
