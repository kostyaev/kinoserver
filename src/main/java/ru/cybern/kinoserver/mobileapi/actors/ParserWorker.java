package ru.cybern.kinoserver.mobileapi.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.parsers.Global;
import ru.cybern.kinoserver.parsers.IParser;
import ru.cybern.kinoserver.parsers.models.Movie;

import java.io.IOException;
import java.util.HashMap;


public class ParserWorker extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private IParser parser;

    private String parserName;

    public static Props props(final IParser parser) {
        return Props.create(new Creator<ParserWorker>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ParserWorker create() throws Exception {
                return new ParserWorker(parser);
            }
        });
    }

    public ParserWorker(IParser parser) throws IOException {
        this.parser = parser;
        this.parserName = parser.getClass().getName();
    }

    @Override
    public void postStop() {
        log.info("Stopping " + parserName +  " actor...");
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Page) {
            log.info("Path is: " + Global.HOME_PATH + Global.IMG_PATH);
            int page = ((Page) message).getPageNum();
            HashMap<String,Movie> lib = parser.parse(page, page);
            log.info("Page is done");
            sender().tell(lib, self());
        }
        else
            unhandled(message);
    }
}
