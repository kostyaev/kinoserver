package ru.cybern.kinoserver.mobileapi.actors.workers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Command;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.parsers.kinopoisk.Parser;
import ru.cybern.kinoserver.parsers.models.Movie;

import javax.inject.Inject;
import java.util.HashMap;


public class KinopoiskWorker extends UntypedActor {

    private Parser parser = new Parser();


    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Page) {
            int page = ((Page) message).getPageNum();
            HashMap<String,Movie> lib = parser.parse(page, page);
            System.out.println("Page is done");
            sender().tell(lib, self());
        } else if (message.equals(Command.STOP))
            getContext().stop(getSelf());


    }
}
