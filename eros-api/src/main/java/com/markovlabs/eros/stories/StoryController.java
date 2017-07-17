package com.markovlabs.eros.stories;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.markovlabs.eros.ListTO;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/eros/v1/stories")
public final class StoryController {
	
	private final StoryService storyService;

	public StoryController(StoryService storyService) {
		this.storyService = storyService;
	}
	
	@GET
	public ListTO<Story> getStorys() {
		return new ListTO<>("stories", storyService.getStorys());
	}

	@GET
	@Path("/{story_id}")
	public Story getStory(@PathParam("story_id") long id) {
		return storyService.getStory(id);
	}

	@POST
	@Path("/{story_id}")
	public Story updateStory(@PathParam("story_id") long id, Story story) {
		return storyService.updateStory(story);
	}

	@POST
	public Story addStory(Story story) {
		return storyService.addStory(story);
	}
}
