package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.owlproperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.impl.PropertyNames;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * User: lvanzandt
 * Date: 7/24/13
 * Time: 8:59 AM
 */
public class PropertyDomainExpression implements Expression, SmartListenerConfigurationProvider
{
    /**
     * Returns an empty collection if the specified object is not an OWL Property Association. If
     * the specified object is an OWL Property then it returns the set of Types in the Domain of
     * the Property.
     *
     * @param object the context Element from the current MD model.
     * @return collection of [0..1] related OWL Class Types of the Domain.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<Type> values = Lists.newArrayList();

        if (object instanceof Association)
        {

            final Association owlProperty = (Association) object;

            // get the set of roles (MemberEnds) of this Association(Class)
            ImmutableList<Property> assocProperties = ImmutableList.copyOf(owlProperty.getMemberEnd());

            // a well-formed UML and ODM-compliant model should return a collection with only 2 members

            // look at each of the (two) properties
            for (Property assocProperty : assocProperties)
            {

                // according to the ODM Specification, version 1, if the property is
                // not navigable from the owner then it references the domain of the owlProperty
                // (otherwise it references the range thereof)

                // if the property cannot be reached from this owlProperty
                if (!assocProperty.isNavigable())
                {

                    // then obtain its Type because that is the domain of the owlProperty
                    Type domainType = assocProperty.getType();

                    values.add(domainType);
                }
            }
        }

        return values;
    }

    /**
     * {@inheritDoc} An implementation of the
     * com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider
     * interface has to return the configuration that identifies the set of
     * properties that are important to the expression. The derived property
     * will be recalculated when one or more of these properties change.
     */
    @Override
    public Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> getListenerConfigurations ()
    {
        Map<java.lang.Class<? extends Element>, Collection<SmartListenerConfig>> configs = Maps.newHashMap();

        Collection<SmartListenerConfig> listeners = Lists.newArrayList();
        SmartListenerConfig smartListenerCfg = new SmartListenerConfig();

        // if the Roles of the association change
        smartListenerCfg.listenTo(PropertyNames.ROLE);

        listeners.add(smartListenerCfg);

        configs.put(Class.class, listeners);

        return configs;
    }
}
