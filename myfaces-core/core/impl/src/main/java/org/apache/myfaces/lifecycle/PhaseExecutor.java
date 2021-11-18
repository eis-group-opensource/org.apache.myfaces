/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

/**
 * Implements the PhaseExecutor for a lifecycle
 *
 * @author Nikolay Petrov
 *
 */
interface PhaseExecutor {
  
  /**
   * Executes a phase of the JavaServer(tm) Faces lifecycle, like UpdateModelValues.
   * The <code>execute</code> method is called by the lifecylce implementation's private
   * <code>executePhase</code>.
   * @param facesContext The <code>FacesContext</code> for the current request we are processing 
   * @return <code>true</code> if execution should be stopped
   */
    boolean execute(FacesContext facesContext);
    
  /**
   * Returns the <code>PhaseId</code> for which the implemented executor is invoked 
   * @return
   */
  PhaseId getPhase();
}