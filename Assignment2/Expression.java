
import java.util.Stack;
import java.util.ArrayList;

//Name: Brendan Furtado
//Student ID: 260737867

public class Expression  {
	private ArrayList<String> tokenList;

	//  Constructor    
	/**
	 * The constructor takes in an expression as a string
	 * and tokenizes it (breaks it up into meaningful units)
	 * These tokens are then stored in an array list 'tokenList'.
	 */

    Expression(String expressionString) throws IllegalArgumentException{

        tokenList = new ArrayList<String>();

        StringBuilder token = new StringBuilder();

        //ADD YOUR CODE BELOW HERE

        //..

        String[] tokens;

        //This splits into tokens but the ++ and -- appear as two consecutive tokens + or - the tokens are retained
        tokens = expressionString.split("(?<=[-+*/()\\[\\]])|(?=[-+*/()\\[\\]])");

        for (int i = 0; i < tokens.length; i++) {

            //Reset the string builder
            token.setLength(0);

            //Replaces spaces with null
            String s = tokens[i].replace(" ","");

            //Ignores any subsequent nulls
            if (s.equals("")) {
                continue;
            }

            token.append(s);

            //This handles making ++ and -- into a singular token ++ & --
            //And then adds it to the tokenList
            if (s.equals("+") || s.equals("-") && i + 1 < tokens.length) {
                if (tokens[i+1].equals("+") || tokens[i+1].equals("-")) {
                    token.append(s);
                    i++;
                }
            }
            tokenList.add(token.toString());
        }


        //..

        //ADD YOUR CODE ABOVE HERE

    }
    /**

     * This method evaluates the expression and returns the value of the expression

     * Evaluation is done using 2 stack ADTs, operatorStack to store operators

     * and valueStack to store values and intermediate results.

     * - You must fill in code to evaluate an expression using 2 stacks

     */

    public Integer eval(){

        Stack<String> operatorStack = new Stack<String>();
        Stack<Integer> valueStack = new Stack<Integer>();

        //ADD YOUR CODE BELOW HERE
        //..

        operatorStack.push("$"); //This indicates the top of the stack for convenience


        for(int i = 0; i < tokenList.size(); i++) {

            //tokenList(i) for below
            String tok = tokenList.get(i);


            //Don't push the OPEN ( or [ brackets on the operator stack. they are redundant;

            // So (2 * 3)  looks like this:
            // Value stack = 2,3
            // Operator stack = $,*,)

            if (tok.equals("(") || tok.equals("[")) {
                continue;
            }
            if (isInteger(tok)) {
                valueStack.push(Integer.valueOf(tok));
            }
            else {
                operatorStack.push(tok);
            }

            //For every iteration through the loop, check if we hit a ) or ] bracket. this implies that we should
            // evaluate what is on the stack.
            //Reduce operates on the stack and decides if a terminal node has been hit in which case it can evaluate
            reduce(operatorStack, valueStack);
        }
        //Return calculation
        return valueStack.pop();

        //..

        //ADD YOUR CODE ABOVE HERE
    }

    //Helper methods

    //Helper method to determine if we hit a ) or ] which indicates the
    // termination of the expression or sub expression.
    //Tokens ) and ] determine the end, return true if there
    private static boolean isTerminal(String token) {

        String[] terminals = { ")", "]" };

        for(int i = 0; i < terminals.length; i++){
            if(token.equals(terminals[i])){

                return true;
            }
        }
        return false;
    }


    public static void reduce(Stack<String> ostack, Stack<Integer> vstack){

		/*  Based on the language grammar provided, reduce will take an element off the stack and determine
		    if the expression is complete in order to evaluate it.
		    Completeness is determined by the terminal tokens which in this case are: ) and ]
		*/

        // Do nothing if it is not a terminal node
        if (!isTerminal(ostack.peek())) {
            return;
        }

        // In the case of (2*3) the 2 stacks look like this:
        // ostack = $,*,)
        // vstack = 2,3

        //remove the terminal token from the stack and discard it.
        String token = ostack.pop();

        // First we deal with the termination of expression which is symbolized by )
        if (token.equals(")")) {

            //Remove the operator from the ostack.
            String operator = ostack.pop();
            int result = 0;
            int val1, val2;

            //For binary operators, we will remove two elements from the value stack and perform the operation
            //We are told that the expression string is always valid so we don't have any extra tests on the stack
            //It is guaranteed to have 2 values.
            switch (operator) {

                // We hit the bottom of the stack and there's nothing to do.
                // Push the terminal node back and return
                case "$":
                    ostack.push(operator);
                    break;
                case "+":
                    val1 = vstack.pop();
                    val2 = vstack.pop();
                    result = val1 + val2;
                    vstack.push(result);
                    break;

                case "*":
                    val1 = vstack.pop();
                    val2 = vstack.pop();
                    result = val1 * val2;
                    vstack.push(result);
                    break;

                case "-":
                    val1 = vstack.pop();
                    val2 = vstack.pop();
                    result = val2 - val1;
                    vstack.push(result);
                    break;

                case "/":
                    val1 = vstack.pop();
                    val2 = vstack.pop();
                    result = val2 / val1;
                    vstack.push(result);
                    break;

                //Handles --
                case "--":
                    val1 = vstack.pop();
                    result = val1 - 1;
                    vstack.push(result);
                    break;

                //Handles ++
                case "++":
                    val1 = vstack.pop();
                    result = val1 + 1;
                    vstack.push(result);
                    break;


            } //Close binary operator cases
        }

        //Else we encounter terminal token ]
        //Handles absolute value and It is the termination of absolute value symbol.
        else {
            int result = vstack.pop();
            if(result < 0){
                result *= -1;
            }
            vstack.push(result);
        }

    }

    /**
     * Helper method to test if a string is an integer
     * Returns true for strings of integers like "456"
     * and false for string of non-integers like "+"
     * - DO NOT EDIT THIS METHOD
     */

    private boolean isInteger(String element){
        try{
            Integer.valueOf(element);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

	/**
	 * Method to help print out the expression stored as a list in tokenList.
	 * - DO NOT EDIT THIS METHOD    
	 */

	@Override
	public String toString(){	
		String s = new String(); 
		for (String t : tokenList )
			s = s + "~"+  t;
		return s;		
	}

}

