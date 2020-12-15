import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

public class Main {

    public static int count;
    public static void main(String[] args) {
        count = 0;
        char[] puzzle = new char[81];
        for(int i = 0; i<81; i++){
            puzzle[i] = args[0].charAt(i);
        }

        puzzle = solve(puzzle);
        System.out.println(beautify(puzzle));
    }

    public static String beautify(char[] puzzle){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<81; i++){
            if(puzzle[i] == '_'){
                sb.append("  ");
            }
            else {
                sb.append(puzzle[i] + " ");
            }

            if((i+1)%9 == 0){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static char[] solve(char[] puzzle){
        //check if its the last one
        //BASE CASE
        //if last one, check for correctness
        //if correct, return puzzle
        //else return null
        //RECURSIVE CASE
        //else
        //loop 1-9
        //fill with current number, call solve()
        //if return is puzzle, return puzzle
        //else go to next iteration
        //if all iteration complete, return null

        System.out.println("the puzzle atm: \n" + beautify(puzzle) + "\n");
        count ++;
        if(count %1000 == 0){
            System.out.println(count);
        }

        char[] answer;
        if(findIndexOf('_', puzzle) == -1){
            if(check(puzzle)){
                return puzzle;
            }
            else{
                return null;
            }
        }
        else{
            int index = findIndexOf('_', puzzle);
            char[] possibleNumbers = getPossibleNum(index, puzzle);
            System.out.println("possible numbers: " + new String(possibleNumbers));
            for(int i = 0; i<possibleNumbers.length; i++){
                answer = puzzle.clone();
                answer[index] = possibleNumbers[i];
                answer = solve(answer);
                if(answer != null){
                    return answer;
                }
            }
            return null;
        }
    }

    private static char[] getPossibleNum(int index, char[] puzzle){
        int x = index/9;
        int y = index%9;
        int b = ((index/3)%3 + (index/27)*3);

        char[] row = copyOfRange(puzzle, 9*x, 9*(x+1));
        char[] column = new char[]{puzzle[y], puzzle[y+9], puzzle[y+18], puzzle[y+27], puzzle[y+36], puzzle[y+45], puzzle[y+54], puzzle[y+63], puzzle[y+72]};
        char[] box = new char[]
                        {puzzle[(b*3 + 18*(b/3))], puzzle[(b*3 + 18*(b/3))+1], puzzle[(b*3 + 18*(b/3))+2],
                        puzzle[(b*3 + 18*(b/3))+9], puzzle[(b*3 + 18*(b/3))+10], puzzle[(b*3 + 18*(b/3))+11],
                        puzzle[(b*3 + 18*(b/3))+18], puzzle[(b*3 + 18*(b/3))+19], puzzle[(b*3 + 18*(b/3))+20]};

        char[] nums = new char[]{'1','2','3','4','5','6','7','8','9'};
        for(int i = 1; i<=9; i++){
            if(findIndexOf((char) ('0'+i), row) != -1){
                nums = removeElement((char) ('0'+i), nums);
                continue;
            }
            if(findIndexOf((char) ('0'+i), column) != -1){
                nums = removeElement((char) ('0'+i), nums);
                continue;
            }
            if(findIndexOf((char) ('0'+i), box) != -1){
                nums = removeElement((char) ('0'+i), nums);
                continue;
            }
        }
        return nums;
    }

    private static char[] removeElement(char toRemove, char[] array){
        char[] newArray = new char[array.length - 1];
        int index = findIndexOf(toRemove, array);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                newArray[j++] = array[i];
            }
        }
        return newArray;
    }

    private static int findIndexOf(char target, char[] array){
        for(int i = 0; i<array.length; i++){
            if(array[i] == target){
                return i;
            }
        }
        return -1;
    }

    private static boolean check(char[] puzzle){
        char [][] horizontals = new char[9][];
        char [][] verticals = new char[9][];
        char [][] boxes = new char[9][];
        for(int i = 0; i<9; i++){
            horizontals[i] = copyOfRange(puzzle, 9*i, 9*(i+1));
            verticals[i] = new char[]{puzzle[i], puzzle[i+9], puzzle[i+18], puzzle[i+27], puzzle[i+36], puzzle[i+45], puzzle[i+54], puzzle[i+63], puzzle[i+72]};
            boxes[i] = new char[]
                    {puzzle[27*(i/3)+((i%3)*3)], puzzle[27*(i/3)+((i%3)*3)+1], puzzle[27*(i/3)+((i%3)*3)+2],
                    puzzle[27*(i/3)+((i%3)*3)+9], puzzle[27*(i/3)+((i%3)*3)+10], puzzle[27*(i/3)+((i%3)*3)+11],
                    puzzle[27*(i/3)+((i%3)*3)+18], puzzle[27*(i/3)+((i%3)*3)+19], puzzle[27*(i/3)+((i%3)*3)+20]};
        }

        for(int i = 0; i<9; i++){
            if(checkSectionForError(horizontals[i])){
//                System.err.println("error in row "+i);
                return false;
            }
            if(checkSectionForError(verticals[i])){
//                System.err.println("error in column "+i);
                return false;
            }
            if(checkSectionForError(boxes[i])){
//                System.err.println("error in box "+i);
                return false;
            }
        }

        return true;
    }

    private static boolean checkSectionForError(char[] section) {
        Arrays.sort(section);
        for(int j = 0; j<9; j++){
            if(section[j]-'0' != j+1){
                return true;
            }
        }
        return false;
    }
}
