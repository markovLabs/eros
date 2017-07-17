package com.markovlabs.eros.matches;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.markovlabs.eros.ListTO;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/eros/v1/matches/mappings")
public class MatchesController {

	private final MatchesService matchesService;

	public MatchesController(MatchesService matchesService) {
		this.matchesService = matchesService;
	}

	@GET
	public ListTO<MatchMapping> getMatchMappings() {
		return new ListTO<>("mappings", matchesService.getMatchMappings());
	}

	@GET
	@Path("/{mapping_id}")
	public MatchMapping getMatchMapping(@PathParam("mapping_id") long id) {
		return matchesService.getMatchMapping(id);
	}

	@POST
	public MatchMapping addMatchMapping(MatchMapping matchMapping) {
		return matchesService.addMatchMapping(matchMapping);
	}

}
