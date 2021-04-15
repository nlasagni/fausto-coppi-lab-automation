package it.unibo.lss.fcla.athletictraining.adapter.presenter

/**
 * The output port of a presenter component.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface PresenterOutput {

    /**
     * Renders of the [viewModel] specified.
     */
    fun renderViewModel(viewModel: ViewModel)
}
