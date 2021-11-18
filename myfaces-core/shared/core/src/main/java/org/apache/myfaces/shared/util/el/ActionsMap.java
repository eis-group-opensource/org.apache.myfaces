/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util.el;

import java.util.*;

/**
 * @author Sylvain Vieujot (latest modification by $Author: skitching $)
 * @version $Revision: 355303 $ $Date: 2005-12-09 02:36:08 +0100 (Fr, 09 Dez 2005) $
 *
 * You can use this class to trigger an action when a boolean is set to true.
 *
 * Example : in JSF pages, for dataTable, to remove elements :
 * Backing bean (#{inboxFace}).
 * public ActionsMap getRemoveEmailUnid(){
 *         return new ActionsMap(){
 *            public void performAction(String unid) {
 *                InboxMailDAO<TInboxMail> dao = getInboxMailDAO();
 *                TInboxMail email = dao.getByPrimaryKey( unid );
 *                dao.remove( email );
 *            }
 *        };
 *    }
 * JSF page :
 * &lt;h:selectBooleanCheckbox value="#{inboxFace.removeEmailUnid[email.unid]}"/&gt;
 */
public abstract class ActionsMap implements Map {

    private Set keys;

    public ActionsMap(){
        // NoOp
    }

    public ActionsMap(Set keys){
        this.keys = keys;
    }

    /**
     * This method should fire the command.
     */
    public abstract void performAction(String command);

    public int size() {
        return keys.size();
    }

    public boolean isEmpty() {
        return keys.isEmpty();
    }

    public boolean containsKey(Object key) {
        return keys.contains( key );
    }

    public boolean containsValue(Object value) {
        if( ! (value instanceof Boolean) )
            return false;
        return ((Boolean)value).booleanValue();
    }

    public Object get( Object key) {
        return Boolean.FALSE;
    }

    public Boolean put(String key, Boolean value) {
        if( value!=null && value.booleanValue() )
            performAction( key );
        return Boolean.FALSE;
    }

    public Object remove(Object key) {
        if( keys.remove( key ) )
            return Boolean.FALSE;
        return null;
    }

    public void putAll(Map map) {
        Iterator it = map.entrySet().iterator();

        while (it.hasNext())
        {
            Entry entry = (Entry) it.next();
            Object obj = entry.getValue();
            if( (obj instanceof Boolean) && ((Boolean) obj).booleanValue() )
                performAction( (String) entry.getKey() );
        }
    }

    public void clear() {
        keys.clear();
    }

    public Set keySet() {
        return keys;
    }

    public Collection values() {
        return Collections.nCopies(keys.size(), Boolean.FALSE);
    }

    public Set entrySet() {
        Set set = new HashSet( keys.size() );

        Iterator it = keys.iterator();

        while (it.hasNext())
        {
            String command = (String) it.next();
            set.add( new CommandEntry(command) );
        }

        return set;
    }

    private class CommandEntry implements Entry{

        private final String command;
        private boolean commandPerformed = false;

        public CommandEntry(String command){
            this.command = command;
        }

        public Object getKey() {
            return command;
        }

        public Object getValue() {
            return Boolean.valueOf(commandPerformed);
        }

        public Object setValue(Object performCommand) {
            if( (performCommand instanceof Boolean) && ((Boolean)performCommand).booleanValue() ){
                performAction( command );
                commandPerformed = true;
            }
            return Boolean.valueOf(commandPerformed);
        }
    }
}

