package src.lib;

// Selfmade class to handle char stacks. 
// Constructor creates a char array of input size. 
// Creates array and can push char's to the top of the array and remove char's.
// Automatically removes duplicates and empty spaces.
//
// Designed to be used for improving movement controls by remembering already pressed buttons.
// 

public class CharStack {

    public char[] stack; // This is the stack

    // Constructor to instance class and create the stack. Input specifies stack size
    public CharStack(int length) {
        
        this.stack = new char[length];

    }

    // Pushes char c to top of stack
    public void push(char c) {
        
        // 1. Checks if char c already in stack, if so removes from stack. 
        for (int i = 0; i < stack.length; i++) {
            if (stack[i] == c) {
                stack[i] = 'x';
            }
        }

        // 2. Checks for empty spaces. Moves up elements to close the spaces.
        for (int i = 0; i < stack.length; i++) {
            if (stack[i] == 'x') {
                stack[i] = compact(i);
            }
        }

        // 3. Moves all elements down 1 index.
        for (int i = stack.length-1; i > 0; i--) {

            stack[i] = stack[i - 1];

        }

        // 4. Changes top element into char c.
        stack[0] = c;

        return;
    }

    // Remove char c from stack and close empty spaces.
    public void pop(char c) {

        for (int i = 0; i < stack.length; i++) {
            if (stack[i] == c) {
                stack[i] = 'x';
            }
        }
        for (int i = 0; i < stack.length; i++) {
            if (stack[i] == 'x') {
                stack[i] = compact(i);
            }
        }

        return; 
    }

    // Recursive function to close empty spaces. 
    private char compact(int i) {
        char c;
        try {

            if (stack[i+1] == 'x') {
                c = compact(i+1);


            } else {
                c = stack[i+1];
                stack[i+1] = 'x';
                
            }

            
        } catch (Exception e) {
            // TODO: handle exception
            c = 'x';
        }
        
        return c;

    }

    // Method to print the current stack. Used for testing and debugging.
    public void printStack() {

        System.out.println(stack);
    }
    
}
