package ru.cybern.kinoserver.mobileapi.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.parsers.impl.KinopoiskParser;
import ru.cybern.kinoserver.parsers.impl.SoundtracknetParser;
import ru.cybern.kinoserver.parsers.impl.WhatsongParser;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.IOException;

@Singleton
public class ParserRunner {

    @Inject
    private IParserBean parserBean;

    private final static int NUM_OF_THREADS = 10;

    public void startKinopoisk() {
        int pageCnt = 0;
        try {
            pageCnt = KinopoiskParser.getLastPageNumber();
        } catch (IOException e) {
            pageCnt = 50;
        }
        ActorSystem system = ActorSystem.create("parsers");
        ActorRef a = system.actorOf(ParserManager.props(parserBean, new KinopoiskParser(true),
                NUM_OF_THREADS, pageCnt), "kinopoiskManager");
        //system.actorOf(Props.create(Terminator.class, a), "terminator");
    }

    public void startWhatsong() {
        int pageCnt = 2;
        ActorSystem system = ActorSystem.create("parsers");
        ActorRef a = system.actorOf(ParserManager.props(parserBean, new WhatsongParser(true),
                NUM_OF_THREADS, pageCnt), "whatsongManager");
        //system.actorOf(Props.create(Terminator.class, a), "terminator");
    }

    public void startSoundtracknet() {
        int pageCnt = 2;
        ActorSystem system = ActorSystem.create("parsers");
        ActorRef a = system.actorOf(ParserManager.props(parserBean, new SoundtracknetParser(true),
                NUM_OF_THREADS, pageCnt), "soundtrackManager");
        //system.actorOf(Props.create(Terminator.class, a), "terminator");
    }

    public void startSTCollect() {}

    public static class Terminator extends UntypedActor {

        private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
        private final ActorRef ref;

        public Terminator(ActorRef ref) {
            this.ref = ref;
            getContext().watch(ref);
        }

        @Override
        public void onReceive(Object msg) {
            if (msg instanceof Terminated) {
                log.info("{} has terminated, shutting down system", ref.path());
                getContext().system().shutdown();
            } else {
                unhandled(msg);
            }
        }

    }


}
