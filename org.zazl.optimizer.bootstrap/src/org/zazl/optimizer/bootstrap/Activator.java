package org.zazl.optimizer.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dojotoolkit.compressor.JSCompressorFactory;
import org.dojotoolkit.compressor.closure.ClosureJSCompressorFactory;
import org.dojotoolkit.json.JSONParser;
import org.dojotoolkit.optimizer.JSOptimizerFactory;
import org.dojotoolkit.optimizer.amd.rhinoast.AMDJSOptimizerFactory;
import org.dojotoolkit.optimizer.servlet.JSServlet;
import org.dojotoolkit.server.util.osgi.OSGiResourceLoader;
import org.dojotoolkit.server.util.resource.ResourceLoader;
import org.dojotoolkit.server.util.rhino.RhinoClassLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
	private static final String[] defaultBundleIds = {
	  	"org.json.js", 
		"org.dojotoolkit.server.util.js",
		"org.dojotoolkit.optimizer.amd",
		"org.dojotoolkit.optimizer.servlet"
	};
	private BundleContext context;
	private ServiceTracker httpServiceTracker = null;
	private HttpService httpService = null;
	private ServiceReference httpServiceReference = null;
	private boolean registered = false;

	public void start(BundleContext bundleContext) throws Exception {
		this.context = bundleContext;
		httpServiceTracker = new HttpServiceTracker(bundleContext);
		httpServiceTracker.open();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		httpServiceTracker.close();
		httpServiceTracker = null;
		this.context = null;
	}
	
	protected boolean register() {
		if (!registered && httpService != null) {
			InputStream is = null;
			List<String> bundleIdList = new ArrayList<String>(Arrays.asList(defaultBundleIds));
			Bundle b = null;
			String bundleId = System.getProperty("zazl.idsprovider");
			if (bundleId != null) {
				bundleIdList.add(bundleId);
				b = findBundle(bundleId);
			}
			if (b == null) {
				b = context.getBundle();
			}
			try {
				is = b.getResource("bundleids.json").openStream();
				Reader reader = new InputStreamReader(is);
				bundleIdList.addAll((List<String>)JSONParser.parse(reader));
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				if (is != null){ try { is.close(); } catch (IOException e) {}}
			}
			String[] bundleIds = new String[bundleIdList.size()];
			bundleIds = bundleIdList.toArray(bundleIds);
			ResourceLoader resourceLoader = new OSGiResourceLoader(context, bundleIds);
			RhinoClassLoader rhinoClassLoader = new RhinoClassLoader(resourceLoader);
			JSCompressorFactory jsCompressorFactory = null;
			Boolean jscompress = Boolean.valueOf(System.getProperty("zazl.jscompress", "false"));
			if (jscompress) {
				jsCompressorFactory = new ClosureJSCompressorFactory();
			}
			JSOptimizerFactory jsOptimizerFactory = new AMDJSOptimizerFactory();
	
			JSServlet jsServlet = new JSServlet(resourceLoader, jsOptimizerFactory, rhinoClassLoader, "zazl", null, null, jsCompressorFactory);
			ResourceServlet resourceServlet = new ResourceServlet(resourceLoader);
			try {
				System.out.println("Registering Zazl JavaScript servlet");
				httpService.registerServlet("/_javascript", jsServlet, null, null);
				System.out.println("Registering Resource servlet");
				httpService.registerServlet("/", resourceServlet , null, null);
				registered = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return registered;
	}
	
	private Bundle findBundle(String id) {
		Bundle[] bundles = context.getBundles();
		for (Bundle b : bundles) {
			if (b.getSymbolicName().equals(id)) {
				return b;
			}
		}
		return null;
	}
	
	private class HttpServiceTracker extends ServiceTracker {
		public HttpServiceTracker(BundleContext context) {
			super(context, HttpService.class.getName(), null);
		}

		public Object addingService(ServiceReference reference) {
			httpServiceReference = reference;
			httpService = (HttpService) context.getService(reference);
			register();
			return httpService;
		}

		public void removedService(ServiceReference reference, Object service) {
			if (registered) {
				httpService.unregister("/");
				httpService.unregister("/_javascript");
			}
			super.removedService(reference, service);
		}			
	}
}
