import backend.InMemoryChessBackend
import backend.Moves
case class ChessRoutes()(implicit cc: castor.Context, log: cask.Logger)
    extends cask.Routes {
  val chessBackend = new InMemoryChessBackend

  @cask.get("/chess")
  def listTrainings() = {
    val list = chessBackend.listTrainings()
    ujson.Obj(
      "trainings" -> upickle.default.writeJs(list)
    )
  }

  @cask.getJson("/chess/:id")
  def getTraining(id: String) = {
    val t = chessBackend.getTraining(id).get
    upickle.default.writeJs(t)
  }
  /*  */
  @cask.postJson("/chess")
  def createTraining(move: ujson.Value) = {
    val id = chessBackend.createTraining(Moves(Seq(move.str), Seq()))
    ujson.Obj("id" -> id)
  }

  @cask.postJson("/chess/:id/move")
  def updateTraining(id: String, move: ujson.Value) = {
    val updated = chessBackend
      .getTraining(id)
      .get
      .copy(moves = Moves(Seq(move.str), Seq()))
    val b = chessBackend.updateTraining(id, updated)
    ujson.Obj("updated" -> b)
  }

  initialize()
}
