package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.unionclass;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.StereotypedElement;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.StereotypedRelationByNameFinder;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.StereotypedRelationByNameFinder
        .RelationshipDirection;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.PropertyNames;

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
public class DisjointWithExpression implements Expression, SmartListenerConfigurationProvider
{
    /**
     * Returns empty collection if the specified object is not an OWL Restriction Class.
     * If the specified object is an OWL Restriction Class then it returns a collection
     * of the rdf Labels of the Disjoint OWL Restriction Classes (if any) for the element.
     *
     * @param object the context Element from the current MD model.
     * @return collection of Disjoint with Labels.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<Object> values = Lists.newArrayList();

        if (object instanceof Class)
        {
            final Class owlClass = (Class) object;

            StereotypedRelationByNameFinder<Class> relationFinder = new StereotypedRelationByNameFinder(owlClass);

            List<DirectedRelationship> disjointWithRels = relationFinder.findRelationshipWithAppliedStereotypeName
                    ("disjointWith", RelationshipDirection.Both);

            // collect all the OWL Restriction Classes of which this class is disjoint
            for (DirectedRelationship disjointWithRel : disjointWithRels)
            {
                List<Element> endElements = Lists.newArrayList(disjointWithRel.getTarget());
                endElements.addAll(disjointWithRel.getSource());

                for (Element endElement : endElements)
                {
                    if ((endElement instanceof Class) && (endElement != owlClass))
                    {
                        Class superClass = (Class) endElement;

                        StereotypedElement<Class> owlProperty = new StereotypedElement<Class>(superClass, "owlClass");

                        values.addAll(owlProperty.getTagValueValueSpecificationByName("label"));
                    }
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

        // if the name of the element changes
        smartListenerCfg.listenTo(PropertyNames.NAME);

        listeners.add(smartListenerCfg);

        configs.put(Class.class, listeners);

        return configs;
    }
}
