define([
		'jquery', 
		'backbone',
		'underscore', 
		'text!templates/SongList.html'], 
function($, Backbone, _, template){
	var View = Backbone.View.extend({
		el: '#container',
		initialize: function(options) {
			this.template = _.template( template, { songs: options.songs.toJSON() } );
		},
		render: function(){
			$(this.el).html( this.template );
		}
	});
	
	return View;
});
