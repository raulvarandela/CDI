import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class algoritmo {

    private static int numeroMayor(int[] array) {
        int min = MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > min) {
                min = array[i];
            }
        }
        return min;
    }

    private static int numeroMenor(int[] array) {
        int min = MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static void main(String[] args) {

        //declaro los arrays
        int arrayInicial[] = {112,343,546,234,647,65,11,465,11,463,235,235,3458};
        int arrayFinal[] = new int[arrayInicial.length];
        ArrayList<Integer> arrayNumeros = new ArrayList<Integer>(); //array para saber cuantos numeros diferentes hay
        int min = numeroMenor(arrayInicial);
        int max = numeroMayor(arrayInicial);

        for (int i = min; i < (max + 1); i++) { //bucle para saber cuantos numeros diferentes hay
            arrayNumeros.add(i);
        }

        int arrayRepeticiones[] = new int[arrayNumeros.size()];//array que almacenará cuantas veces se repite los numeros
        for (int i = 0; i < arrayNumeros.size(); i++) { //bucle para saber cuantas veces se repite cada numero
            int cont = 0; //contador que almacenará cuantas veces se repite cada elemento
            for (int j = 0; j < arrayInicial.length; j++) {
                if (arrayInicial[j] == arrayNumeros.get(i)) {
                    cont++;
                }
            }
           arrayRepeticiones[i] = cont;
        }

        //bucle final
        for (int i = 0; i < arrayFinal.length; i++) {
            for (int j = 0; j < arrayNumeros.size(); j++) {
                for (int k = 0; k < arrayRepeticiones[j]; k++) {
                    arrayFinal[i] = arrayNumeros.get(j);
                    i++;
                }
            }
        }

        //bucles para mostrar los arrays por pantalla
        System.out.println("Array sin ordenar");
        for (int i = 0; i < arrayInicial.length; i++) {
            System.out.print(arrayInicial[i] + " ");
        }
        System.out.println("");

        System.out.println("Array ordenado");
        for (int j = 0; j < arrayFinal.length; j++) {
            System.out.print(arrayFinal[j] + " ");
        }
        System.out.println("");

    }


}
