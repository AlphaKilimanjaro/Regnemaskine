// *** Copyright © 2019 Alpha Kilimanjaro. All rights reserved.
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Regnemaskine {
    enum Operator {
        Plus,
        Minus,
        Gange,
        Division,
        Parantes1,
        Parantes2
    };

    public static void main(String[] args) {
        String udtryk = args[0];
        System.out.println(udtryk);

        String talStr[] = udtryk.split("[+\\-*\\/()]+");

        List<Double> tal = new ArrayList<Double>();
        for (String s : talStr) {
            try {
                tal.add(Double.parseDouble(s));
            } catch (Exception e) {
                System.out.println("Parse error");
                return;
            }
        }

        System.out.println(tal.toString());

        HashMap<Character, Operator> opPar = new HashMap<>();
        opPar.put('+', Operator.Plus);
        opPar.put('-', Operator.Minus);
        opPar.put('*', Operator.Gange);
        opPar.put('/', Operator.Division);
        opPar.put('(', Operator.Parantes1);
        opPar.put(')', Operator.Parantes2);

        List<Operator> operatorer = new ArrayList<Operator>();

        for (Character c : udtryk.toCharArray()) {
                operatorer.add(opPar.get(c));
        }
        while(operatorer.remove(null));

        System.out.println(operatorer.toString());

        List<Integer> prioriteter = new ArrayList<Integer>();

        int p = 0;
        for (Operator o : operatorer) {
            switch (o) {
                case Plus:
                case Minus:
                    prioriteter.add(new Integer(p));
                    break;
                case Gange:
                case Division:
                    prioriteter.add(new Integer(p+1));
                    break;
                case Parantes1:
                    p += 2;
                    break;
                case Parantes2:
                    p -= 2;
                    break;
            }
        }

        while (operatorer.remove(Operator.Parantes1));
        while (operatorer.remove(Operator.Parantes2));

        System.out.println(operatorer.toString());
        System.out.println(prioriteter.toString());

        int maxP = Collections.max (prioriteter);
        System.out.println(maxP);

        Double[] results = new Double[operatorer.size()]; 
        for (int curP = maxP; curP >= 0; curP--) {
            for (int i = 0; i < prioriteter.size(); i++) {
                if (prioriteter.get (i) == curP) {
                    double input1;
                    if (i > 0 && results[i-1] != null)
                        input1 = results[i-1];
                    else input1 = tal.get(i);
                    double input2;
                    if (i < prioriteter.size() - 1 && results[i+1] != null)
                        input2 = results[i+1];
                    else input2 = tal.get(i+1);
                    double r;
                    switch (operatorer.get(i)) {
                        case Plus:
                            r = input1 + input2;
                            break;
                        case Minus:
                            r = input1 - input2;
                            break;
                        case Gange:
                            r = input1 * input2;
                            break;
                        case Division:
                            r = input1 / input2;
                            break;
                        default:
                            System.out.println("Error!");
                            return;
                    }
                    results[i] = r;

                    // expand
                    for (int j = i - 1; j >= 0 && results[j] != null; j--)
                        results[j] = r;
                    for (int j = i + 1; j < operatorer.size() && results[j] != null; j++)
                        results[j] = r;
                }
            }
        }

        System.out.println("Resultat: " + results[0]);
    }
}
