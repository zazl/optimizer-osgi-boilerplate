define(['backbone', './Song'], function(Backbone, Song){
	var SongList = Backbone.Collection.extend({
		model: Song,
		url: "/music/songs"
	});
	return SongList;
});
