/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.captcha;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.component.html.util.HtmlComponentUtils;
import org.apache.myfaces.component.html.util.ParameterResourceHandler;
import org.apache.myfaces.custom.captcha.util.CAPTCHAConstants;
import org.apache.myfaces.custom.captcha.util.CAPTCHAImageGenerator;
import org.apache.myfaces.custom.captcha.util.CAPTCHAResponseStream;
import org.apache.myfaces.custom.captcha.util.CAPTCHATextGenerator;
import org.apache.myfaces.custom.captcha.util.ColorGenerator;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.renderkit.html.util.ResourceLoader;

import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

/**
 *
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "org.apache.myfaces.CAPTCHA"
 *   type = "org.apache.myfaces.CAPTCHA"
 * @since 1.1.7
 * @author Hazem Saleh
 *
 */
public class CAPTCHARenderer extends Renderer implements ResourceLoader
{

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {

        CAPTCHAComponent captchaComponent = (CAPTCHAComponent) component;

        generateImageTag(context, captchaComponent);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        super.encodeEnd(context, component);
    }

    /*
     * This helper method is used for generating the img tag that will
     * use the (AddResource) to generate the url of the generated image.
     */
    private void generateImageTag(FacesContext context,
            CAPTCHAComponent component) throws IOException
    {

        AddResource addResource;
        ResponseWriter writer = context.getResponseWriter();
        Map params = HtmlComponentUtils.getParameterMap(component);
        String url;
        String captchaSessionKeyName = component.getCaptchaSessionKeyName();
        String width = component.getImageWidth();
        String height = component.getImageHeight();

        // determine width and height of the generated image.
        if (width == null)
        {
            width = CAPTCHAConstants.DEFAULT_CAPTCHA_WIDTH + "";
        }

        if (height == null)
        {
            height = CAPTCHAConstants.DEFAULT_CAPTCHA_HEIGHT + "";
        }

        writer.startElement(HTML.IMG_ELEM, component);

        // constructing the parameter map to be passed to the (AddResource).
        if (captchaSessionKeyName != null)
        {
            params.put(CAPTCHAComponent.ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME,
                    captchaSessionKeyName);
        }

        // write the url to trigger the (AddResource).
        addResource = AddResourceFactory.getInstance(context);

        url = context.getExternalContext().encodeResourceURL(
                addResource.getResourceUri(context,
                        new ParameterResourceHandler(this.getClass(), params)));

        // adding dummy parameter to prevent caching.
        writer.writeAttribute(HTML.SRC_ATTR, url + "&dummyParameter="
                + System.currentTimeMillis(), null);

        // write rest of attributes.
        writer.writeAttribute(HTML.WIDTH_ATTR, width, null);

        writer.writeAttribute(HTML.HEIGHT_ATTR, height, null);

        writer.endElement(HTML.IMG_ELEM);
    }

    /*
     * This method is implemented to be called from the (AddResource).
     * It wraps the CAPTCHA image generation.
     */
    public void serveResource(ServletContext servletContext,
            HttpServletRequest request, HttpServletResponse response,
            String resourceUri) throws IOException
    {

        // get the FacesContext from the ServletContext.
        FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(HtmlComponentUtils
                .getLifecycleId(servletContext));
        FacesContext facesContext = facesContextFactory.getFacesContext(
                servletContext, request, response, lifecycle);
        facesContext.setResponseStream(new CAPTCHAResponseStream(response
                .getOutputStream()));

        // render the CAPTCHA.
        try
        {
            try
            {
                renderCAPTCHA(facesContext);
            }
            catch (IOException e)
            {
                throw new FacesException("Could not render the CAPTCHA : "
                        + e.getMessage(), e);
            }
            facesContext.getResponseStream().close();
        }
        finally
        {
            facesContext.release();
        }
    }

    /*
     * This method is used for rendering the CAPTCHA component.
     */
    protected void renderCAPTCHA(FacesContext facesContext) throws IOException
    {

        // getting CAPTCHA attributes.
        HttpServletResponse response = (HttpServletResponse) facesContext
                .getExternalContext().getResponse();
        ResponseStream out = facesContext.getResponseStream();
        Map requestMap = facesContext.getExternalContext()
                .getRequestParameterMap();
        String captchaSessionKeyName = requestMap.get(
                CAPTCHAComponent.ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME).toString();

        // construct the CAPTCHA image generator object.
        CAPTCHAImageGenerator captchaImageGenerator = new CAPTCHAImageGenerator();

        try
        {
            String captchaText;
            Color endingColor = ColorGenerator.generateRandomColor(null);
            Color startingColor = ColorGenerator
                    .generateRandomColor(endingColor);

            // Generate random CAPTCHA text.
            captchaText = CAPTCHATextGenerator.generateRandomText();

            // Set the generated text in the user session.
            facesContext.getExternalContext().getSessionMap().put(
                    captchaSessionKeyName, captchaText);

            // Generate the image, the BG color is randomized from starting to ending colors.
            captchaImageGenerator.generateImage(response, captchaText,
                    startingColor, endingColor);
        }
        finally
        {
            out.close();
            facesContext.responseComplete();
        }
    }
}
