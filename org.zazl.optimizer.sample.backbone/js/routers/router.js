define([
	'jquery', 
	'backbone', 
	'underscore', 
	'models/SongList',
	'views/SongListView'], 
function($, Backbone, _, SongList, SongListView){
	var Router = Backbone.Router.extend({
		initialize: function(){
			Backbone.history.start();
		},
		routes: {
			'': 'home'
		},
		'home': function() {
			var songlist = new SongList();
			songlist.fetch({
				success: function(collection, response, options) {
					new SongListView({songs: collection}).render();
				},
				error: function(collection, xhr, options) {
					console.log("failed!!!");
				}
			});
		}
	});
	
	return Router;
});
