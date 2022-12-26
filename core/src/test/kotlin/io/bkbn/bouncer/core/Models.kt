package io.bkbn.bouncer.core

import java.util.UUID

enum class CrudAction {
  CREATE,
  READ,
  UPDATE,
  DELETE
}

data class Repository(val name: String, val isPublic: Boolean = false)

data class User(val name: String, val roles: List<String> = emptyList(), val id: UUID = UUID.randomUUID())

enum class OrgRoleType {
  OWNER,
  MAINTAINER,
  GUEST
}

data class OrgRole(
  val organizationId: Int,
  val role: OrgRoleType
)

data class Org(
  val id: Int,
  val name: String,
)

data class OrgUser(
  val id: Int,
  val name: String,
  val roles: List<OrgRole>
)
