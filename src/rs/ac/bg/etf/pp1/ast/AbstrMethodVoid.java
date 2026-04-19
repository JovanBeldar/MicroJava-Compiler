// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class AbstrMethodVoid extends AbstractMethodDecl {

    private String I1;
    private FormalParameters FormalParameters;

    public AbstrMethodVoid (String I1, FormalParameters FormalParameters) {
        this.I1=I1;
        this.FormalParameters=FormalParameters;
        if(FormalParameters!=null) FormalParameters.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public FormalParameters getFormalParameters() {
        return FormalParameters;
    }

    public void setFormalParameters(FormalParameters FormalParameters) {
        this.FormalParameters=FormalParameters;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParameters!=null) FormalParameters.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParameters!=null) FormalParameters.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParameters!=null) FormalParameters.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstrMethodVoid(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(FormalParameters!=null)
            buffer.append(FormalParameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstrMethodVoid]");
        return buffer.toString();
    }
}
