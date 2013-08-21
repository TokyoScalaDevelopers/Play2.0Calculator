package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import com.github.TokyoScalaDevelopers.CalculatorParser.{CalculatorParser, CalculatorEvaluator,Number}

case class CalculatorEquation(equation: String)

object Calculator extends Controller {
  val calculatorForm = Form(
    mapping(
      "equation" -> nonEmptyText
    )(CalculatorEquation.apply)(CalculatorEquation.unapply)
  )

  def index = Action {
    Ok(views.html.calculatorIndex("Please enter an equation to evaluate!", calculatorForm))
  }

  def calculateResult(equation: String): Option[String] = {
    CalculatorParser.parseExpression(equation).flatMap(CalculatorEvaluator.evaluate).map({ case Number(num) =>
      "%s: %s".format(equation, num.toString)
    })
  }

  def calculate = Action { implicit request =>
    calculatorForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.calculatorIndex("Invalid equation", formWithErrors)),
      wrappedEquation => calculateResult(wrappedEquation.equation) match {
        case Some(result) => Ok(views.html.calculatorIndex(result, calculatorForm))
        case None => BadRequest(views.html.calculatorIndex("Unable to evaluate expression", calculatorForm))
      }
    )
  }
}
