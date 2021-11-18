/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.event;

import java.util.ArrayList;
import java.util.Collections;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class PhaseId implements Comparable{

    // FIELDS
    public static final javax.faces.event.PhaseId ANY_PHASE;
    public static final javax.faces.event.PhaseId APPLY_REQUEST_VALUES;
    public static final javax.faces.event.PhaseId INVOKE_APPLICATION;
    public static final javax.faces.event.PhaseId PROCESS_VALIDATIONS;
    public static final javax.faces.event.PhaseId RENDER_RESPONSE;
    public static final javax.faces.event.PhaseId RESTORE_VIEW;
    public static final javax.faces.event.PhaseId UPDATE_MODEL_VALUES;
    public static final java.util.List VALUES;

    static
    {
        int i = 0;
        ArrayList<PhaseId> list = new ArrayList<PhaseId>(6);

        ANY_PHASE = new PhaseId("ANY_PHASE",i++);
        list.add(ANY_PHASE);
        RESTORE_VIEW = new PhaseId("RESTORE_VIEW",i++);
        list.add(RESTORE_VIEW);
        APPLY_REQUEST_VALUES = new PhaseId("APPLY_REQUEST_VALUES",i++);
        list.add(APPLY_REQUEST_VALUES);
        PROCESS_VALIDATIONS = new PhaseId("PROCESS_VALIDATIONS",i++);
        list.add(PROCESS_VALIDATIONS);
        UPDATE_MODEL_VALUES = new PhaseId("UPDATE_MODEL_VALUES",i++);
        list.add(UPDATE_MODEL_VALUES);
        INVOKE_APPLICATION = new PhaseId("INVOKE_APPLICATION",i++);
        list.add(INVOKE_APPLICATION);
        RENDER_RESPONSE = new PhaseId("RENDER_RESPONSE",i++);
        list.add(RENDER_RESPONSE);
        VALUES = Collections.unmodifiableList(list);
    }


    private final String _name;
    private final int _ordinal;

    // CONSTRUCTORS
    private PhaseId(String name, int ordinal)
    {
        this._name = name;
        this._ordinal = ordinal;
    }

    // METHODS
    public int compareTo(Object other)
    {
        return _ordinal - ((PhaseId)other)._ordinal;
    }

    public int getOrdinal()
    {
        return _ordinal;
    }

    public String toString()
    {
        return _name + "(" + _ordinal + ")";
    }

}
