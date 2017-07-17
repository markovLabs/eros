package com.markovlabs.eros.messages;

import java.util.List;

public interface MessageService {
	
	MessageTO addMessage(MessageTO message);
	List<Message> getMessages(MessageTO message);
	
	public static class MessageTO {
		
		private final Message msg;
		private final int messageReceived;

		public MessageTO(Message msg, int messagesReceived) {
			this.msg = msg;
			this.messageReceived = messagesReceived;
		}

		public Message getMsg() {
			return msg;
		}

		public int getMessageReceived() {
			return messageReceived;
		}
	}
}
