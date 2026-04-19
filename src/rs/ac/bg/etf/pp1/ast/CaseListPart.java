// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:59


package rs.ac.bg.etf.pp1.ast;

public class CaseListPart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private CaseDummy CaseDummy;
    private NumberDummy NumberDummy;
    private ListOfStatements ListOfStatements;

    public CaseListPart (CaseDummy CaseDummy, NumberDummy NumberDummy, ListOfStatements ListOfStatements) {
        this.CaseDummy=CaseDummy;
        if(CaseDummy!=null) CaseDummy.setParent(this);
        this.NumberDummy=NumberDummy;
        if(NumberDummy!=null) NumberDummy.setParent(this);
        this.ListOfStatements=ListOfStatements;
        if(ListOfStatements!=null) ListOfStatements.setParent(this);
    }

    public CaseDummy getCaseDummy() {
        return CaseDummy;
    }

    public void setCaseDummy(CaseDummy CaseDummy) {
        this.CaseDummy=CaseDummy;
    }

    public NumberDummy getNumberDummy() {
        return NumberDummy;
    }

    public void setNumberDummy(NumberDummy NumberDummy) {
        this.NumberDummy=NumberDummy;
    }

    public ListOfStatements getListOfStatements() {
        return ListOfStatements;
    }

    public void setListOfStatements(ListOfStatements ListOfStatements) {
        this.ListOfStatements=ListOfStatements;
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
        if(CaseDummy!=null) CaseDummy.accept(visitor);
        if(NumberDummy!=null) NumberDummy.accept(visitor);
        if(ListOfStatements!=null) ListOfStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseDummy!=null) CaseDummy.traverseTopDown(visitor);
        if(NumberDummy!=null) NumberDummy.traverseTopDown(visitor);
        if(ListOfStatements!=null) ListOfStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseDummy!=null) CaseDummy.traverseBottomUp(visitor);
        if(NumberDummy!=null) NumberDummy.traverseBottomUp(visitor);
        if(ListOfStatements!=null) ListOfStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseListPart(\n");

        if(CaseDummy!=null)
            buffer.append(CaseDummy.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NumberDummy!=null)
            buffer.append(NumberDummy.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListOfStatements!=null)
            buffer.append(ListOfStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseListPart]");
        return buffer.toString();
    }
}
