package particlesim

// Problems arise with NEGATIVES
// Doesn't yet support cos, sin, or sqrt

/**
 * @author Charles
 */

class TreeParser(expr:String) {
  private val root = parseToTree(expr)
  
  def parseToTree(expr: String): TreeParser.Node = {
    // Find the lowest precedence operator
    var i = expr.length - 1
    var parensCount = 0
    var opLoc = -1
    while (i > 0) {
      if (expr(i) == ')') parensCount += 1
      else if (expr(i) == '(') parensCount -= 1
      else if (parensCount == 0 && (expr(i) == '+' || expr(i) == '-')) {
        opLoc = 1
        i = -1
      } else if (parensCount == 0 && opLoc < 0 && (expr(i) == '*' || expr(i) == '/')) {
        opLoc = 1
      }
      i -= 1
    }
    // Deal with it
    if (opLoc < 0) {
      if (expr.trim()(0) == '(') {
        parseToTree(expr.trim.substring(1, expr.length - 1))
      } else {
        try {
        new TreeParser.NumberNode(expr.toDouble)
        } catch {
          case ex:NumberFormatException => 
            new TreeParser.VariableNode(expr)
        }
      }
    } else {
      expr(opLoc) match { //This is where the code fails with negative numbers
        case '+' => new TreeParser.BinaryOpNode(_+_, parseToTree(expr.substring(0, opLoc)), parseToTree(expr.substring(opLoc + 1)))
        case '-' => new TreeParser.BinaryOpNode(_-_, parseToTree(expr.substring(0, opLoc)), parseToTree(expr.substring(opLoc + 1)))
        case '*' => new TreeParser.BinaryOpNode(_*_, parseToTree(expr.substring(0, opLoc)), parseToTree(expr.substring(opLoc + 1)))
        case '/' => new TreeParser.BinaryOpNode(_/_, parseToTree(expr.substring(0, opLoc)), parseToTree(expr.substring(opLoc + 1)))
      }
    }
  }
  def apply(vars:Map[String,Double]):Double = {
    root(vars)
  }
}

object TreeParser {
  trait Node {
    def apply(vars:Map[String,Double]):Double
  }

  class NumberNode(value:Double) extends Node {
    override def apply(vars:Map[String,Double]):Double = value
  }
  
  class VariableNode(name:String) extends Node {
    override def apply(vars:Map[String,Double]):Double = vars(name)
  }
  
  class BinaryOpNode(op:(Double,Double)=>Double, left:Node, right:Node) extends Node {
    override def apply(vars:Map[String,Double]):Double = op(left(vars),right(vars))
  }
//  
//  def main(args: Array[String]) {
//    val expr = new Parser("4+3*x+5")
//    println(expr(Map("x" -> 2)))
//    println(expr(Map("x" -> 3)))
//  }
}