/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: jakobk $)
 * @version $Revision: 1124393 $ $Date: 2011-05-18 22:31:56 +0300 (Wed, 18 May 2011) $
 */
public class FacesMessage
        implements Serializable
{
    private static final long serialVersionUID = 4851488727794169661L;

    public static final String FACES_MESSAGES = "javax.faces.Messages";

    public static final FacesMessage.Severity SEVERITY_INFO = new Severity("Info", 1);
    public static final FacesMessage.Severity SEVERITY_WARN = new Severity("Warn", 2);
    public static final FacesMessage.Severity SEVERITY_ERROR = new Severity("Error", 3);
    public static final FacesMessage.Severity SEVERITY_FATAL = new Severity("Fatal", 4);
    public static final List VALUES;
    public static final Map VALUES_MAP;
    static
    {
        Map<String, FacesMessage.Severity> map = new HashMap<String, FacesMessage.Severity>(7);
        map.put(SEVERITY_INFO.toString(), SEVERITY_INFO);
        map.put(SEVERITY_WARN.toString(), SEVERITY_WARN);
        map.put(SEVERITY_ERROR.toString(), SEVERITY_ERROR);
        map.put(SEVERITY_FATAL.toString(), SEVERITY_FATAL);
        VALUES_MAP = Collections.unmodifiableMap(map);

        List<FacesMessage.Severity> severityList = new ArrayList<FacesMessage.Severity>(map.values());
        Collections.sort(severityList); // the JSF spec requires it to be sorted
        VALUES = Collections.unmodifiableList(severityList);
    }

    private transient FacesMessage.Severity _severity;  // transient, b/c FacesMessage.Severity is not Serializable
    private String _summary;
    private String _detail;

    public FacesMessage()
    {
        _severity = SEVERITY_INFO;
    }

    public FacesMessage(String summary)
    {
        _summary = summary;
        _severity = SEVERITY_INFO;
    }

    public FacesMessage(String summary, String detail)
    {
        _summary = summary;
        _detail = detail;
        _severity = SEVERITY_INFO;
    }

    public FacesMessage(FacesMessage.Severity severity,
                        String summary,
                        String detail)
    {
        if(severity == null) throw new NullPointerException("severity");
        _severity = severity;
        _summary = summary;
        _detail = detail;
    }

    public FacesMessage.Severity getSeverity()
    {
        return _severity;
    }

    public void setSeverity(FacesMessage.Severity severity)
    {
        if(severity == null) throw new NullPointerException("severity");
        _severity = severity;
    }

    public String getSummary()
    {
        return _summary;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public String getDetail()
    {
        if (_detail == null)
        {
            // Javadoc:
            // If no localized detail text has been defined for this message, return the localized summary text instead
            return _summary;
        }
        return _detail;
    }

    public void setDetail(String detail)
    {
        _detail = detail;
    }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.defaultWriteObject();  // write summary, detail
        out.writeInt(_severity._ordinal);  // FacesMessage.Severity is not Serializable, write ordinal only
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();  // read summary, detail

        // FacesMessage.Severity is not Serializable, read ordinal and get related FacesMessage.Severity
        int severityOrdinal = in.readInt();
        _severity = (Severity) VALUES.get(severityOrdinal - 1);
    }


    public static class Severity
            implements Comparable
    {
        private String _name;
        private int _ordinal;

        private Severity(String name, int ordinal)
        {
            _name = name;
            _ordinal = ordinal;
        }

        public int getOrdinal()
        {
            return _ordinal;
        }

        public String toString()
        {
            return _name;
        }

        public int compareTo(Object o)
        {
            if (!(o instanceof Severity))
            {
                throw new IllegalArgumentException(o.getClass().getName());
            }
            return getOrdinal() - ((Severity)o).getOrdinal();
        }
    }

}
