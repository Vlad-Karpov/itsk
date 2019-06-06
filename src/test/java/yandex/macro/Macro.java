package yandex.macro;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Вычисление макро выражений.
 */
public class Macro {

    public enum MacroExpressionOperation {

        UNKNOWN(0, "Unknown", 0),
        ROOT(1, "Root", 0),
        MACRO(2, "Macro", 1000),
        CONSTANT_NUMBER(3, "Constant number", 1000),
        OPERATION_PLUS(4, "Operation plus", 100),
        OPERATION_MINUS(5, "Operation minus", 100),
        OPERATION_MULTIPLY(6, "Operation multiply", 200),
        OPERATION_DIVIDE(7, "Operation divide", 200);

        private int code;
        private String desc;
        private int priority;

        MacroExpressionOperation(int code, String desc, int priority) {
            this.code = code;
            this.desc = desc;
            this.priority = priority;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }
    }

    public class Variant {

        private Object value = null;
        private Class typeValue = null;

        public Variant(Object value) {
            this();
            this.value = value;
            this.typeValue = value.getClass();
        }

        public Variant() {
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Class getTypeValue() {
            return typeValue;
        }

        public void setTypeValue(Class typeValue) {
            this.typeValue = typeValue;
        }

        @Override
        public String toString() {
            if (value == null) {
                return "null";
            } else {
                return value.toString();
            }
        }
    }

    public class MacroExpressionNode {

        private MacroExpressionOperation op = MacroExpressionOperation.UNKNOWN;
        private Variant value = new Variant();
        private MacroExpression subExcprition;
        private MacroExpressionNode parent = null;
        private List<MacroExpressionNode> child = new ArrayList<MacroExpressionNode>();

        public MacroExpressionNode() {
        }

        public MacroExpressionOperation getOp() {
            return op;
        }

        public void setOp(MacroExpressionOperation op) {
            this.op = op;
        }

        public Variant getValue() {
            return value;
        }

        public void setValue(Variant value) {
            this.value = value;
        }

        public MacroExpressionNode getParent() {
            return parent;
        }

        public void setParent(MacroExpressionNode parent) {
            this.parent = parent;
        }

        public List<MacroExpressionNode> getChild() {
            return child;
        }

        public void setChild(List<MacroExpressionNode> child) {
            this.child = child;
        }

        public MacroExpression getSubExcprition() {
            return subExcprition;
        }

        public void setSubExcprition(MacroExpression subExcprition) {
            this.subExcprition = subExcprition;
        }
    }

    public class MacroExpression {

        private String expression;
        private MacroExpressionNode root;
        private MacroExpressionNode currentNode;

        private int roundParenthes = 0;

        private int constNumberBounds = 0;
        private StringBuilder constNumberExpression = new StringBuilder();

        public MacroExpression() {
            root = new MacroExpressionNode();
            root.setOp(MacroExpressionOperation.ROOT);
            currentNode = root;
        }

        public MacroExpression(MacroExpressionNode theRoot, String theExpression) {
            root = theRoot;
            currentNode = root;
            setExpression(theExpression);
        }

        public MacroExpression(MacroExpressionNode theRoot) {
            root = theRoot;
            currentNode = root;
        }

        public MacroExpression(String theExpression) {
            this();
            setExpression(theExpression);
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
            parse();
        }

        public void parse() {
            int i = 0;
            while (i < expression.length()) {
                char ch = expression.charAt(i);
                switch (ch) {
                    case '(': {
                        constantCheck();
                        roundParenthes++;
                        i++;
                        StringBuilder subExpression = new StringBuilder();
                        while (roundParenthes > 0) {
                            ch = expression.charAt(i);
                            if (ch == '(') roundParenthes++;
                            if (ch == ')') roundParenthes--;
                            if (roundParenthes > 0) subExpression.append(ch);
                            i++;
                        }
                        i--;
                        parenthesCheck(subExpression.toString());
                        break;
                    }
                    case '.':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '0': {
                        constNumberBounds++;
                        constNumberExpression.append(ch);
                        break;
                    }
                    case ' ': {
                        constantCheck();
                        break;
                    }
                    case '+': {
                        constantCheck();
                        operationCheck(MacroExpressionOperation.OPERATION_PLUS);
                        break;
                    }
                    case '-': {
                        constantCheck();
                        operationCheck(MacroExpressionOperation.OPERATION_MINUS);
                        break;
                    }
                    case '*': {
                        constantCheck();
                        operationCheck(MacroExpressionOperation.OPERATION_MULTIPLY);
                        break;
                    }
                    case '/': {
                        constantCheck();
                        operationCheck(MacroExpressionOperation.OPERATION_DIVIDE);
                        break;
                    }
                    default: {
                        constantCheck();
                        break;
                    }
                }
                i++;
            }
            constantCheck();
        }

