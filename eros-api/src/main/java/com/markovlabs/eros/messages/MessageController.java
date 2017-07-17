package com.markovlabs.eros.messages;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.markovlabs.eros.ListTO;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/eros/v1/messages")
public class MessageController {

	private final MessageServiceFacade messageService;

	public MessageController(MessageServiceFacade messageService) {
		this.messageService = messageService;
	}

	@GET
	public ListTO<Message> getMessages() {
		return new ListTO<>("messages", messageService.getMessages());
	}

	@GET
	public ListTO<Message> getMessages(@QueryParam("from") long fromDater, @QueryParam("to") long toDater) {
		return new ListTO<>("messages", messageService.getLastMessages(fromDater, toDater, 0));
	}

	@GET
	public ListTO<Message> getLastMessages(@QueryParam("from") long fromDater, @QueryParam("to") long toDater,
			@QueryParam("messages_received") int messagesReceived) {
		return new ListTO<>("messages", messageService.getLastMessages(fromDater, toDater, messagesReceived));
	}

	@GET
	@Path("/{message_id}")
	public Message getMessage(@PathParam("message_id") long id) {
		return messageService.getMessage(id);
	}

	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
}
