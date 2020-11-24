package io.janstenpickle.trace4cats.http4s.server

import cats.effect.IO
import cats.{~>, Id}
import io.janstenpickle.trace4cats.Span
import io.janstenpickle.trace4cats.inject.Spanned
import syntax._

import scala.concurrent.ExecutionContext

class ServerSyntaxSpec
    extends BaseServerTracerSpec[IO, Spanned[IO, *]](
      9082,
      λ[IO ~> Id](_.unsafeRunSync()),
      λ[Spanned[IO, *] ~> IO](ga => Span.noop[IO].use(ga.run)),
      (routes, filter, ep) => routes.inject(ep, requestFilter = filter),
      (app, filter, ep) => app.inject(ep, requestFilter = filter),
      IO.timer(ExecutionContext.global)
    )
