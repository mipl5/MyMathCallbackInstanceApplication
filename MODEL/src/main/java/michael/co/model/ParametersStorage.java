package michael.co.model;

public class ParametersStorage {
    private int number1;
    private char operation;
    private int number2;
    private int result;

    public ParametersStorage(){
        /*empty space*/
    }
    // setters:
    public void setNumber1(int number1){
        this.number1 = number1;
    }
    public void setNumber2(int number2){
        this.number2 = number2;
    }
    public void setOperation(char operation){
        this.operation = operation;
    }
    public void setResult(int result){
        this.result = result;
    }
    // getters:
    public int getResult(){
        return this.result;
    }
    public int getNumber1(){
        return number1;
    }
    public int getNumber2(){
        return number2;
    }
    public char getOperation(){
        return operation;
    }
    public boolean isSameResult(int result){
        return this.result == result;
    }
}
