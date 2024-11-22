package com.andreste.fetch

import com.andreste.fetch.models.HiringItem
import com.andreste.fetch.repositories.HiringRepository
import com.andreste.fetch.viewmodels.HiringViewModel
import com.andreste.fetch.viewmodels.ViewState
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@FlowPreview
@ExperimentalCoroutinesApi
class HiringViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val hiringRepository: HiringRepository = mockk<HiringRepository>()
    private lateinit var viewModel: HiringViewModel

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        viewModel = HiringViewModel(hiringRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getItems sets state to Content when repository returns items`() = testScope.runTest {
        // Arrange
        val mockItems = listOf(
            HiringItem(
                id = 1,
                listId = 1,
                name = "First item"
            ),
            HiringItem(
                id = 2,
                listId = 2,
                name = "Second item"
            )
        )
        val mockResponse = mockRetrofitResponse(mockItems)
        coEvery { hiringRepository.getItems() } returns mockResponse

        assert(viewModel.state.value is ViewState.Loading)

        // Act
        viewModel.getItems()

        // Assert
        assert((viewModel.state.value as ViewState.Content).list.size == 2)
        assert((viewModel.state.value as ViewState.Content).list[0].name == "First item")
        assert((viewModel.state.value as ViewState.Content).list[1].name == "Second item")
    }

    @Test
    fun `getItems sets state to Content when repository returns items, but filters out null names`() = testScope.runTest {
        // Arrange
        val mockItems = listOf(
            HiringItem(
                id = 1,
                listId = 1,
                name = ""
            ),
            HiringItem(
                id = 2,
                listId = 2,
                name = "Second item"
            )
        )
        val mockResponse = mockRetrofitResponse(mockItems)
        coEvery { hiringRepository.getItems() } returns mockResponse

        assert(viewModel.state.value is ViewState.Loading)

        // Act
        viewModel.getItems()

        // Assert
        assert((viewModel.state.value as ViewState.Content).list.size == 1)
        assert((viewModel.state.value as ViewState.Content).list[0].name == "Second item")
    }

    @Test
    fun `getItems sets state to Error when repository returns error`() = testScope.runTest {
        // Arrange
        coEvery { hiringRepository.getItems() } returns mockErrorResponse()

        // Act
        viewModel.getItems()

        // Assert
        assert(viewModel.state.value is ViewState.Error)
        assert((viewModel.state.value as ViewState.Error).message == "Could not get items")
    }

    private fun mockRetrofitResponse(items: List<HiringItem>): Response<List<HiringItem>> {
        return Response.success(items)
    }

    private fun mockErrorResponse(): Response<List<HiringItem>> {
        return Response.error(500, "Error".toResponseBody())
    }
}