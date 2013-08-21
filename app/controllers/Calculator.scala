package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

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

  def calculate = Action { implicit request =>
    calculatorForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.calculatorIndex("Invalid equation", formWithErrors)),
      wrappedEquation => Ok(views.html.calculatorIndex("Found equation: " + wrappedEquation.equation, calculatorForm))
    )
  }
}
