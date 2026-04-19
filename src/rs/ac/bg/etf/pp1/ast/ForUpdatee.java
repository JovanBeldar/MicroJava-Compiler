// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class ForUpdatee extends ForUpdate {

    private DesignatorStatementUpdate DesignatorStatementUpdate;

    public ForUpdatee (DesignatorStatementUpdate DesignatorStatementUpdate) {
        this.DesignatorStatementUpdate=DesignatorStatementUpdate;
        if(DesignatorStatementUpdate!=null) DesignatorStatementUpdate.setParent(this);
    }

    public DesignatorStatementUpdate getDesignatorStatementUpdate() {
        return DesignatorStatementUpdate;
    }

    public void setDesignatorStatementUpdate(DesignatorStatementUpdate DesignatorStatementUpdate) {
        this.DesignatorStatementUpdate=DesignatorStatementUpdate;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementUpdate!=null) DesignatorStatementUpdate.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementUpdate!=null) DesignatorStatementUpdate.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementUpdate!=null) DesignatorStatementUpdate.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForUpdatee(\n");

        if(DesignatorStatementUpdate!=null)
            buffer.append(DesignatorStatementUpdate.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForUpdatee]");
        return buffer.toString();
    }
}
