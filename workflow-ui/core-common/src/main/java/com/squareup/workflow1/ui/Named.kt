package com.squareup.workflow1.ui

/**
 * Allows renderings that do not implement [Compatible] themselves to be distinguished
 * by more than just their type. Instances are [compatible] if they have the same name
 * and have [compatible] [wrapped] fields.
 */
@Deprecated("Use an implementation of NamedCompatible")
@WorkflowUiExperimentalApi
data class Named<W : Any>(
  val wrapped: W,
  val name: String
) : Compatible {
  init {
    require(name.isNotBlank()) { "name must not be blank." }
  }

  override val compatibilityKey: String = keyFor(wrapped, name)

  override fun toString(): String {
    return "${super.toString()}: $compatibilityKey"
  }

  companion object {
    /**
     * Calculates the [Named.compatibilityKey] for a given [value] and [name].
     */
    fun keyFor(
      value: Any,
      name: String = ""
    ): String {
      return ((value as? Compatible)?.compatibilityKey ?: value::class.java.name) + "-Named($name)"
    }
  }
}