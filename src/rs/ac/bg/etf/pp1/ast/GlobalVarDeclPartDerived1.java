// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclPartDerived1 extends GlobalVarDeclPart {

    public GlobalVarDeclPartDerived1 () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclPartDerived1(\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclPartDerived1]");
        return buffer.toString();
    }
}
