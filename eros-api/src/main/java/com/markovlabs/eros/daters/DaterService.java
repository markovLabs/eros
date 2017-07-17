package com.markovlabs.eros.daters;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.JOOQRecordUtility.updateRecord;
import static com.markovlabs.eros.model.tables.Dater.DATER;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import javaslang.control.Try;

public class DaterService {
	
	private final DSLContext erosDb;

	public DaterService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public List<Dater> getDatersWithIds(List<Long> ids) {
		return erosDb.selectFrom(DATER).where(DATER.ID.in(ids)).fetch().map(Dater::of);
	}
	
	public List<Dater> getDaters() {
		return erosDb.selectFrom(DATER).fetch().map(Dater::of);
	}

	public Dater getDater(long id) {
		return Try.of(() -> erosDb.selectFrom(DATER).where(DATER.ID.equal(id)).fetchOne())
				.map(Dater::of)
				.getOrElseThrow(e -> new DaterNotFoundException(e));
	}
	
	public Dater getDaterOrThrow(Collection<Long> daterIds, long daterId){
		return daterIds.stream()
				.filter(id -> daterId == id)
				.map(id -> getDater(id))
				.findFirst()
				.orElseThrow(DaterNotFoundException::new);
	}
	
	public Optional<Dater> getDater(Collection<Long> daterIds, long daterId){
		return daterIds.stream()
				.filter(id -> daterId == id)
				.map(id -> getDater(id))
				.findFirst();
	}

	public Dater getDater(String email, String pwd) {
		 List<Dater> daters = erosDb.selectFrom(DATER)
				 .where(DATER.EMAIL.eq(email)
						 .and(DATER.PWD.eq(pwd)))
				 .fetch()
				 .map(Dater::of);
		 
		 if(daters.size() != 1) {
			 throw new DaterNotFoundException("# of daters found for " + email + " is " + daters.size());
		 } else {
			return daters.get(0);
		 }
	}

	public Dater updateDater(Dater dater) {
		return updateRecord(erosDb, DATER, dater);
	}

	public Dater addDater(Dater dater) {
		return addRecord(erosDb, DATER, dater);
	}
	
	public static class DaterNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public DaterNotFoundException(String message, Throwable e){
			super(message, e);
		}
		public DaterNotFoundException(Throwable e){
			super(e);
		}
		public DaterNotFoundException(String message){
			super(message);
		}
		public DaterNotFoundException(){
			super();
		}
	}

}
