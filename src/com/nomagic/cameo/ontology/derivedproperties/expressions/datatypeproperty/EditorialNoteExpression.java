/*
 * AttributeTypesExpression
 *
 * $Revision$ $Date$
 * $Author$
 *
 * Copyright (c) 2013 NoMagic, Inc. All Rights Reserved.
 */
package com.nomagic.cameo.ontology.derivedproperties.expressions.datatypeproperty;

import com.google.common.collect.Lists;
import com.nomagic.cameo.ontology.derivedproperties.expressions.FactPredicateByNameFinder;
import com.nomagic.cameo.ontology.derivedproperties.expressions.FactPredicateListenerConfigFactory;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.Map;

/**
 * Expression collects and returns a collection of EditorialNote InstanceSpecifications for
 * the OWL DatatypeProperty.
 *
 * @author Lonnie VanZandt
 * @version 1.0
 */
public class EditorialNoteExpression implements Expression,
        SmartListenerConfigurationProvider
{

    /**
     * Returns empty collection if the specified object is not an OWL class. If
     * specified object is an OWL class then returns the set of EditorialNotes
     * for that OWL Class.
     *
     * @param object the context Element from the current MD model.
     * @return collection of related EditorialNote InstanceSpecifications.
     */
    @Override
    public Object getValue(@CheckForNull RefObject object)
    {

        if (object instanceof Class) {

            Association DatatypeProperty = (Association) object;

            FactPredicateByNameFinder factPredicateByNameFinder = new FactPredicateByNameFinder((Class) DatatypeProperty);

            return factPredicateByNameFinder.findInstanceSpecifications("editorialNote");
        } else {
            return Lists.newArrayList();
        }
    }

    /**
     * {@inheritDoc} An implementation of the
     * com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider
     * interface has to return the configuration that identifies the set of
     * properties that are important to the expression. The derived property
     * will be recalculated when one or more of these properties change.
     */
    @Override
    public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations()
    {
        return FactPredicateListenerConfigFactory.getListenerConfigurations();
    }
}
