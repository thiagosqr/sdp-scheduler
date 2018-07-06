package com.github.thiagosqr.scheduler.registry

import com.github.thiagosqr.scheduler.actor.{ActorFactory, ActorFactoryComponent}
import com.github.thiagosqr.scheduler.config.app.AppConfigComponent
import com.github.thiagosqr.scheduler.dao._
import com.github.thiagosqr.scheduler.io.IOServiceComponent
import com.github.thiagosqr.scheduler.services.JobConfigReaderServiceComponent

object ComponentRegistry extends AppConfigComponent
  with IOServiceComponent
  with JobConfigReaderServiceComponent
  with DatabaseServiceComponent
  with MigrationComponent
  with DaoServiceComponent
  with ActorFactoryComponent {

  override val appConfigService: ComponentRegistry.AppConfigService = new AppConfigService
  override val ioService: ComponentRegistry.IOService = new IOService
  override val jobConfigReaderService: ComponentRegistry.JobConfigReaderService = new JobConfigReaderService
  override val databaseService: DatabaseService = new H2DatabaseService
  override val migrationService: ComponentRegistry.MigrationService = new MigrationService
  override val daoService: DaoService = new DaoServiceImpl
  override val actorFactory: ActorFactory = new ActorFactoryImpl
}