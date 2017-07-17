package com.markovlabs.eros.daters;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.markovlabs.eros.ListTO;
import com.markovlabs.eros.questions.AnswerService;
import com.markovlabs.eros.questions.ProfileAnswer;
import com.markovlabs.eros.questions.StoryAnswer;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/eros/v1/daters/")
public class DaterController {

	private final DaterService daterService;
	private final ImageService imageService;
	private final AnswerService answerService;

	public DaterController(DaterService daterService, ImageService imageService, AnswerService answerService) {
		this.daterService = daterService;
		this.imageService = imageService;
		this.answerService = answerService;
	}

	@GET
	public ListTO<Dater> getDaters() {
		return new ListTO<>("daters", daterService.getDaters());
	}

	@GET
	@Path("/{dater_id}")
	public Dater getDater(@PathParam("dater_id") long id) {
		return daterService.getDater(id);
	}

	@GET
	public Dater getDater(@QueryParam("email") String email, @QueryParam("pwd") String pwd) {
		return daterService.getDater(email, pwd);
	}

	@POST
	@Path("/{dater_id}")
	public Dater updateDater(@PathParam("dater_id") long id, Dater dater) {
		return daterService.updateDater(dater);
	}

	@POST
	public Dater addDater(Dater dater) {
		return daterService.addDater(dater);
	}

	@GET
	@Path("/{dater_id}/images")
	public ListTO<Image> getImages(@PathParam("dater_id") long daterId) {
		return new ListTO<>("images", imageService.getImagesWithDaterId(daterId));
	}

	@GET
	@Path("/{dater_id}/images/{image_id}")
	public Image getImage(@PathParam("dater_id") long daterId, @PathParam("image_id") long imageId) {
		return imageService.getImageWithIdAndDaterId(imageId, daterId);
	}

	@POST
	@Path("/{dater_id}/images/")
	public Image addImage(@PathParam("dater_id") long daterId, Image image) {
		image.setDaterId(daterId);
		return imageService.addImage(image);
	}

	@DELETE
	@Path("/{dater_id}/images/{image_id}")
	public void removeImage(@PathParam("dater_id") long daterId, @PathParam("image_id") long imageId) {
		imageService.removeImageWithDaterId(imageId, daterId);
	}

	@GET
	@Path("/{dater_id}/story_answers/")
	public ListTO<StoryAnswer> getStoryAnswers(@PathParam("dater_id") long daterId) {
		return new ListTO<>("story_answers", answerService.getStoryAnswers(daterId));
	}

	@GET
	@Path("/{dater_id}/story_answers/{answer_id}")
	public StoryAnswer getStoryAnswer(@PathParam("dater_id") long daterId, @PathParam("answer_id") long answerId) {
		return answerService.getStoryAnswer(daterId, answerId);
	}

	@POST
	@Path("/{dater_id}/story_answers/")
	public StoryAnswer addStoryAnswer(@PathParam("dater_id") long daterId, StoryAnswer storyAnswer) {
		storyAnswer.setDaterId(daterId);
		return answerService.addStoryAnswer(storyAnswer);
	}

	@GET
	@Path("/{dater_id}/profile_answers/")
	public ListTO<ProfileAnswer> getProfileAnswers(@PathParam("dater_id") long daterId) {
		return new ListTO<>("profile_answers", answerService.getProfileAnswers(daterId));
	}

	@GET
	@Path("/{dater_id}/profile_answers/{profile_answer_id}")
	public ProfileAnswer getProfileAnswer(@PathParam("dater_id") long daterId,
			@PathParam("profile_answer_id") long profileAnswerId) {
		return answerService.getProfileAnswer(daterId, profileAnswerId);
	}

	@POST
	@Path("/{dater_id}/profile_answers/")
	public ProfileAnswer addProfileAnswer(@PathParam("dater_id") long daterId, ProfileAnswer profileAnswer) {
		profileAnswer.setDaterId(daterId);
		return answerService.addProfileAnswer(profileAnswer);
	}
}
