package com.github.thiagosqr.scheduler.messages

import com.github.thiagosqr.scheduler.config.job.{JobConfig, JobType}

sealed trait SchedulerMessage
case class Work(name: String, command: String, jobType: JobType)
case class Done(name: String, command: String, jobType: JobType, success: Boolean)
case class Schedule(configs: List[JobConfig])
