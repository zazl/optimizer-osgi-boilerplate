package org.zazl.optimizer.sample.backbone.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
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
	private Map<String, List<Map<String, Object>>> artistAlbums = null;
	private Map<String, List<Map<String, Object>>> albumSongs = null;
	private ResourceLoader resourceLoader = null;
	
	public MusicDataServlet(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	public MusicDataServlet() {}

	public void init(ServletConfig config) throws ServletException {
		if (resourceLoader == null) {
			resourceLoader = new ServletResourceLoader(config.getServletContext());
		}
		artistAlbums = new HashMap<String, List<Map<String, Object>>>();
		albumSongs = new HashMap<String, List<Map<String, Object>>>();
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
			
			for (Map<String, Object> album : albums) {
				String artist = (String)album.get("artist");
				List<Map<String, Object>> l = artistAlbums.get(artist);
				if (l == null) {
					l = new ArrayList<Map<String, Object>>();
					artistAlbums.put(artist, l);
				}
				l.add(album);
			}
			
			for (Map<String, Object> song : songs) {
				String album = (String)song.get("album");
				List<Map<String, Object>> l = albumSongs.get(album);
				if (l == null) {
					l = new ArrayList<Map<String, Object>>();
					albumSongs.put(album, l);
				}
				l.add(song);
			}

			for (Map<String, Object> artist : artists) {
				String name = (String)artist.get("name");
				int albumCount = 0;
				for (Map<String, Object> album : albums) {
					String artistName = (String)album.get("artist");
					if (artistName.equals(name)) {
						albumCount++;
					}
				}
				artist.put("albumCount", albumCount);
				int songCount = 0;
				for (Map<String, Object> song : songs) {
					String artistName = (String)song.get("artist");
					if (artistName.equals(name)) {
						songCount++;
					}
				}
				artist.put("songCount", songCount);
			}
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
			String album = path.substring(path.lastIndexOf('/'));
			List<Map<String, Object>> l = songs;
			if (album.charAt(0) == '/') album = album.substring(1);
			if (album.length() > 0) {
				l = albumSongs.get(album);
			}
			JSONSerializer.serialize(resp.getWriter(), l, true);
		} else if (path.startsWith(ALBUMS_PATH)) {
			resp.setContentType("application/json;charset=UTF-8");
			String artist = path.substring(path.lastIndexOf('/'));
			List<Map<String, Object>> l = albums;
			if (artist.charAt(0) == '/') artist = artist.substring(1);
			if (artist.length() > 0) {
				l = artistAlbums.get(artist);
			}
			JSONSerializer.serialize(resp.getWriter(), l, true);
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
