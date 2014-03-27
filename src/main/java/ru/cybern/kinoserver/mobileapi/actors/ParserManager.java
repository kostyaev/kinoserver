package ru.cybern.kinoserver.mobileapi.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ru.cybern.kinoserver.mobileapi.actors.managers.KinopoiskManager;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;

import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class ParserManager {

    @Inject
    private IParserBean parserBean;

    private int kinopoiskPageCount = 2;

    public void startKinopoisk() {
        ActorSystem system = ActorSystem.create("parsers");
        ActorRef a = system.actorOf(KinopoiskManager.props(parserBean, kinopoiskPageCount), "kinopoisk");
        //system.actorOf(Props.create(Terminator.class, a), "terminator");
    }

    public void startSTCollect() {}

    public void startWhatSong() {}

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
