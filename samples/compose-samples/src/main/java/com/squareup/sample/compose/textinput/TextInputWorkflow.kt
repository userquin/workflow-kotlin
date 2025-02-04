package com.squareup.sample.compose.textinput

import com.squareup.sample.compose.textinput.TextInputWorkflow.Rendering
import com.squareup.sample.compose.textinput.TextInputWorkflow.State
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action

object TextInputWorkflow : StatefulWorkflow<Unit, State, Nothing, Rendering>() {

  data class State(
    val textA: String = "",
    val textB: String = "",
    val showingTextA: Boolean = true
  )

  data class Rendering(
    val text: String,
    val onTextChanged: (String) -> Unit,
    val onSwapText: () -> Unit
  )

  private val swapText = action {
    state = state.copy(showingTextA = !state.showingTextA)
  }

  private fun changeText(text: String) = action {
    state = if (state.showingTextA) {
      state.copy(textA = text)
    } else {
      state.copy(textB = text)
    }
  }

  override fun initialState(
    props: Unit,
    snapshot: Snapshot?
  ): State = State()

  override fun render(
    renderProps: Unit,
    renderState: State,
    context: RenderContext
  ): Rendering = Rendering(
    text = if (renderState.showingTextA) renderState.textA else renderState.textB,
    onTextChanged = { context.actionSink.send(changeText(it)) },
    onSwapText = { context.actionSink.send(swapText) }
  )

  override fun snapshotState(state: State): Snapshot? = null
}
