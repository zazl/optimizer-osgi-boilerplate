define(['backbone'], function(Backbone) {
	var Artist = Backbone.Model.extend({
		defaults: {
			name: null,
			albumCount: 0,
			songCount: 0
		}
	});
	
	return Artist;
});
