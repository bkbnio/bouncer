package io.bkbn.bouncer.core

import kotlin.reflect.KClass

class Policy<E : Any, A : Any, R : Any> {

  private val rules = mutableListOf<Rule<E, A, R>>()

  fun can(description: String, action: A, resource: KClass<R>, check: (E, R) -> Boolean) {
    rules.add(Rule(description, action, resource, check))
  }

  fun enforce(entity: E, action: A, resource: R): Boolean {
    return rules
      .filter { it.action == action }
      .any { it.check(entity, resource) }
  }

  private data class Rule<E : Any, A : Any, R : Any>(
    val description: String,
    val action: A,
    val resource: KClass<R>,
    val check: (E, R) -> Boolean
  )
}

fun <E : Any, A : Any, R : Any> bouncerPolicy(init: Policy<E, A, R>.() -> Unit): Policy<E, A, R> {
  val policy = Policy<E, A, R>()
  policy.init()
  return policy
}
