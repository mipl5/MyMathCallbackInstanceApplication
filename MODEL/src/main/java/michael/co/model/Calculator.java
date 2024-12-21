package michael.co.model;

public class Calculator {
    private ParametersStorage parametersStorage;
    private boolean isSameResult;
    public Calculator(){
        /*empty space*/
    }
    public void setParametersStorage(ParametersStorage parametersStorage){
        this.parametersStorage = parametersStorage;
    }

    public boolean getIsSameResult(){
        return this.isSameResult;
    }

    public void calculate(){
        int number1 = parametersStorage.getNumber1();
        int number2 = parametersStorage.getNumber2();
        char operation = parametersStorage.getOperation();
        int result = DoCalculation(number1, number2, operation);
        if (parametersStorage.isSameResult(result))
            isSameResult = true;
    }

    private int DoCalculation(int number1, int number2, char operation) {
        if (operation == '+') // addition
            return number1 + number2;
        else if (operation == '-') // subtraction
            return number1 - number2;
        else if (operation == '*') // multiply
            return number1 * number2;
        else if (operation == '/') // divide
            return number1 / number2;
        else // mod
            return number1 % number2;
    }
}
