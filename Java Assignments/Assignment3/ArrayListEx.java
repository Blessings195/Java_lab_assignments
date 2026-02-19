import java.util.ArrayList;
public class ArrayListEx {
    public static void main(String[] args) {
        ArrayList<Integer> intArrL = new ArrayList<Integer>();
        intArrL.add(345);
        intArrL.add(999);

        for(int i = 1; i< 10; i++){
            intArrL.add(i*i);
        }
        for (Integer e: intArrL){
            System.out.println(e);
        }
        //Another way to write the for loop for ArrayList
        intArrL.forEach(e-> System.out.println(e));
        System.out.println("Size of ArrayList: " + intArrL.size());
        System.out.println("Index of 999: " + intArrL.indexOf(999));
        intArrL.add(1);
        System.out.println("First Index of 1: " + intArrL.indexOf(1));
        System.out.println("Last Index of 1: " + intArrL.lastIndexOf(1));

        //Try removing an element
        intArrL.remove(1);

        //intArrL.forEach(e->{int r = e*e; System.out.println(r);});

        //System.out.println(intArrL);

        //Create a new ArrayList

        ArrayList<Integer> newArrL = new ArrayList<Integer>();
        newArrL.add(3);
        newArrL.add(6);
        newArrL.add(9);

        //Add these elements to the old ArrayList
        intArrL.addAll(newArrL);
        intArrL.forEach(e->System.out.println(e));

        System.out.println(intArrL);

        System.out.println(intArrL.isEmpty());
        
        //ArrayList of strings
        ArrayList<String> wordList = new ArrayList<String>(5);

        wordList.add("Cat");
        wordList.add("Bird");
        wordList.add("Cow");
        wordList.add("Pig");
        wordList.add("Buffalo");
        wordList.add("Horse");
        wordList.add("Cotton");


        wordList.forEach(w->{if (w.startsWith("C")) System.out.println(w);});

        wordList.forEach(w->{if (w.startsWith("C") && w.length() > 3) System.out.println(w);});
        wordList.remove("Cat");
        wordList.remove(3);

        
    }
}
