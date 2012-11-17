package org.zazl.optimizer.sample.backbone.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dojotoolkit.json.JSONParser;
import org.dojotoolkit.json.JSONSerializer;
import org.dojotoolkit.optimizer.servlet.ServletResourceLoader;
import org.dojotoolkit.server.util.resource.ResourceLoader;

public class MusicDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SONGS_PATH = "/songs";
	private static final String ALBUMS_PATH = "/albums";
	private static final String ARTISTS_PATH = "/artists";
	private List<Map<String, Object>> songs = null;
	private List<Map<String, Object>> albums = null;
	private List<Map<String, Object>> artists = null;
	private ResourceLoader resourceLoader = null;
	
	public MusicDataServlet(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	public MusicDataServlet() {}

	public void init(ServletConfig config) throws ServletException {
		if (resourceLoader == null) {
			resourceLoader = new ServletResourceLoader(config.getServletContext());
		}
		InputStream is = null;
		try {
			is = resourceLoader.getResource("/data/songs.json").openStream();
			Reader r = new InputStreamReader(is);
			songs = (List<Map<String, Object>>)JSONParser.parse(r);
			is.close();
			is = resourceLoader.getResource("/data/albums.json").openStream();
			r = new InputStreamReader(is);
			albums = (List<Map<String, Object>>)JSONParser.parse(r);
			is.close();
			is = resourceLoader.getResource("/data/artists.json").openStream();
			r = new InputStreamReader(is);
			artists = (List<Map<String, Object>>)JSONParser.parse(r);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) { try { is.close(); } catch (IOException e) {}}
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		if (path.startsWith(SONGS_PATH)) {
			resp.setContentType("application/json;charset=UTF-8");
			JSONSerializer.serialize(resp.getWriter(), songs, true);
		} else if (path.startsWith(ALBUMS_PATH)) {
			resp.setContentType("application/json;charset=UTF-8");
			JSONSerializer.serialize(resp.getWriter(), albums, true);
		} else if (path.startsWith(ARTISTS_PATH)) {
			resp.setContentType("application/json;charset=UTF-8");
			JSONSerializer.serialize(resp.getWriter(), artists, true);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}
}
