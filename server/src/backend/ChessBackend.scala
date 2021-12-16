package backend

import java.util.UUID
import upickle.default.{ReadWriter => RW, macroRW}
case class User(id: UserId, name: String)
case class Moves(ms: Seq[String], cs: Seq[Moves])
object Moves {
  implicit val rw: RW[Moves] = macroRW
}
case class Training(id: TrainingId, moves: Moves)
object Training {
  implicit val rw: RW[Training] = macroRW
}

type UserId = String
type TrainingId = String

trait WithId

trait ChessBackend:
  def createTraining(mvs: Moves): TrainingId
  def updateTraining(id: TrainingId, t: Training): Boolean
  def getTraining(tid: TrainingId): Option[Training]
  def deleteTraining(tid: TrainingId): Boolean
  def listTrainings(): Seq[Training]
  protected def createId(): String =
    UUID.randomUUID.toString()

class InMemoryChessBackend extends backend.ChessBackend:
  var ts = Map[TrainingId, Training]()
  def updateTraining(id: TrainingId, t: Training): Boolean =
    if ts.get(id).isDefined then
      ts = ts + (id -> t)
      true
    else
      false

  def createTraining(mvs: Moves): TrainingId =
    val tid: TrainingId = createId()
    ts = ts + (tid -> Training(tid, mvs))
    tid

  def getTraining(tid: TrainingId): Option[Training] =
    ts.get(tid)
    
  def deleteTraining(tid: TrainingId): Boolean =
    if ts.get(tid).isDefined then
      ts = ts - tid
      true
    else
      false


  def listTrainings(): Seq[Training] = 
    ts.valuesIterator.toList