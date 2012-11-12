define(['backbone'], function(Backbone) {
	var Album = Backbone.Model.extend({
	    initialize: function(attributes, options) {
	    	this.artist = options.artist;
	    }
	});
	
	return Album;
});
