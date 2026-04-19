// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:59


package rs.ac.bg.etf.pp1.ast;

public class DesignatorLength extends Designator {

    private String I1;
    private LengthDummy LengthDummy;

    public DesignatorLength (String I1, LengthDummy LengthDummy) {
        this.I1=I1;
        this.LengthDummy=LengthDummy;
        if(LengthDummy!=null) LengthDummy.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public LengthDummy getLengthDummy() {
        return LengthDummy;
    }

    public void setLengthDummy(LengthDummy LengthDummy) {
        this.LengthDummy=LengthDummy;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LengthDummy!=null) LengthDummy.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LengthDummy!=null) LengthDummy.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LengthDummy!=null) LengthDummy.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorLength(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(LengthDummy!=null)
            buffer.append(LengthDummy.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorLength]");
        return buffer.toString();
    }
}
