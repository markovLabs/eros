package com.markovlabs.eros.messages;

import java.util.List;

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
	public ListTO<Message> getLastMessagesForDaters(@QueryParam("from") Long fromDater, @QueryParam("to") Long toDater,
			@QueryParam("messages_received") Integer messagesReceived) {
		List<Message> messages = null;
		if(fromDater == null && toDater==null) {
			messages = messageService.getMessages();
		} else{
			messagesReceived = (messagesReceived == null) ? 0 : messagesReceived;
			messages =  messageService.getLastMessages(fromDater, toDater, messagesReceived);
		}
		return new ListTO<>("messages", messages);
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
