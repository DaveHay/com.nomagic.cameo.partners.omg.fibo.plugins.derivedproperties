package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.owlclass;

import com.google.common.collect.Lists;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.SlotNameAndValueListenerConfigFactory;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.StereotypedElement;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.annotation.CheckForNull;
import javax.jmi.reflect.RefObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: lvanzandt
 * Date: 8/19/13
 * Time: 3:56 PM
 */
public class LabelExpression implements Expression, SmartListenerConfigurationProvider
{
    /**
     * Returns empty collection if the specified object is not an AssociationClass.
     * If the specified object is an AssociationClass then it returns a collection
     * of the rdf Labels of the Parents for the element.
     *
     * @param object the context Element from the current MD model.
     * @return collection of Parent.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<Object> values = Lists.newArrayList();

        if (object instanceof Class)
        {
            final Class owlClass = (Class) object;

            final StereotypedElement<Class> owlProperty = new StereotypedElement<Class>(owlClass, "owlClass");

            values.addAll(owlProperty.getTagValueValueSpecificationByName("label"));
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
        return SlotNameAndValueListenerConfigFactory.getListenerConfigurations();
    }
}
