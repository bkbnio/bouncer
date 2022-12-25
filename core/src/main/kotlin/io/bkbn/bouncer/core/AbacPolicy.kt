package io.bkbn.bouncer.core

class AbacPolicy<Action : Enum<*>, Resource : Any> {

  private val rules = mutableListOf<Rule<Action, Resource>>()

  fun can(description: String, action: Action, check: (Resource) -> Boolean) {
    rules.add(Rule(description, action, check))
  }

  fun enforce(action: Action, resource: Resource): Boolean {
    return rules
      .filter { it.action == action }
      .any { it.check(resource) }
  }

  private data class Rule<Action : Enum<*>, Resource : Any>(
    val description: String,
    val action: Action,
    val check: (Resource) -> Boolean
  )
}

fun <Action : Enum<*>, Resource : Any> abacPolicy(
  init: AbacPolicy<Action, Resource>.() -> Unit
): AbacPolicy<Action, Resource> {
  val policy = AbacPolicy<Action, Resource>()
  policy.init()
  return policy
}
