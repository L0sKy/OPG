import java.io.*;
import java.util.*;

public class OPG_exp {
    static int[][] OP_Matrix = {{1,-1,-1,-1,1,1},
            {1,1,-1,-1,1,1},
            {1,1,-2,-2,1,1},
            {-1,-1,-1,-1,0,-2},
            {1,1,-2,-2,1,1},
            {-1,-1,-1,-1,-2,0}};
    static Hashtable<Character,Integer> OP = new Hashtable<Character,Integer>(
            Map.of('+',0,
            '*',1,
            'i',2,
            '(',3,
            ')',4,
            '#',5));
    public static void main(String[] args) throws Exception{
        File in = new File(args[0]);
        Reader r = new FileReader(in);
        BufferedReader br = new BufferedReader(r);
        String exp = br.readLine();
        Character[] temp_top = new Character[3];
        exp = exp.substring(0,exp.indexOf('\r'))+"#";
        LinkedList<Character> stack = new LinkedList<>();
        stack.add('#');
        for(Character c : exp.toCharArray()){
            if(OP.get(c) == null){
                System.out.println("E");
                return;
            }
            while(OP_Matrix[OP.get(get_top_vt(stack))][OP.get(c)] == 1){
                switch (get_top_vt(stack)){
                    case '+':
                        temp_top[0] = stack.removeLast();
                        temp_top[1] = stack.removeLast();
                        temp_top[2] = stack.removeLast();
                        if(temp_top[0]!='n'||temp_top[1]!='+'||temp_top[2]!='n'){
                            System.out.println("RE");
                            return;
                        }
                        stack.add('n');
                        System.out.println("R");
                        break;
                    case '*':
                        temp_top[0] = stack.removeLast();
                        temp_top[1] = stack.removeLast();
                        temp_top[2] = stack.removeLast();
                        if(temp_top[0]!='n'||temp_top[1]!='*'||temp_top[2]!='n'){
                            System.out.println("RE");
                            return;
                        }
                        stack.add('n');
                        System.out.println("R");
                        break;
                    case 'i':
                        stack.removeLast();
                        stack.add('n');
                        System.out.println("R");
                        break;
                    case '(':
                        System.out.println("RE");
                        return;
                    case ')':
                        temp_top[0] = stack.removeLast();
                        temp_top[1] = stack.removeLast();
                        temp_top[2] = stack.removeLast();
                        if(temp_top[0]!=')'||temp_top[1]!='n'||temp_top[2]!='('){
                            System.out.println("RE");
                            return;
                        }
                        stack.add('n');
                        System.out.println("R");
                        break;
                    default:
                        System.out.println('E');
                }
            }
            if(OP_Matrix[OP.get(get_top_vt(stack))][OP.get(c)] == -2){
                System.out.println("E");
                return;
            }
            else if(OP_Matrix[OP.get(get_top_vt(stack))][OP.get(c)] == 0 ||OP_Matrix[OP.get(get_top_vt(stack))][OP.get(c)] == -1) {
                if(c != '#')
                    System.out.println("I"+c);
                stack.add(c);
            }
        }
        temp_top[0] = stack.removeLast();
        temp_top[1] = stack.removeLast();
        temp_top[2] = stack.removeLast();
        if(temp_top[0]!='#'||temp_top[1]!='n'||temp_top[2]!='#'){
            System.out.println("RE");
            return;
        }
    }

    static Character get_top_vt(LinkedList<Character> stack){
        if(OP.get(stack.getLast())==null){
            stack.removeLast();
            Character temp = stack.getLast();
            stack.add('n');
            return temp;
        }else
            return stack.getLast();
    }
}
