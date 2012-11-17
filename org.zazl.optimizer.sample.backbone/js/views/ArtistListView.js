define([
		'jquery', 
		'backbone',
		'underscore', 
		'text!templates/ArtistList.html'], 
function($, Backbone, _, template){
	var View = Backbone.View.extend({
		el: '#main',
		initialize: function(options) {
			this.template = _.template( template, { artists: options.artists.toJSON() } );
		},
		render: function(){
			$(this.el).append( this.template );
		}
	});
	
	return View;
});
