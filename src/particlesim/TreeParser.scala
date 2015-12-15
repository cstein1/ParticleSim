package particlesim

/**
 * @author mLewis
 */

class TreeParser(expr: String) {
  private val root = TreeParser.parse(expr)
  
  def eval(vars:Map[String,Double]):Double = root eval vars
  
  override def toString = expr
}

object TreeParser {
  val ops = "+-*/".toSet
  def eval(form:String, vars:Map[String,Double]=null):Double = {
    val root = parse(form.filter(_!=' '))
    root.eval(vars)
  } 
  
  private def parse(f:String):Node = {
    var opLoc= -1
    var parensCount = 0
    var i = f.length-1
    while(i>0) {
      if(f(i)=='(') parensCount += 1
      else if(f(i)==')') parensCount -= 1
      else if(parensCount==0 && (f(i)=='+' || f(i)=='-' && !ops.contains(f(i-1)))) {
        opLoc = i
        i = -1
      } else if(parensCount==0 && opLoc == -1 && (f(i)=='*' || f(i) == '/')) {
        opLoc = i
      }
      i-=1
    }
    if(opLoc<0) {
      if(f(0)=='(') {
        parse(f.substring(1,f.length-1))
      }else if(f.startsWith("sin(")) {
        new SingleOpNode(parse(f.substring(4,f.length-1)), math.sin)
      }else if(f.startsWith("cos(")) {
        new SingleOpNode(parse(f.substring(4,f.length-1)), math.cos)
      }else if(f.startsWith("tan(")) {
        new SingleOpNode(parse(f.substring(4,f.length-1)), math.tan)
      }else if(f.startsWith("sqrt(")) {
        new SingleOpNode(parse(f.substring(5,f.length-1)), math.sqrt)
      }else if(f.startsWith("-")) {
        new NegNode(f.substring(1))
      }else try {
        new NumberNode(f.toDouble)
      } catch {
        case ex:NumberFormatException => new VariableNode(f)
      }
    } else {
      f(opLoc) match {
        case '+' => new BinaryOpNode(parse(f.take(opLoc)), parse(f.drop(opLoc+1)),_+_)
        case '-' => new BinaryOpNode(parse(f.take(opLoc)), parse(f.drop(opLoc+1)),_-_)
        case '*' => new BinaryOpNode(parse(f.take(opLoc)), parse(f.drop(opLoc+1)),_*_)
        case '/' => new BinaryOpNode(parse(f.take(opLoc)), parse(f.drop(opLoc+1)),_/_)
      }
    }
  }
  
  trait Node extends Serializable {
    def eval(vars: Map[String, Double]): Double
  }

  class NumberNode(value: Double) extends Node {
    override def eval(vars: Map[String, Double]): Double = value
  }

  class VariableNode(name: String) extends Node {
    override def eval(vars: Map[String, Double]): Double = vars(name)
  }

  class BinaryOpNode(left: Node, right: Node, op: (Double, Double) => Double) extends Node {
    override def eval(vars: Map[String, Double]): Double = op(left eval vars, right eval vars)
  }

  class SingleOpNode(arg: Node,op: (Double) => Double) extends Node {
    def eval(vars: Map[String, Double]): Double = op(arg eval vars)
  }

  class NegNode(inp: String) extends Node {
    def eval(vars: Map[String, Double]): Double = -1 * (TreeParser.parse(inp).eval(vars))
  }
}