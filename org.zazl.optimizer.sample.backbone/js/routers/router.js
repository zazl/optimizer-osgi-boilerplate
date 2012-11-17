define([
	'jquery', 
	'backbone', 
	'underscore', 
	'models/SongList',
	'views/SongListView',
	'models/ArtistList',
	'views/ArtistListView',
	'models/AlbumList',
	'views/AlbumListView'
	], 
function($, Backbone, _, SongList, SongListView, ArtistList, ArtistListView, AlbumList, AlbumListView){
	var Router = Backbone.Router.extend({
		initialize: function(){
			Backbone.history.start();
		},
		routes: {
			'songs': 'songs',
			'albums': 'albums',
			'artists': 'artists',
			'': 'artists'
		},
		'songs': function() {
			var songlist = new SongList();
			songlist.fetch({
				success: function(collection, response, options) {
					new SongListView({songs: collection}).render();
				},
				error: function(collection, xhr, options) {
					console.log("failed!!!");
				}
			});
		},
		'artists': function() {
			var artistlist = new ArtistList();
			artistlist.fetch({
				success: function(collection, response, options) {
					new ArtistListView({artists: collection}).render();
				},
				error: function(collection, xhr, options) {
					console.log("failed!!!");
				}
			});
		},
		'albums': function() {
			var albumslist = new AlbumList();
			albumslist.fetch({
				success: function(collection, response, options) {
					new AlbumListView({albums: collection}).render();
				},
				error: function(collection, xhr, options) {
					console.log("failed!!!");
				}
			});
		}
	});
	
	return Router;
});
