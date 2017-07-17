package com.markovlabs.eros.stories;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.JOOQRecordUtility.updateRecord;
import static com.markovlabs.eros.model.tables.Story.STORY;
import java.util.List;
import org.jooq.DSLContext;
import javaslang.control.Try;

public class StoryService {

	private final DSLContext erosDb;

	public StoryService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public List<Story> getStorys() {
		return erosDb.selectFrom(STORY).fetch().map(Story::of);
	}

	public Story getStory(long id) {
		return Try.of(() -> erosDb.selectFrom(STORY)
					.where(STORY.ID.equal(id))
					.fetchOne())
				.map(Story::of)
				.getOrElseThrow(e -> new StoryNotFoundException(e));

	}

	public Story updateStory(Story story) {
		return updateRecord(erosDb, STORY, story);
	}

	public Story addStory(Story story) {
		return addRecord(erosDb, STORY, story);
	}

	public static class StoryNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public StoryNotFoundException(String message, Throwable e) {
			super(message, e);
		}

		public StoryNotFoundException(Throwable e) {
			super(e);
		}

		public StoryNotFoundException(String message) {
			super(message);
		}

		public StoryNotFoundException() {
			super();
		}
	}

}
