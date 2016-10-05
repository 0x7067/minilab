package command;

import model.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandBlock extends Command{
    private List<Command> commands;

    public CommandBlock() {
        super(-1);

        commands = new ArrayList<Command>();
    }


    public void addCommand(Command c){
        commands.add(c);
    }

    public void execute(){
        for (int i = 0; i < commands.size(); i++) {
            Command c = commands.get(i);
            System.out.println("Comando: " + i + ": " + c);
            c.execute();
        }
    }
}
