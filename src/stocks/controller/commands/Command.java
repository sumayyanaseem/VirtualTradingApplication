package stocks.controller.commands;

/**
 * An interface for a command which offers a
 * method execute to be implemented by various command classes.
 */
public interface Command {
  /**
   * This method will be overridden by command
   * classes to execute specific actions supported by each command class.
   */
  void execute();
}
