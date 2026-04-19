// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:59


package rs.ac.bg.etf.pp1.ast;

public class ExprCond extends Expr {

    private ConditionalExpr ConditionalExpr;

    public ExprCond (ConditionalExpr ConditionalExpr) {
        this.ConditionalExpr=ConditionalExpr;
        if(ConditionalExpr!=null) ConditionalExpr.setParent(this);
    }

    public ConditionalExpr getConditionalExpr() {
        return ConditionalExpr;
    }

    public void setConditionalExpr(ConditionalExpr ConditionalExpr) {
        this.ConditionalExpr=ConditionalExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionalExpr!=null) ConditionalExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionalExpr!=null) ConditionalExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionalExpr!=null) ConditionalExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprCond(\n");

        if(ConditionalExpr!=null)
            buffer.append(ConditionalExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprCond]");
        return buffer.toString();
    }
}
