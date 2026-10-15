package mx.tec.redacta.ui.screens

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mx.tec.redacta.R
import mx.tec.redacta.domain.EstadoModelo
import mx.tec.redacta.ui.theme.RedactaTema
import mx.tec.redacta.ui.theme.RedactaTheme

/** El comando que copia el modelo a la carpeta de la app. Se escribe en la terminal, no aquí. */
const val COMANDO_ADB =
    "adb push gemma3-1b-it-int4.litertlm /sdcard/Android/data/mx.tec.redacta/files/"

/**
 * Cuando no hay modelo con qué trabajar: no está el archivo, o está pero no
 * cargó. En vez de un error genérico, dice exactamente qué hacer.
 */
@Composable
fun SinModeloScreen(estado: EstadoModelo, onBuscar: () -> Unit) {
    val c = MaterialTheme.colorScheme
    val e = RedactaTema.espaciado
    val (titulo, explicacion) = when (estado) {
        is EstadoModelo.Error ->
            ("El modelo no cargó" to ("Encontré el archivo, pero no se pudo cargar. Si no pesa 584 MB, " +
                "la descarga quedó incompleta. Cópialo otra vez, desde la carpeta donde lo descargaste:"))
        else ->
            ("Falta el modelo" to ("Redacta no trae a Gemma adentro: pesa 584 MB. " +
                "Cópialo a la app desde la terminal, en la carpeta donde lo descargaste:"))
    }
    val portapapeles = LocalClipboard.current
    val alcance = rememberCoroutineScope()
    Surface(color = c.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(e.xxl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(Modifier.size(104.dp).background(c.tertiaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                Icon(painterResource(R.drawable.ic_descarga), contentDescription = null, tint = c.onTertiaryContainer, modifier = Modifier.size(52.dp))
            }
            Spacer(Modifier.height(e.xl))
            Text(titulo, style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
            Spacer(Modifier.height(e.sm))
            Text(explicacion, style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant, textAlign = TextAlign.Center)
            Spacer(Modifier.height(e.lg))
            Surface(shape = MaterialTheme.shapes.large, color = c.surfaceContainerHighest, modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(start = e.lg, top = e.sm, bottom = e.sm, end = e.xs), verticalAlignment = Alignment.CenterVertically) {
                    Text(COMANDO_ADB, style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            alcance.launch { portapapeles.setClipEntry(ClipData.newPlainText("Comando", COMANDO_ADB).toClipEntry()) }
                        },
                    ) {
                        Icon(painterResource(R.drawable.ic_copiar), contentDescription = "Copiar el comando")
                    }
                }
            }
            if (estado is EstadoModelo.Error) {
                Spacer(Modifier.height(e.sm))
                Text(
                    estado.mensaje,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    color = c.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (estado is EstadoModelo.Falta) {
                Spacer(Modifier.height(e.sm))
                Text(
                    "La app lo busca en ${estado.ruta}",
                    style = MaterialTheme.typography.bodySmall,
                    color = c.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.height(e.xl))
            Button(onClick = onBuscar, modifier = Modifier.fillMaxWidth().height(56.dp), shape = CircleShape) {
                Text("Ya lo copié: buscar otra vez", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
private fun SinModeloPreview() {
    RedactaTheme {
        SinModeloScreen(
            EstadoModelo.Falta("/storage/emulated/0/Android/data/mx.tec.redacta/files/gemma3-1b-it-int4.litertlm"),
        ) {}
    }
}
