package ru.cybern.kinoserver.mobileapi.actors.workers;

import akka.actor.UntypedActor;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Command;
import ru.cybern.kinoserver.mobileapi.actors.helpers.Page;
import ru.cybern.kinoserver.parsers.whatsong.Parser;


public class WhatSongWorker extends UntypedActor {

    @Override
    public void preStart() {
        context().parent().tell(Command.DONE, self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Page) {
            int page = ((Page) message).getPageNum();
            Parser.parse(page, page);
        }
        else if (message.equals(Command.STOP))
            getContext().stop(self());


    }
}
