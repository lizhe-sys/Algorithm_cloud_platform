package com.bh.sfapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Caculator {
    public Double calculate(String s) {
        String expression = s.replaceAll(" ", "");   // 去除空格
        List<String> infix = expressionToList(expression);  // List
        List<String> suffix = infixToSuffix(infix); // 中缀转后缀
        Stack<String> stack = new Stack<>();    // 存储中间结果
        // 逆波兰计算器
        for (int i = 0; i < suffix.size(); i++) {
            String tmp = suffix.get(i);
            if (isOper(tmp)) {
                String num2 = stack.pop();
                String num1 = stack.pop();
                String reuslt = cal(num1, tmp, num2);
                stack.push(reuslt);
            } else { // 数字直接入栈
                stack.push(tmp);
            }
        }
        return Double.parseDouble(stack.pop());
    }

    /**
     * num1 和 num2 进行 oper 计算
     *
     * @param num1
     * @param oper
     * @param num2
     * @return
     */
    public String cal(String num1, String oper, String num2) {
        Double result = Double.valueOf(0);
        Double a = Double.parseDouble(num1);
        Double b = Double.parseDouble(num2);
        switch (oper) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
        }
        return String.valueOf(result);
    }

    /**
     * 中缀转后缀
     *
     * @param infix
     * @return
     */
    public List<String> infixToSuffix(List<String> infix) {
        List<String> suffix = new ArrayList<>();
        Stack<String> stack1 = new Stack<>();   // 只用于保存操作符
        Stack<String> stack2 = new Stack<>();   // 用于保存中间结果
        for (int i = 0; i < infix.size(); i++) {
            String tmp = infix.get(i);
            if (isOper(tmp)) {   // 操作符要根据情况来入栈 1
                if (stack1.isEmpty() || "(".equals(tmp)) {
                    // 如果 stack1 是空的，或者 tmp 是左括号，直接入栈
                    stack1.push(tmp);
                } else { // stack1 不是空的，且 tmp 不是左括号
                    if (")".equals(tmp)) {
                        // tmp 是右括号，则把 stack1 遇到左括号之前，全部倒入 stack2
                        while (!"(".equals(stack1.peek())) {
                            stack2.push(stack1.pop());
                        }
                        stack1.pop();   // 丢掉左括号
                    } else {
                        // tmp 是 +-*/ 其中之一
                        while (!stack1.isEmpty() && priority(stack1.peek()) >= priority(tmp)) {
                            // 在 tmp 能够碾压 stack1 的栈顶操作符之前，把 stack1 的栈顶操作符倒入 stack 2 中
                            stack2.push(stack1.pop());
                        }
                        // 离开 while 时，要么 stack1 已经倒空了，要么就是现在 tmp 可以压住 stack.peek() 了
                        stack1.push(tmp);
                    }
                }
            } else { // 操作数直接入栈 2
                stack2.push(tmp);
            }
        }

        // stack1 剩余操作符全部倒入 stack2
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }

        // stack2 的逆序才是结果，所以要再倒一次
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }

        // 现在 stack1 的元素倒出来的顺序就是后缀表达式
        while (!stack1.isEmpty()) {
            suffix.add(stack1.pop());
        }

        return suffix;
    }

    /**
     * 字符串转为中缀 List
     *
     * @param expression
     * @return
     */
    public List<String> expressionToList(String expression) {
        List<String> list = new ArrayList<>();
        int len = expression.length();
        String keepNum = "";
        for (int i = 0; i < len; i++) {
            char c = expression.charAt(i);
            if (isNum(c) || c =='.' || c == 'E') {
                if( c == 'E' ){
                    keepNum += c;
                    if( expression.charAt(i+1) == '-' ) {
                        keepNum += '-';
                        i++;
                    }
                    continue;
                }
                if ( i != len - 1 && ( isNum(expression.charAt(i + 1) ) || expression.charAt(i + 1) == '.' || expression.charAt(i + 1) == 'E')   ) {
                    // 如果下一个字符也是数字或者小数点
                    keepNum += c;
                } else {
                    // 当前是最后一个字符，或者下一个开始不是数字
                    keepNum += c;
                    list.add(keepNum);
                    keepNum = "";
                }
            } else {
                list.add(c + "");
            }
        }
//        System.out.println( list );
        return list;
    }

    /**
     * 判断字符 c 是不是数字
     *
     * @param c
     * @return
     */
    public boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 判断字符串 str 是不是操作符
     *
     * @param str
     * @return
     */
    public boolean isOper(String str) {
        return "+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str) ||
                "(".equals(str) || ")".equals(str);
    }

    /**
     * 返回操作符的优先级，+- 为 0，* / 为 1
     *
     * @return
     */
    public int priority(String oper) {
        if ("+".equals(oper) || "-".equals(oper)) {
            return 0;
        } else if ("*".equals(oper) || "/".equals(oper)) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
//        Caculator caculator = new Caculator();
//        Double calculate = caculator.calculate("( (1.1111E5+6.338357925415039E-4)*3.5555+5.888888 ) / 7.222");
//        System.out.println( calculate );
//        double value1 = Double.parseDouble("6.338357925415039E-4");
//        double value2 = Double.parseDouble("6.338357925415039E-4");
//        System.out.println( value1  * value2 );
    }
}
