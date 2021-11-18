/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFListener;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * UICommand is a base abstraction for components that implement ActionSource.
 * <p>
 * See the javadoc for this class in the <a
 * href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF
 * Specification</a> for further details.
 * <p>
 */
@JSFComponent(defaultRendererType = "javax.faces.Text")
public class UIInput extends UIOutput implements EditableValueHolder
{
    public static final String COMPONENT_TYPE = "javax.faces.Input";
    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String CONVERSION_MESSAGE_ID = "javax.faces.component.UIInput.CONVERSION";
    public static final String REQUIRED_MESSAGE_ID = "javax.faces.component.UIInput.REQUIRED";
    public static final String UPDATE_MESSAGE_ID = "javax.faces.component.UIInput.UPDATE";
    private static final String ERROR_HANDLING_EXCEPTION_LIST = "org.apache.myfaces.errorHandling.exceptionList";

    private static final Validator[] EMPTY_VALIDATOR_ARRAY = new Validator[0];

    private boolean _immediate;
    private boolean _immediateSet;

    private Object _submittedValue;
    private boolean _localValueSet = false;

    private boolean _valid = true;

    private boolean _required;
    private boolean _requiredSet;

    private String _converterMessage;
    private String _requiredMessage;
    private String _validatorMessage;

    private MethodBinding _validator;
    private List<Validator> _validatorList;

    private MethodBinding _valueChangeListener;

