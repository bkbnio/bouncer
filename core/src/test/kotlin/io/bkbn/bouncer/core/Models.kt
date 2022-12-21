package io.bkbn.bouncer.core

enum class CrudAction {
  CREATE,
  READ,
  UPDATE,
  DELETE
}

data class Repository(val name: String)

data class User(val name: String, val roles: List<String>)
