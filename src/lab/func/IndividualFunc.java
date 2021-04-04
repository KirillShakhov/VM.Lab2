package lab.func;

import lab.interfaces.ICommand;
import lab.interfaces.IFunc;
import lab.modules.CalcModule;

import java.util.Scanner;

public class IndividualFunc implements IFunc, ICommand {
    private String xyz;
    @Override
    public double solve(double x) {
        CalcModule calcModule = new CalcModule(xyz);
        return calcModule.execute(x);
    }

    @Override
    public String getMessage() {
        return "Ввести собственное уравнение";
    }

    @Override
    public void execute() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Пример уравнения: x^2-cos(x)-x\nВведите уравнение:");
            this.xyz = scanner.nextLine();
            IFunc.execute(this);
        }catch (Exception e){
            System.out.println("Произошла проблема.");
        }
    }
}
