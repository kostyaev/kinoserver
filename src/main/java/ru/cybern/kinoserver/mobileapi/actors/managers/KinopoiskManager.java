package ru.cybern.kinoserver.mobileapi.actors.managers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Command;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.mobileapi.actors.workers.KinopoiskWorker;
import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.mobileapi.controllers.impl.FilmBean;
import ru.cybern.kinoserver.mobileapi.controllers.impl.ParserBean;
import ru.cybern.kinoserver.parsers.models.Movie;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedList;


public class KinopoiskManager extends UntypedActor {

    @Resource(mappedName = ParserBean.JNDI_NAME)
    IParserBean parserBean;

    private int pageNumber;

    private LinkedList<Page> freePages;

    private int count;

    private int max;


    @Override
    public void preStart() {
        count = 0;
        pageNumber = 2;
        max = pageNumber;
        freePages = new LinkedList<>();
        for(int i = 1; i <= pageNumber; i++)
            freePages.add(new Page(i));

        while(count < max) {
            Page page = getFreePage();
            System.out.println("Starting worker for page: " + page);
            ActorRef worker = getContext().actorOf(Props.create(KinopoiskWorker.class), "kinopoisk" + count++);
            worker.tell(page, getSelf());
        }
    }



    private Page getFreePage() {
        Page p = freePages.poll();
        System.out.println("Page size for now is: " + freePages.size());
        return p;
    }

    @Override
    public void onReceive(Object message) throws Exception {
      if(message.equals(Command.DONE)) {
            if(!freePages.isEmpty()) {
                sender().tell(getFreePage(), getSelf());
            } else {
                sender().tell(Command.STOP, getSelf());
                count--;
            }
      } else if(message instanceof HashMap) {
          System.out.println("Received data from worker: " + sender().toString());
          if(!freePages.isEmpty()) {
              sender().tell(getFreePage(), getSelf());
          } else {
              sender().tell(Command.STOP, getSelf());
              count--;
          }
          System.out.println("Собрано фильмов: " + ((HashMap) message).size());
          parserBean.update((HashMap<String,Movie>) message);


        }
        if(count <= 0 )
            getContext().stop(getSelf());

    }
}
