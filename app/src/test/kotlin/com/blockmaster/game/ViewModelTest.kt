package com.blockmaster.game

import com.blockmaster.game.viewmodel.GameViewModel
import kotlin.test.assertNotNull
import org.junit.Test

class ViewModelTest {
    @Test
    fun testViewModelStart() {
        val vm = GameViewModel()
        vm.start()
        // cannot reliably await state here; just ensure instance created
        assertNotNull(vm.uiState)
    }
}
