package ru.cybern.kinoserver.mobileapi.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.parsers.IParser;
import ru.cybern.kinoserver.parsers.models.Movie;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class ParserManager extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private IParserBean parserBean;

    private int pageCount;

    private LinkedList<Page> freePages;

    private int workersCount;

    private IParser parser;

    public static Props props(final IParserBean parserBean, final IParser parser,
                              final int threadCount, final int pageCount) {
        return Props.create(new Creator<ParserManager>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ParserManager create() throws Exception {
                return new ParserManager(parserBean, parser, threadCount, pageCount);
            }
        });
    }

    public ParserManager(IParserBean parserBean, IParser parser,
                            int threadCount, int pageCount) throws IOException {
        this.parserBean = parserBean;
        this.parser = parser;
        this.pageCount = pageCount;
        this.workersCount = threadCount;
    }

    @Override
    public void preStart() {
        freePages = new LinkedList<>();
        for(int i = 1; i <= pageCount; i++)
            freePages.add(new Page(i));
        for(int i = 0; i < workersCount && i < pageCount; i++) {
            Page page = getFreePage();
            log.info("Starting worker for page: " + page);
            ActorRef worker = getContext().actorOf(ParserWorker.props(parser), parser.getClassName() + i);
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
       if(message instanceof List) {
            log.info("Received data from Worker: " + sender().toString());
            if(!freePages.isEmpty()) {
                sender().tell(getFreePage(), getSelf());
            } else {
                workersCount--;
            }
            log.info("Gathered Movies: " + ((List) message).size());
            parserBean.update((List<Movie>) message);
        }
        else
            unhandled(message);
        if(workersCount <= 0 )
            getContext().stop(getSelf());
    }
}
