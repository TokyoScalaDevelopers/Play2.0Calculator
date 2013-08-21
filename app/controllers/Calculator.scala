package controllers

import play.api._
import play.api.mvc._

object Calculator extends Controller {
  def index = Action {
    Ok(views.html.calculatorIndex("Please enter an equation to evaluate!"))
  }

  def calculate = Action {
    Ok(views.html.calculatorIndex("Received POST"))
  }
}
