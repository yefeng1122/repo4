package Test1;

import java.util.Random;
import java.util.Scanner;

/*1. 定义方法 found(String str)，要求如下:
        形参: String str
        返回值类型 int
        实现:从str中随机获取一个字符，将该字符打印在控制台，并且统计该字符在str中出现的次数
        2. 在main方法中完成以下要求，提示用户从控制台键盘录入一个字符串,调用found(String str)方法，在控制台输出返回的次数
        格式如下:*/
public class DaTi1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个字符串：");
        String s = sc.nextLine();
        int i = found(s);
        System.out.println("随机字符出现的次数是："+i);
    }
    //两个明确 返回值 int 参数列表String
    public static int found(String str) {
        Random r = new Random();
        int i = r.nextInt(str.length());
        char ch = str.charAt(i);
        int count = 0;
        for (int j = 0; j < str.length(); j++) {
            if (ch == str.charAt(j)) {
                count++;
            }
        }
        System.out.println("随机字符是："+ch);
        return count;
    }
}
