package migration;

public interface IMigrationAction {

	boolean run() throws Error;
	
}