        private void parenthesCheck(String subExpression) {
            MacroExpression newMacroExpression = new MacroExpression(subExpression);
            MacroExpressionNode newMacroExpressionNode = new MacroExpressionNode();
            newMacroExpressionNode.setOp(MacroExpressionOperation.MACRO);
            newMacroExpressionNode.setSubExcprition(newMacroExpression);
            addNode(newMacroExpressionNode);
        }

        private void operationCheck(MacroExpressionOperation theOperation){
            MacroExpressionNode newOperationNode = new MacroExpressionNode();
            newOperationNode.setOp(theOperation);
            addNode(newOperationNode);
        }

        private void constantCheck(){
            if (constNumberBounds > 0) {
                MacroExpressionNode newDoubleConstantNode = new MacroExpressionNode();
                newDoubleConstantNode.setOp(MacroExpressionOperation.CONSTANT_NUMBER);
                newDoubleConstantNode.setValue(new Variant(Double.parseDouble(constNumberExpression.toString())));
                constNumberExpression = new StringBuilder();
                constNumberBounds = 0;
                addNode(newDoubleConstantNode);
            }
        }

        private void addNode(MacroExpressionNode addNode) {
            MacroExpressionNode previous = null;
            MacroExpressionNode current = currentNode;
            while (current != null && current.getOp().getPriority() >= addNode.getOp().getPriority()) {
                previous = current;
                current = current.getParent();
            }
            if (current != null) {
                if (previous != null) {
                    for (int i = 0; i < current.getChild().size(); i++) {
                        if (current.getChild().get(i) == previous) {
                            current.getChild().remove(i);
                            current.getChild().add(addNode);
                            addNode.getChild().add(previous);
                            addNode.setParent(current);
                            currentNode = addNode;
                            break;
                        }
                    }
                } else {
                    current.getChild().add(addNode);
                    addNode.setParent(current);
                    currentNode = addNode;
                }
            } else {
                throw new RuntimeException("RT Error #1!");
            }
        }

        public Variant evolute() {
            return evolute(root).getValue();
        }

        private MacroExpressionNode evolute(MacroExpressionNode node) {
            MacroExpressionNode result = null;
            switch (node.getOp()) {
                case ROOT: {
                    if (node.getChild().size() > 0) {
                        root.setValue(evolute(node.getChild().get(0)).getValue());
                    }
                    result = root;
                    break;
                }
                case MACRO: {
                    node.setValue(new Variant(node.getSubExcprition().evolute().getValue()));
                    result = node;
                    break;
                }
                case CONSTANT_NUMBER: {
                    result = node;
                    break;
                }
                case OPERATION_PLUS: {
                    Double doubleS = (Double) evolute(node.getChild().get(0)).getValue().getValue();
                    doubleS = doubleS + (Double) evolute(node.getChild().get(1)).getValue().getValue();
                    node.setValue(new Variant(doubleS));
                    result = node;
                    break;
                }
                case OPERATION_MINUS: {
                    Double doubleS = (Double) evolute(node.getChild().get(0)).getValue().getValue();
                    doubleS = doubleS - (Double) evolute(node.getChild().get(1)).getValue().getValue();
                    node.setValue(new Variant(doubleS));
                    result = node;
                    break;
                }
                case OPERATION_MULTIPLY: {
                    Double doubleS = (Double) evolute(node.getChild().get(0)).getValue().getValue();
                    doubleS = doubleS * (Double) evolute(node.getChild().get(1)).getValue().getValue();
                    node.setValue(new Variant(doubleS));
                    result = node;
                    break;
                }
                case OPERATION_DIVIDE: {
                    Double doubleS = (Double) evolute(node.getChild().get(0)).getValue().getValue();
                    doubleS = doubleS / (Double) evolute(node.getChild().get(1)).getValue().getValue();
                    node.setValue(new Variant(doubleS));
                    result = node;
                    break;
                }
                default: {
                    result = node;
                    break;
                }
            }
            return result;
        }

    }

    @Test
    public void test1() {
        MacroExpression expression = new MacroExpression("(((10 + 8) * 3.14 + 125) - 1)/2.17 + 5 + 6 * 5");
        //MacroExpression expression = new MacroExpression("3 * (4 + 5 * ((10 - 10) / 2 - 10) - 10) * 4");
        System.out.println(expression.evolute());
    }

}
