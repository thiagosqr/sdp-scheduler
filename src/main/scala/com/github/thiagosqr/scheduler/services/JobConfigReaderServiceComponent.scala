package com.github.thiagosqr.scheduler.services

import java.io.File

import com.github.thiagosqr.scheduler.config.app.AppConfigComponent
import com.github.thiagosqr.scheduler.config.job.{JobConfig, JobFrequencySerializer, JobTypeSerializer}
import com.github.thiagosqr.scheduler.io.IOServiceComponent
import com.typesafe.scalalogging.LazyLogging
import org.json4s.jackson.JsonMethods
import org.json4s.{DefaultFormats, FileInput}

trait JobConfigReaderServiceComponent {
  this: AppConfigComponent with IOServiceComponent =>

  val jobConfigReaderService: JobConfigReaderService

  class JobConfigReaderService() extends LazyLogging {
    private val customSerializer = List(
      JobFrequencySerializer,
      JobTypeSerializer
    )

    implicit val formats = DefaultFormats ++ customSerializer + JobConfig.jobConfigFieldSerializer

    def readJobConfigs(): List[JobConfig] = ioService
      .getAllFilesWithExtension(appConfigService.configPath, appConfigService.configExtension)
      .flatMap{
        case path => try {
          val config = JsonMethods.parse(FileInput(new File(path))).extract[JobConfig]
          Some(config)
        }catch {
          case ex: Throwable =>
            logger.error("Error reading config: {}", path, ex)
            None
        }

      }
  }
}
