/* Joel Goode - CMSC 350 - Project 2 11/8/22
 * This method needs to be named Polynomial
 * Instances of the Polynomial class should define an individual polynomial
 * Objects should be represented internally by a singly linked 
 *  Each node of that linked list should contain one term of the polynomial consisting of its coefficient and exponent
 * A compareTo method 
 * An iterator method 
 * A toString method 
 */

import java.util.Iterator;

public class Polynomial implements Comparable<Polynomial>, Iterable<Polynomial.Node>{

    public static class Node {
        
        private double coefficient;
        private int exponent;
        private Node nextNode;
        
        public Node(double coefficient, int exponent) {
            this.coefficient = coefficient;
            this.exponent = exponent;
        }

        public double getCoef() {
            return coefficient;
        }

        public int getExp() {
            return exponent;
        }
    }
    
    private Node head;

    public Polynomial (String polyFile) throws InvalidPolynomialSyntax{

        if (polyFile == null || polyFile.isEmpty()){
            throw new InvalidPolynomialSyntax("Error, the uploaded file cannot be null or empty!");
        }

        // Split String into terms
        String[] polyArray = polyFile.split(" ");

        if (polyArray == null || polyArray.length == 0){
            throw new InvalidPolynomialSyntax("Error, The included Polynomial cannot be null or empty!");
        }
        if (polyArray.length % 2 != 0){
            throw new InvalidPolynomialSyntax("Error, Invalid format! Coefficient or Exponent missing!");
        }

        Node previousNode = null;
        Node currentNode = null;

        for (int i = polyArray.length - 1; i >= 0; i -= 2) {

            String coeStr = polyArray[i-1];
            String expStr = polyArray[i];
            double coe = 0;
            int exp = 0;

            // Assigning coefficient and exponents checking for invalid inputs
            try{
                coe = Double.parseDouble(coeStr);
            }
            catch (NumberFormatException nfe){
                throw new InvalidPolynomialSyntax("Invalid format! -- Coefficient");
            }
            try{
                exp = Integer.parseInt(expStr);
            }
            catch (NumberFormatException nfe){
                throw new InvalidPolynomialSyntax("Invalid format! -- Exponent");
            }

            if (coeStr != null && !coeStr.isEmpty()){
                coe = Double.parseDouble(coeStr);
            }
            else{
                throw new InvalidPolynomialSyntax("Error, Coefficient cannot be null or empty!");
            }

            if (expStr != null && !expStr.isEmpty()){
                exp = Integer.parseInt(expStr);
            }
            else{
                throw new InvalidPolynomialSyntax("Error, Exponent cannot be null or empty!");
            }

            currentNode = new Node(coe, exp);
            currentNode.nextNode = previousNode;
            previousNode = currentNode;
        }
        head = currentNode;

        if (head == null){
            throw new InvalidPolynomialSyntax("Error, Node cannot be null or empty!");
        }

        Node node = head;
        int exp = node.exponent;
        
        while ((node = node.nextNode) != null){

            if (exp <= node.exponent){
                throw new InvalidPolynomialSyntax("Error, Exponents are not in descending order!");
            }
        }
    }

    // Compare the polynomials
    public int compareTo(Polynomial other){

        int comparison = 0;
        Node leftSide = head;
        Node rightSide = other.head;
        
        while ((leftSide = leftSide.nextNode) != null && (rightSide = rightSide.nextNode) != null){
            comparison = Integer.compare(leftSide.exponent, rightSide.exponent);

            if (comparison == 0){
                comparison = Double.compare(leftSide.coefficient, rightSide.coefficient);
                
                if (comparison != 0){
                    break;
                }
            }
            else if (comparison < 0){
                break;
            }
        }
        return comparison;
    }

    // Iteration for the polynomial
    private class PolynomialIterator implements Iterator <Node>{
        
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Polynomial.Node next() {
            Node solution = current;
            current = current.nextNode;
            return solution;
        }
    }
    
    @Override
    public Iterator<Node> iterator() {
        return new PolynomialIterator();
    }

    // Converts to String representation of the polynomial
    @Override
    public String toString() {

        Iterator<Node> iterator = iterator();
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        int builderIndex = 0;
        int builderIndex1 = 0;

        while (iterator.hasNext()){

            Node node = iterator.next();

            if (!first && node.coefficient > 0){
                builder.append("+");
            }
            if (node.coefficient != 0){
                builder.append(node.coefficient).append("x^").append(node.exponent);
                first = false;
            }
        }
        
        builderIndex = builder.indexOf("x^0");
        builderIndex1 = builder.indexOf("^1");

        if (builderIndex != -1){
            builder.delete(builderIndex, builderIndex + builder.length());
        }
        if (builderIndex1 != -1){
            builder.delete(builderIndex1, builderIndex1 + 2);
        }
        return builder.toString();
    }
}
