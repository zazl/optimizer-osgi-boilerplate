package org.zazl.optimizer.sample.backbone.server;

import javax.servlet.ServletException;

import org.dojotoolkit.server.util.osgi.OSGiResourceLoader;
import org.dojotoolkit.server.util.resource.ResourceLoader;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
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
		httpServiceTracker.close();
		httpServiceTracker = null;
		this.context = null;
	}

	private class HttpServiceTracker extends ServiceTracker {
		public HttpServiceTracker(BundleContext context) {
			super(context, HttpService.class.getName(), null);
		}

		public Object addingService(ServiceReference reference) {
			httpServiceReference = reference;
			httpService = (HttpService) context.getService(reference);
			if (!registered) {
				try {
					ResourceLoader resourceLoader = new OSGiResourceLoader(context, new String[]{});
					System.out.println("Registering Music Data servlet");
					httpService.registerServlet("/music", new MusicDataServlet(resourceLoader), null, null);
					registered = true;
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (NamespaceException e) {
					e.printStackTrace();
				}
			}
			return httpService;
		}

		public void removedService(ServiceReference reference, Object service) {
			if (registered) {
				httpService.unregister("/music");
			}
			super.removedService(reference, service);
		}			
	}
}
