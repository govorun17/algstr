package BoerMour;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BoerraMurraMain {
    private static int compares;

    public static void print(int[] array){
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if(i!=array.length-1)
                System.out.print(",");
        }
        System.out.println("]");
    }

    public static void printMap(Map map){
        System.out.print("[");
       for(Object i:map.keySet()){
           System.out.print(i+"("+map.get(i)+")"+" ");
       }
        System.out.println("]");
    }

    public static int[] g1(String line,String pattern){
        int[] Ro = Ro(line,pattern);
        int[] g1 = new int[line.length()];
        for (int k = 0; k < line.length()-1; k++) {
            if(Ro[k]<=Ro[k+1]){
                int q =Ro[k+1];
                g1[line.length()-q-1] = k+1;
                print(g1);
                while(q>Ro[k]){
                q = Ro[line.length()-1 - q +1];
                g1[line.length()-q-1]=k+1;
                print(g1);
                }
            }
        }
        System.out.print("g1 = ");
        print(g1);
        return g1;
    }

    //variation of border
    public static int[] g2(String pattern){
        int[] border = betta(pattern);
        int[] g2 = new int[border.length+1];
        g2[g2.length-1]=0;
        int m=border[border.length-1];
        for (int i = 0; i<g2.length-1 ; i++) {
            if(i<=border.length-m){
                g2[i]=m;
            }
            if(i==border.length-m){
                m=border[m-1];
            }
        }
        System.out.print("g2 =");
        print(g2);
        return g2;
    }

    public static int[] Ro(String line,String pattern) {
        int[] betta=betta(new StringBuilder(pattern).reverse().toString());
        System.out.print("b=");
        print(betta);
        int[] ro = new int[line.length()];
        int j=0;
        for (int i = 0; i < ro.length; i++) {
            ro[i]=betta[j];
            if(j==betta.length-1){
                j=0;
            }
            else{
                j++;
            }
        }
        int[] Ro = new int[line.length()];
        for (int i = line.length()-1; i >=0; i--) {
            Ro[i]=ro[line.length()-1-i];
        }
       /* String reversed = new StringBuilder(pattern).reverse().toString();
        int i=1;
        int j=0;
        int[] ro = new int[pattern.length()];
        while(i< pattern.length()){
            if(reversed.charAt(j) == reversed.charAt(i)){
                ro[i]=j+1;
                i++;
                j++;
            }
            else{
                if(j==0){
                    ro[i]=0;
                    i++;
                }else {
                    j = ro[j - 1];
                }
            }
        }
        int[] reversedRo = new int[pattern.length()];
        for (int k = pattern.length()-1; k >=0 ; k--) {
            reversedRo[k]=ro[pattern.length()-1-k];
        }
        System.out.print("ro = ");
        print(reversedRo);
        int[] Ro = new int[pattern.length()];
        j=0;
        for (int k = 0; k < pattern.length(); k++) {
            if(j< pattern.length()){
                Ro[k]=reversedRo[j];
                j++;
            }
            else{
                j=0;
                Ro[k]=reversedRo[j];
                j++;
            }
        }*/
        System.out.print("Ro = ");
        print(Ro);
        return Ro;
    }

    public static void findBoerMoor(String line, String pattern){
        int pLength=pattern.length();
        int sLength=line.length();
        Map<Character,Integer> sigma1=sigma1(line);
        int[] sigma2=sigma2(line,pattern);
        System.out.println("Position");
        int i=pLength-1;
        int j;
        while(i<=sLength){
            int[] i_j=hctam(line,pattern,i,pLength-1);
            i=i_j[0];
            j=i_j[1];
            if(j==-1){
                System.out.println("["+(i+1)+","+(i+pLength)+"]");
                j++;
                i++;
            }
            /*if(i!=0){
                i+=Math.max(sigma1.get(line.charAt(i-1)),sigma2[j])-1;
            }else{*/
                i+=Math.max(sigma1.get(line.charAt(i)),sigma2[j]);
           // }

        }
    }

    public static Map<Character,Integer> sigma1(String str){
        Map<Character,Integer> sigma = new HashMap<Character,Integer>();
        for (int i = 0; i < str.length(); i++) {
            sigma.put(str.charAt(i),str.length());
        }
        for (int i = str.length()-1; i >=0; i--) {
            if(sigma.get(str.charAt(i))==str.length()) {
                sigma.put(str.charAt(i), str.length() - 1 - i);
            }
        }
        System.out.print("sigma1 = ");
        printMap(sigma);
        return sigma;
    }
    
    public static int[] sigma2(String line,String pattern){
        int[] g1=g1(line,pattern);
        int[] g2=g2(pattern);
        int[] sigma = new int[g2.length];
        for (int i = sigma.length-1; i >=0 ; i--) {
            if(i!=0 && g1[i-1]!=0){
                sigma[i]=sigma.length-1-g1[i-1];
            }
            else{
                sigma[i]=sigma.length-i+sigma.length-2-g2[i];
            }
        }
        System.out.print("sigma2=");
        print(sigma);
        return sigma;

    }

    public static int[] hctam(String str, String pattern,int i, int j){
        while (i>=0 && j>=0 && i < str.length() && j < pattern.length() && str.charAt(i) == pattern.charAt(j)) {
            compares++;
            j--;
            i--;
        }
        if(j!=-1)compares++;
        return new int[]{i, j};
    }

    public static int[] betta(String s) {
        int length = s.length();
        int[] betta = new int[length];
        betta[0] = 0;
        for (int i = 1; i < length; i++) {
            int b = betta[i - 1];
            while (b > 0 && s.charAt(i) != s.charAt(b)) {
                b = betta[b - 1];
            }
            if (s.charAt(i) == s.charAt(b)) {
                betta[i] = b + 1;
            } else {
                betta[i] = 0;
            }
        }
        return betta;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter str: ");
        String str = scanner.nextLine();

        System.out.print("Enter pattern: ");
        String pattern = scanner.nextLine();

        compares=0;
        //sigma1(str);
        //sigma2(str,pattern);
        findBoerMoor(str,pattern);
        System.out.println("Compares count: "+compares );
    }
}
