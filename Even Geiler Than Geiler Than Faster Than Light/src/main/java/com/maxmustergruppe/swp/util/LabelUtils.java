package com.maxmustergruppe.swp.util;

import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

/**
 * Utilities for Label.
 *
 * @author Hai Trinh
 */
public class LabelUtils {
    /**
     * Center the text of a label.
     *
     * @param label The label
     */
    public static void centering(final Label label){
        label.setTextHAlignment(HAlignment.Center);
        label.setTextVAlignment(VAlignment.Center);
    }
}
