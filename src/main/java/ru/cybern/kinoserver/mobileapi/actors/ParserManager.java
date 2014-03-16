package ru.cybern.kinoserver.mobileapi.actors;

import akka.actor.ActorSystem;

import javax.ejb.Singleton;

@Singleton
public class ParserManager {

    final static ActorSystem system = ActorSystem.create("parsers");



}
