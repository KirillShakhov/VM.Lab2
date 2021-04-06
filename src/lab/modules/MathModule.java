package lab.modules;
import lab.interfaces.IFunc;
import lab.interfaces.ISysFunc;
import lab.models.Point;
import lab.models.ResultSetForSys;
import java.util.ArrayList;
import java.util.Scanner;

public class MathModule {
    public static void execute(IFunc func) {
        //Для нелинейных уравнений
        PrinterModule pr = new PrinterModule();
        Scanner scanner = new Scanner(System.in);
        double left = -5, right = 5, eps = 0.001;
        while (true) {
            pr.print("Введите левую границу:");
            left = Double.parseDouble(scanner.nextLine());
            pr.print("Введите правую границу:");
            right = Double.parseDouble(scanner.nextLine());
            if (left > right) {
                double t = left;
                left = right;
                right = t;
            }
            while (true) {
                pr.print("Введите точность:");
                eps = Double.parseDouble(scanner.nextLine());
                if (eps > 0 && eps < 1) {
                    break;
                } else {
                    pr.print("Точность должна быть больше 0 и меньше 1.");
                }
            }
            break;
        }

        ArrayList<Point> points = new ArrayList<>();
        double point1, point2;
        if (MathModule.doubChecker(func, left, right)) {
            point1 = MathModule.doubMetod(func, left, right, eps);
            if (pointChecker(left, right, point1)) {
                pr.print("Результат метода деления пополам: " + point1);
                points.add(new Point(point1, 0));
            } else {
                pr.print("Результат метода деления пополам: решение не удовлетворяет заданному интервалу");
            }
        } else {
            pr.print("Уравнение не имеет решений методом деления пополам.");
        }
        if (MathModule.cordChecker(func, left, right)) {
            point2 = MathModule.chordMethod(func, left, right, eps);
            if (pointChecker(left, right, point2)) {
                pr.print("Результат метода хорд: " + point2);
                points.add(new Point(point2, 0));
            } else {
                pr.print("Результат метода хорд: решение не удовлетворяет заданному интервалу");
            }
        } else {
            pr.print("Уравнение не имеет решений методом хорд.");
        }
        new GraphModule(func, points, left, right);
    }

    private static boolean cordChecker(IFunc func, double left, double right) {
        return true;
    }

    public static void execute(ISysFunc func) {
        // для систем нелинейных уравнений
        PrinterModule pr = new PrinterModule();
        Scanner scanner = new Scanner(System.in);
        double eps, x, y;
        while (true) {
            pr.print("Введите приближение x:");
            x = Double.parseDouble(scanner.nextLine());
            pr.print("Введите приближение y:");
            y = Double.parseDouble(scanner.nextLine());
            while (true) {
                pr.print("Введите точность:");
                eps = Double.parseDouble(scanner.nextLine());
                if (eps > 0 && eps < 1) {
                    break;
                } else {
                    pr.print("Точность должна быть больше 0 и меньше 1.");
                }
            }
            break;
        }
        ArrayList<Point> points = new ArrayList<>();
        ResultSetForSys result = MathModule.iterMetod(func, x, y, eps);
        points.add(result.getPoint());
        result.print();
        new GraphModule(func.getDraw(), points, -10, 10);
    }

    public static boolean doubChecker(IFunc function, double left, double right) {
        boolean value = false;
        for (double i = left; i < right; i += 0.5) {
            for (double j = left; j < right; j += 0.5) {
                if (function.solve(i) * function.solve(j) <= 0) {
                    value = true;
                }
            }
        }
        return value;
    }

    //Выбирается начальное приближение к отрезку [left, right], такое, что f(left)×f(right)<0
    public static double doubMetod(IFunc function, double left, double right, double eps) {
        double dx = 0, xi = 0;
        if (function.solve(left) == 0) {
            return left;
        }
        if (function.solve(right) == 0) {
            return right;
        }
        while (right - left > eps) {
            dx = (right - left) / 2.0;
            xi = left + dx;
            if (sign(function.solve(left)) == sign(function.solve(xi))) left = xi;
            else right = xi;
        }
        return xi;
    }

    private static int sign(double x) {
        if (x > 0) return 1;
        else if (x < 0) return -1;
        return 0;
    }

    public static double chordMethod(IFunc function, double left, double right, double eps) {
        while (Math.abs(right - left) > eps) {
            left = right - (right - left) * function.solve(right) / (function.solve(
                    right) - function.solve(left));
            right = left - (left - right) * function.solve(left) / (function.solve(left) - function.solve(right));
        }
        return right;
    }

    public static ResultSetForSys iterMetod(ISysFunc func, double x, double y, double eps) {
        ResultSetForSys result = new ResultSetForSys();
        double x0 = x, y0 = y, d1, d2;
        int i = 1;
        do {
            x = func.g_x(y0);
            y = func.g_y(x0);
            d1 = func.f1(x, y);
            d2 = func.f2(x, y);
            x0 = x;
            y0 = y;
            result.addIter(x, y, Math.abs(d1), Math.abs(d2));
        } while (Math.abs(d1) > eps || Math.abs(d2) > eps);
        result.setPoint(new Point(x, y));
        return result;
    }

    public static boolean pointChecker(double left, double right, double point) {
        return point >= left && point <= right;
    }
}
