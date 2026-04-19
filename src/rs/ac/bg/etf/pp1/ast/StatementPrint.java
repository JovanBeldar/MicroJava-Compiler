// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class StatementPrint extends Statement {

    private Expr Expr;
    private NumberPrint NumberPrint;

    public StatementPrint (Expr Expr, NumberPrint NumberPrint) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.NumberPrint=NumberPrint;
        if(NumberPrint!=null) NumberPrint.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public NumberPrint getNumberPrint() {
        return NumberPrint;
    }

    public void setNumberPrint(NumberPrint NumberPrint) {
        this.NumberPrint=NumberPrint;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(NumberPrint!=null) NumberPrint.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(NumberPrint!=null) NumberPrint.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(NumberPrint!=null) NumberPrint.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrint(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NumberPrint!=null)
            buffer.append(NumberPrint.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrint]");
        return buffer.toString();
    }
}
