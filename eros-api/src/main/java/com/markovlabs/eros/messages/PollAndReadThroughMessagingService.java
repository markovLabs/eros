package com.markovlabs.eros.messages;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.model.tables.Messages.MESSAGES;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.jooq.DSLContext;

public final class PollAndReadThroughMessagingService implements MessageService {
	
	private final DSLContext erosDb;

	public PollAndReadThroughMessagingService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	@Override
	public MessageTO addMessage(MessageTO msg) {
		return new MessageTO(addRecord(erosDb, MESSAGES, msg.getMsg()), 0);
	}

	@Override
	public List<Message> getMessages(MessageTO msgTO) {
		long from = msgTO.getMsg().getFromDaterId();
		long to = msgTO.getMsg().getToDaterId();
		List<Message> allMsgs = erosDb.selectFrom(MESSAGES)
				.where(MESSAGES.FROM_DATER_ID.eq(from)
						.and(MESSAGES.TO_DATER_ID.eq(to)))
				.orderBy(MESSAGES.TIME_RECEIVED)
				.fetch()
				.map(Message::of);
		int numOfUnsentMsgs = allMsgs.size() - msgTO.getMessageReceived();
		return allMsgs.stream().limit(numOfUnsentMsgs).collect(toList());
	}
}
