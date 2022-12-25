package io.bkbn.bouncer.core

import kotlin.reflect.KClass

class Policy<Actor : Any, Action : Enum<*>, Resource : Any> {

  private val rules = mutableListOf<Rule<Actor, Action, Resource>>()

  fun can(description: String, action: Action, check: (Actor, Resource) -> Boolean) {
    rules.add(Rule(description, action, check))
  }

  fun enforce(entity: Actor, action: Action, resource: Resource): Boolean {
    return rules
      .filter { it.action == action }
      .any { it.check(entity, resource) }
  }

  private data class Rule<Actor : Any, Action : Enum<*>, Resource : Any>(
    val description: String,
    val action: Action,
    val check: (Actor, Resource) -> Boolean
  )
}

fun <Actor : Any, Action : Enum<*>, Resource : Any> bouncerPolicy(
  init: Policy<Actor, Action, Resource>.() -> Unit
): Policy<Actor, Action, Resource> {
  val policy = Policy<Actor, Action, Resource>()
  policy.init()
  return policy
}
