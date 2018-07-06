package com.github.thiagosqr.scheduler.actor

import sys.process._
import akka.actor.Actor
import com.github.thiagosqr.scheduler.config.job.Sql
import com.github.thiagosqr.scheduler.config.job.Console
import com.github.thiagosqr.scheduler.dao.DaoService
import com.github.thiagosqr.scheduler.messages.{Done, Work}
import com.typesafe.scalalogging.LazyLogging

class Worker(daoService: DaoService) extends Actor with LazyLogging{

  override def receive: Receive = {
    case w @ Work(name, command, jobType) => doWork(w)
  }

  private def doWork(work: Work): Unit = {

    val r = work.jobType match {
      case Console =>

        System.out.println("Console Command running on Thread:" + Thread.currentThread().getName)
        val result = work.command.!
        result == 0
      case Sql =>

        System.out.println("Sql Command running on Thread:" + Thread.currentThread().getName)
        val connection = daoService.getConnection()
        try {
          val statement = connection.prepareStatement(work.command)
          val result: List[String] = daoService.executeSelect(statement) {
            case rs =>
              val numClumns = rs.getMetaData.getColumnCount
              daoService.readResultSet(rs) {
                case row =>
                  (1 to numClumns).map {
                    case i => row.getObject(i)
                  }.mkString("\t")
              }
          }
          logger.info("Sql query results: ")
          result.foreach(r => logger.info(r))
          true
        }finally {
          connection.close()
        }
    }

    sender ! Done(work.name, work.command, work.jobType, r)
  }
}
