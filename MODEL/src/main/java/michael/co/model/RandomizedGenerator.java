package michael.co.model;

import java.util.Random;

public class RandomizedGenerator {
    private int min;
    private int max;
    private static char[] operationsSet = {'+', '-', '*', '/', '%'};
    private char operation;
    private static Random rnd = new Random();
    private int number;

    public RandomizedGenerator(){
        /*empty space*/
    }
    public void setMin(int min){
        this.min = min;
    }
    public void setMax(int max){
        this.max = max;
    }
    public void generateOperation(){
        operation = operationsSet[rnd.nextInt(operationsSet.length)];
    }
    public char charGetOperation(){
        return this.operation;
    }
    public void generateNumber(){
        number = rnd.nextInt(max - min + 1) + min;
    }
    public int getNumber(){
        return this.number;
    }
}
