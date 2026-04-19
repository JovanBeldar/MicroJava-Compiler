// generated with ast extension for cup
// version 0.8
// 9/2/2026 18:31:58


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclarationList extends GlobalVarDeclList {

    private GlobalVarDeclList GlobalVarDeclList;
    private GlobalVarDeclPart GlobalVarDeclPart;

    public GlobalVarDeclarationList (GlobalVarDeclList GlobalVarDeclList, GlobalVarDeclPart GlobalVarDeclPart) {
        this.GlobalVarDeclList=GlobalVarDeclList;
        if(GlobalVarDeclList!=null) GlobalVarDeclList.setParent(this);
        this.GlobalVarDeclPart=GlobalVarDeclPart;
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.setParent(this);
    }

    public GlobalVarDeclList getGlobalVarDeclList() {
        return GlobalVarDeclList;
    }

    public void setGlobalVarDeclList(GlobalVarDeclList GlobalVarDeclList) {
        this.GlobalVarDeclList=GlobalVarDeclList;
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
        if(GlobalVarDeclList!=null) GlobalVarDeclList.accept(visitor);
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarDeclList!=null) GlobalVarDeclList.traverseTopDown(visitor);
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarDeclList!=null) GlobalVarDeclList.traverseBottomUp(visitor);
        if(GlobalVarDeclPart!=null) GlobalVarDeclPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclarationList(\n");

        if(GlobalVarDeclList!=null)
            buffer.append(GlobalVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarDeclPart!=null)
            buffer.append(GlobalVarDeclPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclarationList]");
        return buffer.toString();
    }
}
