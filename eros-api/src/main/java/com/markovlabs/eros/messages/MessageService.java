package com.markovlabs.eros.messages;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public interface MessageService {
	
	MessageTO addMessage(MessageTO message);
	List<Message> getMessages(MessageTO message);
	List<Message> getMessagesBetweenDaters(MessageTO messageTO);
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({"msg", "message_received"})
	public static class MessageTO {
		
		private final Message msg;
		private final Integer messageReceived;

		public MessageTO(Message msg, Integer messagesReceived) {
			this.msg = msg;
			this.messageReceived = messagesReceived;
		}

		public Message getMsg() {
			return msg;
		}

		public Integer getMessageReceived() {
			return messageReceived == null ? 0 : messageReceived;
		}
	}
}
