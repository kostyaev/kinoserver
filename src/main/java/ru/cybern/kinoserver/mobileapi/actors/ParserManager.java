package ru.cybern.kinoserver.mobileapi.actors;

import akka.Main;
import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Command;
import ru.cybern.kinoserver.mobileapi.actors.managers.KinopoiskManager;

import javax.ejb.Singleton;

@Singleton
public class ParserManager {


    public static void startKinopoisk() {
        ActorSystem system = ActorSystem.create("parsers");
        ActorRef a = system.actorOf(Props.create(KinopoiskManager.class), "kinopoisk");
        system.actorOf(Props.create(Terminator.class, a), "terminator");

    }



    public void startSTCollect() {

    }

    public void startWhatSong() {

    }



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
