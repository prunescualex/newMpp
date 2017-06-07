package Controller;



public class ControllerException extends Exception {
    public ControllerException(){
        System.out.println(this.getCause());
    }
}
