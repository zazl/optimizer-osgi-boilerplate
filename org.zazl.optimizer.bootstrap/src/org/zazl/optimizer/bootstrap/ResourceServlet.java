package org.zazl.optimizer.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dojotoolkit.server.util.resource.ResourceLoader;

public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResourceLoader resourceLoader = null;
	
	public ResourceServlet(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = request.getPathInfo();
		URL url = resourceLoader.getResource(target);
		if (url != null) {
			String mimeType = getServletContext().getMimeType(target);
			if (mimeType == null) {
				mimeType = "text/plain";
			}
			response.setContentType(mimeType);
			InputStream is = null;
			URLConnection urlConnection = null;
			OutputStream os = null;
			os = response.getOutputStream();
			try {
				urlConnection = url.openConnection();
				long lastModifed = urlConnection.getLastModified();
				if (lastModifed > 0) {
				    String ifNoneMatch = request.getHeader("If-None-Match");
					
				    if (ifNoneMatch != null && ifNoneMatch.equals(Long.toString(lastModifed))) {
				    	response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				        return;
				    }

		 			response.setHeader("ETag", Long.toString(lastModifed));
				}
				is = urlConnection.getInputStream();
				byte[] buffer = new byte[4096];
				int len = 0;
				while((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
			}
			finally {
				if (is != null) {try{is.close();}catch(IOException e){}}
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "path ["+target+"] not found");
		}
	}
}
