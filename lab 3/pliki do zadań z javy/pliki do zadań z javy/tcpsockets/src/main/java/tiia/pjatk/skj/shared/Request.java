package tiia.pjatk.skj.shared;

public class Request {

    private static final byte GET_COMMAND_CODE = 0;

    public enum Command {
        GET(GET_COMMAND_CODE);

        private byte commandCode;

        Command(byte code){
            this.commandCode = code;
        }

        public byte getCode(){
            return this.commandCode;
        }

        public static Command getCommandByName(String name){
            Command foundCommand = null;

            for(Command command : Command.values()){
                if(command.name().equals(name)){
                    foundCommand = command;
                    break;
                }
            }
            return foundCommand;
        }

        public static Command getCommandByCode(byte code){
            Command foundCommand = null;

            for(Command command : Command.values()){
                if(command.getCode() == code){
                    foundCommand = command;
                    break;
                }
            }
            return foundCommand;
        }
    }

}
