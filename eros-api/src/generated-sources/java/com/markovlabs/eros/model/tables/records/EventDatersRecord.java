/**
 * This class is generated by jOOQ
 */
package com.markovlabs.eros.model.tables.records;


import com.markovlabs.eros.model.tables.EventDaters;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventDatersRecord extends UpdatableRecordImpl<EventDatersRecord> implements Record5<Long, Long, Long, Byte, Byte> {

	private static final long serialVersionUID = -1396668838;

	/**
	 * Setter for <code>eros.EVENT_DATERS.ID</code>.
	 */
	public void setId(Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>eros.EVENT_DATERS.ID</code>.
	 */
	public Long getId() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>eros.EVENT_DATERS.EVENT_ID</code>.
	 */
	public void setEventId(Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>eros.EVENT_DATERS.EVENT_ID</code>.
	 */
	public Long getEventId() {
		return (Long) getValue(1);
	}

	/**
	 * Setter for <code>eros.EVENT_DATERS.DATER_ID</code>.
	 */
	public void setDaterId(Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>eros.EVENT_DATERS.DATER_ID</code>.
	 */
	public Long getDaterId() {
		return (Long) getValue(2);
	}

	/**
	 * Setter for <code>eros.EVENT_DATERS.PROFILE_EVALUATION_COMPLETED_FLAG</code>.
	 */
	public void setProfileEvaluationCompletedFlag(Byte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>eros.EVENT_DATERS.PROFILE_EVALUATION_COMPLETED_FLAG</code>.
	 */
	public Byte getProfileEvaluationCompletedFlag() {
		return (Byte) getValue(3);
	}

	/**
	 * Setter for <code>eros.EVENT_DATERS.MESSAGING_EVALUATION_COMPLETED_FLAG</code>.
	 */
	public void setMessagingEvaluationCompletedFlag(Byte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>eros.EVENT_DATERS.MESSAGING_EVALUATION_COMPLETED_FLAG</code>.
	 */
	public Byte getMessagingEvaluationCompletedFlag() {
		return (Byte) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Long> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, Long, Long, Byte, Byte> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, Long, Long, Byte, Byte> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return EventDaters.EVENT_DATERS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return EventDaters.EVENT_DATERS.EVENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field3() {
		return EventDaters.EVENT_DATERS.DATER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field4() {
		return EventDaters.EVENT_DATERS.PROFILE_EVALUATION_COMPLETED_FLAG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field5() {
		return EventDaters.EVENT_DATERS.MESSAGING_EVALUATION_COMPLETED_FLAG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value2() {
		return getEventId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value3() {
		return getDaterId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value4() {
		return getProfileEvaluationCompletedFlag();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value5() {
		return getMessagingEvaluationCompletedFlag();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventDatersRecord value1(Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventDatersRecord value2(Long value) {
		setEventId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventDatersRecord value3(Long value) {
		setDaterId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventDatersRecord value4(Byte value) {
		setProfileEvaluationCompletedFlag(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventDatersRecord value5(Byte value) {
		setMessagingEvaluationCompletedFlag(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventDatersRecord values(Long value1, Long value2, Long value3, Byte value4, Byte value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached EventDatersRecord
	 */
	public EventDatersRecord() {
		super(EventDaters.EVENT_DATERS);
	}

	/**
	 * Create a detached, initialised EventDatersRecord
	 */
	public EventDatersRecord(Long id, Long eventId, Long daterId, Byte profileEvaluationCompletedFlag, Byte messagingEvaluationCompletedFlag) {
		super(EventDaters.EVENT_DATERS);

		setValue(0, id);
		setValue(1, eventId);
		setValue(2, daterId);
		setValue(3, profileEvaluationCompletedFlag);
		setValue(4, messagingEvaluationCompletedFlag);
	}
}
