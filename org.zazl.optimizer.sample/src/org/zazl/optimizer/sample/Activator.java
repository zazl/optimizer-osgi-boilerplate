package org.zazl.optimizer.sample;

import org.dojotoolkit.compressor.JSCompressorFactory;
import org.dojotoolkit.compressor.shrinksafe.ShrinksafeJSCompressorFactory;
import org.dojotoolkit.optimizer.JSOptimizerFactory;
import org.dojotoolkit.optimizer.amd.rhinoast.AMDJSOptimizerFactory;
import org.dojotoolkit.optimizer.servlet.JSServlet;
import org.dojotoolkit.server.util.osgi.OSGiResourceLoader;
import org.dojotoolkit.server.util.resource.ResourceLoader;
import org.dojotoolkit.server.util.rhino.RhinoClassLoader;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
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
		this.context = null;
	}
	
	protected boolean register() {
		if (!registered && httpService != null) {
			String[] bundleIds = {
				"org.json.js", 
				"org.dojotoolkit.server.util.js",
				"org.dojotoolkit.optimizer.amd",
				"org.dojotoolkit.optimizer.servlet",
				"org.dojotoolkit.dojo"
			};
			ResourceLoader resourceLoader = new OSGiResourceLoader(context, bundleIds);
			RhinoClassLoader rhinoClassLoader = new RhinoClassLoader(resourceLoader);
			JSCompressorFactory jsCompressorFactory = null;
			Boolean jscompress = Boolean.valueOf(System.getProperty("zazl.jscompress", "false"));
			if (jscompress) {
				jsCompressorFactory = new ShrinksafeJSCompressorFactory();
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
