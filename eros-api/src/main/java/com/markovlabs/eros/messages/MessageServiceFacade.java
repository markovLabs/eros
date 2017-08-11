package com.markovlabs.eros.messages;

import static com.markovlabs.eros.model.tables.Messages.MESSAGES;
import java.util.List;
import org.jooq.DSLContext;

import com.markovlabs.eros.messages.MessageService.MessageTO;

import javaslang.control.Try;

public final class MessageServiceFacade {
	
	private final DSLContext erosDb;
	private final MessageService msgService;

	public MessageServiceFacade(DSLContext erosDb, MessageService msgService) {
		this.erosDb = erosDb;
		this.msgService = msgService;
	}

	public List<Message> getMessages() {
		return erosDb.selectFrom(MESSAGES).fetch().map(Message::of);
	}

	public Message getMessage(long id) {
		return Try.of(() -> erosDb.selectFrom(MESSAGES).where(MESSAGES.ID.equal(id)).fetchOne())
				.map(Message::of)
				.getOrElseThrow(e -> new MessageNotFoundException(e));

	}
	
	public Message addMessage(Message message) {
		return msgService.addMessage(new MessageTO(message, 0))
				.getMsg();
	}
	
	public List<Message> getLastMessages(MessageTO messageTO) {
		return msgService.getMessages(messageTO);
	}
	public List<Message> getLastMessagesBetweenDaters(MessageTO messageTO) {
		return msgService.getMessagesBetweenDaters(messageTO);
	}
	
	public static class MessageNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public MessageNotFoundException(String message, Throwable e){
			super(message, e);
		}
		public MessageNotFoundException(Throwable e){
			super(e);
		}
		public MessageNotFoundException(String message){
			super(message);
		}
		public MessageNotFoundException(){
			super();
		}
	}

}
