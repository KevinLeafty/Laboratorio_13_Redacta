package mx.tec.redacta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import mx.tec.redacta.domain.EstadoModelo
import mx.tec.redacta.ui.screens.RedactaScreen
import mx.tec.redacta.ui.screens.SinModeloScreen
import mx.tec.redacta.ui.state.RedactaViewModel
import mx.tec.redacta.ui.theme.RedactaTheme

/** `@AndroidEntryPoint`: sin esto, `hiltViewModel()` no tiene de dónde sacar el ViewModel. */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedactaTheme {
                val viewModel: RedactaViewModel = hiltViewModel()
                val estado by viewModel.ui.collectAsStateWithLifecycle()
                when (val modelo = estado.modelo) {
                    is EstadoModelo.Falta, is EstadoModelo.Error -> SinModeloScreen(modelo, onBuscar = viewModel::buscarModelo)
                    else -> RedactaScreen(
                        estado = estado,
                        onTexto = viewModel::alCambiarTexto,
                        onTarea = viewModel::elegirTarea,
                        onRedactar = viewModel::redactar,
                        onDetener = viewModel::detener,
                        onUsar = viewModel::usarRespuesta,
                    )
                }
            }
        }
    }
}
