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
		initialize: function() {
			this.on("route:songs", function(album){
				var songlist = new SongList({album: album});
				songlist.fetch({
					success: function(collection, response, options) {
						new SongListView({songs: collection}).render();
					},
					error: function(collection, xhr, options) {
						console.log("failed!!!");
					}
				});
			});
			this.on("route:albums", function(artist){
				var albumslist = new AlbumList({artist: artist});
				albumslist.fetch({
					success: function(collection, response, options) {
						new AlbumListView({albums: collection}).render();
					},
					error: function(collection, xhr, options) {
						console.log("failed!!!");
					}
				});
			});
			this.on("route:artists", function(){
				var artistlist = new ArtistList();
				artistlist.fetch({
					success: function(collection, response, options) {
						new ArtistListView({artists: collection}).render();
					},
					error: function(collection, xhr, options) {
						console.log("failed!!!");
					}
				});
			});
			Backbone.history.start();
		},
		routes: {
			'songs/:album': 'songs',
			'albums/:artist': 'albums',
			'songs': 'songs',
			'albums': 'albums',
			'artists': 'artists',
			'artists/': 'artists',
			'': 'artists'
		}
	});
	
	return Router;
});
