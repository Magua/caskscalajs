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
  @cask.delete("chess/:id")
  def deleteTraining(id: String) = {
    val b = chessBackend.deleteTraining(id)
    ujson.Obj("deleted" -> b)
  }
  /*  */
  @cask.postJson("/chess")
  def createTraining(name: ujson.Value, moves: ujson.Value) = {
    val ms = moves.str.split(",").map(_.trim).toSeq
    val id = chessBackend.createTraining(name.str, Moves(ms))
    ujson.Obj("id" -> id)
  }
  
  @cask.postJson("/chess/:id")
  def updateTraining(id: String, name: ujson.Value, moves: ujson.Value) = {
    val ms = moves.str.split(",").map(_.trim).toSeq
    val updated = chessBackend
      .getTraining(id)
      .get
      .copy(moves = Moves(ms), name = name.str)
    val b = chessBackend.updateTraining(id, updated)
    ujson.Obj("updated" -> b)
  }

  initialize()
}
