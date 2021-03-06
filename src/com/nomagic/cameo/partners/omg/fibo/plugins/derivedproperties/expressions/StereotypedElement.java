package com.nomagic.cameo.partners.omg.fibo.plugins.derivedproperties.expressions;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Slot;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification;

import java.util.List;

/**
 * User: lvanzandt
 * Date: 7/24/13
 * Time: 7:48 PM
 */
public class StereotypedElement<UnadornedType extends Element>
{
    protected final Optional<UnadornedType> unadornedElement;
    protected final Optional<String> stereotypeName;

    public StereotypedElement (UnadornedType unadornedElement, String stereotypeName)
    {
        this.unadornedElement = Optional.fromNullable(unadornedElement);
        this.stereotypeName = Optional.fromNullable(stereotypeName);
    }

    public List<ValueSpecification> getTagValueValueSpecificationByName (String propertyName)
    {
        if (unadornedElement.isPresent())
        {
            Optional<Property> stereoProperty = Optional.fromNullable(StereotypesHelper.findStereotypePropertyFor
                    (unadornedElement.get(), propertyName));

            if (stereoProperty.isPresent())
            {
                Optional<Slot> slot = Optional.fromNullable(StereotypesHelper.getSlot(unadornedElement.get(),
                        stereoProperty.get(), false));

                if (slot.isPresent())
                {
                    return slot.get().getValue();
                }
            }
        }

        return Lists.newArrayList();
    }
}
