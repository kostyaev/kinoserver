package ru.cybern.kinoserver.mobileapi.actors.workers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.parsers.kinopoisk.Parser;
import ru.cybern.kinoserver.parsers.models.Movie;

import java.util.HashMap;


public class KinopoiskWorker extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Parser parser = new Parser(true);

    @Override
    public void postStop() {
        log.info("Stopping kinopoisk actor...");
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Page) {
            int page = ((Page) message).getPageNum();
            HashMap<String,Movie> lib = parser.parse(page, page);
            log.info("Page is done");
            sender().tell(lib, self());
        }
        else
            unhandled(message);
    }
}
