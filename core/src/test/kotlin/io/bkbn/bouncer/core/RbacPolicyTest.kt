package io.bkbn.bouncer.core

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class RbacPolicyTest : DescribeSpec({
  describe("Basic RBAC Policy Tests") {
    it("Can enforce a single RBAC policy") {
      // arrange
      val org = Org(1, "Acme")
      val orgUser = OrgUser(1, "Bob", listOf(OrgRole(1, OrgRoleType.OWNER)))

      // act
      val result = simpleRbacPolicy.enforce(orgUser, CrudAction.DELETE, OrgRoleType.OWNER, org)

      // assert
      result shouldBe true
    }
  }
}) {
  companion object {
    private val simpleRbacPolicy = rbacPolicy<OrgUser, CrudAction, OrgRoleType, Org> {
      can("Owner can delete", CrudAction.DELETE, OrgRoleType.OWNER) { user, organization, role ->
        user.id == organization.id && user.roles.any { it.organizationId == organization.id && it.role == role }
      }
    }
  }
}
