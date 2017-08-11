package com.markovlabs.eros.messages;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.model.tables.Messages.MESSAGES;
import java.util.List;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

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
		Condition condition = MESSAGES.FROM_DATER_ID.eq(from).and(MESSAGES.TO_DATER_ID.eq(to));
		return getMessagesWhen(msgTO, condition);
	}

	@Override
	public List<Message> getMessagesBetweenDaters(MessageTO msgTO) {
		long from = msgTO.getMsg().getFromDaterId();
		long to = msgTO.getMsg().getToDaterId();
		Condition fromToCondition = MESSAGES.FROM_DATER_ID.eq(from).and(MESSAGES.TO_DATER_ID.eq(to));
		Condition toFromCondition = MESSAGES.FROM_DATER_ID.eq(to).and(MESSAGES.TO_DATER_ID.eq(from));
		return getMessagesWhen(msgTO, fromToCondition.or(toFromCondition));
	}

	private List<Message> getMessagesWhen(MessageTO msgTO, Condition condition) {
		Condition eventCondition = getEventcondition(msgTO.getMsg().getEventId());
		List<Message> allMsgs = erosDb.selectFrom(MESSAGES).where(condition.and(eventCondition))
				.orderBy(MESSAGES.TIME_RECEIVED.desc()).fetch().map(Message::of);
		int numOfUnsentMsgs = allMsgs.size() - msgTO.getMessageReceived();
		numOfUnsentMsgs = numOfUnsentMsgs <= 0 ? 0 : numOfUnsentMsgs; 
		return allMsgs.stream().limit(numOfUnsentMsgs)
				.sorted((m1, m2) -> getTimeReceived(m1).compareTo(getTimeReceived(m2)))
				.collect(toList());
	}
	
	private LocalDateTime getTimeReceived(Message msg){
		return LocalDateTime.parse(msg.getTimeReceivedAsString().replace(" ", "T").replace(".0", ""));
	}

	private Condition getEventcondition(Long eventId) {
		if (eventId != null) {
			return MESSAGES.EVENT_ID.equal(eventId);
		} else {
			return DSL.trueCondition();
		}
	}
}
