package com.github.thiagosqr.scheduler.actor

import com.github.thiagosqr.scheduler.config.app.AppConfigComponent
import com.github.thiagosqr.scheduler.dao.DaoServiceComponent

trait ActorFactory {
  def createMasterActor(): Master
  def createWorkerActor(): Worker
}

trait ActorFactoryComponent {
  this: AppConfigComponent
    with DaoServiceComponent =>

  val actorFactory: ActorFactory

  class ActorFactoryImpl extends ActorFactory {
    override def createMasterActor(): Master = new Master(appConfigService.workers, this)
    override def createWorkerActor(): Worker = new Worker(daoService)
  }


}