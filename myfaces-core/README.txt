Exigen modified version of Apache MyFaces Core 1.2.7 and Apache MyFaces Shared 3.0.6

Changed:
 - org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils was modified to support IE 9, because of MIME Handling changes
	url: http://blogs.msdn.com/b/ie/archive/2010/10/26/mime-handling-changes-in-internet-explorer.aspx
 - org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder was modified to support apostrophe escaping
	EISISSUE-4223 not escaping apostrophe in inputTextarea, after ajax request/response it breaks inputTextarea field.
