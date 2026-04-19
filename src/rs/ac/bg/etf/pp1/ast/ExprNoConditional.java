// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:59


package rs.ac.bg.etf.pp1.ast;

public class ExprNoConditional extends Expr {

    private ExprNoCond ExprNoCond;

    public ExprNoConditional (ExprNoCond ExprNoCond) {
        this.ExprNoCond=ExprNoCond;
        if(ExprNoCond!=null) ExprNoCond.setParent(this);
    }

    public ExprNoCond getExprNoCond() {
        return ExprNoCond;
    }

    public void setExprNoCond(ExprNoCond ExprNoCond) {
        this.ExprNoCond=ExprNoCond;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprNoCond!=null) ExprNoCond.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprNoCond!=null) ExprNoCond.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprNoCond!=null) ExprNoCond.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprNoConditional(\n");

        if(ExprNoCond!=null)
            buffer.append(ExprNoCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprNoConditional]");
        return buffer.toString();
    }
}
