package com.github.thiagosqr.scheduler.dao

import java.sql.{Connection, PreparedStatement, ResultSet, Statement}

trait DaoService {

  def getConnection(): Connection

  def executeSelect[T](sql: PreparedStatement)(f: (ResultSet) => List[T]): List[T] =
    try{
      f(sql.executeQuery())
    }finally {
      sql.close()
    }


  def readResultSet[T](rs: ResultSet)(f: ResultSet => T): List[T] =
    Iterator.continually((rs.next(), rs)).takeWhile(_._1).map {
      case(_, row) => f(rs)
    }.toList

}

trait DaoServiceComponent {
  this: DatabaseServiceComponent =>

  val daoService: DaoService

  class DaoServiceImpl extends DaoService {
    override def getConnection(): Connection = databaseService.getConnection
  }
}