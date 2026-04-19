package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	public boolean errorDetected = false;
	public static final int Bool = 5;
	public static final int Enum = 6;
//	int nVars;

	Logger log = Logger.getLogger(getClass());
	
	private Obj progObj = null;
	private Struct currentType;
	private Integer currentValue;
	private Struct currentValueType;
	private Obj currentMethod = null;
	private Obj mainMethod = null;
	private Struct structEnum = null;
	private static Struct boolType;
	private int currentEnumValue = 0;
	private List<Integer> enumValueList = new ArrayList<>();
	private boolean returnHappened = false;
	private int forCounter = 0;
	private int forSwitchCounter = 0;
	private List<Struct> actParsList = new ArrayList<>();
	private Stack<List<Struct>> actParsListStack = new Stack<>();
	private Stack<List<Integer>> caseValuesStack = new Stack<>();
	int nVars;
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if(line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}
	
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if(line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public static void initialization() {
		boolType = new Struct(Bool);
		Obj boolObj = Tab.insert(Obj.Type, "bool", boolType);
		boolObj.setAdr(-1);
		boolObj.setLevel(-1);
		List<String> uniMeths = new ArrayList<>();
		uniMeths.add("chr");
		uniMeths.add("ord");
		uniMeths.add("len");
		for(String meth : uniMeths) {
			for(Obj fp :Tab.find(meth).getLocalSymbols()) {
				fp.setFpPos(1);
			}
		}
	}
	
	public static Struct getBoolType() {
		return boolType;
	}
	
//	public void visit(ProgName progName) {
//		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
//	}
	
//	public void visit(Program program) {
//		nVars = Tab.currentScope.getnVars();
//		Tab.chainLocalSymbols(program.getProgName().obj);
//		Tab.closeScope();
//	}
	
	@Override
	public void visit(ProgName progName) {
		progObj = Tab.insert(Obj.Prog, progName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(progObj);
		Tab.closeScope();
		if(mainMethod == null) {
			report_error("Error: main method not found!", null);
		} else if(mainMethod.getLevel() > 0) {
			report_error("Error: main method has formal parameters!", null);
		}
	}
	
	@Override
	public void visit(Type type) {
		Obj objType = Tab.find(type.getI1());
		if(objType == Tab.noObj || objType.getKind() != Obj.Type) {
			report_error("Error: " + type.getI1() + " does not represent type!", type);
			type.struct = currentType = Tab.noType;
		} else {
			type.struct = currentType = objType.getType();
		}
	}
	
	@Override
	public void visit(ConstDeclPart constDeclPart) {
		if(Tab.find(constDeclPart.getI1()) != Tab.noObj) {
			report_error("Error: name " + constDeclPart.getI1() + " already exists!", constDeclPart);
		} else {
			if(currentType != Tab.noType) {
				if(!currentValueType.assignableTo(currentType)) {
					report_error("Error: type of constant does not match the type of assigned value!", constDeclPart);
				} else {
					Obj objConst = Tab.insert(Obj.Con, constDeclPart.getI1(), currentType);
					objConst.setAdr(currentValue);
				}
			}
		}
	}
	
	@Override
	public void visit(ConstDeclNum constDeclNum) {
		currentValue = constDeclNum.getN1();
		currentValueType = Tab.intType;
	}
	
	@Override
	public void visit(ConstDeclChar constDeclChar) {
		currentValue = constDeclChar.getC1() + '0';
		currentValueType = Tab.charType;
	}
	
	@Override
	public void visit(ConstDeclBool constDeclBool) {
		currentValue = constDeclBool.getB1();
		currentValueType = boolType;
	}
	
	@Override
	public void visit(GlobalVarDeclPartNoSquare globalVar) {
		if(Tab.find(globalVar.getI1()) != Tab.noObj) {
			report_error("Error: name " + globalVar.getI1() + " already exists!", globalVar);
		} else {
			if(currentType != Tab.noType) {
				Tab.insert(Obj.Var, globalVar.getI1(), currentType);
			}
		}
	}
	
	@Override
	public void visit(GlobalVarDeclPartSquare globalVar) {
		if(Tab.find(globalVar.getI1()) != Tab.noObj) {
			report_error("Error: name " + globalVar.getI1() + " already exists!", globalVar);
		} else {
			if(currentType != Tab.noType) {
				Tab.insert(Obj.Var, globalVar.getI1(), new Struct(Struct.Array, currentType));
			}
		}
	}
	
	@Override
	public void visit(VarDeclPartNoSquare varDecl) {
		if(Tab.currentScope().findSymbol(varDecl.getI1()) != null) {
			report_error("Error: name " + varDecl.getI1() + " already exists within scope!", varDecl);
		} else {
			if(currentType != Tab.noType) {
				Tab.insert(Obj.Var, varDecl.getI1(), currentType);
			}
		}
	}
	
	@Override
	public void visit(VarDeclPartSquare varDecl) {
		if(Tab.currentScope().findSymbol(varDecl.getI1()) != null) {
			report_error("Error: name " + varDecl.getI1() + " already exists within scope!", varDecl);
		} else {
			if(currentType != Tab.noType) {
				Tab.insert(Obj.Var, varDecl.getI1(), new Struct(Struct.Array, currentType));
			}
		}
	}
	
	@Override
	public void visit(MethTypeName methTypeName) {
		if(Tab.find(methTypeName.getI2()) != Tab.noObj) {
			report_error("Error: name " + methTypeName.getI2() + " already exists with scope!", methTypeName);
		} else {
			if(methTypeName.getI2().equals("main")) {
				report_error("Error: cannot declare main as non-void function!", methTypeName);
			} else {
				methTypeName.obj = currentMethod  = Tab.insert(Obj.Meth, methTypeName.getI2(), currentType);
				Tab.openScope();
			}	
		}
	}
	
	@Override
	public void visit(MethVoidName methVoidName) {
		if(Tab.find(methVoidName.getI1()) != Tab.noObj) {
			report_error("Error: name " + methVoidName.getI1() + " already exists with scope!", methVoidName);
		} else {
			methVoidName.obj = currentMethod = Tab.insert(Obj.Meth, methVoidName.getI1(), Tab.noType);
			Tab.openScope();
			if(methVoidName.getI1().equals("main")) {
				mainMethod  = currentMethod;
			}
		}
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		if(currentMethod != null) {
			Tab.chainLocalSymbols(currentMethod);
			Tab.closeScope();
			if(!currentMethod.getType().equals(Tab.noType) && !returnHappened) {
				report_error("Error: non-void function " + currentMethod.getName() + " does not have return statement!", methodDecl);
			}
			returnHappened = false;
			currentMethod = null;
		}
	}
	
	@Override
	public void visit(FormParsPartNoSquare formPars) {
		if(Tab.currentScope().findSymbol(formPars.getI2()) != null) {
			report_error("Error: formal parameter " + formPars.getI2() + " already declared!", formPars);
		} else {
			if(currentMethod == null) {
				report_error("Error: formal parameter declared outside function!", formPars);
			} else {
				Obj objForm = Tab.insert(Obj.Var, formPars.getI2(), currentType);
				objForm.setFpPos(1);
				currentMethod.setLevel(currentMethod.getLevel() + 1);
			}
		}
	}
	
	@Override
	public void visit(FormParsPartSquare formPars) {
		if(Tab.currentScope().findSymbol(formPars.getI2()) != null) {
			report_error("Error: formal parameter " + formPars.getI2() + " already declared!", formPars);
		} else {
			if(currentMethod == null) {
				report_error("Error: formal parameter declared outside function!", formPars);
			} else {
				Obj objForm = Tab.insert(Obj.Var, formPars.getI2(), new Struct(Struct.Array, currentType));
				objForm.setFpPos(1);
				currentMethod.setLevel(currentMethod.getLevel() + 1);
			}
		}
	}
	
	@Override
	public void visit(EnumDeclName enumDeclName) {
		if(Tab.find(enumDeclName.getI1()) != Tab.noObj) {
			report_error("Error: name " + enumDeclName.getI1() + " already exists within scope!", enumDeclName);
		} else {
			structEnum = new Struct(Enum);
			Tab.insert(Obj.Type, enumDeclName.getI1(), structEnum);
			Tab.openScope();
		}
	}
	
	@Override
	public void visit(EnumDeclPartNoEqual enumDeclPart) {
		Obj objEnumConst = null;
		if(Tab.currentScope().findSymbol(enumDeclPart.getI1()) != null) {
			report_error("Error: name " + enumDeclPart.getI1() + " already exists within scope!", enumDeclPart);
		} else {
			if(structEnum == null) {
				report_error("Error: constant of enum " + enumDeclPart.getI1() + " declared outside enum definition!", enumDeclPart);
			} else {
				boolean uniqueValue = true;
				for(Integer i : enumValueList) {
					if(i.intValue() == currentEnumValue) {
						report_error("Error: value " + currentEnumValue + " within enum is not unique!", enumDeclPart);
						uniqueValue = false;
						break;
					}
				}
				if(uniqueValue) {
					enumValueList.add(currentEnumValue);
					objEnumConst = Tab.insert(Obj.Con, enumDeclPart.getI1(), structEnum);
					objEnumConst.setAdr(currentEnumValue);
					objEnumConst.setFpPos(1);
					currentEnumValue++;
				}
			}
		}
	}
	
	@Override
	public void visit(EnumDeclPartEqual enumDeclPart) {
		Obj objEnumConst = null;
		if(Tab.currentScope().findSymbol(enumDeclPart.getI1()) != null) {
			report_error("Error: name " + enumDeclPart.getI1() + " already exists within scope!", enumDeclPart);
		} else {
			if(structEnum == null) {
				report_error("Error: constant of enum " + enumDeclPart.getI1() + " declared outside enum definition!", enumDeclPart);
			} else {
				boolean uniqueValue = true;
				for(Integer i : enumValueList) {
					if(i.intValue() == enumDeclPart.getN2()) {
						report_error("Error: value " + enumDeclPart.getN2() + " within enum is not unique!", enumDeclPart);
						uniqueValue = false;
						break;
					}
				}
				if(uniqueValue) {
					enumValueList.add(enumDeclPart.getN2());
					objEnumConst = Tab.insert(Obj.Con, enumDeclPart.getI1(), structEnum);
					objEnumConst.setAdr(enumDeclPart.getN2());
					objEnumConst.setFpPos(1);
					currentEnumValue = enumDeclPart.getN2() + 1;
				}
			}
		}
	}
	
	@Override
	public void visit(EnumDecl enumDecl) {
		if(structEnum == null) {
			report_error("Error: failed to open scope for given enum!", enumDecl);
		} else {
			Tab.chainLocalSymbols(structEnum);
			Tab.closeScope();
			structEnum = null;
			currentEnumValue = 0;
			enumValueList.clear();
		}
	}
	//untested + enum should be added TO-DO
	@Override
	public void visit(FactorNumber factorNumber) {
		factorNumber.struct = Tab.intType;
	}
	
	@Override
	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;
	}
	
	@Override
	public void visit(FactorBool factorBool) {
		factorBool.struct = boolType;
	}
	
	@Override
	public void visit(FactorVariableReference factorVariableReference) {
		factorVariableReference.struct = factorVariableReference.getDesignator().obj.getType();
	}
	
	@Override
	public void visit(FactorListPart factorListPart) {
		factorListPart.struct = factorListPart.getFactor().struct;
	}
	
	@Override
	public void visit(FactorListt factorList) {
		Struct structLeft = factorList.getFactorList().struct;
		Struct structRight = factorList.getFactor().struct;
		if(!(structLeft.equals(Tab.intType) || structLeft.getKind() == Enum) || 
				!(structRight.equals(Tab.intType) || structRight.getKind() == Enum)) {
			report_error("Error: multiplication of non-integer values!", factorList);
			factorList.struct = Tab.noType;
		} else {
			factorList.struct = Tab.intType;
		}
	}
	
	@Override
	public void visit(Term term) {
		term.struct = term.getFactorList().struct;
	}
	
	@Override
	public void visit(TermListPartMinus termListPartMinus) {
		if(!termListPartMinus.getTerm().struct.equals(Tab.intType) &&
				termListPartMinus.getTerm().struct.getKind() != Enum) {
			report_error("Error: negation of non-int value", termListPartMinus);
			termListPartMinus.struct = Tab.noType;
		} else {
			termListPartMinus.struct = Tab.intType;
		}
	}
	
	@Override
	public void visit(TermListPartNoMinus termListPartNoMinus) {
		termListPartNoMinus.struct = termListPartNoMinus.getTerm().struct;
	}
	
	@Override
	public void visit(TermListt termList) {
		Struct structLeft = termList.getTermList().struct;
		Struct structRight = termList.getTerm().struct;
		if(!(structLeft.equals(Tab.intType) || structLeft.getKind() == Enum) ||
				!(structRight.equals(Tab.intType) || structRight.getKind() == Enum)) {
			report_error("Error: addition of non-integer values!", termList);
			termList.struct = Tab.noType;
		} else {
			termList.struct = Tab.intType;
		}
	}
	
	@Override
	public void visit(ExprNoCond exprNoCond) {
		exprNoCond.struct = exprNoCond.getTermList().struct;
	}
	
	@Override
	public void visit(ExprNoConditional exprNoConditional) {
		exprNoConditional.struct = exprNoConditional.getExprNoCond().struct;
	}
	
	@Override
	public void visit(DesignatorVar designatorVar) {
		Obj objDsgn = Tab.find(designatorVar.getI1());
		if(objDsgn == Tab.noObj) {
			report_error("Error: undefined symbol: " + designatorVar.getI1() + " used!", designatorVar);
			designatorVar.obj = Tab.noObj;
		} else if(objDsgn.getKind() != Obj.Var && objDsgn.getKind() != Obj.Con && objDsgn.getKind() != Obj.Meth) {
			report_error("Error: improper usage of symbol: " + designatorVar.getI1() + "!", designatorVar);
			designatorVar.obj = Tab.noObj;
		} else {
			designatorVar.obj = objDsgn;
			if(objDsgn.getKind() == Obj.Con) {
				report_info("Symbolic constant " + objDsgn.getName() + " used!"
						+ "[" + "Kind: " + objDsgn.getKind() + ", Name: " + objDsgn.getName() +
						", Level: " + objDsgn.getLevel() + ", Type:" + objDsgn.getType().getKind() +"]", designatorVar);
			} else if(objDsgn.getKind() == Obj.Var) {
				if(objDsgn.getLevel() == 0) {
					report_info("Global variable " + objDsgn.getName() + " used!"
							+ "[" + "Kind: " + objDsgn.getKind() + ", Name: " + objDsgn.getName() +
							", Level: " + objDsgn.getLevel() + ", Type:" + objDsgn.getType().getKind() +"]", designatorVar);
				} else {
					if(objDsgn.getFpPos() == 0) {
						report_info("Local variable " + objDsgn.getName() + " used!"
								+ "[" + "Kind: " + objDsgn.getKind() + ", Name: " + objDsgn.getName() +
								", Level: " + objDsgn.getLevel() + ", Type:" + objDsgn.getType().getKind() +"]", designatorVar);
					} else {
						report_info("Formal argument " + objDsgn.getName() + " of function " + currentMethod.getName() + " used!"
								+ "[" + "Kind: " + objDsgn.getKind() + ", Name: " + objDsgn.getName() +
								", Level: " + objDsgn.getLevel() + ", Type:" + objDsgn.getType().getKind() +"]", designatorVar);
					}
				}
			}
		}
	}
	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Obj objArray = Tab.find(designatorArrayName.getI1());
		if(objArray == Tab.noObj) {
			report_error("Error: undefined symbol: " + designatorArrayName.getI1() + " used as array!", designatorArrayName);
			designatorArrayName.obj = Tab.noObj;
		} else if(objArray.getKind() != Obj.Var || objArray.getType().getKind() != Struct.Array) {
			report_error("Error: improper usage of symbol: " + designatorArrayName.getI1() + " -not an array!", designatorArrayName);
			designatorArrayName.obj = Tab.noObj;
		} else {
			designatorArrayName.obj = objArray;
		}
	}
	
	@Override
	public void visit(DesignatorArray designatorArray) {
		Obj objArray = designatorArray.getDesignatorArrayName().obj;
		if(objArray == Tab.noObj) {
			designatorArray.obj = Tab.noObj;
		} else if(!designatorArray.getExpr().struct.equals(Tab.intType) && 
				designatorArray.getExpr().struct.getKind() != Enum) {
			report_error("Error: expression for array indexing not integer!", designatorArray);
			designatorArray.obj = Tab.noObj;
		} else {
			designatorArray.obj = new Obj(Obj.Elem, objArray.getName() + "[elem]", objArray.getType().getElemType());
			report_info("Accessing element of an array " + objArray.getName() + "!"
					+ "[" + "Kind: " + designatorArray.obj.getKind() + ", Name: " + designatorArray.obj.getName() 
					+ ", Level: " + objArray.getLevel() + ", Type: " + designatorArray.obj.getType().getKind() + "]", designatorArray);
		}
	}
	
	@Override
	public void visit(FactorNewExpr factorNewExpr) {
		if(!factorNewExpr.getExpr().struct.equals(Tab.intType) &&
				factorNewExpr.getExpr().struct.getKind() != Enum) {
			report_error("Error: expression for array size not integer!", factorNewExpr);
			factorNewExpr.struct = Tab.noType;
		} else {
			factorNewExpr.struct = new Struct(Struct.Array, currentType);
		}
	}
	
	@Override
	public void visit(FactorParenthesis factorParenthesis) {
		factorParenthesis.struct = factorParenthesis.getExpr().struct;
	}
	
	@Override
	public void visit(FactorFunctionCallNoActPars factorFunctionCallNoActPars) {
		Obj objFactor = factorFunctionCallNoActPars.getDesignator().obj;
		if(objFactor == Tab.noObj) {
			factorFunctionCallNoActPars.struct = Tab.noType;
		} else {	
			if(objFactor.getKind() != Obj.Meth || !(factorFunctionCallNoActPars.getDesignator() instanceof DesignatorVar)) {
				report_error("Error: symbol " + objFactor.getName() + " does not represent function!", factorFunctionCallNoActPars);
				factorFunctionCallNoActPars.struct = Tab.noType;
			} else {
				factorFunctionCallNoActPars.struct = objFactor.getType();
				List<Struct> formParsList = new ArrayList<>();
				for(Obj local : factorFunctionCallNoActPars.getDesignator().obj.getLocalSymbols()) {
					if(local.getKind() == Obj.Var && local.getFpPos() == 1) {
						formParsList.add(local.getType());
					}
				}
				if(formParsList.size() > 0) {
					report_error("Error: no parameters found for function " + objFactor.getName(), factorFunctionCallNoActPars);
				}
//				if(formParsList.size() != actParsList.size()) {
//					report_error("Error: improper number of arguments for function " + objFactor.getName() + "!", factorFunctionCallNoActPars);
//				} else {
//					for(int i = 0; i < formParsList.size(); i++) {
//						Struct fp = formParsList.get(i);
//						Struct ap = actParsList.get(i);
//						if(!ap.assignableTo(fp)) {
//							report_error("Error: improper type for parameter " + i + " of function " + objFactor.getName() + "!", factorFunctionCallNoActPars);
//						}
//					}
//				}
				if(!errorDetected) {
					report_info("Global function " + objFactor.getName() + " called!"  +
							"[" + "Kind: " + objFactor.getKind() + ", Name: " + objFactor.getName() +
							", Level: " + objFactor.getLevel() + ", Type: " + objFactor.getType().getKind() + "]", factorFunctionCallNoActPars);
				}
			}
		}
	}
	
	@Override
	public void visit(FactorFunctionCallActPars factorFunctionCallActPars) {
		Obj objFactor = factorFunctionCallActPars.getDesignator().obj;
		if(objFactor == Tab.noObj) {
			factorFunctionCallActPars.struct = Tab.noType;
		} else {	
			if(objFactor.getKind() != Obj.Meth || !(factorFunctionCallActPars.getDesignator() instanceof DesignatorVar)) {
				report_error("Error: symbol " + objFactor.getName() + " does not represent function!", factorFunctionCallActPars);
				factorFunctionCallActPars.struct = Tab.noType;
			} else {
				factorFunctionCallActPars.struct = objFactor.getType();
				List<Struct> formParsList = new ArrayList<>();
				for(Obj local : factorFunctionCallActPars.getDesignator().obj.getLocalSymbols()) {
					if(local.getKind() == Obj.Var && local.getFpPos() == 1) {
						formParsList.add(local.getType());
					}
				}
				
				if(formParsList.size() != actParsList.size()) {
					report_error("Error: improper number of arguments for function " + objFactor.getName() + "!", factorFunctionCallActPars);
				} else {
					for(int i = 0; i < formParsList.size(); i++) {
						Struct fp = formParsList.get(i);
						Struct ap = actParsList.get(i);
						if(!ap.assignableTo(fp) && !(fp.equals(Tab.intType) && ap.getKind() == Enum)) {
							report_error("Error: improper type for parameter " + i + " of function " + objFactor.getName() + "!", factorFunctionCallActPars);
						}
					}
				}
				if(!errorDetected) {
					report_info("Global function " + objFactor.getName() + " called!"  +
							"[" + "Kind: " + objFactor.getKind() + ", Name: " + objFactor.getName() +
							", Level: " + objFactor.getLevel() + ", Type: " + objFactor.getType().getKind() + "]", factorFunctionCallActPars);
				}
			}
		}
	}
	
	@Override
	public void visit(DesignatorEnum designatorEnum) {
		Obj objEnum = Tab.find(designatorEnum.getI1());
		if(objEnum == Tab.noObj) {
			report_error("Error: symbol " + designatorEnum.getI1() + " used as enum is not defined!", designatorEnum);
			designatorEnum.obj = Tab.noObj;
		} else if(objEnum.getKind() != Obj.Type || objEnum.getType().getKind() != Enum) {
			report_error("Error: symbol " + designatorEnum.getI1() + " does not represent enum!", designatorEnum);
			designatorEnum.obj = Tab.noObj;
		} else {
			Struct structEnum = objEnum.getType();
			Obj objConst = structEnum.getMembers().searchKey(designatorEnum.getI2());
			if(objConst == null) {
				report_error("Error: accessing undefined constant " + designatorEnum.getI2() + " in enum " + designatorEnum.getI1() + "!", designatorEnum);
				designatorEnum.obj = Tab.noObj;
			} else if(objConst.getKind() != Obj.Con || objConst.getType().getKind() != Enum) {
				report_error("Error: symbol " + designatorEnum.getI2() + " does not represent enum constant!", designatorEnum);
				designatorEnum.obj = Tab.noObj;
			} else {
				designatorEnum.obj = objConst;
			}
		}
	}
	
	@Override
	public void visit(DesignatorLength designatorLength) {
		Obj objArray = Tab.find(designatorLength.getI1());
		if(objArray == Tab.noObj) {
			report_error("Error: undefined symbol: " + designatorLength.getI1() + " used as array!", designatorLength);
			designatorLength.obj = Tab.noObj;
		} else if(objArray.getKind() != Obj.Var || objArray.getType().getKind() != Struct.Array) {
			report_error("Error: improper usage of symbol: " + designatorLength.getI1() + " -not an array!", designatorLength);
			designatorLength.obj = Tab.noObj;
		} else {
//			designatorLength.obj = objArray;
			designatorLength.obj = new Obj(Obj.Con, designatorLength.getI1() + ".length", Tab.intType); //?
			designatorLength.getLengthDummy().obj = objArray;
		}
	}
	//untested + enum should be added TO-DO
	
	@Override
	public void visit(AssignNoError assignNoError) {
		assignNoError.struct = assignNoError.getExpr().struct;
	}
	//DODATI SMENE ZA UPDATE TO-DO
	@Override
	public void visit(DesignatorStatementAssign designatorStatementAssign) {
		Obj objDest = designatorStatementAssign.getDesignator().obj;
		if(objDest.getKind() != Obj.Var && objDest.getKind() != Obj.Elem) {
			report_error("Error: cannot assign to " + objDest.getName(), designatorStatementAssign);
		} else if(designatorStatementAssign.getAssignmentError() instanceof AssignNoError) {
			if(!designatorStatementAssign.getAssignmentError().struct.assignableTo(objDest.getType()) &&
					!(objDest.getType().equals(Tab.intType) && designatorStatementAssign.getAssignmentError().struct.getKind() == Enum)) {
				report_error("Error: improper assignment to " + objDest.getName(), designatorStatementAssign);
			}
		}
	}
	
	@Override
	public void visit(DesignatorStatementInc designatorStatementInc) {
		Obj objDest = designatorStatementInc.getDesignator().obj;
		if(objDest.getKind() != Obj.Var && objDest.getKind() != Obj.Elem) {
			report_error("Error: cannot increment " + designatorStatementInc.getDesignator().obj.getName(), designatorStatementInc);
		} else if(!objDest.getType().equals(Tab.intType)) { //npr ovde ne treba ENUM type ? TO-DO
			report_error("Error: cannot increment non-integer " + objDest.getName(), designatorStatementInc);
		}
	}
	
	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		Obj objDest = designatorStatementDec.getDesignator().obj;
		if(objDest.getKind() != Obj.Var && objDest.getKind() != Obj.Elem) {
			report_error("Error: cannot decrement " + designatorStatementDec.getDesignator().obj.getName(), designatorStatementDec);
		} else if(!objDest.getType().equals(Tab.intType)) { //npr ovde ne treba ENUM type ? TO-DO
			report_error("Error: cannot decrement non-integer " + objDest.getName(), designatorStatementDec);
		}
	}
	
	@Override
	public void visit(StatementRead statementRead) {
		Obj objRead = statementRead.getDesignator().obj;
		if(objRead.getKind() != Obj.Var && objRead.getKind() != Obj.Elem) {
			report_error("Error: argument for read function must variable or element of array!", statementRead);
		} else if(!objRead.getType().equals(Tab.intType) && !objRead.getType().equals(Tab.charType) && !objRead.getType().equals(boolType) &&
				objRead.getType().getKind() != Enum) {
			report_error("Error: argument for read function must be integer, char or bool!", statementRead);
		}
	}
	
	@Override
	public void visit(StatementPrint statementPrint) {
		Struct structPrint = statementPrint.getExpr().struct;
		if(!structPrint.equals(Tab.intType) && !structPrint.equals(Tab.charType) && !structPrint.equals(boolType) &&
				structPrint.getKind() != Enum) {
			report_error("Error: first argument for print function must be integer, char or bool!", statementPrint);
		}
	}
	
	@Override
	public void visit(DesignatorStatementAssignUpdate designatorStatementAssignUpdate) {
		Obj objDest = designatorStatementAssignUpdate.getDesignator().obj;
		if(objDest.getKind() != Obj.Var && objDest.getKind() != Obj.Elem) {
			report_error("Error: cannot assign to " + objDest.getName(), designatorStatementAssignUpdate);
		} else if(designatorStatementAssignUpdate.getExpr().struct.assignableTo(objDest.getType()) &&
				!(objDest.getType().equals(Tab.intType) && designatorStatementAssignUpdate.getExpr().struct.getKind() == Enum)) {
			report_error("Error: improper assignment to " + objDest.getName(), designatorStatementAssignUpdate);
		}
	}
	
	@Override
	public void visit(DesignatorStatementIncUpdate designatorStatementIncUpdate) {
		Obj objDest = designatorStatementIncUpdate.getDesignator().obj;
		if(objDest.getKind() != Obj.Var && objDest.getKind() != Obj.Elem) {
			report_error("Error: cannot increment " + designatorStatementIncUpdate.getDesignator().obj.getName(), designatorStatementIncUpdate);
		} else if(!objDest.getType().equals(Tab.intType)) { //npr ovde ne treba ENUM type ? TO-DO
			report_error("Error: cannot increment non-integer " + objDest.getName(), designatorStatementIncUpdate);
		}
	}
	
	@Override
	public void visit(DesignatorStatementDecUpdate designatorStatementDecUpdate) {
		Obj objDest = designatorStatementDecUpdate.getDesignator().obj;
		if(objDest.getKind() != Obj.Var && objDest.getKind() != Obj.Elem) {
			report_error("Error: cannot decrement " + designatorStatementDecUpdate.getDesignator().obj.getName(), designatorStatementDecUpdate);
		} else if(!objDest.getType().equals(Tab.intType)) { //npr ovde ne treba ENUM type ? TO-DO
			report_error("Error: cannot decrement non-integer " + objDest.getName(), designatorStatementDecUpdate);
		}
	}
	
	@Override
	public void visit(StatementReturnNoExpr statementReturnNoExpr) {
		if(currentMethod == null) {
			report_error("Error: return statement found outside function!", statementReturnNoExpr);
		} else if(!currentMethod.getType().equals(Tab.noType)) {
			report_error("Error: return statement doesn't have argument!", statementReturnNoExpr);
		} else {
			returnHappened = true;
		}
	}
	
	@Override
	public void visit(StatementReturnExpr statementReturnExpr) {
		if(currentMethod == null) {
			report_error("Error: return statement found outside function!", statementReturnExpr);
		} else if(!statementReturnExpr.getExpr().struct.equals(currentMethod.getType()) &&
				!(currentMethod.getType().equals(Tab.intType) && statementReturnExpr.getExpr().struct.getKind() == Enum)) {
			report_error("Error: return statement's argument is not proper type!", statementReturnExpr);
		} else {
			returnHappened = true;
		}
	}
	
	@Override
	public void visit(DesignatorStatementNoActPars designatorStatementNoActPars) {
		Obj objFunc = designatorStatementNoActPars.getDesignator().obj;
		if(objFunc.getKind() != Obj.Meth) {
			report_error("Error: " + objFunc.getName() + " is not function!", designatorStatementNoActPars);
		} else {
			List<Struct> formParsList = new ArrayList<>();
			for(Obj local : designatorStatementNoActPars.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getFpPos() == 1) {
					formParsList.add(local.getType());
				}
			}
			if(formParsList.size() > 0) {
				report_error("Error: no parameters found for function " + objFunc.getName(), designatorStatementNoActPars);
			}
//			if(formParsList.size() != actParsList.size()) {
//				report_error("Error: improper number of arguments for function " + objFunc.getName() + "!", designatorStatementNoActPars);
//			} else {
//				for(int i = 0; i < formParsList.size(); i++) {
//					Struct fp = formParsList.get(i);
//					Struct ap = actParsList.get(i);
//					if(!ap.assignableTo(fp)) {
//						report_error("Error: improper type for parameter " + i + " of function " + objFunc.getName() + "!", designatorStatementNoActPars);
//					}
//				}
//			}
			if(!errorDetected) {
				report_info("Global function " + objFunc.getName() + " called!" +
						"[" + "Kind: " + objFunc.getKind() + ", Name: " + objFunc.getName() +
						", Level: " + objFunc.getLevel() + ", Type: " + objFunc.getType().getKind() + "]", designatorStatementNoActPars);
			}
		}
	}
	
	@Override
	public void visit(DesignatorStatementActPars designatorStatementActPars) {
		Obj objFunc = designatorStatementActPars.getDesignator().obj;
		if(objFunc.getKind() != Obj.Meth) {
			report_error("Error: " + objFunc.getName() + " is not function!", designatorStatementActPars);
		} else {
			List<Struct> formParsList = new ArrayList<>();
			for(Obj local : designatorStatementActPars.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getFpPos() == 1) {
					formParsList.add(local.getType());
				}
			}
			
			if(formParsList.size() != actParsList.size()) {
				report_error("Error: improper number of arguments for function " + objFunc.getName() + "!", designatorStatementActPars);
			} else {
				for(int i = 0; i < formParsList.size(); i++) {
					Struct fp = formParsList.get(i);
					Struct ap = actParsList.get(i);
					if(!ap.assignableTo(fp) && !(fp.equals(Tab.intType) && ap.getKind() == Enum)) {
						report_error("Error: improper type for parameter " + i + " of function " + objFunc.getName() + "!", designatorStatementActPars);
					}
				}
			}
			if(!errorDetected) {
				report_info("Global function " + objFunc.getName() + " called!" +
						"[" + "Kind: " + objFunc.getKind() + ", Name: " + objFunc.getName() +
						", Level: " + objFunc.getLevel() + ", Type: " + objFunc.getType().getKind() + "]", designatorStatementActPars);
			}
		}
	}
	
	@Override
	public void visit(DesignatorStatementNoActParsUpdate designatorStatementNoActParsUpdate) {
		Obj objFunc = designatorStatementNoActParsUpdate.getDesignator().obj;
		if(objFunc.getKind() != Obj.Meth) {
			report_error("Error: " + objFunc.getName() + " is not function!", designatorStatementNoActParsUpdate);
		} else {
			List<Struct> formParsList = new ArrayList<>();
			for(Obj local : designatorStatementNoActParsUpdate.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getFpPos() == 1) {
					formParsList.add(local.getType());
				}
			}
			if(formParsList.size() > 0) {
				report_error("Error: no parameters found for function " + objFunc.getName(), designatorStatementNoActParsUpdate);
			}
//			if(formParsList.size() != actParsList.size()) {
//				report_error("Error: improper number of arguments for function " + objFunc.getName() + "!", designatorStatementNoActParsUpdate);
//			} else {
//				for(int i = 0; i < formParsList.size(); i++) {
//					Struct fp = formParsList.get(i);
//					Struct ap = actParsList.get(i);
//					if(!ap.assignableTo(fp)) {
//						report_error("Error: improper type for parameter " + i + " of function " + objFunc.getName() + "!", designatorStatementNoActParsUpdate);
//					}
//				}
//			}
			if(!errorDetected) {
				report_info("Global function " + objFunc.getName() + " called!" +
						"[" + "Kind: " + objFunc.getKind() + ", Name: " + objFunc.getName() +
						", Level: " + objFunc.getLevel() + ", Type: " + objFunc.getType().getKind() + "]", designatorStatementNoActParsUpdate);
			}
		}
	}
	
	@Override
	public void visit(DesignatorStatementActParsUpdate designatorStatementActParsUpdate) {
		Obj objFunc = designatorStatementActParsUpdate.getDesignator().obj;
		if(objFunc.getKind() != Obj.Meth) {
			report_error("Error: " + objFunc.getName() + " is not function!", designatorStatementActParsUpdate);
		}  else {
			List<Struct> formParsList = new ArrayList<>();
			for(Obj local : designatorStatementActParsUpdate.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getFpPos() == 1) {
					formParsList.add(local.getType());
				}
			}
			
			if(formParsList.size() != actParsList.size()) {
				report_error("Error: improper number of arguments for function " + objFunc.getName() + "!", designatorStatementActParsUpdate);
			} else {
				for(int i = 0; i < formParsList.size(); i++) {
					Struct fp = formParsList.get(i);
					Struct ap = actParsList.get(i);
					if(!ap.assignableTo(fp) && !(fp.equals(Tab.intType) && ap.getKind() == Enum)) {
						report_error("Error: improper type for parameter " + i + " of function " + objFunc.getName() + "!", designatorStatementActParsUpdate);
					}
				}
			}
			if(!errorDetected) {
				report_info("Global function " + objFunc.getName() + " called!" +
						"[" + "Kind: " + objFunc.getKind() + ", Name: " + objFunc.getName() +
						", Level: " + objFunc.getLevel() + ", Type: " + objFunc.getType().getKind() + "]", designatorStatementActParsUpdate);
			}
		}
	}
	
	@Override
	public void visit(ForNonTerm forNonTerm) {
		forCounter++;
		forSwitchCounter++;
	}
	
	@Override
	public void visit(SwitchNonTerm switchNonTerm) {
		forSwitchCounter++;
		caseValuesStack.push(new ArrayList<>());
	}
	
	@Override
	public void visit(CaseListPart caseListPart) {
		caseValuesStack.peek().add(caseListPart.getNumberDummy().getN1());
	}
	
	@Override
	public void visit(StatementSwitch statementSwitch) {
		forSwitchCounter--;
		if(!statementSwitch.getExpr().struct.equals(Tab.intType)) {
			report_error("Error: expression in switch statement must be integer!", statementSwitch);
		}
		List<Integer> caseValuesList = caseValuesStack.pop();
		for(int i = 0; i < caseValuesList.size(); i++) {
			for(int j = i + 1; j < caseValuesList.size(); j++) {
				if(caseValuesList.get(i).intValue() == caseValuesList.get(j).intValue()) {
					report_error("Error: values for cases must be unique within switch statement!", statementSwitch);
				}
			}
		}
	}
	
	/*@Override
	public void visit(StatementForNoInitNoCondNoUpdt statementForNoInitNoCondNoUpdt) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementForInitNoCondNoUpdt statementForInitNoCondNoUpdt) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementForNoInitCondNoUpdt statementForNoInitCondNoUpdt) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementForNoInitNoCondUpdt statementForNoInitNoCondUpdt) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementForInitCondNoUpdt statementForInitCondNoUpdt) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementForInitNoCondUpdt statementForInitNoCondUpdt) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementForNoInitCondUpdt statementForNoInitCondUpdt) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementForInitCondUpdt statementForInitCondUpdt) {
		forCounter--;
		forSwitchCounter--;
	}*/
	
	@Override
	public void visit(StatementFor statementFor) {
		forCounter--;
		forSwitchCounter--;
	}
	
	@Override
	public void visit(StatementBreak statementBreak) {
		if(forSwitchCounter == 0) {
			report_error("Error: break statement called outside for loop or switch statement!", statementBreak);
		}
	}
	
	@Override
	public void visit(StatementContinue statementContinue) {
		if(forCounter == 0) {
			report_error("Error: continue statement called outside for loop!", statementContinue);
		}
	}
	
	@Override
	public void visit(CondFactExpr condFactExpr) {
		if(!condFactExpr.getExprNoCond().struct.equals(boolType)) {
			report_error("Error: expression is not boolean!", condFactExpr);
			condFactExpr.struct = Tab.noType;
		} else {
			condFactExpr.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondFactt condFactt) {
		Struct structLeft = condFactt.getExprNoCond().struct;
		Struct structRight = condFactt.getExprNoCond1().struct;
		if(!structLeft.compatibleWith(structRight) && 
				!((structLeft.getKind() == Enum && structRight.getKind() == Enum) ||
						(structLeft.equals(Tab.intType) && structRight.getKind() == Enum) ||
						(structLeft.getKind() == Enum && structRight.equals(Tab.intType))
						)) {
			report_error("Error: operands are not compatible!", condFactt);
			condFactt.struct = Tab.noType;
		} else {
			if(structLeft.isRefType() || structRight.isRefType()) {
				if(condFactt.getRelop() instanceof RelopEqEq || condFactt.getRelop() instanceof RelopNotEq) {
					condFactt.struct = boolType;
				} else {
					report_error("Error: improper comparison of ref types!", condFactt);
					condFactt.struct = Tab.noType;
				}
			} else {
				condFactt.struct = boolType;
			}
		}
	}
	
	@Override
	public void visit(CondTermCondFact condTermCondFact) {
		condTermCondFact.struct = condTermCondFact.getCondFact().struct;
	}
	
	@Override
	public void visit(CondTermm condTermm) {
		Struct structLeft = condTermm.getCondTerm().struct;
		Struct structRight = condTermm.getCondFact().struct;
		if(!structLeft.equals(boolType) || !structRight.equals(boolType)) {
			report_error("Error: expression is not boolean for AND!", condTermm);
			condTermm.struct = Tab.noType;
		} else {
			condTermm.struct = boolType;
		}
	}
	
	@Override
	public void visit(ConditionCondTerm conditionCondTerm) {
		conditionCondTerm.struct = conditionCondTerm.getCondTerm().struct;
		if(!conditionCondTerm.struct.equals(boolType)) {
			report_error("Error: condition is not boolean!", conditionCondTerm);
		}
	}
	
	@Override
	public void visit(Conditionn conditionn) {
		Struct structLeft = conditionn.getCondition().struct;
		Struct structRight = conditionn.getCondTerm().struct;
		if(!structLeft.equals(boolType) || !structRight.equals(boolType)) {
			report_error("Error: expression is not boolean for OR!", conditionn);
			conditionn.struct = Tab.noType;
		} else {
			conditionn.struct = boolType;
		}
		if(!conditionn.struct.equals(boolType)) {
			report_error("Error: condition is not boolean!", conditionn);
		}
	}

	@Override
	public void visit(ActParDummyBegin actParDummyBegin) {
		actParsListStack.push(new ArrayList<>());
	}
	
	@Override
	public void visit(ActPar actPar) {
		actParsListStack.peek().add(actPar.getExpr().struct);
	}
	
	@Override
	public void visit(ActPars actPars) {
		actParsList = actParsListStack.pop();
	}
	
	@Override
	public void visit(ExprCondOr exprCondOr) {
		Struct structLeft = exprCondOr.getExpr().struct;
		Struct structRight = exprCondOr.getExpr1().struct;
		if(!structLeft.equals(structRight) &&
				!((structLeft.equals(Tab.intType) || structLeft.getKind() == Enum) && 
						(structRight.equals(Tab.intType) || structRight.getKind() == Enum))) {
			report_error("Error: expressions must have same type in ternary operator!", exprCondOr);
			exprCondOr.struct = Tab.noType;
		} else {
			exprCondOr.struct = structLeft;
		}
	}
	
	@Override
	public void visit(ExprCond exprCond) {
		exprCond.struct = exprCond.getConditionalExpr().struct;
	}
}
