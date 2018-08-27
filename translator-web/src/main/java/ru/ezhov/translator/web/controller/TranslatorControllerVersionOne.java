package ru.ezhov.translator.web.controller;

import ru.ezhov.translator.core.TranslateLang;
import ru.ezhov.translator.core.TranslateResult;
import ru.ezhov.translator.web.service.Engine;
import ru.ezhov.translator.web.service.EngineFactory;
import ru.ezhov.translator.web.service.PropertiesService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/translate/v1")
public class TranslatorControllerVersionOne {
    @Inject
    private EngineFactory engineFactory;

    @Inject
    private PropertiesService propertiesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response v1(
            @QueryParam("engine") String engine,
            @QueryParam("lang") String lang,
            @QueryParam("text") String text
    ) {
        try {
            TranslateResult translateResult =
                    engineFactory.getTranslate(Engine.valueOf(engine.toUpperCase()))
                            .translate(TranslateLang.from(lang), text);
            return Response
                    .ok(
                            new Answer(translateResult.getText(), translateResult.getAdditionalInformation())
                    )
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response
                    .serverError()
                    .entity(new Answer("Ошибка сервера", "На сервере произошла ошибка"))
                    .build();
        }
    }
}
