package org.example.jsqlparserdemo;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

public class SQLParse {

    public static void main(String[] args) throws Exception {

        Statement stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1");

        Select selectStatement = (Select) stmt;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        System.out.println(tableList);

//        Statements stmts = CCJSqlParserUtil.parseStatements("SELECT * FROM tab1; SELECT * FROM tab2");

        Expression expr = CCJSqlParserUtil.parseExpression("a*(5+mycolumn)");

        Expression whereExpr = CCJSqlParserUtil.parseCondExpression("col1 = 'hello' AND col2 = 10 AND col3 > 12 AND col4 LIKE '%world%' AND col5 = '11'");
        System.out.println(whereExpr);
        System.out.println(whereExpr.getClass());
        if (whereExpr.getClass() == AndExpression.class) {
            Expression left = ((AndExpression)whereExpr).getLeftExpression();
            Expression right = ((AndExpression)whereExpr).getRightExpression();
            System.out.println(left);
            System.out.println(right);
            if (right.getClass() == EqualsTo.class) {
                left = ((EqualsTo)right).getLeftExpression();
                right = ((EqualsTo)right).getRightExpression();
                System.out.println(left);
                System.out.println(right);
                System.out.println(left.getClass());
                System.out.println(right.getClass());
            }
        }

    }
}
