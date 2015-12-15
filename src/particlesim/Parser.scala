package particlesim

object Parser {
  def eval(expr: String, vars: Map[String, Double]): Double = {
    // Find the lowest precedence operator
    var i = expr.length - 1
    var parensCount = 0
    var opLoc = -1
    while (i > 0) {
      if (expr(i) == ')') parensCount += 1
      else if (expr(i) == '(') parensCount -= 1
      else if (parensCount == 0 && (expr(i) == '+' || expr(i) == '-')) {
        opLoc = i
        i = -1
      } else if (parensCount == 0 && opLoc < 0 && (expr(i) == '*' || expr(i) == '/')) {
        opLoc = i
      }
      i -= 1
    }
    // Deal with it
    if (opLoc < 0) {
      if (expr.trim()(0) == '(') {
        eval(expr.trim.substring(1, expr.length - 1), vars)
      } else if (vars.contains(expr)) {
        vars(expr)
      } else if (expr.trim.startsWith("sin")) {
        math.sin(eval(expr.trim.substring(4, expr.length - 1), vars))
      } else if (expr.trim.startsWith("cos")) {
        math.cos(eval(expr.trim.substring(4, expr.length - 1), vars))
      } else if (expr.trim.startsWith("sqrt")) {
        math.sqrt(eval(expr.trim.substring(5, expr.length - 1), vars))
      } else if (expr.trim.startsWith("-")){
    	  println(eval(expr.trim.substring(1),vars))
        -1*eval(expr.trim.substring(1),vars)
      } else expr.toDouble
    } else {
      expr(opLoc) match { 
        case '+' => eval(expr.substring(0, opLoc), vars) + eval(expr.substring(opLoc + 1), vars)
        case '-' => eval(expr.substring(0, opLoc), vars) - eval(expr.substring(opLoc + 1), vars)
        case '*' => eval(expr.substring(0, opLoc), vars) * eval(expr.substring(opLoc + 1), vars)
        case '/' => eval(expr.substring(0, opLoc), vars) / eval(expr.substring(opLoc + 1), vars)
      }
    }
  }

  def main(args: List[String]) {
    println(eval("cos(r) + sin(r)", Map("r" -> math.Pi)))
  }
}