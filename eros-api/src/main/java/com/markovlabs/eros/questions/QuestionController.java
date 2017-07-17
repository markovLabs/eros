package com.markovlabs.eros.questions;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.markovlabs.eros.ListTO;
import com.markovlabs.eros.questions.Question;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/eros/v1/questions")
public final class QuestionController {
	
	private final QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	@GET
	public ListTO<Question> getQuestions() {
		return new ListTO<>("events", questionService.getQuestions());
	}

	@GET
	@Path("/{question_id}")
	public Question getQuestion(@PathParam("question_id") long id) {
		return questionService.getQuestion(id);
	}

	@POST
	@Path("/{question_id}")
	public Question updateQuestion(@PathParam("question_id") long id, Question question) {
		return questionService.updateQuestion(question);
	}

	@POST
	public Question addQuestion(Question question) {
		return questionService.addQuestion(question);
	}
}
