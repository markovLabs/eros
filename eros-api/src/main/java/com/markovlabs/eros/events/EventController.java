package com.markovlabs.eros.events;

import java.util.List;
import static java.util.stream.Collectors.toList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.ImmutableList;
import com.markovlabs.eros.ListTO;
import com.markovlabs.eros.daters.Dater;
import com.markovlabs.eros.daters.DaterService;
import com.markovlabs.eros.matches.DaterMatchTO;
import com.markovlabs.eros.matches.Matches;
import com.markovlabs.eros.matches.Matches.Match;
import com.markovlabs.eros.matches.MatchesService;
import com.markovlabs.eros.questions.AnswerService;
import com.markovlabs.eros.questions.EvaluationAnswer;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/eros/v1/events")
public class EventController {

	private final EventService eventService;
	private final DaterService daterService;
	private final MatchesService matchesService;
	private final AnswerService answerService;

	public EventController(EventService eventService, DaterService daterService, MatchesService matchesService, AnswerService answerService) {
		this.eventService = eventService;
		this.daterService = daterService;
		this.matchesService = matchesService;
		this.answerService = answerService;
	}
	
	@GET
	public ListTO<Event> getEvents(@QueryParam("next_event") String nextEvent, @QueryParam("dater_id") Long daterId) {
		return new ListTO<>("events", getEventList(nextEvent, daterId));
	}
	
	private List<Event> getEventList(String nextEvent, Long daterId) {
		if(nextEvent != null && daterId != null) {
			return eventService.getNextEvent(daterId);
		} else if(nextEvent != null) {
			return ImmutableList.of(eventService.getNextEvent());
		} else {
			return eventService.getEvents();
		}
	}

	@GET
	@Path("/{event_id}")
	public Event getEvent(@PathParam("event_id") long eventId) {
		return eventService.getEvent(eventId);
	}
	
	@POST
	@Path("/{event_id}")
	public Event updateEvent(@PathParam("event_id") long eventId, Event event) {
		return eventService.updateEvent(eventId, event);
	}

	@POST
	public Event addEvent(Event event) {
		return eventService.addEvent(event);
	}

	@GET
	@Path("/{event_id}/daters/")
	public ListTO<Dater> getDaters(@PathParam("event_id") long eventId) {
		return new ListTO<>("daters", daterService.getDatersForEvent(eventId));
	}

	@GET
	@Path("/{event_id}/daters/{dater_id}")
	public Dater getDater(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId) {
		return daterService.getDaterForEvent(eventId, daterId);
	}
	
	@POST
	@Path("/{event_id}/daters/{dater_id}")
	public Dater updateEventDaterInfo(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId, Dater dater) {
		dater.setId(daterId);
		eventService.updateDaterForEvent(eventId, dater);
		return daterService.getDater(dater.getId());
	}
	
	@POST
	@Path("/{event_id}/daters/")
	public Dater addDater(@PathParam("event_id") long eventId, Dater dater) {
		eventService.addDaterForEvent(dater.getId(), eventId);
		return daterService.getDater(dater.getId());
	}
	
	@DELETE
	@Path("/{event_id}/daters/{dater_id}")
	public void removeDater(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId) {
		 eventService.removeDaterFromEvent(daterId, eventId);
	}
	
	@GET
	@Path("/{event_id}/stories/")
	public ListTO<EventStory> getEventStories(@PathParam("event_id") long eventId) {
		return new ListTO<>("stories", eventService.getEventStories(eventId));
	}
	
	@GET
	@Path("/{event_id}/stories/{event_story_id}")
	public EventStory getEventStory(@PathParam("event_id") long eventId, @PathParam("event_story_id") long eventStoryId) {
		return eventService.getEventStory(eventId, eventStoryId);
	}

	@POST
	@Path("/{event_id}/stories/")
	public EventStory addEventStory(@PathParam("event_id") long eventId, EventStory eventStory) {
		eventStory.setEventId(eventId);
		return eventService.addEventStory(eventStory);
	}

	@DELETE
	@Path("/{event_id}/stories/{event_story_id}")
	public void removeEventStory(@PathParam("event_id") long eventId, @PathParam("event_story_id") long eventStoryId) {
		 eventService.removeEventStory(eventStoryId);
	}

	@GET
	@Path("/{event_id}/daters/{dater_id}/matches")
	public ListTO<DaterMatchTO> getMatches(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId) {
		return new ListTO<>("matches", getMatchesForThisEvent(eventId)
				.getMatchesForDater(daterId)
				.stream()
				.map(this::toDaterMatch)
				.collect(toList()));
	}

	private DaterMatchTO toDaterMatch(Match match) {
		Dater dater = daterService.getDater(match.getMatchId());
		return new DaterMatchTO(dater, match.getStoryId());
	}

	private Matches getMatchesForThisEvent(long eventId) {
		List<Long> maleDaterIds = eventService.getMaleDaterIdsForEvent(eventId);
		List<Long> femaleDaterIds = eventService.getFemaleDaterIdsForEvent(eventId);
		Long mappingId = eventService.getMappingId(eventId);
		return matchesService.getMatches(mappingId, maleDaterIds, femaleDaterIds, eventId);
	}

	@GET
	@Path("/{event_id}/daters/{dater_id}/matches/{match_id}")
	public DaterMatchTO getMatch(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId,
			@PathParam("match_id") long matchId) {
		return getMatchesForThisEvent(eventId).getMatchesForDater(daterId)
				.stream()
				.filter(match -> match.getMatchId() == matchId)
				.map(this::toDaterMatch)
				.findFirst().get();
	}

	@GET
	@Path("/{event_id}/daters/{dater_id}/matches/{match_id}/answers")
	public ListTO<EvaluationAnswer> getAnswers(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId,
			@PathParam("match_id") long matchId) {
		assertThatDatersAreMatched(eventId, daterId, matchId);
		return new ListTO<>("answers", answerService.getEvaluationAnswers(eventId, daterId, matchId));
	}

	private void assertThatDatersAreMatched(long eventId, long daterId, long matchId) {
		List<Long> matcheIdsForDater = getMatchesForThisEvent(eventId).getMatcheIdsForDater(daterId);
		assert(daterService.getDater(matcheIdsForDater, matchId).isPresent());
	}

	@POST
	@Path("/{event_id}/daters/{dater_id}/matches/{match_id}/answers")
	public EvaluationAnswer addAnswer(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId,
			@PathParam("match_id") long matchId, EvaluationAnswer answer) {
		assertThatDatersAreMatched(eventId, daterId, matchId);
		answer.setEventId(eventId).setDaterId(daterId).setMatchId(matchId);
		return answerService.addEvaluationAnswer(answer);
	}

	@GET
	@Path("/{event_id}/daters/{dater_id}/matches/{match_id}/answers/${answer_id}")
	public EvaluationAnswer getAnswer(@PathParam("event_id") long eventId, @PathParam("dater_id") long daterId,
			@PathParam("match_id") long matchId, @PathParam("answer_id") long answerId) {
		assertThatDatersAreMatched(eventId, daterId, matchId);
		return answerService.getEvaluationAnswer(eventId, daterId, matchId, answerId);
	}
}
