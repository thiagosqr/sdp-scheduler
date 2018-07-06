package com.github.thiagosqr.scheduler.config.job

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

import org.json4s.FieldSerializer
import org.json4s.JsonAST.JField

import scala.concurrent.duration.{Duration, FiniteDuration}

case class JobConfig(name: String, command: String, jobType: JobType,
                     frequency: JobFrequency, timeOptions: TimeOptions)

object JobConfig {
  val jobConfigFieldSerializer = FieldSerializer[JobConfig](
    {
      case ("timeOptions", x) => Some("time_options", x)
      case ("jobType", x) => Some("type", x)
    },
    {
      case JField("time_options", x) => JField("timeOptions", x)
      case JField("type", x) => JField("jobType", x)
    })
}
