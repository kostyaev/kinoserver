package ru.cybern.kinoserver.mobileapi.actors.managers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Command;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.mobileapi.actors.workers.WhatSongWorker;

import java.util.LinkedList;

public class WhatSongManager extends UntypedActor {

    private int pageNumber;
    private LinkedList<Page> freePages;
    private int count;
    private int max;

    @Override
    public void preStart() {
        count = 0;
        pageNumber = 14;
        max = pageNumber;
    }

    private void genPages() {
        freePages = new LinkedList<>();
        for(int i = 1; i < pageNumber; i++)
            freePages.add(new Page(i));
    }

    private Page getFreePage() {
        return freePages.poll();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message.equals(Command.START)){
            while(count < max) {
                ActorRef worker = getContext().actorOf(Props.create(WhatSongWorker.class), "whatsong" + count++);
                worker.tell(getFreePage(), self());
            }
        }
        else if(message.equals(Command.DONE)) {
            if(!freePages.isEmpty())
                sender().tell(getFreePage(), self());
            else {
                sender().tell(Command.STOP, self());
            }
        }

    }
}
