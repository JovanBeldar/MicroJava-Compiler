// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclarationPart extends GlobalVarDeclList {

    private GlobalVarDeclPart GlobalVarDeclPart;

    public GlobalVarDeclarationPart (GlobalVarDeclPart GlobalVarDeclPart) {
        this.GlobalVarDeclPart=GlobalVarDeclPart;
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.setParent(this);
    }

    public GlobalVarDeclPart getGlobalVarDeclPart() {
        return GlobalVarDeclPart;
    }

    public void setGlobalVarDeclPart(GlobalVarDeclPart GlobalVarDeclPart) {
        this.GlobalVarDeclPart=GlobalVarDeclPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclarationPart(\n");

        if(GlobalVarDeclPart!=null)
            buffer.append(GlobalVarDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclarationPart]");
        return buffer.toString();
    }
}
