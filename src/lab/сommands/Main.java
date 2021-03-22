package lab.сommands;

import lab.interfaces.ICommand;
import lab.modules.MenuModule;

import java.util.ArrayList;

public class Main implements ICommand {
    @Override
    public String getMessage() {
        return "Назад <--";
    }

    @Override
    public void execute() {
        ArrayList<ICommand> commands = new ArrayList<>();
        commands.add(new NonlinearEquationsMenu());
        commands.add(new SystemNonlinearEquations());
        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }
}
