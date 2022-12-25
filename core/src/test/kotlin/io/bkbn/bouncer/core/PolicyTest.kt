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
  describe("RBAC Policy Tests") {
    it("Can enforce a basic RBAC Policy") {
      // arrange
      val org = Org(1, "Acme")
      val orgUser = OrgUser(1, "Bob", listOf(OrgRole(1, OrgRoleType.OWNER)))

      // act
      val result = simpleRbacPolicy.enforce(orgUser, CrudAction.DELETE, OrgRoleType.OWNER, org)

      // assert
      result shouldBe true
    }
  }
  describe("ABAC Policy Tests") {
    it("Can enforce a basic ABAC policy") {
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
    private val adminOnlyPolicy = bouncerPolicy<User, CrudAction, Repository> {
      can(
        description = "Admins can always create repositories",
        action = CrudAction.CREATE,
        check = { user, _ -> user.roles.contains("admin") }
      )
    }

    private val simpleRbacPolicy = rbacPolicy<OrgUser, CrudAction, OrgRoleType, Org> {
      can("Owner can delete", CrudAction.DELETE, OrgRoleType.OWNER) { user, organization, role ->
        user.id == organization.id && user.roles.any { it.organizationId == organization.id && it.role == role }
      }
    }

    private val simpleAbacPolicy = abacPolicy<CrudAction, Repository> {
      can("Always read public repositories", CrudAction.READ) { resource -> resource.isPublic }
    }
  }
}
