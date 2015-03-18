/**
 * 
 */
package com.tingken.test.api;

/**
 * @author tingken.com
 * 
 */
public class ServerCommand {
	public static enum CommandHead{
		SCREEN_CAPTURE("screen-capture"), RESTART("restart"), UPGRADE("upgrade"), NONE("none");
		String command;
		CommandHead(String command){
			this.command = command;
		}
		public String toString(){
			return command;
		}
	}
	
	private CommandHead command;
	private String relative;

	/**
	 * @return the commandHead
	 */
	public CommandHead getCommand() {
		return command;
	}
	/**
	 * @param commandHead the commandHead to set
	 */
	public void setCommand(CommandHead commandHead) {
		this.command = commandHead;
	}
	/**
	 * @return the relative
	 */
	public String getRelative() {
		return relative;
	}
	/**
	 * @param relative the relative to set
	 */
	public void setRelative(String relative) {
		this.relative = relative;
	}

}
