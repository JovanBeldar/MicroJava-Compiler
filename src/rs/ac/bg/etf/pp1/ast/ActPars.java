// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:59


package rs.ac.bg.etf.pp1.ast;

public class ActPars implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ActParDummyBegin ActParDummyBegin;
    private ActPar ActPar;
    private ActParList ActParList;

    public ActPars (ActParDummyBegin ActParDummyBegin, ActPar ActPar, ActParList ActParList) {
        this.ActParDummyBegin=ActParDummyBegin;
        if(ActParDummyBegin!=null) ActParDummyBegin.setParent(this);
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
        this.ActParList=ActParList;
        if(ActParList!=null) ActParList.setParent(this);
    }

    public ActParDummyBegin getActParDummyBegin() {
        return ActParDummyBegin;
    }

    public void setActParDummyBegin(ActParDummyBegin ActParDummyBegin) {
        this.ActParDummyBegin=ActParDummyBegin;
    }

    public ActPar getActPar() {
        return ActPar;
    }

    public void setActPar(ActPar ActPar) {
        this.ActPar=ActPar;
    }

    public ActParList getActParList() {
        return ActParList;
    }

    public void setActParList(ActParList ActParList) {
        this.ActParList=ActParList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParDummyBegin!=null) ActParDummyBegin.accept(visitor);
        if(ActPar!=null) ActPar.accept(visitor);
        if(ActParList!=null) ActParList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParDummyBegin!=null) ActParDummyBegin.traverseTopDown(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
        if(ActParList!=null) ActParList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParDummyBegin!=null) ActParDummyBegin.traverseBottomUp(visitor);
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        if(ActParList!=null) ActParList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActPars(\n");

        if(ActParDummyBegin!=null)
            buffer.append(ActParDummyBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPar!=null)
            buffer.append(ActPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParList!=null)
            buffer.append(ActParList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActPars]");
        return buffer.toString();
    }
}
