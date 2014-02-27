package ru.cybern.kinoserver.migration;

public interface IMigrationAction {

	boolean run() throws Error;
	
}
