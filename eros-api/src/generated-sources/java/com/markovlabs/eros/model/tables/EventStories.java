/**
 * This class is generated by jOOQ
 */
package com.markovlabs.eros.model.tables;


import com.markovlabs.eros.model.Eros;
import com.markovlabs.eros.model.Keys;
import com.markovlabs.eros.model.enums.EventStoriesLabel;
import com.markovlabs.eros.model.tables.records.EventStoriesRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class EventStories extends TableImpl<EventStoriesRecord> {

	private static final long serialVersionUID = -1001202000;

	/**
	 * The reference instance of <code>eros.EVENT_STORIES</code>
	 */
	public static final EventStories EVENT_STORIES = new EventStories();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<EventStoriesRecord> getRecordType() {
		return EventStoriesRecord.class;
	}

	/**
	 * The column <code>eros.EVENT_STORIES.ID</code>.
	 */
	public final TableField<EventStoriesRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>eros.EVENT_STORIES.EVENT_ID</code>.
	 */
	public final TableField<EventStoriesRecord, Long> EVENT_ID = createField("EVENT_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>eros.EVENT_STORIES.STORY_ID</code>.
	 */
	public final TableField<EventStoriesRecord, Long> STORY_ID = createField("STORY_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>eros.EVENT_STORIES.LABEL</code>.
	 */
	public final TableField<EventStoriesRecord, EventStoriesLabel> LABEL = createField("LABEL", org.jooq.util.mysql.MySQLDataType.VARCHAR.asEnumDataType(com.markovlabs.eros.model.enums.EventStoriesLabel.class), this, "");

	/**
	 * Create a <code>eros.EVENT_STORIES</code> table reference
	 */
	public EventStories() {
		this("EVENT_STORIES", null);
	}

	/**
	 * Create an aliased <code>eros.EVENT_STORIES</code> table reference
	 */
	public EventStories(String alias) {
		this(alias, EVENT_STORIES);
	}

	private EventStories(String alias, Table<EventStoriesRecord> aliased) {
		this(alias, aliased, null);
	}

	private EventStories(String alias, Table<EventStoriesRecord> aliased, Field<?>[] parameters) {
		super(alias, Eros.EROS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<EventStoriesRecord, Long> getIdentity() {
		return Keys.IDENTITY_EVENT_STORIES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<EventStoriesRecord> getPrimaryKey() {
		return Keys.KEY_EVENT_STORIES_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<EventStoriesRecord>> getKeys() {
		return Arrays.<UniqueKey<EventStoriesRecord>>asList(Keys.KEY_EVENT_STORIES_PRIMARY, Keys.KEY_EVENT_STORIES_EVENT_STORY_LABEL);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<EventStoriesRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<EventStoriesRecord, ?>>asList(Keys.EVENT_ID_FK_5, Keys.STORY_ID_FK_2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventStories as(String alias) {
		return new EventStories(alias, this);
	}

	/**
	 * Rename this table
	 */
	public EventStories rename(String name) {
		return new EventStories(name, null);
	}
}