    /**
     * Construct an instance of the UIInput.
     */
    public UIInput()
    {
        setRendererType("javax.faces.Text");
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    /**
     * Store the specified object as the "local value" of this component. The
     * value-binding named "value" (if any) is ignored; the object is only
     * stored locally on this component. During the "update model" phase, if
     * there is a value-binding named "value" then this local value will be
     * stored via that value-binding and the "local value" reset to null.
     */
    public void setValue(Object value)
    {
        setLocalValueSet(true);
        super.setValue(value);
    }
    
    /**
     * Return the current value of this component.
     * <p>
     * If a submitted value has been converted but not yet pushed into the
     * model, then return that locally-cached value (see isLocalValueSet).
     * <p>
     * Otherwise, evaluate an EL expression to fetch a value from the model. 
     */
    public Object getValue()
    {
        if (isLocalValueSet()) return super.getLocalValue();
        return super.getValue();
    }

    /**
     * Set the "submitted value" of this component from the relevant data in the
     * current servlet request object.
     * <p>
     * If this component is not rendered, then do nothing; no output would have
     * been sent to the client so no input is expected.
     * <p>
     * Invoke the inherited functionality, which typically invokes the renderer
     * associated with this component to extract and set this component's
     * "submitted value".
     * <p>
     * If this component is marked "immediate", then immediately apply
     * validation to the submitted value found. On error, call context method
     * "renderResponse" which will force processing to leap to the "render
     * response" phase as soon as the "decode" step has completed for all other
     * components.
     */
    public void processDecodes(FacesContext context)
    {
        if (context == null)
        {
            throw new NullPointerException("context");
        }
        try
        {
            setCachedFacesContext(context);
            if (!isRendered())
            {
                return;
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }
        super.processDecodes(context);
        try
        {
            setCachedFacesContext(context);
            if (isImmediate())
            {
                try
                {
                    validate(context);
                }
                catch (RuntimeException e)
                {
                    context.renderResponse();
                    throw e;
                }
                if (!isValid())
                {
                    context.renderResponse();
                }
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }
    }

    public void processValidators(FacesContext context)
    {
        if (context == null)
        {
            throw new NullPointerException("context");
        }
        try
        {
            setCachedFacesContext(context);
            if (!isRendered())
            {
                return;
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }

        super.processValidators(context);

        try
        {
            setCachedFacesContext(context);
            if (!isImmediate())
            {
                try
                {
                    validate(context);
                }
                catch (RuntimeException e)
                {
                    context.renderResponse();
                    throw e;
                }
                if (!isValid())
                {
                    context.renderResponse();
                }
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }
    }

    public void processUpdates(FacesContext context)
    {
        if (context == null)
        {
            throw new NullPointerException("context");
        }
        try
        {
            setCachedFacesContext(context);
            if (!isRendered())
            {
                return;
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }

        super.processUpdates(context);

        try
        {
            setCachedFacesContext(context);
            try
            {
                updateModel(context);
            }
            catch (RuntimeException e)
            {
                context.renderResponse();
                throw e;
            }
            if (!isValid())
            {
                context.renderResponse();
            }
        }
        finally
        {
            setCachedFacesContext(null);
        }
    }

    public void decode(FacesContext context)
    {
        // We (re)set to valid, so that component automatically gets (re)validated
        setValid(true);
        super.decode(context);
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        // invoke standard listeners attached to this component first
        super.broadcast(event);

        // Check if the event is applicable for ValueChangeListener
        if (event instanceof ValueChangeEvent)
        {
            // invoke the single listener defined directly on the component
            MethodBinding valueChangeListenerBinding = getValueChangeListener();
            if (valueChangeListenerBinding != null)
            {
                try
                {
                    valueChangeListenerBinding.invoke(getFacesContext(),
                            new Object[]
                            { event });
                }
                catch (EvaluationException e)
                {
                    Throwable cause = e.getCause();
                    if (cause != null
                            && cause instanceof AbortProcessingException)
                    {
                        throw (AbortProcessingException) cause;
                    }
                    else
                    {
                        throw e;
                    }
                }
            }
        }
    }

    public void updateModel(FacesContext context)
    {
        if (!isValid())
        {
            return;
        }
        if (!isLocalValueSet())
        {
            return;
        }
        ValueExpression expression = getValueExpression("value");
        if (expression == null)
        {
            return;
        }

        try
        {
            expression.setValue(context.getELContext(), getLocalValue());
            setValue(null);
            setLocalValueSet(false);
        }
        catch (Exception e)
        {
            context.getExternalContext().log(e.getMessage(), e);
            _MessageUtils.addErrorMessage(context, this, UPDATE_MESSAGE_ID,
                    new Object[]
                    { _MessageUtils.getLabel(context, this) });
            setValid(false);

            /*
             * we are not allowed to throw exceptions here - we still need the
             * full stack-trace later on to process it later in our
             * error-handler
             */
            queueExceptionInRequest(context, expression, e);
        }
    }

    /**
     * For development and production, we want to offer a single point to which
     * error-handlers can attach. So we queue up all ocurring exceptions and
     * later pass them to the configured error-handler.
     */
    private void queueExceptionInRequest(FacesContext context,
            ValueExpression expression, Exception e)
    {
        List li = (List) context.getExternalContext().getRequestMap().get(ERROR_HANDLING_EXCEPTION_LIST);
        if (null == li)
        {
            li = new ArrayList();
            context.getExternalContext().getRequestMap().put(ERROR_HANDLING_EXCEPTION_LIST, li);
        }
        li.add(new FacesException(
                "Exception while setting value for expression : "
                        + expression.getExpressionString()
                        + " of component with path : "
                        + _ComponentUtils.getPathToComponent(this), e));
    }

    protected void validateValue(FacesContext context, Object convertedValue)
    {
        boolean empty = convertedValue == null
                || (convertedValue instanceof String && ((String) convertedValue)
                        .length() == 0);

        if (isRequired() && empty)
        {
            if (getRequiredMessage() != null)
            {
                String requiredMessage = getRequiredMessage();
                context.addMessage(this.getClientId(context), new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, requiredMessage,
                        requiredMessage));
            }
            else
            {
                _MessageUtils.addErrorMessage(context, this,
                        REQUIRED_MESSAGE_ID, new Object[]
                        { _MessageUtils.getLabel(context, this) });
            }
            setValid(false);
            return;
        }

        if (!empty)
        {
            _ComponentUtils.callValidators(context, this, convertedValue);
        }

    }

    /**
     * Determine whether the new value is valid, and queue a ValueChangeEvent if
     * necessary.
     * <p>
     * The "submitted value" is converted to the necessary type; conversion
     * failure is reported as an error and validation processing terminates for
     * this component. See documentation for method getConvertedValue for
     * details on the conversion process.
     * <p>
     * Any validators attached to this component are then run, passing the
     * converted value.
     * <p>
     * The old value of this component is then fetched (possibly involving the
     * evaluation of a value-binding expression, ie invoking a method on a user
     * object). The old value is compared to the new validated value, and if
     * they are different then a ValueChangeEvent is queued for later
     * processing.
     * <p>
     * On successful completion of this method:
     * <ul>
     * <li> isValid() is true
     * <li> isLocalValueSet() is true
     * <li> submittedValue is reset to null
     * <li> a ValueChangeEvent is queued if the new value != old value
     * </ul>
     */
    public void validate(FacesContext context)
    {
        if (context == null)
            throw new NullPointerException("context");

        try
        {

            Object submittedValue = getSubmittedValue();
            if (submittedValue == null)
                return;

            Object convertedValue = getConvertedValue(context, submittedValue);

            if (!isValid())
                return;

            validateValue(context, convertedValue);

            if (!isValid())
                return;

            Object previousValue = getValue();
            setValue(convertedValue);
            setSubmittedValue(null);
            if (compareValues(previousValue, convertedValue))
            {
                queueEvent(new ValueChangeEvent(this, previousValue,
                        convertedValue));
            }
        }
        catch (Exception ex)
        {
            throw new FacesException(
                    "Exception while validating component with path : "
                            + _ComponentUtils.getPathToComponent(this), ex);
        }

    }

    /**
     * Convert the provided object to the desired value.
     * <p>
     * If there is a renderer for this component, then call the renderer's
     * getConvertedValue method. While this can of course be implemented in any
     * way the renderer desires, it typically performs exactly the same
     * processing that this method would have done anyway (ie that described
     * below for the no-renderer case).
     * <p>
     * Otherwise:
     * <ul>
     * <li>If the submittedValue is not a String then just return the
     * submittedValue unconverted.
     * <li>If there is no "value" value-binding then just return the
     * submittedValue unconverted.
     * <li>Use introspection to determine the type of the target property
     * specified by the value-binding, and then use Application.createConverter
     * to find a converter that can map from String to the required type. Apply
     * the converter to the submittedValue and return the result.
     * </ul>
     */
    protected Object getConvertedValue(FacesContext context, Object submittedValue)
    {
        try
        {
            Renderer renderer = getRenderer(context);
            if (renderer != null)
            {
                return renderer
                        .getConvertedValue(context, this, submittedValue);
            }
            else if (submittedValue instanceof String)
            {
                Converter converter = _SharedRendererUtils
                        .findUIOutputConverter(context, this);
                if (converter != null)
                {
                    return converter.getAsObject(context, this,
                            (String) submittedValue);
                }
            }
        }
        catch (ConverterException e)
        {
            String converterMessage = getConverterMessage();
            if (converterMessage != null)
            {
                context.addMessage(getClientId(context), new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, converterMessage,
                        converterMessage));
            }
            else
            {
                FacesMessage facesMessage = e.getFacesMessage();
                if (facesMessage != null)
                {
                    context.addMessage(getClientId(context), facesMessage);
                }
                else
                {
                    _MessageUtils.addErrorMessage(context, this,
                            CONVERSION_MESSAGE_ID, new Object[]
                            { _MessageUtils.getLabel(context, this) });
                }
            }
            setValid(false);
        }
        return submittedValue;
    }

    protected boolean compareValues(Object previous, Object value)
    {
        return previous == null ? (value != null) : (!previous.equals(value));
    }

    /**
     * @since 1.2
     */
    public void resetValue()
    {
        setSubmittedValue(null);
        setValue(null);
        setLocalValueSet(false);
        setValid(true);
    }

    /**
     * A boolean value that identifies the phase during which action events should fire.
     * <p>
     * During normal event processing, action methods and action listener methods are fired during
     * the "invoke application" phase of request processing. If this attribute is set to "true",
     * these methods are fired instead at the end of the "apply request values" phase.
     * </p>
     */
    @JSFProperty
    public boolean isImmediate()
    {
        if (_immediateSet)
        {
            return _immediate;
        }
        ValueExpression expression = getValueExpression("immediate");
        if (expression != null)
        {
            return (Boolean) expression.getValue(getFacesContext()
                    .getELContext());
        }
        return false;
    }

    public void setImmediate(boolean immediate)
    {
        this._immediate = immediate;
        this._immediateSet = true;
    }

    /**
     * A boolean value that indicates whether an input value is required.
     * <p>
     * If this value is true and no input value is provided by a postback operation, then
     * the "requiredMessage" text is registered as a FacesMessage for the request, and
     * validation fails. 
     * </p>
     * <p>
     * Default value: false.
     * </p>
     */
    @JSFProperty(defaultValue = "false")
    public boolean isRequired()
    {
        if (_requiredSet)
        {
            return _required;
        }
        ValueExpression expression = getValueExpression("required");
        if (expression != null)
        {
            return (Boolean) expression.getValue(getFacesContext()
                    .getELContext());
        }
        return false;
    }

    public void setRequired(boolean required)
    {
        this._required = required;
        this._requiredSet = true;
    }

    /**
     * Text to be displayed to the user as an error message when conversion of a
     * submitted value to the target type fails.
     * <p>
     * </p>
     */
    @JSFProperty
    public String getConverterMessage()
    {
        if (_converterMessage != null)
        {
            return _converterMessage;
        }
        ValueExpression expression = getValueExpression("converterMessage");
        if (expression != null)
        {
            return (String) expression.getValue(getFacesContext()
                    .getELContext());
        }
        return null;
    }

    public void setConverterMessage(String converterMessage)
    {
        this._converterMessage = converterMessage;
    }

    /**
     * Text to be displayed to the user as an error message when this component is
     * marked as "required" but no input data is present during a postback (ie the
     * user left the required field blank).
     */
    @JSFProperty
    public String getRequiredMessage()
    {
        if (_requiredMessage != null)
        {
            return _requiredMessage;
        }
        ValueExpression expression = getValueExpression("requiredMessage");
        if (expression != null)
        {
            return (String) expression.getValue(getFacesContext()
                    .getELContext());
        }
        return null;
    }

    public void setRequiredMessage(String requiredMessage)
    {
        this._requiredMessage = requiredMessage;
    }

    /**
     * A method-binding EL expression which is invoked during the validation phase for this
     * component.
     * <p>
     * The invoked method is expected to check the submitted value for this component, and if not
     * acceptable then report a validation error for the component.
     * </p>
     * <p>
     * The method is expected to have the prototype
     * </p>
     * <code>public void aMethod(FacesContext, UIComponent,Object)</code>
     * 
     * @deprecated
     */
    @JSFProperty(stateHolder = true, returnSignature = "void", methodSignature = "javax.faces.context.FacesContext,javax.faces.component.UIComponent,java.lang.Object")
    public MethodBinding getValidator()
    {
        if (_validator != null)
        {
            return _validator;
        }
        ValueExpression expression = getValueExpression("validator");
        if (expression != null)
        {
            return (MethodBinding) expression.getValue(getFacesContext()
                    .getELContext());
        }
        return null;
    }

    /** See getValidator.
     *  
     * @deprecated 
     */
    public void setValidator(MethodBinding validator)
    {
        this._validator = validator;
    }

    /** See getValidator. */
    public void addValidator(Validator validator)
    {
        if (validator == null)
            throw new NullPointerException("validator");
        if (_validatorList == null)
            _validatorList = new ArrayList<Validator>();

        _validatorList.add(validator);
    }

    /** See getValidator. */
    public void removeValidator(Validator validator)
    {
        if (validator == null || _validatorList == null)
            return;

        _validatorList.remove(validator);
    }

    /** See getValidator. */
    public Validator[] getValidators()
    {
        return _validatorList == null ? EMPTY_VALIDATOR_ARRAY : _validatorList
                .toArray(new Validator[_validatorList.size()]);
    }

    /**
     * Text which will be shown if validation fails.
     */
    @JSFProperty
    public String getValidatorMessage()
    {
        if (_validatorMessage != null)
        {
            return _validatorMessage;
        }
        ValueExpression expression = getValueExpression("validatorMessage");
        if (expression != null)
        {
            return (String) expression.getValue(getFacesContext()
                    .getELContext());
        }
        return null;
    }

    public void setValidatorMessage(String validatorMessage)
    {
        this._validatorMessage = validatorMessage;
    }

    /**
     * A method which is invoked during postback processing for the current
     * view if the submitted value for this component is not equal to the value
     * which the "value" expression for this component returns.
     * <p>
     * The phase in which this method is invoked can be controlled via the immediate
     * attribute.
     * </p>
     * 
     * @deprecated
     */
    @JSFProperty(stateHolder = true, returnSignature = "void", methodSignature = "javax.faces.event.ValueChangeEvent")
    public MethodBinding getValueChangeListener()
    {
        if (_valueChangeListener != null)
        {
            return _valueChangeListener;
        }
        ValueExpression expression = getValueExpression("valueChangeListener");
        if (expression != null)
        {
            return (MethodBinding) expression.getValue(getFacesContext()
                    .getELContext());
        }
        return null;
    }

    /**
     * See getValueChangeListener.
     * 
     * @deprecated
     */
    public void setValueChangeListener(MethodBinding valueChangeListener)
    {
        this._valueChangeListener = valueChangeListener;
    }

    /**
     * Specifies whether the component's value is currently valid, ie whether the
     * validators attached to this component have allowed it.
     */
    @JSFProperty(defaultValue = "true", tagExcluded = true)
    public boolean isValid()
    {
        return _valid;
    }

    public void setValid(boolean valid)
    {
        this._valid = valid;
    }

    /**
     * Specifies whether a local value is currently set.
     * <p>
     * If false, values are being retrieved from any attached ValueBinding.
     */
    @JSFProperty(defaultValue = "false", tagExcluded = true)
    public boolean isLocalValueSet()
    {
        return _localValueSet;
    }

    public void setLocalValueSet(boolean localValueSet)
    {
        this._localValueSet = localValueSet;
    }

    /**
     * Gets the current submitted value. This value, if non-null, is set by the
     * Renderer to store a possibly invalid value for later conversion or
     * redisplay, and has not yet been converted into the proper type for this
     * component instance. This method should only be used by the decode() and
     * validate() method of this component, or its corresponding Renderer;
     * however, user code may manually set it to null to erase any submitted
     * value.
     */
    @JSFProperty(tagExcluded = true)
    public Object getSubmittedValue()
    {
        return _submittedValue;
    }

    public void setSubmittedValue(Object submittedValue)
    {
        this._submittedValue = submittedValue;
    }

    public void addValueChangeListener(ValueChangeListener listener)
    {
        addFacesListener(listener);
    }

    public void removeValueChangeListener(ValueChangeListener listener)
    {
        removeFacesListener(listener);
    }

    /**
     * The valueChange event is delivered when the value attribute
     * is changed.
     */
    @JSFListener(event="javax.faces.event.ValueChangeEvent")
    public ValueChangeListener[] getValueChangeListeners()
    {
        return (ValueChangeListener[]) getFacesListeners(ValueChangeListener.class);
    }

    @Override
    public Object saveState(FacesContext facesContext)
    {
        Object[] values = new Object[14];
        values[0] = super.saveState(facesContext);
        values[1] = _immediate;
        values[2] = _immediateSet;
        values[3] = _required;
        values[4] = _requiredSet;
        values[5] = _converterMessage;
        values[6] = _requiredMessage;
        values[7] = saveAttachedState(facesContext, _validator);
        values[8] = saveAttachedState(facesContext, _validatorList);
        values[9] = _validatorMessage;
        values[10] = saveAttachedState(facesContext, _valueChangeListener);
        values[11] = _valid;
        values[12] = _localValueSet;
        values[13] = _submittedValue;

        return values;
    }

    @Override
    public void restoreState(FacesContext facesContext, Object state)
    {
        Object[] values = (Object[]) state;
        super.restoreState(facesContext, values[0]);
        _immediate = (Boolean) values[1];
        _immediateSet = (Boolean) values[2];
        _required = (Boolean) values[3];
        _requiredSet = (Boolean) values[4];
        _converterMessage = (String) values[5];
        _requiredMessage = (String) values[6];
        _validator = (MethodBinding) restoreAttachedState(facesContext,
                values[7]);
        _validatorList = (List<Validator>) restoreAttachedState(facesContext,
                values[8]);
        _validatorMessage = (String) values[9];
        _valueChangeListener = (MethodBinding) restoreAttachedState(
                facesContext, values[10]);
        _valid = (Boolean) values[11];
        _localValueSet = (Boolean) values[12];
        _submittedValue = values[13];
    }
}
