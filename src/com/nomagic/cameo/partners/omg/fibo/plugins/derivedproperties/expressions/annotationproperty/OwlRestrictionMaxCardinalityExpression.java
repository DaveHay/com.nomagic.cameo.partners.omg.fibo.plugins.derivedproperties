package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.annotationproperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.MatchingRelationByNameFinder;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions
        .NamedPropertyOfDependentNamedSupplierListenerConfigFactory;
import com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions.StereotypedElement;
import com.nomagic.magicdraw.validation.SmartListenerConfigurationProvider;
import com.nomagic.uml2.ext.jmi.reflect.Expression;
import com.nomagic.uml2.ext.jmi.smartlistener.SmartListenerConfig;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification;

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
public class OwlRestrictionMaxCardinalityExpression implements Expression, SmartListenerConfigurationProvider
{
    private final String stereoName = "owlRestriction";
    private final String propertyName = "maxCardinality";

    /**
     * Returns empty collection if the specified object is not an OWL Property. If
     * specified object is an OWL Property then returns the set of owlRestriction cardinalities.
     *
     * @param object the context Element from the current MD model.
     * @return collection of related OWL Restriction cardinalities.
     */
    @Override
    public Object getValue (@CheckForNull RefObject object)
    {
        List<ValueSpecification> values = Lists.newArrayList();

        if (object instanceof Association)
        {

            Association owlProperty = (Association) object;

            MatchingRelationByNameFinder matchingRelationByNameFinder = new MatchingRelationByNameFinder(owlProperty);

            ImmutableList<Class> owlRestrictions = ImmutableList.copyOf(matchingRelationByNameFinder
                    .findRelatedClassWithAppliedStereotypeName(stereoName));

            for (Class owlRestrictClass : owlRestrictions)
            {

                StereotypedElement<Class> owlRestrict = new StereotypedElement<Class>(owlRestrictClass, stereoName);

                values.addAll(owlRestrict.getTagValueValueSpecificationByName(propertyName));
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
        return NamedPropertyOfDependentNamedSupplierListenerConfigFactory.getListenerConfigurations();
    }
}
