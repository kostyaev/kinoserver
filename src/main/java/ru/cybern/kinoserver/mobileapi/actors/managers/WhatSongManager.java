package ru.cybern.kinoserver.mobileapi.actors.managers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.mobileapi.actors.workers.WhatsongWorker;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.parsers.models.Movie;

import java.util.HashMap;
import java.util.LinkedList;


public class WhatsongManager extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private IParserBean parserBean;

    private int pageCount;

    private LinkedList<Page> freePages;

    private int workersCount;

    public static Props props(final IParserBean parserBean, final int pageCount) {
        return Props.create(new Creator<WhatsongManager>() {
            private static final long serialVersionUID = 1L;

            @Override
            public WhatsongManager create() throws Exception {
                return new WhatsongManager(parserBean, pageCount);
            }
        });
    }

    public WhatsongManager(IParserBean parserBean, int pageCount) {
        this.parserBean = parserBean;
        this.pageCount = pageCount;
        this.workersCount = pageCount;
    }

    @Override
    public void preStart() {
        freePages = new LinkedList<>();
        for(int i = 1; i <= pageCount; i++)
            freePages.add(new Page(i));
        for(int i = 0; i < workersCount; i++) {
            Page page = getFreePage();
            log.info("Starting worker for page: " + page);
            ActorRef worker = getContext().actorOf(Props.create(WhatsongWorker.class), "kinopoisk" + i);
            worker.tell(page, getSelf());
        }
    }

    private Page getFreePage() {
        Page p = freePages.poll();
        log.info("Page size is: " + freePages.size());
        return p;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof HashMap) {
            log.info("Received data from Worker: " + sender().toString());
            if(!freePages.isEmpty()) {
                sender().tell(getFreePage(), getSelf());
            } else {
                workersCount--;
            }
            log.info("Gathered Movies: " + ((HashMap) message).size());
            parserBean.update((HashMap<String,Movie>) message);
        }
        else
            unhandled(message);
        if(workersCount <= 0 )
            getContext().stop(getSelf());
    }
}
