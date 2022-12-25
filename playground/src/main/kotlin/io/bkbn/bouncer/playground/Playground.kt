package io.bkbn.bouncer.playground

import io.bkbn.bouncer.core.rbacPolicy

enum class OrganizationRoleType {
  OWNER,
  MAINTAINER,
  GUEST
}

data class OrganizationRole(
  val userId: Int,
  val organizationId: Int,
  val role: OrganizationRoleType
)

data class Organization(
  val id: Int,
  val name: String,
)

data class User(
  val id: Int,
  val name: String,
)

enum class CrudAction {
  CREATE,
  READ,
  UPDATE,
  DELETE
}

fun main() {
  println("For easy testing, edit this file with whatever you choose :)")

  // when a user is the owner of an organization, they can do anything
  // when a user is a maintainer of an organization, they can do anything except delete
  // when a user is a guest of an organization, they can only read

  val orgRole = OrganizationRole(
    userId = 1,
    organizationId = 1,
    role = OrganizationRoleType.OWNER
  )

  val policy = rbacPolicy<User, CrudAction, OrganizationRoleType, Organization> {
    can("Owner can delete", CrudAction.DELETE, OrganizationRoleType.OWNER) { user, organization, role ->
      user.id == organization.id && orgRole.userId == user.id && orgRole.role == role
    }
  }

  println(policy.enforce(User(1, "Bob"), CrudAction.DELETE, OrganizationRoleType.OWNER, Organization(1, "Acme")))

}
