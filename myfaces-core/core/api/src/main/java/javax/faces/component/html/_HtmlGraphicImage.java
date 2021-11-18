/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIGraphic;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Renders an HTML img element.
 * <p>
 * The value attribute specifies the url of the image to be displayed;
 * see the documentation for attribute "url" for more details.
 * </p>
 */
@JSFComponent
(name = "h:graphicImage",
clazz = "javax.faces.component.html.HtmlGraphicImage",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlGraphicImageTag",
defaultRendererType = "javax.faces.Image"
)
abstract class _HtmlGraphicImage extends UIGraphic 
    implements _EventProperties,
    _StyleProperties, _UniversalProperties, _AltProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Graphic";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlGraphicImage";

  /**
   * HTML: Overrides the natural height of this image, by specifying height in pixels.
   * 
   */
  @JSFProperty
  public abstract String getHeight();

  /**
   * HTML: Specifies server-side image map handling for this image.
   * 
   */
  @JSFProperty(defaultValue="false")
  public abstract boolean isIsmap();
  
  /**
   * HTML: A link to a long description of the image.
   * 
   */
  @JSFProperty
  public abstract String getLongdesc();
  
  /**
   * HTML: Specifies an image map to use with this image.
   * 
   */
  @JSFProperty
  public abstract String getUsemap();
  
  /**
   * HTML: Overrides the natural width of this image, by specifying width in pixels.
   * 
   */
  @JSFProperty
  public abstract String getWidth();

}
