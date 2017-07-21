package com.markovlabs.eros;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.markovlabs.eros.daters.DaterController;
import com.markovlabs.eros.daters.DaterService;
import com.markovlabs.eros.daters.ImageService;
import com.markovlabs.eros.events.EventController;
import com.markovlabs.eros.events.EventService;
import com.markovlabs.eros.matches.MatchesController;
import com.markovlabs.eros.matches.MatchesService;
import com.markovlabs.eros.messages.MessageController;
import com.markovlabs.eros.messages.MessageServiceFacade;
import com.markovlabs.eros.messages.PollAndReadThroughMessagingService;
import com.markovlabs.eros.questions.AnswerService;
import com.markovlabs.eros.questions.QuestionController;
import com.markovlabs.eros.questions.QuestionService;

import io.dropwizard.Application;
import io.dropwizard.bundles.version.VersionBundle;
import io.dropwizard.bundles.version.VersionSupplier;
import io.dropwizard.bundles.version.suppliers.MavenVersionSupplier;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ErosApp extends Application<ErosAppConfig> {
	
	private static final Logger log = LoggerFactory.getLogger(ErosApp.class);
	
	private static final String EROS_ARTIFACT_ID = "eros-api";
	private static final String EROS_GROUP_ID = "com.markovLabs";
	
	@Override
	public void initialize(Bootstrap<ErosAppConfig> bootstrap) {
		VersionSupplier versionSupplier = new MavenVersionSupplier(EROS_GROUP_ID, EROS_ARTIFACT_ID);
		bootstrap.addBundle(new VersionBundle(versionSupplier));
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
	}

	@Override
	public void run(ErosAppConfig config, Environment env) throws Exception {
		ObjectMapper jsonMapper = env.getObjectMapper();
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		DSLContext erosDb = newErosDb(config);
		assemblyApp(env, erosDb, config.getImgQueue());
	}
	
	private DSLContext newErosDb(ErosAppConfig config) {  
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(config.getDbURL());
        dataSource.setUsername(config.getDbUser());
        dataSource.setPassword(config.getDbPassword());
        log.info("BasicDataSource bean for MySQL has been created with url " + config.getDbURL());
        return DSL.using(dataSource, SQLDialect.MYSQL);
    }
	
	private void assemblyApp(Environment env, DSLContext erosDb, String imgQueue) {
		DaterService daterService = new DaterService(erosDb);
		ImageService imageService = new ImageService(erosDb, imgQueue);
		AnswerService answerService = new AnswerService(erosDb);
		EventService eventService = new EventService(erosDb);
		MatchesService matchesService = new MatchesService(erosDb);
		QuestionService questionService = new QuestionService(erosDb);
		MessageServiceFacade messageService = newMessageServiceFacade(erosDb);
		
		env.jersey().register(new DaterController(daterService, imageService, answerService));
		env.jersey().register(new EventController(eventService, daterService, matchesService, answerService));
		env.jersey().register(new MatchesController(matchesService));
		env.jersey().register(new MessageController(messageService));
		env.jersey().register(new QuestionController(questionService));
		env.jersey().register(new ResponseInterceptor());
	}

	private MessageServiceFacade newMessageServiceFacade(DSLContext erosDb) {
		return new MessageServiceFacade(erosDb,
				new PollAndReadThroughMessagingService(erosDb));
	}

	public static void main(String[] args) throws Exception {
		new ErosApp().run(args);
	}

}
