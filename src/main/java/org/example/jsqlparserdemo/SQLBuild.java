package org.example.jsqlparserdemo;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.SelectUtils;

public class SQLBuild {
    public static void main(String[] args) throws Exception {
        Select select = SelectUtils.buildSelectFromTable(new Table("mytable"));
        System.out.println(select); // SELECT * FROM mytable

        select = SelectUtils.buildSelectFromTableAndExpressions(new Table("mytable"), new Column("a"), new Column("b"));
        System.out.println(select); //SELECT a, b FROM mytable

        select = SelectUtils.buildSelectFromTableAndExpressions(new Table("mytable"), "a+b", "test");
        System.out.println(select); // SELECT a + b, test FROM mytable

        // 如何传入where语句呢？
//        select = SelectUtils.buildSelectFromTableAndExpressions(new Table("mytable"), new Where);

        // ---

        Select stmt = (Select) CCJSqlParserUtil.parse("SELECT col1 AS a, col2 AS b, col3 AS c FROM table WHERE col_1 = 10 AND col_2 = 20 AND col_3 = 30");
        System.out.println("before " + stmt.toString());

        ((PlainSelect)stmt.getSelectBody()).getWhere().accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(Column column) {
                column.setColumnName(column.getColumnName().replace("_", ""));
            }
        });

        System.out.println("after " + stmt.toString());

        // ---

        stmt = (Select) CCJSqlParserUtil.parse("SELECT * FROM table WHERE col1 > 10 AND col2 = 'hello' AND col3 LIKE '%baa%' AND col4 = true AND zone = 'sh'");
        System.out.println("before " + stmt.toString());

        ((PlainSelect)stmt.getSelectBody()).getWhere().accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(EqualsTo expr) {
                Expression left = expr.getLeftExpression();
                Expression right = expr.getRightExpression();
                if (left.getClass() == Column.class) {
                    String currColName = ((Column)left).getColumnName();
                    if (right.getClass() == StringValue.class ) {
                        if (!currColName.equals("zone")) {
                            ((Column)left).setColumnName("string_map_v2{'" + currColName + "'}");
                        }
                    } else if (right.getClass() == LongValue.class) {
                        ((Column)left).setColumnName("number_map_v2{'" + currColName + "'}");
                    }
                }
            }

            @Override
            public void visit(GreaterThan expr) {
                Expression left = expr.getLeftExpression();
                Expression right = expr.getRightExpression();
                if (left.getClass() == Column.class) {
                    String currColName = ((Column)left).getColumnName();
                    if (right.getClass() == StringValue.class ) {
                        if (!currColName.equals("zone")) {
                            ((Column)left).setColumnName("string_map_v2{'" + currColName + "'}");
                        }
                    } else if (right.getClass() == LongValue.class) {
                        ((Column)left).setColumnName("number_map_v2{'" + currColName + "'}");
                    }
                }
            }

            // 其他...
        });

        System.out.println("after " + stmt.toString());

    }
}
