package lab.modules;


public class PrinterModule {

    public PrinterModule() {
    }
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Printer";
    }
}
