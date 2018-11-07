package tp.chat

import java.time.{LocalDateTime, ZoneOffset}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  *
  * @author Tomas Perez Molina
  */
case class Subscribe(name: String)
case object Unsubscribe
case class AddedMember(name: String)
case class RemovedMember(name: String)
case class NewMessage(msg: Message)
case class SendMessage(content: String, sent: LocalDateTime)
case class Message(member: String, content: String, sent: LocalDateTime)

case class SubscribeTo(room: ActorRef)
case class UnsubscribeFrom(room: ActorRef)
case class SendTo(room: ActorRef, content: String)
case object GetMessages
case class UserMessages(msgs: List[Message])

class ChatRoom extends Actor {
  override def receive: Receive = onMessage(Map.empty)

  private def onMessage(members: Map[ActorRef, String]): Receive = {

    case Subscribe(name) =>
      members.foreach {
        case (ref, _) => ref ! AddedMember(name)
      }
      context.become(onMessage(members + (sender() -> name)))

    case Unsubscribe =>
      val senderName: Option[String] = members.get(sender())
      senderName.foreach { name =>
        context.become(onMessage(members - sender()))
        members.foreach {
          case (ref, _) => ref ! RemovedMember(name)
        }
      }

    case SendMessage(content, time) =>
      val senderName: Option[String] = members.get(sender())
      senderName.foreach { name =>
        members.foreach {
          case (ref, _) => ref ! NewMessage(Message(name, content, time))
        }
      }

  }
}

class ChatUser(val name: String) extends Actor {
  implicit val localDateOrdering: Ordering[LocalDateTime] = Ordering.by(- _.toEpochSecond(ZoneOffset.UTC))

  override def receive: Receive = onMessage(List.empty)

  private def onMessage(messages: List[Message]): Receive = {
    case SubscribeTo(room) => room ! Subscribe(name)
    case UnsubscribeFrom(room) => room ! Unsubscribe
    case SendTo(room, content) => room ! SendMessage(content, LocalDateTime.now())
    case NewMessage(msg) => context.become(onMessage(msg :: messages))
    case GetMessages => sender() ! UserMessages(messages.sortBy( _.sent))
  }
}

class ChatPrinter extends Actor {
  override def receive: Receive = {
    case UserMessages(msgs) => msgs.foreach { msg =>
      println(s"${msg.member}: ${msg.content} - ${msg.sent}")
    }
  }
}

object ChatApp extends App {
  val system = ActorSystem("mySystem")
  val room = system.actorOf(Props[ChatRoom], "chatRoom")
  val johnny = system.actorOf(Props(new ChatUser("Johnny")), "johnny")
  val papa = system.actorOf(Props(new ChatUser("Papa")), "papa")
  val printer = system.actorOf(Props[ChatPrinter], "printer")

  johnny ! SubscribeTo(room)
  papa ! SubscribeTo(room)
  papa ! SendTo(room, "johhny johnny")
  johnny ! SendTo(room, "yes papa?")
  papa ! SendTo(room, "telling lies?")
  johnny ! SendTo(room, "no papa")
  johnny ! UnsubscribeFrom(room)
  johnny.tell(GetMessages, printer)
}
