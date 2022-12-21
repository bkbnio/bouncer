package io.bkbn.bouncer.core

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PolicyTest : DescribeSpec({
  describe("Basic Policy Tests") {
    it("Can enforce a basic policy") {
      // arrange
      val user = User("bkbnio", listOf("admin"))
      val repo = Repository("bkbnio/bouncer")

      // act
      val result = adminOnlyPolicy.enforce(user, CrudAction.CREATE, repo)

      // assert
      result shouldBe true
    }
    it("Denies a user when no rule is found") {
      // arrange
      val user = User("not-bkbnio", listOf("scrub"))
      val repo = Repository("bkbnio/bouncer")

      // act
      val result = adminOnlyPolicy.enforce(user, CrudAction.CREATE, repo)

      // assert
      result shouldBe false
    }
    it("Only applies rules with the matching action") {
      // arrange
      val user = User("bkbnio", listOf("admin"))
      val repo = Repository("bkbnio/bouncer")

      // act
      val result = adminOnlyPolicy.enforce(user, CrudAction.READ, repo)

      // assert
      result shouldBe false
    }
  }
}) {
  companion object {
    private val adminOnlyPolicy = bouncerPolicy<User, CrudAction, Repository> {
      can(
        description = "Admins can always create repositories",
        action = CrudAction.CREATE,
        resource = Repository::class,
        check = { user, _ -> user.roles.contains("admin") }
      )
    }
  }
}
