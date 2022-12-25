package io.bkbn.bouncer.core

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class AbacPolicyTest : DescribeSpec({
  describe("Basic ABAC Policy Tests") {
    it("Can enforce a single ABAC policy") {
      // arrange
      val publicRepo = Repository("bkbnio/bouncer", true)
      val privateRepo = Repository("bkbnio/bouncer", false)

      // act
      val publicResult = simpleAbacPolicy.enforce(CrudAction.READ, publicRepo)
      val privateResult = simpleAbacPolicy.enforce(CrudAction.READ, privateRepo)

      // assert
      publicResult shouldBe true
      privateResult shouldBe false
    }
  }
}) {
  companion object {
    private val simpleAbacPolicy = abacPolicy<CrudAction, Repository> {
      can("Always read public repositories", CrudAction.READ) { resource -> resource.isPublic }
    }
  }
}
