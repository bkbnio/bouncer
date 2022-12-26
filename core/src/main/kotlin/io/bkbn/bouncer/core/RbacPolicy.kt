package io.bkbn.bouncer.core

import kotlin.reflect.KClass

class RbacPolicy<Actor : Any, Action : Enum<*>, Role : Enum<*>, Resource : Any> : BouncerPolicy {

  private val rules = mutableListOf<Rule<Actor, Action, Role, Resource>>()

  fun can(description: String, action: Action, role: Role, check: (Actor, Resource, Role) -> Boolean) {
    rules.add(Rule(description, action, role, check))
  }

  fun enforce(actor: Actor, action: Action, role: Role, resource: Resource): Boolean {
    return rules
      .filter { it.action == action }
      .filter { it.role == role }
      .any { it.check(actor, resource, role) }
  }

  private data class Rule<Actor : Any, Action : Enum<*>, Role : Enum<*>, Resource : Any>(
    val description: String,
    val action: Action,
    val role: Role,
    val check: (Actor, Resource, Role) -> Boolean
  )

}

fun <Actor : Any, Action : Enum<*>, Role : Enum<*>, Resource : Any> rbacPolicy(
  init: RbacPolicy<Actor, Action, Role, Resource>.() -> Unit
): RbacPolicy<Actor, Action, Role, Resource> {
  val policy = RbacPolicy<Actor, Action, Role, Resource>()
  policy.init()
  return policy
}
