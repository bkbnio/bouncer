package io.bkbn.bouncer.core

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class AbacPolicyTest : DescribeSpec({
  describe("Basic ABAC Policy Tests") {
    it("Can enforce a single ABAC policy") {
      // arrange
      val publicRepo = Repository("bkbnio/bouncer", true)
      val privateRepo = Repository("bkbnio/bouncer", false)
      val user = User("Mr. Man")

      // act
      val publicResult = simplePolicy.enforce(user, CrudAction.READ, publicRepo)
      val privateResult = simplePolicy.enforce(user, CrudAction.READ, privateRepo)

      // assert
      publicResult shouldBe true
      privateResult shouldBe false
    }
    it("Can enforce that a user can only modify itself") {
      // arrange
      val userA = User("Mr. Man")
      val userB = User("Mr. Woman")

      // act
      val userAResult = userPolicy.enforce(userA, CrudAction.UPDATE, userA)
      val userBResult = userPolicy.enforce(userB, CrudAction.UPDATE, userA)

      // assert
      userAResult shouldBe true
      userBResult shouldBe false
    }
    it("Can handle a void actor") {
      // arrange
      val publicRepo = Repository("bkbnio/bouncer", true)
      val privateRepo = Repository("bkbnio/bouncer", false)

      // act
      val publicResult = voidUserPolicy.enforce(Unit, CrudAction.READ, publicRepo)
      val privateResult = voidUserPolicy.enforce(Unit, CrudAction.READ, privateRepo)

      // assert
      publicResult shouldBe true
      privateResult shouldBe false
    }
  }
}) {
  companion object {
    private val simplePolicy = abacPolicy<User, CrudAction, Repository> {
      can("Always read public repositories", CrudAction.READ) { _, resource -> resource.isPublic }
    }

    private val userPolicy = abacPolicy<User, CrudAction, User> {
      can("Only modify yourself", CrudAction.UPDATE) { actor, resource -> actor.id == resource.id }
    }

    private val voidUserPolicy = abacPolicy<Unit, CrudAction, Repository> {
      can("Read public repositories", CrudAction.READ) { _, resource -> resource.isPublic }
    }
  }
}
