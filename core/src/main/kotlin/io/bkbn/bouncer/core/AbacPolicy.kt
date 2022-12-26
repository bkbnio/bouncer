package io.bkbn.bouncer.core

class AbacPolicy<Actor: Any, Action : Enum<*>, Resource : Any> {

  private val rules = mutableListOf<Rule<Actor, Action, Resource>>()

  fun can(description: String, action: Action, check: (Actor, Resource) -> Boolean) {
    rules.add(Rule(description, action, check))
  }

  fun enforce(actor: Actor, action: Action, resource: Resource): Boolean {
    return rules
      .filter { it.action == action }
      .any { it.check(actor, resource) }
  }

  private data class Rule<Actor: Any, Action : Enum<*>, Resource : Any>(
    val description: String,
    val action: Action,
    val check: (Actor, Resource) -> Boolean
  )
}

fun <Actor: Any, Action : Enum<*>, Resource : Any> abacPolicy(
  init: AbacPolicy<Actor, Action, Resource>.() -> Unit
): AbacPolicy<Actor, Action, Resource> {
  val policy = AbacPolicy<Actor, Action, Resource>()
  policy.init()
  return policy
}
