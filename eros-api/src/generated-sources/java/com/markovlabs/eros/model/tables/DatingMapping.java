/**
 * This class is generated by jOOQ
 */
package com.markovlabs.eros.model.tables;


import com.markovlabs.eros.model.Eros;
import com.markovlabs.eros.model.Keys;
import com.markovlabs.eros.model.tables.records.DatingMappingRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class DatingMapping extends TableImpl<DatingMappingRecord> {

	private static final long serialVersionUID = 2070888708;

	/**
	 * The reference instance of <code>eros.DATING_MAPPING</code>
	 */
	public static final DatingMapping DATING_MAPPING = new DatingMapping();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DatingMappingRecord> getRecordType() {
		return DatingMappingRecord.class;
	}

	/**
	 * The column <code>eros.DATING_MAPPING.ID</code>.
	 */
	public final TableField<DatingMappingRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>eros.DATING_MAPPING.MAPPING</code>.
	 */
	public final TableField<DatingMappingRecord, String> MAPPING = createField("MAPPING", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * Create a <code>eros.DATING_MAPPING</code> table reference
	 */
	public DatingMapping() {
		this("DATING_MAPPING", null);
	}

	/**
	 * Create an aliased <code>eros.DATING_MAPPING</code> table reference
	 */
	public DatingMapping(String alias) {
		this(alias, DATING_MAPPING);
	}

	private DatingMapping(String alias, Table<DatingMappingRecord> aliased) {
		this(alias, aliased, null);
	}

	private DatingMapping(String alias, Table<DatingMappingRecord> aliased, Field<?>[] parameters) {
		super(alias, Eros.EROS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<DatingMappingRecord, Long> getIdentity() {
		return Keys.IDENTITY_DATING_MAPPING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<DatingMappingRecord> getPrimaryKey() {
		return Keys.KEY_DATING_MAPPING_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<DatingMappingRecord>> getKeys() {
		return Arrays.<UniqueKey<DatingMappingRecord>>asList(Keys.KEY_DATING_MAPPING_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DatingMapping as(String alias) {
		return new DatingMapping(alias, this);
	}

	/**
	 * Rename this table
	 */
	public DatingMapping rename(String name) {
		return new DatingMapping(name, null);
	}
}
