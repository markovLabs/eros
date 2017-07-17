package com.markovlabs.eros;

import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;
import org.jooq.impl.TableImpl;

public class JOOQRecordUtility {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <R extends UpdatableRecord, T extends TableImpl<R>, V> V addRecord(DSLContext db, T table, V recordValue) {
		R record = db.newRecord(table);
		record.from(recordValue);
		record.insert();
		return (V) record.into(recordValue.getClass());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <R extends UpdatableRecord, T extends TableImpl<R>, V> V updateRecord(DSLContext db, T table, V recordValue) {
		R record = db.newRecord(table);
		record.from(recordValue);
		record.update();
		return (V) record.into(recordValue.getClass());
	}

}
