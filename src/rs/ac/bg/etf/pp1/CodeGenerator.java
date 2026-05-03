package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPC;
	
	public static void initializeMethods() {
		Obj ordMethod = Tab.find("ord");
		Obj chrMethod = Tab.find("chr");
		ordMethod.setAdr(Code.pc);
		chrMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj lenMethod = Tab.find("len");
		lenMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	private Stack<Integer> skipAndFalse = new Stack<>();
	private Stack<Integer> skipOrTrue = new Stack<>();
	private Stack<Integer> skipThen = new Stack<>();
	private Stack<Integer> skipElse = new Stack<>();
	private Stack<Integer> returnToForUpdate = new Stack<>();
	private Stack<Integer> returnToForCondition = new Stack<>();
	private int jumpToForThen;
	
	private Stack<Integer> jumpToNextCaseStart = new Stack<>();
	private Stack<Integer> jumpToNextCaseStatementStart = new Stack<>();
	
	private Stack<Integer> skipTernary2nd = new Stack<>();
	
	private Stack<List<Integer>> breakStack = new Stack<>();
	
	private boolean insideCase = false;
	
	public int getMainPC() {
		return this.mainPC;
	}
	
	@Override
	public void visit(MethVoidName methVoidName) {
		methVoidName.obj.setAdr(Code.pc);
		if(methVoidName.getI1().equals("main")) {
			mainPC = Code.pc;
		}
		Code.put(Code.enter);
		Code.put(methVoidName.obj.getLevel());
		Code.put(methVoidName.obj.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethTypeName methTypeName) {
		methTypeName.obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(methTypeName.obj.getLevel());
		Code.put(methTypeName.obj.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(StatementPrint statementPrint) {
		if(statementPrint.getNumberPrint() instanceof NoNumberPrint) {
			Code.loadConst(0);
		} else {
			Code.loadConst(((NumberPrintt)statementPrint.getNumberPrint()).getN1());
		}
		if(statementPrint.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(FactorNumber factorNumber) {
		Code.loadConst(factorNumber.getN1());
	}
	
	@Override
	public void visit(FactorChar factorChar) {
		Code.loadConst(factorChar.getC1());
	}
	
	@Override
	public void visit(FactorBool factorBool) {
		Code.loadConst(factorBool.getB1());
	}
	
	@Override
	public void visit(NumberPrintt numberPrintt) {
		Code.loadConst(numberPrintt.getN1());
	}
	
	@Override
	public void visit(TermListt termListt) {
		if(termListt.getAddop() instanceof AddopPl) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(FactorListt factorListt) {
		if(factorListt.getMulop() instanceof MulopAs) {
			Code.put(Code.mul);
		} else if (factorListt.getMulop() instanceof MulopSl) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}
	
	@Override
	public void visit(FactorVariableReference factorVariableReference) {
		if(factorVariableReference.getDesignator() instanceof DesignatorLength) {
			Code.load(((DesignatorLength)factorVariableReference.getDesignator()).getLengthDummy().obj);
			int offset = Tab.find("len").getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		} else {
			Code.load(factorVariableReference.getDesignator().obj);
		}
	}
	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Code.load(designatorArrayName.obj);
	}
	
	@Override
	public void visit(DesignatorStatementAssignUpdate designatorStatementAssignUpdate) {
		Code.store(designatorStatementAssignUpdate.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatementAssign designatorStatementAssign) {
		Code.store(designatorStatementAssign.getDesignator().obj);
	}
	
	@Override
	public void visit(TermListPartMinus termListPartMinus) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(FactorNewExpr factorNewExpr) {
		Code.put(Code.newarray);
		if(factorNewExpr.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	
	@Override
	public void visit(DesignatorStatementInc designatorStatementInc) {
		if(designatorStatementInc.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatementInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatementInc.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatementIncUpdate designatorStatementIncUpdate) {
		if(designatorStatementIncUpdate.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatementIncUpdate.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatementIncUpdate.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		if(designatorStatementDec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatementDec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatementDec.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatementDecUpdate designatorStatementDecUpdate) {
		if(designatorStatementDecUpdate.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatementDecUpdate.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatementDecUpdate.getDesignator().obj);
	}
	
	@Override
	public void visit(StatementReturnNoExpr statementReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(StatementReturnExpr statementReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(StatementRead statementRead) {
		if(statementRead.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(statementRead.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatementNoActPars designatorStatementNoActPars) {
		int offset = designatorStatementNoActPars.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(!designatorStatementNoActPars.getDesignator().obj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(DesignatorStatementActPars designatorStatementActPars) {
		int offset = designatorStatementActPars.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(!designatorStatementActPars.getDesignator().obj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(DesignatorStatementNoActParsUpdate designatorStatementNoActParsUpdate) {
		int offset = designatorStatementNoActParsUpdate.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(!designatorStatementNoActParsUpdate.getDesignator().obj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(DesignatorStatementActParsUpdate designatorStatementActParsUpdate) {
		int offset = designatorStatementActParsUpdate.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(!designatorStatementActParsUpdate.getDesignator().obj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(FactorFunctionCallNoActPars factorFunctionCallNoActPars) {
		int offset = factorFunctionCallNoActPars.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(FactorFunctionCallActPars factorFunctionCallActPars) {
		int offset = factorFunctionCallActPars.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(CondFactExpr condFactExpr) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		skipAndFalse.push(Code.pc - 2);
	}
	
	@Override
	public void visit(CondFactt condFactt) {
		Relop relOp = condFactt.getRelop();
		if(relOp instanceof RelopEqEq) {
			Code.putFalseJump(Code.eq, 0);
		} else if(relOp instanceof RelopNotEq) {
			Code.putFalseJump(Code.ne, 0);
		} else if(relOp instanceof RelopGr) {
			Code.putFalseJump(Code.gt, 0);
		} else if(relOp instanceof RelopGrEq) {
			Code.putFalseJump(Code.ge, 0);
		} else if(relOp instanceof RelopLe) {
			Code.putFalseJump(Code.lt, 0);
		} else { //RelopLeEq
			Code.putFalseJump(Code.le, 0);
		}
		skipAndFalse.push(Code.pc - 2);
	}
	
	@Override
	public void visit(OrNonTerm orNonTerm) {
		Code.putJump(0);
		skipOrTrue.push(Code.pc - 2);
		while(!skipAndFalse.empty()) {
			Code.fixup(skipAndFalse.pop());
		}
	}
	
	@Override
	public void visit(Conditionn conditionn) {
		if(!(conditionn.getParent() instanceof Conditionn)) {
			Code.putJump(0);
			skipOrTrue.push(Code.pc - 2);
			while(!skipAndFalse.empty()) {
				Code.fixup(skipAndFalse.pop());
			}
			Code.putJump(0);
//			skipThen = Code.pc - 2;
			skipThen.push(Code.pc - 2);
			while(!skipOrTrue.empty()) {
				Code.fixup(skipOrTrue.pop());
			}
		}
	}
	
	@Override
	public void visit(ConditionCondTerm conditionCondTerm) {
		if(!(conditionCondTerm.getParent() instanceof Conditionn)) {
			Code.putJump(0);
			skipOrTrue.push(Code.pc - 2);
			while(!skipAndFalse.empty()) {
				Code.fixup(skipAndFalse.pop());
			}
			Code.putJump(0);
//			skipThen = Code.pc - 2;
			skipThen.push(Code.pc - 2);
			while(!skipOrTrue.empty()) {
				Code.fixup(skipOrTrue.pop());
			}
		}
	}
	
	@Override
	public void visit(NoElseStmt noElseStmt) {
		Code.fixup(skipThen.pop());
	}
	
	@Override
	public void visit(ElseDummy elseDummy) {
		Code.putJump(0);
//		skipElse = Code.pc - 2;
		skipElse.push(Code.pc - 2);
		Code.fixup(skipThen.pop());
	}
	
	@Override
	public void visit(ElseStmt elseStmt) {
		Code.fixup(skipElse.pop());
	}
	
//	@Override
//	public void visit(StatementForInitCondUpdt statementForInitCondUpdt) {
//		//prvo jmp na condition!
//		
//		Code.fixup(skipThen.pop());
//	}
	
	@Override
	public void visit(StatementFor statementFor) {
		Code.putJump(returnToForUpdate.pop());
		if(statementFor.getForCond() instanceof ForCondd) {
			Code.fixup(skipThen.pop());
		}
		returnToForCondition.pop();
		for(Integer i : breakStack.peek()) {
			Code.fixup(i.intValue());
		}
		breakStack.pop();
	}
	
	@Override
	public void visit(ForRparen forRparen) {
		Code.putJump(returnToForCondition.peek());
		Code.fixup(jumpToForThen);
	}
	
	@Override
	public void visit(ForSecondSemicolon forSecondSemicolon) {
		Code.putJump(0);
		jumpToForThen = Code.pc - 2;
		returnToForUpdate.push(Code.pc);
	}
	
	@Override
	public void visit(ForFirstSemicolon forFirstSemicolon) {
		returnToForCondition.push(Code.pc);
	}
	
	@Override
	public void visit(CaseDummy caseDummy) {
		if(jumpToNextCaseStart.peek() != -1) {
			Code.fixup(jumpToNextCaseStart.peek());
		}
		Code.put(Code.dup);
		insideCase = true;
	}
	
	@Override
	public void visit(NumberDummy numberDummy) {
		Code.loadConst(numberDummy.getN1());
		Code.putFalseJump(Code.eq, 0);
		jumpToNextCaseStart.pop();
		jumpToNextCaseStart.push(Code.pc - 2);
		if(jumpToNextCaseStatementStart.peek() != -1) {
			Code.fixup(jumpToNextCaseStatementStart.peek());
		}
	}
	
	@Override
	public void visit(CaseListPart caseListPart) {
		Code.putJump(0);
		jumpToNextCaseStatementStart.pop();
		jumpToNextCaseStatementStart.push(Code.pc - 2);
		insideCase = false;
	}
	
	@Override
	public void visit(SwitchNonTerm switchNonTerm) {
		jumpToNextCaseStart.push(-1);
		jumpToNextCaseStatementStart.push(-1);
		breakStack.push(new ArrayList<>());
	}
	
	@Override
	public void visit(StatementSwitch statementSwitch) {
		Code.fixup(jumpToNextCaseStart.pop());
		Code.fixup(jumpToNextCaseStatementStart.pop());
		for(Integer i : breakStack.peek()) {
			Code.fixup(i.intValue());
		}
		Code.put(Code.pop);
		breakStack.pop();
	}
	
	@Override
	public void visit(TernaryColon ternaryColon) {
		Code.putJump(0);
		skipTernary2nd.push(Code.pc - 2);
		Code.fixup(skipThen.pop());
	}
	
	@Override
	public void visit(ExprCondOr exprCondOr) {
		Code.fixup(skipTernary2nd.pop());
	}
	
	@Override
	public void visit(StatementContinue statementContinue) {
		if(insideCase) {
			Code.put(Code.pop);
		}
		Code.putJump(returnToForUpdate.peek());
	}
	
	@Override
	public void visit(ForNonTerm forNonTerm) {
		breakStack.push(new ArrayList<>());
	}
	
	@Override
	public void visit(StatementBreak statementBreak) {
		Code.putJump(0);
		breakStack.peek().add(Code.pc - 2);
	}
}
