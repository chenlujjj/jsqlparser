package org.example.jsqlparserdemo;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;

import java.util.HashMap;
import java.util.Map;

public class SQLAnalyze {

    public static void main(String[] args) throws Exception {
        Select stmt = (Select) CCJSqlParserUtil.parse("SELECT * FROM table WHERE col1 > 10 AND col2 = 'hello' AND col3 LIKE '%baa%' AND col4 = true");

        System.out.println(stmt.getWithItemsList());

        System.out.println(stmt.getSelectBody());

//        Map<String, Expression> map = new HashMap<>();
//        for (SelectItem selectItem : ((PlainSelect)stmt.getSelectBody()).getSelectItems()) {
//            selectItem.accept(new SelectItemVisitorAdapter() {
//                @Override
//                public void visit(SelectExpressionItem item) {
//                    map.put(item.getExpression(), item.getExpression());
//                }
//            });
//        }
//        System.out.println("map " + map);
    }


}
