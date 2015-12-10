package particlesim

// Problems arise with NEGATIVES
// Doesn't yet support cos, sin, or sqrt
object Parser {
  def eval(expr: String, vars:Map[String,Double]): Double = {
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
        eval(expr.trim.substring(1, expr.length - 1),vars)
      } else if(vars.contains(expr)){
        vars(expr)
      } else {
        expr.toDouble
      }
    } else {
      expr(opLoc) match {
        case '+' => println(eval(expr.substring(0, opLoc),vars) + eval(expr.substring(opLoc + 1),vars))
        0
        case '-' => eval(expr.substring(0, opLoc),vars) + eval(expr.substring(opLoc + 1),vars)
        case '*' => eval(expr.substring(0, opLoc),vars) + eval(expr.substring(opLoc + 1),vars)
        case '/' => eval(expr.substring(0, opLoc),vars) + eval(expr.substring(opLoc + 1),vars)
      }

    }
  }
}