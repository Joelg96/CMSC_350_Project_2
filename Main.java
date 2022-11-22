/* Joel Goode - CMSC 350 - Project 2 11/8/22
 * Allow the user toselect the input file from the default directory by using an object of the JFileChooser class.
 * Populate an ArrayList of objects of type Polynomial 
 * Should the InvalidPolynomialSyntax exception be thrown, it should be caught and a JOptionPane message 
 * should be displayed containing the reason for the invalid syntax
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JFileChooser;

public class Main{
    public static void main(String[] args){

        JFileChooser chooser = new JFileChooser(".");
        String file = "";
        
        // Comparator interface implemented in a lambda expression
        Comparator<Polynomial> comparator = (Polynomial leftSide, Polynomial rightSide) -> {

            // Iterator objects used to iterate through the polynomials
            Iterator<Polynomial.Node> leftSideIterator = leftSide.iterator();
            Iterator<Polynomial.Node> rightSideIterator = rightSide.iterator();

            boolean leftSideBool = leftSideIterator.hasNext();
            boolean rightSideBool = rightSideIterator.hasNext();

            int solution = 0;

            // While until the end of the polynomials is reached
            while (leftSideBool && rightSideBool){
                Polynomial.Node leftSideNode = leftSideIterator.next();
                Polynomial.Node rightSideNode = rightSideIterator.next();

                solution = leftSideNode.getExp() - rightSideNode.getExp();

                if (solution < 0){
                    return solution;
                }

                leftSideBool = leftSideIterator.hasNext();
                rightSideBool = rightSideIterator.hasNext();

                if (leftSideBool && !rightSideBool){
                    return 1;
                }
                if (!leftSideBool && rightSideBool){
                    return -1;
                }
            }
            return solution;
        };

        // Open file chooser
        int returnValue = chooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile().getAbsolutePath();

            try (BufferedReader input = new BufferedReader(new FileReader(file))){

                ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();
                String line = null;

                // Read each line of the file and add to arraylist
                while ((line = input.readLine()) != null){
                    Polynomial polynomial = new Polynomial(line);
                    polynomials.add(polynomial);
                    System.out.println(polynomial);
                }

                if (polynomials.size() == 0){
                    throw new InvalidPolynomialSyntax("Error, Polynomials file cannot be empty!");
                }

                boolean strongFailed = false;
                boolean weakFailed = false;
                Polynomial previous = null;

                // Comparing
                for (Polynomial current : polynomials) {
                    if(previous == null){
                        previous = current;
                    }
                    else{
                        if (!strongFailed && previous.compareTo(current) > 0){
                            strongFailed = true;
                        }
                        if (!weakFailed && comparator.compare(previous, current) > 0){
                            weakFailed = true;
                        }
                    }
                    if (strongFailed && weakFailed){
                        break;
                    }
                }

                // Display results
                if (strongFailed){
                    System.out.println("\n>>Strong Order: Fail!");
                }
                else{
                    System.out.println(">>Strong Order: Pass!");
                }

                if (weakFailed){
                    System.out.println("\n>>Weak Order: Fail!");
                }
                else{
                    System.out.println(">>Weak Order: Pass!");
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
